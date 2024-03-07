package application.service.interfaces;

import java.util.Map;

public interface ICampaignService {
    void startCampaign(String username, Map<String, Double> productNamesAndDiscountPercentages);
    void stopCurrentCampaign(String username);
}