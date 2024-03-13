package application.service;

import application.service.interfaces.*;
import data.model.entity.*;
import data.model.entity.enums.OrderStatus;
import data.repository.interfaces.IOrderRepository;
import javax.inject.Inject;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class OrderService implements IOrderService {
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
    public void placeOrder(String username, Map<String, Integer> productNamesAndQuantities) throws RemoteException {
        Status status = this.statusService.findStatusByName(OrderStatus.IN_PROCESS);
        User user = this.userService.getUserByUsername(username);
        Order order = new Order(status, user);

        for(String name : productNamesAndQuantities.keySet()) {
            int quantity = productNamesAndQuantities.get(name);
            this.productService.reduceNumberInStockOfProduct(name, quantity, username);

            Product product = this.productService.findOrderByName(name);
            OrderItem orderItem = new OrderItem(order, product, quantity);
            order.getItems().add(orderItem);
        }

        this.orderRepository.create(order);
    }

    @Override
    public Optional<Order> getOrderById(UUID id) {
        return this.orderRepository.getById(id);
    }

    @Override
    public List<Order> getAllOrders() {
        return this.orderRepository.getAll();
    }
}