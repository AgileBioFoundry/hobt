package org.abf.hobt.host;

import org.abf.hobt.dao.DAOFactory;
import org.abf.hobt.dao.model.OrganismModel;
import org.abf.hobt.service.ice.IceParts;
import org.abf.hobt.service.ice.search.SearchQuery;
import org.abf.hobt.service.ice.search.SearchResults;

public class HostParts {

    public SearchResults get(long orgId) {

        OrganismModel organismModel = DAOFactory.getOrganismDAO().get(orgId);
        SearchQuery searchQuery = new SearchQuery();

        if (organismModel != null) {
            searchQuery.setQueryString(organismModel.getName());
        }

        IceParts iceParts = new IceParts();
        return iceParts.search(searchQuery);
    }
}
