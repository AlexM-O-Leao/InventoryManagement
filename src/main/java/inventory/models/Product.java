package inventory.models;

import javax.json.JsonObjectBuilder;

public interface Product {
    int getId();

    String getProductName();

    int getQtd();

    void setProductName(String productName);

    void setQtd(int productQtd);

    JsonObjectBuilder getJsonObjectBuilder();

}
