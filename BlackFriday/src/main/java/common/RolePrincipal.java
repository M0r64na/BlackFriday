package common;

import data.model.entity.Role;
import lombok.AllArgsConstructor;
import java.io.Serializable;
import java.security.Principal;

@AllArgsConstructor
public class RolePrincipal implements Principal, Serializable {
    private Role role;

    @Override
    public String getName() {
        return role.getRoleName().name();
    }
}