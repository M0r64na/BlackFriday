package data.repository.interfaces;

import data.model.entity.Role;
import data.model.entity.enums.RoleName;
import data.repository.base.IRepository;

public interface IRoleRepository extends IRepository<Role> {
    Role findByName(RoleName name);
}