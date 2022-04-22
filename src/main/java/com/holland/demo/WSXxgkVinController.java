package com.holland.demo;

import com.holland.demo.WSXxgkVin.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/WSXxgkVin")
public class WSXxgkVinController {

    @Resource
    private XSXxgkVinServiceHelper wsXxgkVinService;

    /**
     * 查询已报送VIN数
     * 输入参数： key = 临时认证码，不能为空；xshzh = 型式核准号；返回数据：一个标准的XML字符串
     */
    @RequestMapping("/GetVinCountByXxgkh")
    public GetVinCountByXxgkhResponse getVinCountByXxgkh(String xxgkh) {
        return wsXxgkVinService.getVinCountByXxgkh(xxgkh);
    }

    /**
     * 查询已报送VIN总数通过报送日期
     * 输入参数： key = 临时认证码，不能为空；dtFrom = 报送起始时间；dtTo = 报送截至时间；时间格式 YYYY-MM-DD HH24:MI:SS；返回数据：一个标准的XML字符串
     */
    @RequestMapping("/GetVinCountByDate")
    public GetVinCountByDateResponse getVinCountByDate(String dtFrom, String dtTo) {
        return wsXxgkVinService.getVinCountByDate(dtFrom, dtTo);
    }

    /**
     * 查询已报送VIN所对应的环保激活码
     * 输入参数： key = 临时认证码，不能为空；vin = vin号；返回数据：一个标准的XML字符串
     */
    @RequestMapping("/GetHbcodeByVin")
    public GetHbcodeByVinResponse getHbcodeByVin(String vin) {
        return wsXxgkVinService.getHbcodeByVin(vin);
    }

    /**
     * 删除已上报VIN信息
     * 输入参数： key = 临时认证码，不能为空；vin = vin号；返回数据：一个标准的XML字符串
     */
    @RequestMapping("/DelData")
    public DelDataResponse delData(String vin) {
        return wsXxgkVinService.delData(vin);
    }

    /**
     * 退出本次连接
     * 输入参数；Key = 临时认证码，不能为空；返回数据：一个标准的XML字符串
     */
    @RequestMapping("/Logout")
    public LogoutResponse logout(String key) {
        return wsXxgkVinService.logout(key);
    }

    /**
     * 上报Vin数据；注意，报送成功后1小时以后才会算正式报送完成
     * 输入参数： key = 临时认证码，不能为空；strVinData: 信息主体
     */
    @RequestMapping("/SendVinData")
    public SendVinDataResponse sendVinData(String strVinData) {
        return wsXxgkVinService.sendVinData(strVinData);
    }
}
