package org.abf.hobt.host;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.abf.hobt.IceApiClient;
import org.abf.hobt.cache.ElementCacheType;
import org.abf.hobt.cache.ElementCaches;
import org.abf.hobt.dto.PartStudy;
import org.abf.hobt.service.ice.entry.PartData;
import org.abf.hobt.service.ice.search.SearchResult;
import org.abf.hobt.service.ice.search.SearchResults;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class HostExperiments {

    private final IceApiClient client;

    public HostExperiments() {
        this.client = IceApiClient.getInstance();
    }

    public List<PartStudy> get(long hostId) {
        List<PartStudy> studies = new ArrayList<>();
        // get parts for this host
        HostParts hostParts = new HostParts(null);
        SearchResults searchResults = hostParts.get(hostId, false, 0, Integer.MAX_VALUE, false);
        SearchResults searchResults2 = hostParts.get(hostId, true, 0, Integer.MAX_VALUE, false);
        if (searchResults == null && searchResults2 == null)
            return studies;

        if (searchResults == null) searchResults = new SearchResults();
        if (searchResults2 == null) searchResults2 = new SearchResults();

        long totalCount = searchResults2.getResultCount() + searchResults.getResultCount();
        new ElementCaches(hostId).updateStatistics(ElementCacheType.EXPERIMENT, totalCount);

        searchResults.getResults().addAll(searchResults2.getResults());

        HashMap<String, String> dedupeSet = new LinkedHashMap<>();

        // get experiments for each ice entry
        for (SearchResult result : searchResults.getResults()) {
            PartData partData = result.getEntryInfo();
            if (partData == null)
                continue;

            List<PartStudy> experimentData = experimentData(partData.getId());      // todo : paging
            if (experimentData == null || experimentData.isEmpty())
                continue;

            // de-dupe
            for (PartStudy study : experimentData) {
                String setLabel = dedupeSet.get(study.getUrl().trim());
                if (!study.getLabel().equalsIgnoreCase(setLabel)) {
                    dedupeSet.put(study.getUrl().trim(), study.getLabel().trim());
                    studies.add(study);
                }
            }
        }

        new ElementCaches(hostId).updateStatistics(ElementCacheType.EXPERIMENT, studies.size());
        return studies;
    }

    public List<PartStudy> experimentData(long id) {
        List<?> list = this.client.get("/rest/parts/" + id + "/experiments", List.class, null);

        final Type partStudyType = new TypeToken<ArrayList<PartStudy>>() {
        }.getType();
        final Gson gson = new GsonBuilder().create();
        return gson.fromJson(gson.toJsonTree(list), partStudyType);
    }
}
