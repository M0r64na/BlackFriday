package application.service.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface ICampaignService extends Remote {
    void startCampaign(String username, Map<String, Double> productNamesAndDiscountPercentages) throws RemoteException;
    void stopCurrentCampaign(String username) throws RemoteException;
}