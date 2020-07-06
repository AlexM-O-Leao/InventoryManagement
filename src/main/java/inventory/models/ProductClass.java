package inventory.models;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="products")
public class ProductClass implements Product {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private int id;
    private String productName;
    private int qtd;

    public ProductClass(String productName) {
        this.productName = productName;
        this.qtd = 1;
    }

    public ProductClass(String productId, String productName) {
        this(productName);
        this.id = Integer.parseInt(productId);
    }

    public ProductClass() {
        super();
    }

    public int getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String prd_productName) {
        this.productName= prd_productName;
    }

    public int getQtd() {
        return qtd;
    }

    public void setQtd(int qtd) {
        this.qtd = qtd;
    }

    public JsonObjectBuilder getJsonObjectBuilder() {
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        jsonObjectBuilder.add("id", this.getId());
        jsonObjectBuilder.add("name", this.getProductName());
        jsonObjectBuilder.add("quantity", this.getQtd());
        return jsonObjectBuilder;
    }


    public boolean equals(Object obj) {
        if(obj instanceof Product) {
            return this.getId() == ((Product)obj).getId();
        }
        return super.equals(obj);
    }
}
