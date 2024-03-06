package application.service;

import application.service.interfaces.*;
import data.model.entity.*;
import data.model.entity.enums.OrderStatus;
import data.repository.OrderRepository;
import data.repository.interfaces.IOrderRepository;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class OrderService implements IOrderService {
    private static final IOrderRepository orderRepository = new OrderRepository();
    private static final IUserService userService = new UserService();
    private static final IStatusService statusService = new StatusService();
    private static final IProductService productService = new ProductService();

    @Override
    public void create(String username, Map<String, Integer> productNamesAndQuantities) {
        Status status = statusService.findByName(OrderStatus.IN_PROCESS);
        User user = userService.getByUsername(username);
        Order order = new Order(status, user);

        for(String name : productNamesAndQuantities.keySet()) {
            int quantity = productNamesAndQuantities.get(name);
            productService.reduceNumberInStock(name, quantity, username);

            Product product = productService.findByName(name);
            OrderItem orderItem = new OrderItem(order, product, quantity);
            order.getItems().add(orderItem);
        }

        orderRepository.create(order);
    }

    @Override
    public Optional<Order> getById(UUID id) {
        return orderRepository.getById(id);
    }

    @Override
    public List<Order> getAll() {
        return orderRepository.getAll();
    }
}