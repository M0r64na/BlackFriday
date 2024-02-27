package data.repository;

import data.model.entity.User;
import data.repository.base.RepositoryBase;
import data.util.PasswordEncoder;
import org.hibernate.HibernateException;
import org.hibernate.Session;

public class UserRepository extends RepositoryBase<User> {
    public UserRepository() {
        super();
    }

    @Override
    public void create(User user) throws HibernateException
    {
        String encodedPassword = PasswordEncoder.encodePassword(user.getPassword());
        user.setPassword(encodedPassword);

        Session newSession = sessionFactory.openSession();

        newSession.beginTransaction();
        newSession.persist(user);
        newSession.getTransaction().commit();

        newSession.close();
    }

    @Override
    public User update(User user) throws HibernateException {
        this.updatePassword(user);

        Session newSession = sessionFactory.openSession();

        newSession.beginTransaction();
        User updated = newSession.merge(user);
        newSession.getTransaction().commit();

        newSession.close();

        return updated;
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

    private void updatePassword(User user) {
        String encodedPassword = this.getById(user.getId()).orElseThrow().getPassword();

        if(!encodedPassword.equals(user.getPassword())) {
            encodedPassword = PasswordEncoder.encodePassword(user.getPassword());
            user.setPassword(encodedPassword);
        }
    }
}