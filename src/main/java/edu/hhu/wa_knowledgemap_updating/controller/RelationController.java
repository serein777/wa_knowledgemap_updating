package edu.hhu.wa_knowledgemap_updating.controller;

import edu.hhu.wa_knowledgemap_updating.dto.RelationDto;
import edu.hhu.wa_knowledgemap_updating.service.impl.RelationServiceImpl;
import jdk.nashorn.internal.runtime.logging.Logger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Slf4j
public class RelationController {
    @Autowired
    RelationServiceImpl relationServiceImpl;
    @RequestMapping(path = "/relation/add",method = RequestMethod.POST)
    public void addRelation(@RequestBody RelationDto relationDto){
        log.info("enter:relation/add");
        log.info("relationDto"+relationDto);
        relationServiceImpl.bind(relationDto.getStartNodeName(),relationDto.getEndNodeName(),relationDto.getRelation());
    }
}
