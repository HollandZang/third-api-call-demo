package com.holland.demo.WSXxgkVin;

import com.holland.demo.WSXxgkVin.generate.WSXxgkVin;
import com.holland.demo.WSXxgkVin.generate.WSXxgkVinSoap;
import com.holland.demo.util.Action;
import com.holland.net.Net;
import com.holland.net.common.PairBuilder;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class XSXxgkVinServiceHelper {

    private final WSXxgkVinSoap wsXxgkVinService = new WSXxgkVin().getWSXxgkVinSoap();

    private final String manufid;
    private final String password;
//    private static int i = 0;

    private String key;
    private long key_create_time;
    private LoginResponse cacheLogin;

    public XSXxgkVinServiceHelper(final String manufid, final String password) {
        final LoginResponse login = login(manufid, password, System.currentTimeMillis());
        if (!login.succeed) {
            System.err.println("Can't connect to 'XSXxgkVin', please check your XSXxgkVin-Account!");
//            throw new RuntimeException("Can't connect to 'XSXxgkVin', please check your XSXxgkVin-Account!");
        }

        this.manufid = manufid;
        this.password = password;
    }

    /**
     * 登录中心服务接口，成功返回临时认证码32位KEY码，超时则需重新登录
     * 输入参数：manufid = 企业编号(机构代码)，不能为空；password = 报送密码；返回数据：一个标准的XML字符串
     * <p>
     * a、调用成功
     * <result><succeed>true</succeed>
     * <data>HHVESGTOTHLDUVUMMCQUNCPURNSFQLTW</data>
     * </result>
     * <p>
     * b、调用失败
     * <result><succeed>false</succeed><data/>密码错误</data></result>
     * <result><succeed>false</succeed><data>用户或密码错误</data></result>
     */
    private synchronized LoginResponse login(final String manufid, final String password, final long callTime) {
        final boolean isCache = cacheLogin != null && callTime <= key_create_time + 50;
//        System.out.println(String.format("callTime:[%d]\tkey_time:[%d]\tisCache:[%b]", callTime, key_create_time, isCache));
        if (isCache)
            return cacheLogin;

        final String res = wsXxgkVinService.login(manufid, password);
//        final String res = "<result><succeed>succeed</succeed><data>" + ++i + "</data></result>";
        System.out.println("Invoke 'WSXxgkVin.login' api, result is [" + res + "]");
        final LoginResponse response = new LoginResponse(res);
        if (response.succeed) {
            key = response.data;
            cacheLogin = response;
        }
        key_create_time = System.currentTimeMillis();
        return response;
    }

    /**
     * 查询已报送VIN数
     * 输入参数： key = 临时认证码，不能为空；xshzh = 型式核准号；返回数据：一个标准的XML字符串
     */
    public GetVinCountByXxgkhResponse getVinCountByXxgkh(final String xxgkh) {
        return retryAction(
                () -> new GetVinCountByXxgkhResponse(wsXxgkVinService.getVinCountByXxgkh(key, xxgkh))
                , response -> response.succeed);
    }

    /**
     * 查询已报送VIN总数通过报送日期
     * 输入参数： key = 临时认证码，不能为空；dtFrom = 报送起始时间；dtTo = 报送截至时间；时间格式 YYYY-MM-DD HH24:MI:SS；返回数据：一个标准的XML字符串
     */
    public GetVinCountByDateResponse getVinCountByDate(final String dtFrom, final String dtTo) {
        return retryAction(
                () -> new GetVinCountByDateResponse(wsXxgkVinService.getVinCountByDate(key, dtFrom, dtTo))
                , response -> response.succeed);
    }

    /**
     * 查询已报送VIN所对应的环保激活码
     * 输入参数： key = 临时认证码，不能为空；vin = vin号；返回数据：一个标准的XML字符串
     */
    public GetHbcodeByVinResponse getHbcodeByVin(final String vin) {
        return retryAction(
                () -> new GetHbcodeByVinResponse(wsXxgkVinService.getHbcodeByVin(key, vin))
                , response -> response.succeed);
//                , response -> i == 7 || response.succeed);
    }

    /**
     * 删除已上报VIN信息
     * 输入参数： key = 临时认证码，不能为空；vin = vin号；返回数据：一个标准的XML字符串
     */
    public DelDataResponse delData(final String vin) {
        return retryAction(
                () -> new DelDataResponse(wsXxgkVinService.delData(key, vin))
                , response -> response.succeed);
    }

    /**
     * 退出本次连接
     * 输入参数；Key = 临时认证码，不能为空；返回数据：一个标准的XML字符串
     */
    public LogoutResponse logout(final String key) {
        return new LogoutResponse(wsXxgkVinService.logout(key));
    }

    /**
     * 上报Vin数据；注意，报送成功后1小时以后才会算正式报送完成
     * 输入参数： key = 临时认证码，不能为空；strVinData: 信息主体
     */
    public SendVinDataResponse sendVinData(final String strVinData) {
        return retryAction(
                () -> new SendVinDataResponse(wsXxgkVinService.sendVinData(key, strVinData))
                , response -> response.succeed);
    }

    private <T> T retryAction(final Supplier<T> action, final Predicate<T> predicate) {
        return Action.retry(
                action
                , predicate
                , 50
                , 1
                , () -> login(manufid, password, System.currentTimeMillis())
                , response -> response.succeed);
    }

    public static void main(String[] args) throws InterruptedException {
        final Net net = new Net();
        for (int i = 0; i < 100; i++) {
            Thread.sleep(1);
            int finalI = i;
            net.async.get("http://localhost:9001/WSXxgkVin/GetHbcodeByVin", null
                    , new PairBuilder().add("vin", "test")
                    , r -> {
                        System.out.println(finalI);
                    }
            );
        }
    }
}
