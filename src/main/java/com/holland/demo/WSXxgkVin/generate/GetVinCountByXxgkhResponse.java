
package com.holland.demo.WSXxgkVin.generate;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>anonymous complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="getVinCountByXxgkhResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "getVinCountByXxgkhResult"
})
@XmlRootElement(name = "getVinCountByXxgkhResponse")
public class GetVinCountByXxgkhResponse {

    protected String getVinCountByXxgkhResult;

    /**
     * ��ȡgetVinCountByXxgkhResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGetVinCountByXxgkhResult() {
        return getVinCountByXxgkhResult;
    }

    /**
     * ����getVinCountByXxgkhResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGetVinCountByXxgkhResult(String value) {
        this.getVinCountByXxgkhResult = value;
    }

}
