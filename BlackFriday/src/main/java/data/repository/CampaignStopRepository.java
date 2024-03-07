package data.repository;

import data.model.entity.CampaignStop;
import data.repository.base.RepositoryBase;
import data.repository.interfaces.ICampaignStopRepository;
import org.hibernate.Session;
import java.util.UUID;

public class CampaignStopRepository extends RepositoryBase<CampaignStop> implements ICampaignStopRepository {
    @Override
    public CampaignStop findByCampaignId(UUID campaignId) {
        Session newSession = sessionFactory.openSession();

        CampaignStop res = newSession
                .createQuery("FROM CampaignStop cs WHERE cs.campaign.id =: id", CampaignStop.class)
                .setParameter("id", campaignId)
                .uniqueResult();

        newSession.close();

        return res;
    }
}