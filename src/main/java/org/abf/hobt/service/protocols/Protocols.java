package org.abf.hobt.service.protocols;

import org.abf.hobt.common.ResultData;
import org.abf.hobt.dao.DAOFactory;
import org.abf.hobt.dao.model.OrganismModel;
import org.abf.hobt.dto.Protocol;

import java.util.HashMap;
import java.util.Map;

public class Protocols {

    private static final String FILTER = "filter";
    private static final String SEARCH_KEY = "key";
    private static final String ORDER_FIELD = "order_field"; // activity (default), relevance, date, name, id
    private static final String ORDER_DIR = "order_dir";
    private static final String FIELDS = "fields";  // list of fields from "item". comma separated
    private static final String PAGE_SIZE = "page_size"; // number of items per one page 1...100. default is 10
    private static final String PAGE_ID = "page_id"; // id of page 1...n (default is 1)

    public Protocols() {
    }

    /**
     * Retrieves (paged) protocols (currently from protocols.io) for the specified organism. Retrieval is done using
     * the name of the organism as the search term
     *
     * @param organismId
     * @param start
     * @param limit
     * @param sort
     * @param asc
     * @return
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

        return ProtocolsIORestClient.getInstance().get(callParameters);
    }
}
