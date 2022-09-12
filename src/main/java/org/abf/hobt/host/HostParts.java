package org.abf.hobt.host;

import org.abf.hobt.cache.ElementCacheType;
import org.abf.hobt.cache.ElementCaches;
import org.abf.hobt.dao.DAOFactory;
import org.abf.hobt.dao.model.OrganismModel;
import org.abf.hobt.service.ice.IceParts;
import org.abf.hobt.service.ice.entry.EntryType;
import org.abf.hobt.service.ice.search.SearchQuery;
import org.abf.hobt.service.ice.search.SearchResults;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HostParts {

    public SearchResults get(long orgId, boolean strainsOnly) {

        OrganismModel organismModel = DAOFactory.getOrganismDAO().get(orgId);
        SearchQuery searchQuery = new SearchQuery();

        if (organismModel != null) {
            searchQuery.setQueryString(organismModel.getName());
        }

        ArrayList<EntryType> types;
        if (strainsOnly) {
            types = new ArrayList<>(List.of(EntryType.STRAIN));
        } else {
            types = new ArrayList<>(Arrays.asList(EntryType.STRAIN, EntryType.PLASMID, EntryType.PART, EntryType.SEED));
        }
        searchQuery.setEntryTypes(types);
        IceParts iceParts = new IceParts();
        SearchResults results = iceParts.search(searchQuery);
        if (results.getResultCount() == 0)
            return results;

        // update stats
        ElementCacheType type = strainsOnly ? ElementCacheType.STRAIN : ElementCacheType.PART;
        new ElementCaches(orgId).updateStatistics(type, results.getResultCount());
        return results;
    }
}
