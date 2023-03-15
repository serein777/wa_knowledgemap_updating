package edu.hhu.wa_knowledgemap_updating.service;

import edu.hhu.wa_knowledgemap_updating.dto.StreamInflowKettleDto;

public interface StreamInflowService {
    public boolean updateIncrementalInfo(StreamInflowKettleDto node);
}
