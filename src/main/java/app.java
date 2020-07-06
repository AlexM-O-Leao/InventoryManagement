import inventory.models.Category;
import inventory.models.CategoryClass;
import inventory.models.Product;
import inventory.models.ProductClass;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class app {
    public static void printCat(Category category) {
        System.out.println(category.getName());
        for (Product product : category.getProducts()) {
            System.out.println(product.getProductName());
        }
    }

    public static void main(String[] args) {
        Category category = new CategoryClass("Teste");
        category.getProducts().add(new ProductClass("Pepsi2"));
        
        printCat(category);

        SessionFactory sessionFactory = null;
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("/hibernate.cfg.xml")
                .build();
        try {
            sessionFactory  = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch(Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(category);
        session.getTransaction().commit();
        session.close();

    }
}
