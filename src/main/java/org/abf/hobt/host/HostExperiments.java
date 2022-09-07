package org.abf.hobt.host;

import org.abf.hobt.IceApiClient;
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
        SearchResults searchResults = hostParts.get(hostId, false);
        if (searchResults == null || searchResults.getResultCount() == 0)
            return studies;


        // get experiments for each ice entry
        for (SearchResult result : searchResults.getResults()) {
            PartData partData = result.getEntryInfo();
            if (partData == null)
                continue;

            List<PartStudy> experimentData = experimentData(partData.getId());
            if (experimentData == null)
                continue;

            studies.addAll(experimentData);
        }
        return studies;
    }

    public List<PartStudy> experimentData(long id) {
        return this.client.get("/rest/parts/" + id + "/experiments", List.class, null);
    }
}
