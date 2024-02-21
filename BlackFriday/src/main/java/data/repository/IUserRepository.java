package data.repository;

import data.model.entity.User;
import data.repository.base.IRepository;

// Repository methods specific for the user entity
public interface IUserRepository extends IRepository<User> {
    // TODO hash the user password using the Argon2id algorithm (argon2-jvm) and override the create and update methods
}