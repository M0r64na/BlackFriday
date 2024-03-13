package application.service.interfaces;

import data.model.entity.Order;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.*;

public interface IOrderService extends Remote {
    void placeOrder(String username, Map<String, Integer> productNamesAndQuantities) throws RemoteException;
    Optional<Order> getOrderById(UUID id) throws RemoteException;
    List<Order> getAllOrders() throws RemoteException;
}