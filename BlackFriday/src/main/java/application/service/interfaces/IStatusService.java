package application.service.interfaces;

import data.model.entity.Status;
import data.model.entity.enums.OrderStatus;

public interface IStatusService {
    Status findByName(OrderStatus orderStatus);
    void initialize();
}