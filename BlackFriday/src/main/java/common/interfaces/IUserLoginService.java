package common.interfaces;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IUserLoginService extends Remote {
    void login() throws LoginException, RemoteException;
    void logout() throws LoginException, RemoteException;
    Subject getSubject() throws RemoteException;
}