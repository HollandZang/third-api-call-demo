package com.holland.zdl.WSXxgkVin;

import com.holland.zdl.util.Parser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class GetHbcodeByVinResponse {

    public boolean succeed;
    public String data;

    public GetHbcodeByVinResponse(String xml) {
        final Document doc = Parser.xml2Doc(xml);
        if (doc == null) {
            this.succeed = false;
            this.data = "xml parse error";
        } else {
            final Element root = doc.getDocumentElement();

            this.succeed = "succeed".equals(root.getElementsByTagName("succeed")
                    .item(0)
                    .getFirstChild()
                    .getNodeValue());

            this.data = root.getElementsByTagName("data")
                    .item(0)
                    .getFirstChild()
                    .getNodeValue();
        }
    }
}
