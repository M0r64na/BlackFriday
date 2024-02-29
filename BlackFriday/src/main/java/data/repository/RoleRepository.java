package data.repository;

import data.model.entity.Role;
import data.model.entity.RoleName;
import data.repository.base.RepositoryBase;
import org.hibernate.Session;

public class RoleRepository extends RepositoryBase<Role> {
    public RoleRepository() {
        super();
    }

    public Role findByName(RoleName name) {
        Session newSession = sessionFactory.openSession();

        Role res = newSession
                .createQuery("FROM Role r WHERE r.name =: name", Role.class)
                .setParameter("name", name)
                .uniqueResult();

        newSession.close();

        return res;
    }
}