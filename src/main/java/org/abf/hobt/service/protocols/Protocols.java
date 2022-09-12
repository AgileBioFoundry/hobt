package org.abf.hobt.service.protocols;

import org.abf.hobt.cache.ElementCacheType;
import org.abf.hobt.cache.ElementCaches;
import org.abf.hobt.common.ResultData;
import org.abf.hobt.dao.DAOFactory;
import org.abf.hobt.dao.model.OrganismModel;
import org.abf.hobt.dto.Protocol;

import java.util.HashMap;
import java.util.Map;

public class Protocols {

    private static final String FILTER = "filter";          // protocols filter (currently retrieving only 'public')
    private static final String SEARCH_KEY = "key";         // search term for searching protocol names, desc, authors
    private static final String ORDER_FIELD = "order_field"; // activity (default), relevance, date, name, id
    private static final String ORDER_DIR = "order_dir";     // asc or desc (default)
    private static final String FIELDS = "fields";           // list of fields from "item". comma separated
    private static final String PAGE_SIZE = "page_size";    // number of items per one page 1...100. default is 10
    private static final String PAGE_ID = "page_id";        // id of page 1...n (default is 1)

    public Protocols() {
    }

    /**
     * Retrieves (paged) protocols (currently from protocols.io) for the specified organism. Retrieval is done using
     * the name of the organism as the search term. The number available is cached
     *
     * @param organismId unique identifier for host organism
     * @param start      start offset
     * @param limit      max results to return
     * @param sort       sort field
     * @param asc        sort order
     * @return wrapper around list of retrieved protocols and available count
     */
    public ResultData<Protocol> getByOrganism(long organismId, int start, int limit, String sort, boolean asc) {
        // retrieve
        OrganismModel model = DAOFactory.getOrganismDAO().get(organismId);
        if (model == null)
            throw new IllegalArgumentException("Invalid organism id: " + organismId);

        Map<String, String> callParameters = new HashMap<>();
        callParameters.put(FILTER, "public");
        callParameters.put(SEARCH_KEY, model.getName());
        callParameters.put(ORDER_FIELD, "relevance");
        callParameters.put(ORDER_DIR, asc ? "asc" : "desc");
        callParameters.put(PAGE_SIZE, Integer.toString(limit));
        callParameters.put(PAGE_ID, Integer.toString(start + 1));

        ResultData<Protocol> resultData = ProtocolsIORestClient.getInstance().get(callParameters);

        // update cache
        new ElementCaches(organismId).updateStatistics(ElementCacheType.PROTOCOL, resultData.getAvailable());
        return resultData;
    }
}
