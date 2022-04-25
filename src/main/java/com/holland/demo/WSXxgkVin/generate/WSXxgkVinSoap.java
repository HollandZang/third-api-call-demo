
package com.holland.demo.WSXxgkVin.generate;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "WSXxgkVinSoap", targetNamespace = "http://web1.vecc-mep.org.cn/WSXxgkVin")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface WSXxgkVinSoap {


    /**
     * <h3>��¼���ķ���ӿڣ��ɹ�������ʱ��֤��32λKEY�룬��ʱ�������µ�¼</h3><p>���������manufid = ��ҵ���(��������)������Ϊ�գ�password =  �������룻�������ݣ�һ����׼��XML�ַ���<br /><br><br><br><br><p>
     * 
     * @param password
     * @param manufid
     * @return
     *     returns java.lang.String
     */
    @WebMethod(action = "http://web1.vecc-sepa.org.cn/WSXxgkVin/login")
    @WebResult(name = "loginResult", targetNamespace = "http://web1.vecc-sepa.org.cn/WSXxgkVin")
    @RequestWrapper(localName = "login", targetNamespace = "http://web1.vecc-sepa.org.cn/WSXxgkVin", className = "com.holland.demo.WSXxgkVin.generate.Login")
    @ResponseWrapper(localName = "loginResponse", targetNamespace = "http://web1.vecc-sepa.org.cn/WSXxgkVin", className = "com.holland.demo.WSXxgkVin.generate.LoginResponse")
    public String login(
        @WebParam(name = "manufid", targetNamespace = "http://web1.vecc-sepa.org.cn/WSXxgkVin")
        String manufid,
        @WebParam(name = "password", targetNamespace = "http://web1.vecc-sepa.org.cn/WSXxgkVin")
        String password);

    /**
     * <h3>��ѯ�ѱ���VIN��</h3><p>��������� key = ��ʱ��֤�룬����Ϊ�գ�xshzh =  ��ʽ��׼�ţ��������ݣ�һ����׼��XML�ַ���<br /><br><br><br><br><p>
     * 
     * @param xxgkh
     * @param key
     * @return
     *     returns java.lang.String
     */
    @WebMethod(action = "http://web1.vecc-sepa.org.cn/WSXxgkVin/getVinCountByXshzh")
    @WebResult(name = "getVinCountByXxgkhResult", targetNamespace = "http://web1.vecc-sepa.org.cn/WSXxgkVin")
    @RequestWrapper(localName = "getVinCountByXxgkh", targetNamespace = "http://web1.vecc-sepa.org.cn/WSXxgkVin", className = "com.holland.demo.WSXxgkVin.generate.GetVinCountByXxgkh")
    @ResponseWrapper(localName = "getVinCountByXxgkhResponse", targetNamespace = "http://web1.vecc-sepa.org.cn/WSXxgkVin", className = "com.holland.demo.WSXxgkVin.generate.GetVinCountByXxgkhResponse")
    public String getVinCountByXxgkh(
        @WebParam(name = "key", targetNamespace = "http://web1.vecc-sepa.org.cn/WSXxgkVin")
        String key,
        @WebParam(name = "xxgkh", targetNamespace = "http://web1.vecc-sepa.org.cn/WSXxgkVin")
        String xxgkh);

    /**
     * <h3>��ѯ�ѱ���VIN����ͨ����������</h3><p>��������� key = ��ʱ��֤�룬����Ϊ�գ�dtFrom =  ������ʼʱ�䣻dtTo =  ���ͽ���ʱ�䣻ʱ���ʽ YYYY-MM-DD HH24:MI:SS���������ݣ�һ����׼��XML�ַ���<br /><br><br><br><br><p>
     * 
     * @param dtFrom
     * @param dtTo
     * @param key
     * @return
     *     returns java.lang.String
     */
    @WebMethod(action = "http://web1.vecc-sepa.org.cn/WSXxgkVin/getVinCountByDate")
    @WebResult(name = "getVinCountByDateResult", targetNamespace = "http://web1.vecc-sepa.org.cn/WSXxgkVin")
    @RequestWrapper(localName = "getVinCountByDate", targetNamespace = "http://web1.vecc-sepa.org.cn/WSXxgkVin", className = "com.holland.demo.WSXxgkVin.generate.GetVinCountByDate")
    @ResponseWrapper(localName = "getVinCountByDateResponse", targetNamespace = "http://web1.vecc-sepa.org.cn/WSXxgkVin", className = "com.holland.demo.WSXxgkVin.generate.GetVinCountByDateResponse")
    public String getVinCountByDate(
        @WebParam(name = "key", targetNamespace = "http://web1.vecc-sepa.org.cn/WSXxgkVin")
        String key,
        @WebParam(name = "dtFrom", targetNamespace = "http://web1.vecc-sepa.org.cn/WSXxgkVin")
        String dtFrom,
        @WebParam(name = "dtTo", targetNamespace = "http://web1.vecc-sepa.org.cn/WSXxgkVin")
        String dtTo);

    /**
     * <h3>��ѯ�ѱ���VIN����Ӧ�Ļ���������</h3><p>��������� key = ��ʱ��֤�룬����Ϊ�գ�vin =  vin�ţ��������ݣ�һ����׼��XML�ַ���<br /><br><br><br><br><p>
     * 
     * @param vin
     * @param key
     * @return
     *     returns java.lang.String
     */
    @WebMethod(action = "http://web1.vecc-sepa.org.cn/WSXxgkVin/getHbcodeByVin")
    @WebResult(name = "getHbcodeByVinResult", targetNamespace = "http://web1.vecc-sepa.org.cn/WSXxgkVin")
    @RequestWrapper(localName = "getHbcodeByVin", targetNamespace = "http://web1.vecc-sepa.org.cn/WSXxgkVin", className = "com.holland.demo.WSXxgkVin.generate.GetHbcodeByVin")
    @ResponseWrapper(localName = "getHbcodeByVinResponse", targetNamespace = "http://web1.vecc-sepa.org.cn/WSXxgkVin", className = "com.holland.demo.WSXxgkVin.generate.GetHbcodeByVinResponse")
    public String getHbcodeByVin(
        @WebParam(name = "key", targetNamespace = "http://web1.vecc-sepa.org.cn/WSXxgkVin")
        String key,
        @WebParam(name = "vin", targetNamespace = "http://web1.vecc-sepa.org.cn/WSXxgkVin")
        String vin);

    /**
     * <h3>ɾ�����ϱ�VIN��Ϣ</h3><p>��������� key = ��ʱ��֤�룬����Ϊ�գ�vin =  vin�ţ��������ݣ�һ����׼��XML�ַ���<br /><br><br><br><br><p>
     * 
     * @param vin
     * @param key
     * @return
     *     returns java.lang.String
     */
    @WebMethod(action = "http://web1.vecc-sepa.org.cn/WSXxgkVin/delData")
    @WebResult(name = "delDataResult", targetNamespace = "http://web1.vecc-sepa.org.cn/WSXxgkVin")
    @RequestWrapper(localName = "delData", targetNamespace = "http://web1.vecc-sepa.org.cn/WSXxgkVin", className = "com.holland.demo.WSXxgkVin.generate.DelData")
    @ResponseWrapper(localName = "delDataResponse", targetNamespace = "http://web1.vecc-sepa.org.cn/WSXxgkVin", className = "com.holland.demo.WSXxgkVin.generate.DelDataResponse")
    public String delData(
        @WebParam(name = "key", targetNamespace = "http://web1.vecc-sepa.org.cn/WSXxgkVin")
        String key,
        @WebParam(name = "vin", targetNamespace = "http://web1.vecc-sepa.org.cn/WSXxgkVin")
        String vin);

    /**
     * <h3>�˳���������</h3><p>���������Key = ��ʱ��֤�룬����Ϊ�գ��������ݣ�һ����׼��XML�ַ���<br /><br><br><br><br><p>
     * 
     * @param key
     * @return
     *     returns java.lang.String
     */
    @WebMethod(action = "http://web1.vecc-sepa.org.cn/WSXxgkVin/logout")
    @WebResult(name = "logoutResult", targetNamespace = "http://web1.vecc-sepa.org.cn/WSXxgkVin")
    @RequestWrapper(localName = "logout", targetNamespace = "http://web1.vecc-sepa.org.cn/WSXxgkVin", className = "com.holland.demo.WSXxgkVin.generate.Logout")
    @ResponseWrapper(localName = "logoutResponse", targetNamespace = "http://web1.vecc-sepa.org.cn/WSXxgkVin", className = "com.holland.demo.WSXxgkVin.generate.LogoutResponse")
    public String logout(
        @WebParam(name = "key", targetNamespace = "http://web1.vecc-sepa.org.cn/WSXxgkVin")
        String key);

    /**
     * <h3>�ϱ�Vin���ݣ�ע�⣬���ͳɹ���1Сʱ�Ժ�Ż�����ʽ�������</h3><p>��������� key = ��ʱ��֤�룬����Ϊ�գ�strVinData:  ��Ϣ����<br><br><br><br><p>
     * 
     * @param strVinData
     * @param key
     * @return
     *     returns java.lang.String
     */
    @WebMethod(action = "http://web1.vecc-sepa.org.cn/WSXxgkVin/sendVinData")
    @WebResult(name = "sendVinDataResult", targetNamespace = "http://web1.vecc-sepa.org.cn/WSXxgkVin")
    @RequestWrapper(localName = "sendVinData", targetNamespace = "http://web1.vecc-sepa.org.cn/WSXxgkVin", className = "com.holland.demo.WSXxgkVin.generate.SendVinData")
    @ResponseWrapper(localName = "sendVinDataResponse", targetNamespace = "http://web1.vecc-sepa.org.cn/WSXxgkVin", className = "com.holland.demo.WSXxgkVin.generate.SendVinDataResponse")
    public String sendVinData(
        @WebParam(name = "key", targetNamespace = "http://web1.vecc-sepa.org.cn/WSXxgkVin")
        String key,
        @WebParam(name = "strVinData", targetNamespace = "http://web1.vecc-sepa.org.cn/WSXxgkVin")
        String strVinData);

}
