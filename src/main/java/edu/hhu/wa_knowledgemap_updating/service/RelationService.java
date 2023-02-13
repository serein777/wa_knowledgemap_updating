package edu.hhu.wa_knowledgemap_updating.service;

import edu.hhu.wa_knowledgemap_updating.entity.Node;
import edu.hhu.wa_knowledgemap_updating.entity.Relation;

public interface RelationService {
    public void bind(String name1, String name2, String relationName);
}
