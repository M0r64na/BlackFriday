package application.service.interfaces;

import data.model.entity.Role;
import data.model.entity.enums.RoleName;

public interface IRoleService {
    public Role findByName(RoleName name);
    public void initialize();
}