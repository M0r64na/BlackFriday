package application.service.interfaces;

import data.model.entity.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IProductService {
    void create(String name, String description,
                int numberInStock, double minPrice, double currPrice,
                String usernameCreatedBy);
    Product update(String name, String description,
                   int numberInStock, double minPrice, double currPrice,
                   String usernameLastModifiedBy);
    Optional<Product> getById(UUID id);
    List<Product> getAll();
    Product findByName(String name);
    void deleteById(UUID id);
    void updateMinPrice(String name, BigDecimal newMinPrice, String usernameLastModifiedBy);
    void updateCurrPrice(String name, BigDecimal newCurrPrice, String usernameLastModifiedBY);
    void reduceNumberInStock(String name, int quantity, String usernameLastModifiedBy);
}