package application.service.interfaces;

import data.model.entity.Role;
import data.model.entity.enums.RoleName;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IRoleService {
    Optional<Role> getById(UUID id);
    List<Role> getAll();
    public Role findByName(RoleName name);
    public void initialize();
}