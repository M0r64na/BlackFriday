package application.service;

import application.service.interfaces.IRoleService;
import data.model.entity.Role;
import data.model.entity.enums.RoleName;
import data.repository.RoleRepository;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

public class RoleService implements IRoleService {
    private final RoleRepository roleRepository;

    @Inject
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findRoleByName(RoleName name) {
        return this.roleRepository.findByName(name);
    }

    @Override
    @PostConstruct
    public void initializeRoles() {
        if(!this.roleRepository.getAll().isEmpty()) return;

        this.roleRepository.create(new Role(RoleName.CLIENT));
        this.roleRepository.create(new Role(RoleName.EMPLOYEE));
    }
}