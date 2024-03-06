package data.repository.interfaces;

import data.model.entity.Status;
import data.model.entity.enums.OrderStatus;
import data.repository.base.IRepository;

public interface IStatusRepository extends IRepository<Status> {
    Status findByOrderStatus(OrderStatus orderStatus);
}