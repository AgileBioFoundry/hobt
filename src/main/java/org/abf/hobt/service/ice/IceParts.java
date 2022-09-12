package org.abf.hobt.service.ice;

import org.abf.hobt.IceApiClient;
import org.abf.hobt.service.ice.search.SearchQuery;
import org.abf.hobt.service.ice.search.SearchResults;

/**
 * Part in the ICE registry
 *
 * @author Hector Plahar
 */
public class IceParts {

    private final IceApiClient client;

    public IceParts() {
        this.client = IceApiClient.getInstance();
    }

    public SearchResults search(SearchQuery query) {
        return this.client.post("/rest/search", query, SearchResults.class);
    }
}





