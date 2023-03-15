package edu.hhu.wa_knowledgemap_updating.controller;

import edu.hhu.wa_knowledgemap_updating.entity.ReservoirNode;
import edu.hhu.wa_knowledgemap_updating.entity.RespBean;
import edu.hhu.wa_knowledgemap_updating.repository.ReservoirRepository;
import edu.hhu.wa_knowledgemap_updating.service.ReservoirService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class ReservoirNodeController {
    @Autowired
    ReservoirService reservoirService;
    @Autowired
    ReservoirRepository repository;
    @RequestMapping("/reservoir/map/list")
    @ResponseBody
    public RespBean getAllReservoirNode(){
        List<ReservoirNode> list = reservoirService.getAllReservoirNode();
        return  RespBean.success(list);
    }
    @RequestMapping("/reservoir/map/find")
    @ResponseBody
    public RespBean findByName(HttpServletRequest request, HttpServletResponse response){
        String name=request.getParameter("name");
        if(name==null||name.trim().length()==0){
            response.setStatus(400);
            return  new RespBean(400L,"bad request name为空",null);
        }
        ReservoirNode node = reservoirService.findNodeByName(name);
        return  RespBean.success(node);
    }
}
