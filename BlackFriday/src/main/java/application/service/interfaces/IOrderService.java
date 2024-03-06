package application.service.interfaces;

import data.model.entity.Order;
import java.util.*;

public interface IOrderService {
    void create(String username, Map<String, Integer> productNamesAndQuantities);
    Optional<Order> getById(UUID id);
    List<Order> getAll();
}