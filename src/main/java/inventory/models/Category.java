package inventory.models;

import javax.json.JsonObjectBuilder;
import java.util.List;

public interface Category {
    int getId();

    String getName();

    void setName(String name);

    List<Product> getProducts();

    boolean hasProduct(String productId);

    void removeProduct(String productId);

    Product addProduct(String prd_productName);

    Product getProduct(String productId);

    JsonObjectBuilder getJsonObjectBuilder();
}
