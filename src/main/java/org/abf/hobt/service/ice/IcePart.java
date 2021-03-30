package org.abf.hobt.service.ice;

import org.abf.hobt.IceApiClient;
import org.abf.hobt.common.logging.Logger;
import org.abf.hobt.service.ice.entry.EntryType;
import org.abf.hobt.service.ice.search.SearchQuery;
import org.abf.hobt.service.ice.search.SearchResults;

import java.util.Collections;

/**
 * Part in the ICE registry
 *
 * @author Hector Plahar
 */
public class IcePart {

    private final IceApiClient client;

    public IcePart() {
        this.client = IceApiClient.getInstance();
    }

    public static void main(String[] args) throws Exception {
        IcePart part = new IcePart();
        part.search("host");
    }

    public void search(String term) {
        SearchQuery query = new SearchQuery();
        query.setEntryTypes(Collections.singletonList(EntryType.STRAIN));
        query.setQueryString(term);
        SearchResults results = this.client.post("/rest/search", query, SearchResults.class);
        Logger.info(results.getResultCount() + "");
    }
}





