package application.service.interfaces;

import data.model.entity.Status;
import data.model.entity.enums.OrderStatus;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IStatusService extends Remote {
    Status findStatusByName(OrderStatus orderStatus) throws RemoteException;
    void initializeStatuses() throws RemoteException;
}