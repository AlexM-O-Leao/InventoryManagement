package inventory.controllers;

import inventory.models.Category;
import inventory.models.Product;

import javax.json.JsonObjectBuilder;
import java.util.List;

public interface InventoryController {
    void close();

    List<Category> getCategories();

    boolean hasCategories();

    boolean hasCategoryByName(String categoryName);

    boolean hasCategoryById(String categoryId);

    Category createCategory(String categoryName);

    Category getCategoryByName(String categoryName);

    Category getCategoryById(String categoryId);

    Category changeCategoryNameByName(String categoryName, String newCategoryName);

    Category changeCategoryNameById(String categoryId, String newCategoryName);

    void deleteProduct(String categoryId, String productId);

    boolean hasProduct(String categoryId, String productId);

    Product changeProductName(String categoryId, String productId, String productName);

    Product changeProductQtd(String categoryId, String productId, int productQtd);

    Product createProduct(String categoryId, String productName);

    Product getProduct(String categoryId, String productId);

    void deleteCategory(String categoryId);

    JsonObjectBuilder getCategoryJsonObjectBuilder();
}
