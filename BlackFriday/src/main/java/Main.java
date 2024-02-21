import data.model.entity.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class Main {
    public static void main(String[] args) {
        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .loadProperties("hibernate.properties")
                .build();

        MetadataSources metadataSources = new MetadataSources(serviceRegistry);
        metadataSources.addAnnotatedClass(User.class);

        Metadata metadata = metadataSources.buildMetadata();

        try(SessionFactory sessionFactory = metadata.buildSessionFactory())
        {
            Session newSession = sessionFactory.openSession();

            User testUser = new User("test", "test");

            newSession.beginTransaction();
            newSession.persist(testUser);
            newSession.getTransaction().commit();

            newSession.close();
        }
        catch (HibernateException ex)
        {
            System.out.printf("Hibernate threw an exception: %s", ex.getMessage());
        }
    }
}