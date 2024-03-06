package data.repository;

import data.model.entity.Status;
import data.model.entity.enums.OrderStatus;
import data.repository.base.RepositoryBase;
import data.repository.interfaces.IStatusRepository;
import org.hibernate.Session;

public class StatusRepository extends RepositoryBase<Status> implements IStatusRepository {
    @Override
    public Status findByOrderStatus(OrderStatus orderStatus) {
        Session newSession = sessionFactory.openSession();

        Status res = newSession
                .createQuery("FROM Status s WHERE s.orderStatus =: orderStatus", Status.class)
                .setParameter("orderStatus", orderStatus)
                .uniqueResult();

        newSession.close();

        return res;
    }
}