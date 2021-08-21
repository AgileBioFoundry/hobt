package org.abf.hobt.host;

import org.abf.hobt.dao.DAOFactory;
import org.abf.hobt.dao.hibernate.OrganismDAO;
import org.abf.hobt.dao.model.OrganismModel;

/**
 * @author Hector Plahar
 * <p>
 * Represents a host that has potentially completed a tier. Contains algorithms for determining this
 * and routines for the subsequent steps if this is the case
 */
public class HostTierCompletion {

    private OrganismModel organismModel;
    private OrganismDAO dao;

    public HostTierCompletion(long hostId) {
        this.dao = DAOFactory.getOrganismDAO();
        this.organismModel = this.dao.get(hostId);
    }
}
