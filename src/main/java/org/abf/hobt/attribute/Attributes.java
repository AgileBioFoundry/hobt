package org.abf.hobt.attribute;

import org.abf.hobt.account.AccountAuthorization;
import org.abf.hobt.common.ResultData;
import org.abf.hobt.dao.DAOFactory;
import org.abf.hobt.dao.hibernate.OrganismAttributeDAO;
import org.abf.hobt.dao.hibernate.OrganismDAO;
import org.abf.hobt.dao.model.AccountModel;
import org.abf.hobt.dao.model.OrganismAttributeModel;
import org.abf.hobt.dao.model.OrganismModel;
import org.abf.hobt.dto.Organism;
import org.abf.hobt.dto.OrganismAttribute;

import java.util.Date;
import java.util.List;

public class Attributes {

    private final OrganismAttributeDAO dao;
    private final AccountAuthorization authorization;

    public Attributes() {
        this.dao = DAOFactory.getOrganismAttributeDAO();
        this.authorization = new AccountAuthorization();
    }

    public OrganismAttribute create(String userId, OrganismAttribute organismAttribute) {
        this.authorization.expectAdmin(userId);
        AccountModel accountModel = DAOFactory.getAccountDAO().getByUserId(userId);

        OrganismAttributeModel model = new OrganismAttributeModel();
        model.setCreated(new Date());
        model.setAccount(accountModel);
        model.setAllOrganisms(organismAttribute.isAllOrganisms());
        model.setRequired(organismAttribute.isRequired());
        model.setLabel(organismAttribute.getLabel());
        model.setType(organismAttribute.getType());

        if (!organismAttribute.isAllOrganisms()) {
            OrganismDAO organismDAO = DAOFactory.getOrganismDAO();
            for (Organism organism : organismAttribute.getHosts()) {
                OrganismModel organismModel = organismDAO.get(organism.getId());
                if (organismModel == null)
                    continue;

                model.getOrganisms().add(organismModel);
            }
        }
        model = this.dao.create(model);
        return model.toDataTransferObject();
    }

    public ResultData<OrganismAttribute> get(String userId, int offset, int limit, boolean asc) {
        this.authorization.expectAdmin(userId);

        ResultData<OrganismAttribute> result = new ResultData<>();
        long count = this.dao.getAvailableOrganismAttributeCount();
        result.setAvailable(count);
        List<OrganismAttributeModel> list = this.dao.pageAttributes(offset, limit, asc, "id");
        for (OrganismAttributeModel model : list) {
            result.getRequested().add(model.toDataTransferObject());
        }

        return result;
    }

    public OrganismAttribute update(long id, String userId, OrganismAttribute attribute) {
        this.authorization.expectAdmin(userId);
        OrganismAttributeModel model = this.dao.get(id);
        model.setLabel(attribute.getLabel());
        model.setRequired(attribute.isRequired());
        return this.dao.update(model).toDataTransferObject();
    }
}
