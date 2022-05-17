package com.holland.demo.WSXxgkVin;

import com.holland.demo.util.Parser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class LoginResponse {
    public boolean succeed;
    public String data;

    public static int i;

    public LoginResponse(String xml) {
        if (++i % 7 == 0) {
            this.succeed = true;
            this.data = i + "";
            return;
        }

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

    @Override
    public String toString() {
        return "WSXxgkVinResponse{" +
                "succeed=" + succeed +
                ", data='" + data + '\'' +
                '}';
    }
}
