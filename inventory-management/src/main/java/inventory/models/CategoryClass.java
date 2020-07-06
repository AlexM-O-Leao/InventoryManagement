package inventory.models;

import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="categories")
public class CategoryClass implements Category {
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy="increment")

    private int id;
    private String name;

    @ElementCollection
    @OneToMany(
            orphanRemoval = true,
            cascade = CascadeType.ALL,
            targetEntity = ProductClass.class,
            fetch = FetchType.EAGER)

    private List<Product> products;

    public CategoryClass(String name) {
        this.name = name;
        this.products = new ArrayList<Product>();
    }

    public CategoryClass() {
        super();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return this.products;
    }

    public boolean hasProduct(String productId) {
        return this.products.contains(new ProductClass(productId, null));
    }

    public void removeProduct(String productId) {
        this.products.remove(new ProductClass(productId, null));
    }

    public Product addProduct(String prd_productName) {
        Product product = new ProductClass(prd_productName);
        this.products.add(product);
        return product;
    }

    public Product getProduct(String productId) {
        return this.products.get(this.products.indexOf(new ProductClass(productId, null)));
    }

    public JsonObjectBuilder getJsonObjectBuilder() {
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        jsonObjectBuilder.add("id", this.getId());
        jsonObjectBuilder.add("name", this.getName());

        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        for(Product product : this.getProducts()) {
            jsonArrayBuilder.add(product.getJsonObjectBuilder());
        }
        jsonObjectBuilder.add("products", jsonArrayBuilder);
        return jsonObjectBuilder;
    }
}
