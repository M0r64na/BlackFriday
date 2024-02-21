import data.model.entity.User;
import data.repository.IUserRepository;
import data.repository.UserRepository;
import data.util.SessionFactoryProvider;

public class Main {
    public static void main(String[] args) {
//        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
//                .loadProperties("hibernate.properties")
//                .build();
//
//        MetadataSources metadataSources = new MetadataSources(serviceRegistry);
//        metadataSources.addAnnotatedClass(User.class);
//
//        Metadata metadata = metadataSources.buildMetadata();
//
//        try(SessionFactory sessionFactory = metadata.buildSessionFactory())
//        {
//            Session newSession = sessionFactory.openSession();
//
//            User testUser = new User("test", "test");
//
//            newSession.beginTransaction();
//            newSession.persist(testUser);
//            newSession.getTransaction().commit();
//
//            newSession.close();
//        }
//        catch (HibernateException ex)
//        {
//            System.out.printf("Hibernate threw an exception: %s", ex.getMessage());
//        }
        IUserRepository userRepository = new UserRepository();
        User testUser = new User("test", "test");
        userRepository.create(testUser);
        SessionFactoryProvider.getSessionFactory().close();
    }
}