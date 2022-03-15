package org.abf.hobt.host.publication;

import org.abf.hobt.common.ResultData;
import org.abf.hobt.dao.DAOFactory;
import org.abf.hobt.dao.hibernate.PublicationDAO;
import org.abf.hobt.dao.model.OrganismModel;
import org.abf.hobt.dao.model.PublicationModel;

import java.util.List;

public class Publications {

    private final PublicationDAO dao;

    public Publications() {
        this.dao = DAOFactory.getPublicationDAO();
    }

    public ResultData<Publication> getAll(int offset, int limit, String sort, boolean asc) {
        List<PublicationModel> models = this.dao.list(sort, asc, offset, limit);
        ResultData<Publication> resultData = new ResultData<>();
        for (PublicationModel model : models) {
            resultData.getRequested().add(model.toDataTransferObject());
        }
        resultData.setAvailable(this.dao.listCount());
        return resultData;
    }

    public ResultData<Publication> getByOrganism(long orgId, int offset, int limit, String sort, boolean asc,
                                                 Boolean isPrivileged) {
        OrganismModel organism = DAOFactory.getOrganismDAO().get(orgId);
        if (organism == null)
            throw new IllegalArgumentException("Cannot retrieve publications for null organism");

        long count = this.dao.listByOrganismCount(isPrivileged);
        ResultData<Publication> resultData = new ResultData<>();
        resultData.setAvailable(count);
        List<PublicationModel> list = dao.listByOrganism(organism, sort, asc, offset, limit, isPrivileged);
        for (PublicationModel model : list) {
            resultData.getRequested().add(model.toDataTransferObject());
        }
        return resultData;
    }
}
