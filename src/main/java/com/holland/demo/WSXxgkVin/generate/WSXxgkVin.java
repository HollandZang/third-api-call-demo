
package com.holland.demo.WSXxgkVin.generate;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * <strong>Vin���ͷ���<a href="http://www.vecc-mep.org.cn/download/WSXxgkVin.PDF" target="_blank"><font size="3" color="#FF0000">(��ϸʹ��˵���������)</font></a><br><br></strong>������Դ��<strong>�й��������������������ۼ������</strong> <a href="http://www.vecc-mep.org.cn" target="_blank">http://www.vecc-mep.org.cn/</a>
 * 
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "WSXxgkVin", targetNamespace = "http://web1.vecc-mep.org.cn/WSXxgkVin", wsdlLocation = "http://web1.vecc-mep.org.cn/WSXxgkVin/WSXxgkVin.asmx?WSDL")
public class WSXxgkVin
    extends Service
{

    private final static URL WSXXGKVIN_WSDL_LOCATION;
    private final static WebServiceException WSXXGKVIN_EXCEPTION;
    private final static QName WSXXGKVIN_QNAME = new QName("http://web1.vecc-mep.org.cn/WSXxgkVin", "WSXxgkVin");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://web1.vecc-mep.org.cn/WSXxgkVin/WSXxgkVin.asmx?WSDL");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        WSXXGKVIN_WSDL_LOCATION = url;
        WSXXGKVIN_EXCEPTION = e;
    }

    public WSXxgkVin() {
        super(__getWsdlLocation(), WSXXGKVIN_QNAME);
    }

    public WSXxgkVin(WebServiceFeature... features) {
        super(__getWsdlLocation(), WSXXGKVIN_QNAME, features);
    }

    public WSXxgkVin(URL wsdlLocation) {
        super(wsdlLocation, WSXXGKVIN_QNAME);
    }

    public WSXxgkVin(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, WSXXGKVIN_QNAME, features);
    }

    public WSXxgkVin(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public WSXxgkVin(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns WSXxgkVinSoap
     */
    @WebEndpoint(name = "WSXxgkVinSoap")
    public WSXxgkVinSoap getWSXxgkVinSoap() {
        return super.getPort(new QName("http://web1.vecc-mep.org.cn/WSXxgkVin", "WSXxgkVinSoap"), WSXxgkVinSoap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns WSXxgkVinSoap
     */
    @WebEndpoint(name = "WSXxgkVinSoap")
    public WSXxgkVinSoap getWSXxgkVinSoap(WebServiceFeature... features) {
        return super.getPort(new QName("http://web1.vecc-mep.org.cn/WSXxgkVin", "WSXxgkVinSoap"), WSXxgkVinSoap.class, features);
    }

    /**
     * 
     * @return
     *     returns WSXxgkVinSoap
     */
    @WebEndpoint(name = "WSXxgkVinSoap12")
    public WSXxgkVinSoap getWSXxgkVinSoap12() {
        return super.getPort(new QName("http://web1.vecc-mep.org.cn/WSXxgkVin", "WSXxgkVinSoap12"), WSXxgkVinSoap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns WSXxgkVinSoap
     */
    @WebEndpoint(name = "WSXxgkVinSoap12")
    public WSXxgkVinSoap getWSXxgkVinSoap12(WebServiceFeature... features) {
        return super.getPort(new QName("http://web1.vecc-mep.org.cn/WSXxgkVin", "WSXxgkVinSoap12"), WSXxgkVinSoap.class, features);
    }

    private static URL __getWsdlLocation() {
        if (WSXXGKVIN_EXCEPTION!= null) {
            throw WSXXGKVIN_EXCEPTION;
        }
        return WSXXGKVIN_WSDL_LOCATION;
    }

}
