package data.repository;

import data.model.entity.Role;
import data.model.entity.enums.RoleName;
import data.repository.base.RepositoryBase;
import data.repository.interfaces.IRoleRepository;
import org.hibernate.Session;

public class RoleRepository extends RepositoryBase<Role> implements IRoleRepository {
    @Override
    public Role findByName(RoleName name) {
        Session newSession = sessionFactory.openSession();

        Role res = newSession
                .createQuery("FROM Role r WHERE r.roleName =: name", Role.class)
                .setParameter("name", name)
                .uniqueResult();

        newSession.close();

        return res;
    }
}