package data.repository.interfaces;

import data.model.entity.Product;
import data.repository.base.IRepository;

public interface IProductRepository extends IRepository<Product> {
    Product findByName(String name);
}