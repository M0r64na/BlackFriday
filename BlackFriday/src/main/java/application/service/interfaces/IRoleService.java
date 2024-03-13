package application.service.interfaces;

import data.model.entity.Role;
import data.model.entity.enums.RoleName;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRoleService extends Remote {
    public Role findRoleByName(RoleName name) throws RemoteException;
    public void initializeRoles() throws RemoteException;
}