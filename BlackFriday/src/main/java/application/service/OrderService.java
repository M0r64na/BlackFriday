package application.service;

import application.service.interfaces.*;
import data.model.entity.*;
import data.model.entity.enums.OrderStatus;
import data.repository.OrderRepository;
import data.repository.interfaces.IOrderRepository;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class OrderService implements IOrderService {
//    private static final IOrderRepository orderRepository = new OrderRepository();
//    private static final IUserService userService = new UserService();
//    private static final IStatusService statusService = new StatusService();
//    private static final IProductService productService = new ProductService();
    private final IOrderRepository orderRepository;
    private final IUserService userService;
    private final IStatusService statusService;
    private final IProductService productService;

    @Inject
    public OrderService(IOrderRepository orderRepository, IUserService userService, IStatusService statusService, IProductService productService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.statusService = statusService;
        this.productService = productService;
    }

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