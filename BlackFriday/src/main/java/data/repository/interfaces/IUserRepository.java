package data.repository.interfaces;

import data.model.entity.User;
import data.repository.base.IRepository;

public interface IUserRepository extends IRepository<User> {
    User getByUsername(String username);
}