package inventory.controllers;

import java.util.List;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import inventory.models.Product;
import inventory.models.Category;
import inventory.models.CategoryClass;

public class InventoryControllerClass implements InventoryController {
    private SessionFactory sessionFactory;

    public InventoryControllerClass() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()//"resource/hibernate.cfg.xml")
                .build();
        try {
            this.sessionFactory =
                    new MetadataSources(registry)
//        			.addAnnotatedClass(CategoryClass.class)
//        			.addAnnotatedClass(ProductClass.class)
                            .buildMetadata().buildSessionFactory();
        } catch(Exception e) {
            e.printStackTrace();
            StandardServiceRegistryBuilder.destroy(registry);
            System.exit(1);
        }
    }

    @SuppressWarnings("unchecked")
    public List<Category> readCategories() {
        Session session = sessionFactory.openSession();
        List<Category> productLists = null;
        try {
            productLists = session.createQuery("FROM inventory.models.CategoryClass").list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return productLists;
    }

    private Category readCategoryById(String categoryId) {
        Category category = null;
        Session session = sessionFactory.openSession();
        try {
            category = session.find(CategoryClass.class, Integer.parseInt(categoryId));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return category;
    }

    private Category readCategoryByName(String name) {
        Session session = sessionFactory.openSession();
        Category category = null;
        try {
            Query query = session.createQuery("FROM inventory.models.CategoryClass WHERE name=:name");
            query.setParameter("name", name);
            category = (Category) query.getResultList().get(0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return category;
    }

    private void writeCategory(Category category) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.saveOrUpdate(category);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void close() {
        sessionFactory.close();
    }

    public List<Category> getCategories() {
        return this.readCategories();
    }

    public boolean hasCategories() {
        return !this.getCategories().isEmpty();
    }

    public boolean hasCategoryByName(String categoryName) {
        return this.getCategoryByName(categoryName) != null;
    }

    public boolean hasCategoryById(String categoryId) {
        return this.getCategoryById(categoryId) != null;
    }

    public Category createCategory(String categoryName) {
        Category category = new CategoryClass(categoryName);
        this.writeCategory(category);
        return category;
    }

    public Category getCategoryByName(String categoryName) {
        return this.readCategoryByName(categoryName);
    }

    public Category getCategoryById(String categoryId) {
        return this.readCategoryById(categoryId);
    }

    public Category changeCategoryNameByName(String categoryName, String newCategoryName) {
        Category category = this.readCategoryByName(categoryName);
        category.setName(newCategoryName);
        this.writeCategory(category);
        return category;
    }

    public Category changeCategoryNameById(String categoryId, String newCategoryName) {
        Category category = this.readCategoryById(categoryId);
        category.setName(newCategoryName);
        this.writeCategory(category);
        return category;
    }

    public void deleteProduct(String categoryId, String productId) {
        Category category = this.getCategoryById(categoryId);
        category.removeProduct(productId);
        this.writeCategory(category);
    }

    public boolean hasProduct(String categoryId, String productId) {
        Category category = this.getCategoryById(categoryId);
        return category.hasProduct(productId);
    }

    public Product changeProductName(String categoryId, String productId, String productName) {
        Category category = this.getCategoryById(categoryId);
        Product product = category.getProduct(productId);
        product.setProductName(productName);
        this.writeCategory(category);
        return product;
    }

    public Product changeProductQtd(String categoryId, String productId, int productQtd) {
        Category category = this.getCategoryById(categoryId);
        Product product = category.getProduct(productId);
        product.setQtd(productQtd);
        this.writeCategory(category);
        return product;
    }

    public Product createProduct(String categoryId, String productName) {
        Category category = this.getCategoryById(categoryId);
        Product product = category.addProduct(productName);
        this.writeCategory(category);
        return product;
    }

    public Product getProduct(String categoryId, String productId) {
        Category category = this.getCategoryById(categoryId);
        Product product = category.getProduct(productId);
        return product;
    }

    public void deleteCategory(String categoryId) {
        Category category = getCategoryById(categoryId);
        Session session = sessionFactory.openSession();
        if (category != null) {
            try {
                session.getTransaction().begin();
                session.delete(category);
                session.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                session.close();
            }
        }
    }

    public JsonObjectBuilder getCategoryJsonObjectBuilder() {
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();

        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        for(Category category : this.getCategories()) {
            jsonArrayBuilder.add(category.getJsonObjectBuilder());
        }
        jsonObjectBuilder.add("categories", jsonArrayBuilder);
        return jsonObjectBuilder;
    }

}
