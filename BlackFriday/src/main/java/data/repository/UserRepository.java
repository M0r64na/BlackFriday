package data.repository;

import data.model.entity.User;
import data.repository.base.RepositoryBase;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserRepository extends RepositoryBase<User> implements IUserRepository {
}