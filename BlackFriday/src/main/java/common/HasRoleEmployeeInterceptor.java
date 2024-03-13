package common;

import common.interfaces.IUserLoginService;
import data.model.entity.enums.RoleName;
import javax.security.auth.Subject;
import java.lang.reflect.Method;
import java.security.Principal;
import java.util.Set;
import java.lang.reflect.InvocationHandler;

public class HasRoleEmployeeInterceptor implements InvocationHandler {
    private final IUserLoginService userLoginService;

    public HasRoleEmployeeInterceptor(IUserLoginService userLoginService) {
        this.userLoginService = userLoginService;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Subject subject = this.userLoginService.getSubject();

        if(subject != null) {
            Set<Principal> principals = subject.getPrincipals();
            boolean hasRoleEmployee = false;

            for(Principal principal : principals) {
                if(principal instanceof RolePrincipal && principal.getName().equals(RoleName.EMPLOYEE.name()))
                {
                    hasRoleEmployee = true;
                    break;
                }
            }

            if(!hasRoleEmployee) throw new SecurityException("Access denied. Employee role required");

            return method.invoke(proxy, args);
        }

        throw new SecurityException("No logged in user present");
    }
}
