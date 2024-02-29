package data.repository;

import data.model.entity.User;
import data.repository.base.RepositoryBase;
import org.hibernate.Session;

public class UserRepository extends RepositoryBase<User> {
    public UserRepository() {
        super();
    }

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