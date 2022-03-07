package org.abf.hobt.service.ice.search;

import org.abf.hobt.service.ice.IDataTransferObject;

import java.util.LinkedList;
import java.util.List;

/**
 * Wrapper around a list of search results which also contains information about the search.
 * Information such as query, result count
 *
 * @author Hector Plahar
 */
public class SearchResults implements IDataTransferObject {

    private long resultCount;
    private LinkedList<SearchResult> results;
    private SearchQuery query;

    public SearchResults() {
        results = new LinkedList<>();
    }

    public LinkedList<SearchResult> getResults() {
        return this.results;
    }

    public void setResults(List<SearchResult> results) {
        if (this.results == null)
            this.results = new LinkedList<>();

        this.results.clear();
        this.results.addAll(results);
    }

    public void setResultCount(long count) {
        this.resultCount = count;
    }

    /**
     * @return total query result count. not just the count of results returned
     */
    public long getResultCount() {
        return this.resultCount;
    }

    public SearchQuery getQuery() {
        return query;
    }

    public void setQuery(SearchQuery query) {
        this.query = query;
    }
}
