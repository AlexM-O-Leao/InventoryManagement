package inventory.models;

import javax.json.JsonObjectBuilder;

public interface Product {
    int getId();

    String getProductName();

    float getPrice();

    int getQtd();

    void setProductName(String productName);

    void setPrice(float productPrice);

    void setQtd(int productQtd);

    JsonObjectBuilder getJsonObjectBuilder();

}
