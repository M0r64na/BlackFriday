package data.util;

import data.model.entity.*;
import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public final class SessionFactoryProvider {
    @Getter
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private SessionFactoryProvider() {}

    private static SessionFactory buildSessionFactory()
    {
        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .loadProperties("hibernate.properties")
                .build();

        MetadataSources metadataSources = new MetadataSources(serviceRegistry);
        metadataSources
                .addAnnotatedClass(Role.class)
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Product.class)
                .addAnnotatedClass(Status.class)
                .addAnnotatedClass(Order.class)
                .addAnnotatedClass(OrderItem.class)
                .addAnnotatedClass(Campaign.class)
                .addAnnotatedClass(CampaignItem.class)
                .addAnnotatedClass(CampaignStop.class);

        Metadata metadata = metadataSources.buildMetadata();

        return metadata.buildSessionFactory();
    }
}