package application.service;

import application.service.interfaces.IProductService;
import application.service.interfaces.IUserService;
import data.model.entity.Product;
import data.model.entity.User;
import data.repository.interfaces.IProductRepository;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ProductService implements IProductService {
    private final IProductRepository productRepository;
    private final IUserService userService;

    @Inject
    public ProductService(IProductRepository productRepository, IUserService userService) {
        this.productRepository = productRepository;
        this.userService = userService;
    }

    @Override
    public void createProduct(String name, String description,
                              int numberInStock, BigDecimal minPrice, BigDecimal currPrice,
                              String usernameCreatedBy) throws RemoteException {
        Product product = new Product(name, description, numberInStock, minPrice, currPrice);

        User createdAndLastModifiedBy = this.userService.getUserByUsername(usernameCreatedBy);
        product.setCreatedBy(createdAndLastModifiedBy);
        product.setLastModifiedBy(createdAndLastModifiedBy);

        this.productRepository.create(product);
    }

    @Override
    public Product updateProduct(String name, String description,
                                 int numberInStock, BigDecimal minPrice, BigDecimal currPrice,
                                 String usernameLastModifiedBy) throws RemoteException {
        Product product = this.getProductAndSetLastModifiedBy(name, usernameLastModifiedBy);
        product.setName(name);
        product.setDescription(description);
        product.setNumberInStock(numberInStock);
        product.setMinPrice(minPrice);
        product.setCurrPrice(currPrice);

        return this.productRepository.update(product);
    }

    @Override
    public Optional<Product> getOrderById(UUID id) {
        return this.productRepository.getById(id);
    }

    @Override
    public List<Product> getAllProducts() {
        return this.productRepository.getAll();
    }

    @Override
    public Product findOrderByName(String name) {
        return this.productRepository.findByName(name);
    }

    @Override
    public void deleteOrderById(UUID id) {
        this.productRepository.deleteById(id);
    }

    @Override
    public void updateMinPriceOfProduct(String name, BigDecimal newMinPrice, String usernameLastModifiedBy) throws RemoteException {
        Product product = this.getProductAndSetLastModifiedBy(name, usernameLastModifiedBy);
        product.setMinPrice(newMinPrice);

        this.productRepository.update(product);
    }

    @Override
    public void updateCurrPriceOfProduct(String name, BigDecimal newCurrPrice, String usernameLastModifiedBy) throws RemoteException {
        Product product = this.getProductAndSetLastModifiedBy(name, usernameLastModifiedBy);
        if(newCurrPrice.compareTo(product.getMinPrice()) < 0) throw new RuntimeException("Current price must be greater than or equal to minimum price");

        product.setCurrPrice(newCurrPrice);

        this.productRepository.update(product);
    }

    @Override
    public void reduceNumberInStockOfProduct(String name, int quantity, String usernameLastModifiedBy) throws RemoteException {
        Product product = this.getProductAndSetLastModifiedBy(name, usernameLastModifiedBy);

        int newNumberInStock = product.getNumberInStock() - quantity;
        if(newNumberInStock < 0) throw new RuntimeException("Insufficient product quantity");

        product.setNumberInStock(newNumberInStock);

        this.productRepository.update(product);
    }

    private Product getProductAndSetLastModifiedBy(String name, String usernameLastModifiedBy) throws RemoteException {
        Product product = this.productRepository.findByName(name);

        User lastModifiedBy = this.userService.getUserByUsername(usernameLastModifiedBy);
        product.setLastModifiedBy(lastModifiedBy);

        return product;
    }
}