package org.abf.hobt.service.ice;

import org.abf.hobt.IceApiClient;
import org.abf.hobt.service.ice.entry.EntryType;
import org.abf.hobt.service.ice.search.SearchQuery;
import org.abf.hobt.service.ice.search.SearchResults;

import java.util.Collections;

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
        query.setEntryTypes(Collections.singletonList(EntryType.STRAIN));
        return this.client.post("/rest/search", query, SearchResults.class);
    }
}





