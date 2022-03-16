package org.abf.hobt.host.publication;

import org.abf.hobt.common.ResultData;
import org.abf.hobt.common.util.StringUtils;
import org.abf.hobt.dao.DAOFactory;
import org.abf.hobt.dao.hibernate.PublicationDAO;
import org.abf.hobt.dao.model.OrganismModel;
import org.abf.hobt.dao.model.PublicationModel;

import java.util.Date;
import java.util.List;

public class HostPublications {

    private final long hostId;
    private final String userId;
    private final PublicationDAO dao;

    public HostPublications(long hostId, String userId) {
        this.hostId = hostId;
        this.userId = userId;
        this.dao = DAOFactory.getPublicationDAO();
    }

    /**
     * Retrieve list of organisms for specified host
     *
     * @return list of available organism
     */
    public ResultData<Publication> list(int start, int limit, boolean asc, Boolean isPrivileged) {
        OrganismModel organismModel = DAOFactory.getOrganismDAO().get(this.hostId);
        List<PublicationModel> models = this.dao.listByOrganism(organismModel, "id", asc, start, limit, isPrivileged);
        ResultData<Publication> resultData = new ResultData<>();
        resultData.setAvailable(this.dao.listByOrganismCount(organismModel, isPrivileged));
        for (PublicationModel model : models) {
            resultData.getRequested().add(model.toDataTransferObject());
        }

        return resultData;
    }

    public Publication create(Publication publication) {
        if (!this.validatePublication(publication))
            throw new IllegalArgumentException("Missing fields in publication");

        OrganismModel organismModel = DAOFactory.getOrganismDAO().get(this.hostId);
        if (organismModel == null)
            throw new IllegalArgumentException("Cannot retrieve organism for host " + this.hostId);

        // todo : check that user can create permission
        PublicationModel publicationModel = new PublicationModel();
        publicationModel.setCreated(new Date());
        publicationModel.setAuthors(publication.getAuthors());
        publicationModel.setJournal(publication.getJournal());
        publicationModel.setTitle(publication.getTitle());
        publicationModel.setPrivileged(publication.isPrivileged());
        publicationModel.setLink(publication.getLink());
        publicationModel.setYear(publication.getYear());

        publicationModel.getOrganisms().add(organismModel);

        return this.dao.create(publicationModel).toDataTransferObject();
    }

    private boolean validatePublication(Publication publication) {
        if (StringUtils.isBlank(publication.getAuthors()))
            return false;

        if (StringUtils.isBlank(publication.getJournal()))
            return false;

        if (StringUtils.isBlank(publication.getLink()))
            return false;

        if (StringUtils.isBlank(publication.getTitle()))
            return false;

        if (StringUtils.isBlank(publication.getYear()))
            return false;

        return true;
    }
}
