<?xml version="1.0" encoding="utf-8"?>
<wsdl:definitions xmlns:s="http://www.w3.org/2001/XMLSchema" xmlns:soap12="http://schemas.xmlsoap.org/wsdl/soap12/" xmlns:mime="http://schemas.xmlsoap.org/wsdl/mime/" xmlns:tns="http://web1.vecc-mep.org.cn/WSXxgkVin" xmlns:s0="http://web1.vecc-sepa.org.cn/WSXxgkVin" xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/" xmlns:tm="http://microsoft.com/wsdl/mime/textMatching/" xmlns:http="http://schemas.xmlsoap.org/wsdl/http/" xmlns:soapenc="http://schemas.xmlsoap.org/soap/encoding/" targetNamespace="http://web1.vecc-mep.org.cn/WSXxgkVin" xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">
  <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">&lt;strong&gt;Vin报送服务&lt;a href="http://www.vecc-mep.org.cn/download/WSXxgkVin.PDF" target="_blank"&gt;&lt;font size="3" color="#FF0000"&gt;(详细使用说明点此下载)&lt;/font&gt;&lt;/a&gt;&lt;br&gt;&lt;br&gt;&lt;/strong&gt;数据来源于&lt;strong&gt;中国环境保护部机动车排污监控中心&lt;/strong&gt; &lt;a href="http://www.vecc-mep.org.cn" target="_blank"&gt;http://www.vecc-mep.org.cn/&lt;/a&gt;</wsdl:documentation>
  <wsdl:types>
    <s:schema elementFormDefault="qualified" targetNamespace="http://web1.vecc-sepa.org.cn/WSXxgkVin">
      <s:element name="login">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="manufid" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="password" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="loginResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="loginResult" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="getVinCountByXxgkh">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="key" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="xxgkh" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="getVinCountByXxgkhResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="getVinCountByXxgkhResult" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="getVinCountByDate">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="key" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="dtFrom" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="dtTo" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="getVinCountByDateResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="getVinCountByDateResult" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="getHbcodeByVin">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="key" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="vin" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="getHbcodeByVinResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="getHbcodeByVinResult" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="delData">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="key" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="vin" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="delDataResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="delDataResult" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="logout">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="key" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="logoutResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="logoutResult" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="sendVinData">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="key" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="strVinData" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="sendVinDataResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="sendVinDataResult" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
    </s:schema>
  </wsdl:types>
  <wsdl:message name="loginSoapIn">
    <wsdl:part name="parameters" element="s0:login" />
  </wsdl:message>
  <wsdl:message name="loginSoapOut">
    <wsdl:part name="parameters" element="s0:loginResponse" />
  </wsdl:message>
  <wsdl:message name="getVinCountByXxgkhSoapIn">
    <wsdl:part name="parameters" element="s0:getVinCountByXxgkh" />
  </wsdl:message>
  <wsdl:message name="getVinCountByXxgkhSoapOut">
    <wsdl:part name="parameters" element="s0:getVinCountByXxgkhResponse" />
  </wsdl:message>
  <wsdl:message name="getVinCountByDateSoapIn">
    <wsdl:part name="parameters" element="s0:getVinCountByDate" />
  </wsdl:message>
  <wsdl:message name="getVinCountByDateSoapOut">
    <wsdl:part name="parameters" element="s0:getVinCountByDateResponse" />
  </wsdl:message>
  <wsdl:message name="getHbcodeByVinSoapIn">
    <wsdl:part name="parameters" element="s0:getHbcodeByVin" />
  </wsdl:message>
  <wsdl:message name="getHbcodeByVinSoapOut">
    <wsdl:part name="parameters" element="s0:getHbcodeByVinResponse" />
  </wsdl:message>
  <wsdl:message name="delDataSoapIn">
    <wsdl:part name="parameters" element="s0:delData" />
  </wsdl:message>
  <wsdl:message name="delDataSoapOut">
    <wsdl:part name="parameters" element="s0:delDataResponse" />
  </wsdl:message>
  <wsdl:message name="logoutSoapIn">
    <wsdl:part name="parameters" element="s0:logout" />
  </wsdl:message>
  <wsdl:message name="logoutSoapOut">
    <wsdl:part name="parameters" element="s0:logoutResponse" />
  </wsdl:message>
  <wsdl:message name="sendVinDataSoapIn">
    <wsdl:part name="parameters" element="s0:sendVinData" />
  </wsdl:message>
  <wsdl:message name="sendVinDataSoapOut">
    <wsdl:part name="parameters" element="s0:sendVinDataResponse" />
  </wsdl:message>
  <wsdl:portType name="WSXxgkVinSoap">
    <wsdl:operation name="login">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">&lt;h3&gt;登录中心服务接口，成功返回临时认证码32位KEY码，超时则需重新登录&lt;/h3&gt;&lt;p&gt;输入参数：manufid = 企业编号(机构代码)，不能为空；password =  报送密码；返回数据：一个标准的XML字符串&lt;br /&gt;&lt;br&gt;&lt;br&gt;&lt;br&gt;&lt;br&gt;&lt;p&gt;</wsdl:documentation>
      <wsdl:input message="tns:loginSoapIn" />
      <wsdl:output message="tns:loginSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="getVinCountByXxgkh">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">&lt;h3&gt;查询已报送VIN数&lt;/h3&gt;&lt;p&gt;输入参数： key = 临时认证码，不能为空；xshzh =  型式核准号；返回数据：一个标准的XML字符串&lt;br /&gt;&lt;br&gt;&lt;br&gt;&lt;br&gt;&lt;br&gt;&lt;p&gt;</wsdl:documentation>
      <wsdl:input message="tns:getVinCountByXxgkhSoapIn" />
      <wsdl:output message="tns:getVinCountByXxgkhSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="getVinCountByDate">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">&lt;h3&gt;查询已报送VIN总数通过报送日期&lt;/h3&gt;&lt;p&gt;输入参数： key = 临时认证码，不能为空；dtFrom =  报送起始时间；dtTo =  报送截至时间；时间格式 YYYY-MM-DD HH24:MI:SS；返回数据：一个标准的XML字符串&lt;br /&gt;&lt;br&gt;&lt;br&gt;&lt;br&gt;&lt;br&gt;&lt;p&gt;</wsdl:documentation>
      <wsdl:input message="tns:getVinCountByDateSoapIn" />
      <wsdl:output message="tns:getVinCountByDateSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="getHbcodeByVin">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">&lt;h3&gt;查询已报送VIN所对应的环保激活码&lt;/h3&gt;&lt;p&gt;输入参数： key = 临时认证码，不能为空；vin =  vin号；返回数据：一个标准的XML字符串&lt;br /&gt;&lt;br&gt;&lt;br&gt;&lt;br&gt;&lt;br&gt;&lt;p&gt;</wsdl:documentation>
      <wsdl:input message="tns:getHbcodeByVinSoapIn" />
      <wsdl:output message="tns:getHbcodeByVinSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="delData">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">&lt;h3&gt;删除已上报VIN信息&lt;/h3&gt;&lt;p&gt;输入参数： key = 临时认证码，不能为空；vin =  vin号；返回数据：一个标准的XML字符串&lt;br /&gt;&lt;br&gt;&lt;br&gt;&lt;br&gt;&lt;br&gt;&lt;p&gt;</wsdl:documentation>
      <wsdl:input message="tns:delDataSoapIn" />
      <wsdl:output message="tns:delDataSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="logout">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">&lt;h3&gt;退出本次连接&lt;/h3&gt;&lt;p&gt;输入参数；Key = 临时认证码，不能为空；返回数据：一个标准的XML字符串&lt;br /&gt;&lt;br&gt;&lt;br&gt;&lt;br&gt;&lt;br&gt;&lt;p&gt;</wsdl:documentation>
      <wsdl:input message="tns:logoutSoapIn" />
      <wsdl:output message="tns:logoutSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="sendVinData">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">&lt;h3&gt;上报Vin数据；注意，报送成功后1小时以后才会算正式报送完成&lt;/h3&gt;&lt;p&gt;输入参数： key = 临时认证码，不能为空；strVinData:  信息主体&lt;br&gt;&lt;br&gt;&lt;br&gt;&lt;br&gt;&lt;p&gt;</wsdl:documentation>
      <wsdl:input message="tns:sendVinDataSoapIn" />
      <wsdl:output message="tns:sendVinDataSoapOut" />
    </wsdl:operation>
  </wsdl:portType>
  <wsdl:binding name="WSXxgkVinSoap" type="tns:WSXxgkVinSoap">
    <soap:binding transport="http://schemas.xmlsoap.org/soap/http" />
    <wsdl:operation name="login">
      <soap:operation soapAction="http://web1.vecc-sepa.org.cn/WSXxgkVin/login" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="getVinCountByXxgkh">
      <soap:operation soapAction="http://web1.vecc-sepa.org.cn/WSXxgkVin/getVinCountByXshzh" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="getVinCountByDate">
      <soap:operation soapAction="http://web1.vecc-sepa.org.cn/WSXxgkVin/getVinCountByDate" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="getHbcodeByVin">
      <soap:operation soapAction="http://web1.vecc-sepa.org.cn/WSXxgkVin/getHbcodeByVin" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="delData">
      <soap:operation soapAction="http://web1.vecc-sepa.org.cn/WSXxgkVin/delData" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="logout">
      <soap:operation soapAction="http://web1.vecc-sepa.org.cn/WSXxgkVin/logout" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="sendVinData">
      <soap:operation soapAction="http://web1.vecc-sepa.org.cn/WSXxgkVin/sendVinData" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
  </wsdl:binding>
  <wsdl:binding name="WSXxgkVinSoap12" type="tns:WSXxgkVinSoap">
    <soap12:binding transport="http://schemas.xmlsoap.org/soap/http" />
    <wsdl:operation name="login">
      <soap12:operation soapAction="http://web1.vecc-sepa.org.cn/WSXxgkVin/login" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="getVinCountByXxgkh">
      <soap12:operation soapAction="http://web1.vecc-sepa.org.cn/WSXxgkVin/getVinCountByXshzh" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="getVinCountByDate">
      <soap12:operation soapAction="http://web1.vecc-sepa.org.cn/WSXxgkVin/getVinCountByDate" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="getHbcodeByVin">
      <soap12:operation soapAction="http://web1.vecc-sepa.org.cn/WSXxgkVin/getHbcodeByVin" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="delData">
      <soap12:operation soapAction="http://web1.vecc-sepa.org.cn/WSXxgkVin/delData" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="logout">
      <soap12:operation soapAction="http://web1.vecc-sepa.org.cn/WSXxgkVin/logout" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="sendVinData">
      <soap12:operation soapAction="http://web1.vecc-sepa.org.cn/WSXxgkVin/sendVinData" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
  </wsdl:binding>
  <wsdl:service name="WSXxgkVin">
    <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">&lt;strong&gt;Vin报送服务&lt;a href="http://www.vecc-mep.org.cn/download/WSXxgkVin.PDF" target="_blank"&gt;&lt;font size="3" color="#FF0000"&gt;(详细使用说明点此下载)&lt;/font&gt;&lt;/a&gt;&lt;br&gt;&lt;br&gt;&lt;/strong&gt;数据来源于&lt;strong&gt;中国环境保护部机动车排污监控中心&lt;/strong&gt; &lt;a href="http://www.vecc-mep.org.cn" target="_blank"&gt;http://www.vecc-mep.org.cn/&lt;/a&gt;</wsdl:documentation>
    <wsdl:port name="WSXxgkVinSoap" binding="tns:WSXxgkVinSoap">
      <soap:address location="http://web1.vecc-mep.org.cn/WSXxgkVin/WSXxgkVin.asmx" />
    </wsdl:port>
    <wsdl:port name="WSXxgkVinSoap12" binding="tns:WSXxgkVinSoap12">
      <soap12:address location="http://web1.vecc-mep.org.cn/WSXxgkVin/WSXxgkVin.asmx" />
    </wsdl:port>
  </wsdl:service>
</wsdl:definitions>