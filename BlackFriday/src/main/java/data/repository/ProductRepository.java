package data.repository;

import data.model.entity.Product;
import data.repository.base.RepositoryBase;
import data.repository.interfaces.IProductRepository;
import org.hibernate.Session;

import java.time.LocalDateTime;

public class ProductRepository extends RepositoryBase<Product> implements IProductRepository {
    @Override
    public Product update(Product product) {
        product.setLastModifiedOn(LocalDateTime.now());
        return super.update(product);
    }

    @Override
    public Product findByName(String name) {
        Session newSession = sessionFactory.openSession();

        Product res = newSession
                .createQuery("FROM Product p WHERE p.name =: name", Product.class)
                .setParameter("name", name)
                .uniqueResult();

        newSession.close();

        return res;
    }
}