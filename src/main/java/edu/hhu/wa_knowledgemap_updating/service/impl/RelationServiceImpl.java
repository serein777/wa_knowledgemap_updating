package edu.hhu.wa_knowledgemap_updating.service.impl;

import edu.hhu.wa_knowledgemap_updating.entity.Node;

import  edu.hhu.wa_knowledgemap_updating.entity.Relation;
import edu.hhu.wa_knowledgemap_updating.repository.NodeRepository;
import edu.hhu.wa_knowledgemap_updating.repository.RelationRepository;
import edu.hhu.wa_knowledgemap_updating.service.RelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RelationServiceImpl implements RelationService {
    @Autowired
    NodeRepository nodeRepository;
    @Autowired
    RelationRepository relationRepository;
    @Override
    public void bind(String name1, String name2, String relationName) {
        log.info("enter service");
        Node emiya = nodeRepository.findByName("Emiya");
        log.info("{}"+emiya);
        Node start = nodeRepository.findByName(name1);
        Node end = nodeRepository.findByName(name2);
        log.info("{}"+start);
        log.info("{}"+end);
        Relation relation =new Relation();
        relation.setStartNode(start);
        relation.setEndNode(end);
        relation.setRelation(relationName);
        relationRepository.save(relation);
    }
}
