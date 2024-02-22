package data.util;

import data.model.entity.User;
import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class SessionFactoryProvider {
    @Getter
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory()
    {
        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .loadProperties("hibernate.properties")
                .build();

        MetadataSources metadataSources = new MetadataSources(serviceRegistry);
        metadataSources.addAnnotatedClass(User.class);

        Metadata metadata = metadataSources.buildMetadata();

        return metadata.buildSessionFactory();
    }
}