package application.service;

import application.service.interfaces.IProductService;
import application.service.interfaces.IUserService;
import data.model.entity.Product;
import data.model.entity.User;
import data.repository.ProductRepository;
import data.repository.interfaces.IProductRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ProductService implements IProductService {
    private static final IProductRepository productRepository = new ProductRepository();
    private static final IUserService userService = new UserService();

    @Override
    public void create(String name, String description,
                       int numberInStock, double minPrice, double currPrice,
                       String usernameCreatedBy) {
        Product product = new Product(name, description, numberInStock,
                BigDecimal.valueOf(minPrice), BigDecimal.valueOf(currPrice),
                false);

        User createdAndLastModifiedBy = userService.getByUsername(usernameCreatedBy);
        product.setCreatedBy(createdAndLastModifiedBy);
        product.setLastModifiedBy(createdAndLastModifiedBy);

        productRepository.create(product);
    }

    @Override
    public Product update(String name, String description,
                          int numberInStock, double minPrice, double currPrice,
                          boolean isPartFromBlackFridayCampaign,
                          String usernameLastModifiedBy) {
        Product product = this.getProductAndSetLastModifiedBy(name, usernameLastModifiedBy);
        product.setName(name);
        product.setDescription(description);
        product.setNumberInStock(numberInStock);
        product.setMinPrice(BigDecimal.valueOf(minPrice));
        product.setCurrPrice(BigDecimal.valueOf(currPrice));
        product.setPartFromBlackFridayCampaign(isPartFromBlackFridayCampaign);

        return productRepository.update(product);
    }

    @Override
    public Optional<Product> getById(UUID id) {
        return productRepository.getById(id);
    }

    @Override
    public List<Product> getAll() {
        return productRepository.getAll();
    }

    @Override
    public Product findByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public void deleteById(UUID id) {
        productRepository.deleteById(id);
    }

    @Override
    public void updateMinPrice(String name, double newMinPrice, String usernameLastModifiedBy) {
        Product product = this.getProductAndSetLastModifiedBy(name, usernameLastModifiedBy);
        product.setMinPrice(BigDecimal.valueOf(newMinPrice));

        productRepository.update(product);
    }

    @Override
    public void updateCurrPrice(String name, double newCurrPrice, String usernameLastModifiedBy) {
        Product product = this.getProductAndSetLastModifiedBy(name, usernameLastModifiedBy);
        BigDecimal newCurrPriceAsBigDecimal = BigDecimal.valueOf(newCurrPrice);
        if(newCurrPriceAsBigDecimal.compareTo(product.getMinPrice()) < 0) throw new RuntimeException("Current price must be greater than or equal to minimum price");

        product.setCurrPrice(newCurrPriceAsBigDecimal);

        productRepository.update(product);
    }

    @Override
    public void reduceNumberInStock(String name, int quantity, String usernameLastModifiedBy) {
        Product product = this.getProductAndSetLastModifiedBy(name, usernameLastModifiedBy);

        int newNumberInStock = product.getNumberInStock() - quantity;
        if(newNumberInStock < 0) throw new RuntimeException("Insufficient product quantity");

        product.setNumberInStock(newNumberInStock);

        productRepository.update(product);
    }

    @Override
    public void addToBlackFridayCampaign(String name, String usernameLastModifiedBy) {
        Product product = this.getProductAndSetLastModifiedBy(name, usernameLastModifiedBy);
        product.setPartFromBlackFridayCampaign(true);

        productRepository.update(product);
    }

    @Override
    public void removeToBlackFridayCampaign(String name, String usernameLastModifiedBy) {
        Product product = this.getProductAndSetLastModifiedBy(name, usernameLastModifiedBy);
        product.setPartFromBlackFridayCampaign(false);

        productRepository.update(product);
    }

    private Product getProductAndSetLastModifiedBy(String name, String usernameLastModifiedBy) {
        Product product = productRepository.findByName(name);

        User lastModifiedBy = userService.getByUsername(usernameLastModifiedBy);
        product.setLastModifiedBy(lastModifiedBy);

        return product;
    }
}