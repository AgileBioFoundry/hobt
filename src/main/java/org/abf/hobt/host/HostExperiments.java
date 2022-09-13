package org.abf.hobt.host;

import org.abf.hobt.IceApiClient;
import org.abf.hobt.cache.ElementCacheType;
import org.abf.hobt.cache.ElementCaches;
import org.abf.hobt.dto.PartStudy;
import org.abf.hobt.service.ice.entry.PartData;
import org.abf.hobt.service.ice.search.SearchResult;
import org.abf.hobt.service.ice.search.SearchResults;

import java.util.ArrayList;
import java.util.List;

public class HostExperiments {

    private final IceApiClient client;

    public HostExperiments() {
        this.client = IceApiClient.getInstance();
    }

    public List<PartStudy> get(long hostId) {
        List<PartStudy> studies = new ArrayList<>();
        // get parts for this host
        HostParts hostParts = new HostParts();
        SearchResults searchResults = hostParts.get(hostId, false, 0, Integer.MAX_VALUE, false);
        SearchResults searchResults2 = hostParts.get(hostId, true, 0, Integer.MAX_VALUE, false);
        if (searchResults == null && searchResults2 == null)
            return studies;

        if (searchResults == null) searchResults = new SearchResults();
        if (searchResults2 == null) searchResults2 = new SearchResults();

        long totalCount = searchResults2.getResultCount() + searchResults.getResultCount();
        new ElementCaches(hostId).updateStatistics(ElementCacheType.EXPERIMENT, totalCount);

        searchResults.getResults().addAll(searchResults2.getResults());

        // get experiments for each ice entry
        for (SearchResult result : searchResults.getResults()) {
            PartData partData = result.getEntryInfo();
            if (partData == null)
                continue;

            List<PartStudy> experimentData = experimentData(partData.getId());      // todo : paging
            if (experimentData == null)
                continue;

            studies.addAll(experimentData);
        }

        new ElementCaches(hostId).updateStatistics(ElementCacheType.EXPERIMENT, studies.size());
        return studies;
    }

    public List<PartStudy> experimentData(long id) {
        return this.client.get("/rest/parts/" + id + "/experiments", List.class, null);
    }
}
