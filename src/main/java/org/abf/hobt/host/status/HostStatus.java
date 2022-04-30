package org.abf.hobt.host.status;

import org.abf.hobt.account.AccountAuthorization;
import org.abf.hobt.dao.DAOFactory;
import org.abf.hobt.dao.hibernate.OrganismDAO;
import org.abf.hobt.dao.hibernate.TierDAO;
import org.abf.hobt.dao.model.OrganismModel;
import org.abf.hobt.dao.model.TierModel;
import org.abf.hobt.dao.model.TierStatusModel;
import org.abf.hobt.dto.TierStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HostStatus {

    private final OrganismModel organismModel;
    private final String userId;
    private final AccountAuthorization accountAuthorization;
    private final OrganismDAO dao;
    private final TierDAO tierDAO;

    public HostStatus(long hid, String userId) {
        this.userId = userId;
        this.accountAuthorization = new AccountAuthorization();
        this.dao = DAOFactory.getOrganismDAO();
        this.tierDAO = DAOFactory.getTierDAO();
        this.organismModel = this.dao.get(hid);
        if (organismModel == null)
            throw new IllegalArgumentException("Invalid organism id: " + hid);
    }

    public TierStatus update(TierStatus tierStatus) {
        this.accountAuthorization.expectAdmin(this.userId); // todo : use custom permissions

        // check for valid Organism and Tier
        TierModel tierModel = this.tierDAO.get(tierStatus.getTierId());
        if (tierModel == null)
            throw new IllegalArgumentException("Invalid tier id: " + tierStatus.getTierId());

        if (this.organismModel.getId() != tierStatus.getHostId())
            throw new IllegalArgumentException("Incompatible organism");

        // check for existence of tier status
        TierStatusModel tierStatusModel = new TierStatusModel();
        tierStatusModel.setTier(tierModel);
        tierStatusModel.setComplete(tierStatus.isComplete());
        tierStatusModel.setOrganism(organismModel);
        tierStatusModel = DAOFactory.getTierStatusDAO().create(tierStatusModel);

        // set the next tier for organism
        setCurrentOrganismTier(tierModel, tierStatus.isComplete());

        return tierStatusModel.toDataTransferObject();
    }

    /**
     * Determine what the current tier for organism is
     */
    private void setCurrentOrganismTier(TierModel tierModel, boolean isComplete) {
        if (isComplete) {
            // set the tier after this one
//            TierModel nextModel = this.tierDAO.getAfter(tierModel.getId());
//            if (nextModel != null) {
            organismModel.setTier(tierModel);
            organismModel.setLastUpdateTime(new Date());
            this.dao.update(organismModel);
        }
//        } else {
//            // set the current tier as tier for organism
//            organismModel.setTier(tierModel);
//            organismModel.setLastUpdateTime(new Date());
//            this.dao.update(organismModel);
//        }
    }

    public List<TierStatus> get() {
        this.accountAuthorization.expectAdmin(this.userId); // todo : use custom permissions

        List<TierStatus> list = new ArrayList<>();

        for (TierStatusModel model : organismModel.getTierStatus()) {
            list.add(model.toDataTransferObject());
        }
        return list;
    }
}
