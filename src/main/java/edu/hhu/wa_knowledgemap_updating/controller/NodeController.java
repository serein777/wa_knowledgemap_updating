package edu.hhu.wa_knowledgemap_updating.controller;

import edu.hhu.wa_knowledgemap_updating.entity.Node;
import edu.hhu.wa_knowledgemap_updating.service.impl.NodeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class NodeController {
    @Autowired
    NodeServiceImpl nodeServiceImpl;
    @ResponseBody
    @RequestMapping("/node/list")
    public List<Node> getAll(){
        return  nodeServiceImpl.getAll();
    }
}
