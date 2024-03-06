package data.repository;

import data.model.entity.User;
import data.repository.base.RepositoryBase;
import data.repository.interfaces.IUserRepository;
import org.hibernate.Session;

public class UserRepository extends RepositoryBase<User> implements IUserRepository {
    @Override
    public User getByUsername(String username) {
        Session newSession = sessionFactory.openSession();

        User res = newSession
                .createQuery("FROM User u WHERE u.username =: username", User.class)
                .setParameter("username", username)
                .uniqueResult();

        newSession.close();

        return res;
    }
}