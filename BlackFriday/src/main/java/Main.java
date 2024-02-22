import data.repository.UserRepository;

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
        UserRepository userRepository = new UserRepository();
        System.out.println(userRepository.getAll().size());
    }
}