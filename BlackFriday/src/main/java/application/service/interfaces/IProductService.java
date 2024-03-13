package application.service.interfaces;

import data.model.entity.Product;

import java.math.BigDecimal;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IProductService extends Remote {
    void createProduct(String name, String description,
                       int numberInStock, BigDecimal minPrice, BigDecimal currPrice,
                       String usernameCreatedBy) throws RemoteException;
    Product updateProduct(String name, String description,
                          int numberInStock, BigDecimal minPrice, BigDecimal currPrice,
                          String usernameLastModifiedBy) throws RemoteException;
    Optional<Product> getOrderById(UUID id) throws RemoteException;
    List<Product> getAllProducts() throws RemoteException;
    Product findOrderByName(String name) throws RemoteException;
    void deleteOrderById(UUID id) throws RemoteException;
    void updateMinPriceOfProduct(String name, BigDecimal newMinPrice, String usernameLastModifiedBy) throws RemoteException;
    void updateCurrPriceOfProduct(String name, BigDecimal newCurrPrice, String usernameLastModifiedBY) throws RemoteException;
    void reduceNumberInStockOfProduct(String name, int quantity, String usernameLastModifiedBy) throws RemoteException;
}