package application.service.interfaces;

import data.model.entity.User;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

// TODO add dto objects
public interface IUserService extends Remote {
    void createUser(String username, String password) throws RemoteException;
    User updateUser(String username, String password) throws RemoteException;
    Optional<User> getUserById(UUID id) throws RemoteException;
    List<User> getAllUsers() throws RemoteException;
    void deleteUserById(UUID id) throws RemoteException;
    User getUserByUsername(String username) throws RemoteException;
    void initializeUsers() throws RemoteException;
}