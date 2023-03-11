package edu.hhu.wa_knowledgemap_updating.controller;

import edu.hhu.wa_knowledgemap_updating.entity.ReservoirNode;
import edu.hhu.wa_knowledgemap_updating.repository.ReservoirRepository;
import edu.hhu.wa_knowledgemap_updating.service.ReservoirService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ReservoirController {
    @Autowired
    ReservoirService reservoirService;
    @Autowired
    ReservoirRepository repository;
    @RequestMapping("/reservoir/map/list")
    @ResponseBody
    public List<ReservoirNode> getAllReservoirNode(){
        return reservoirService.getAllReservoirNode();
    }
    @RequestMapping("/reservoir/map/update")
    @ResponseBody
    public String update(){
        return "ok";
    }
}
