package edu.hhu.wa_knowledgemap_updating.controller;

import edu.hhu.wa_knowledgemap_updating.entity.ReservoirNode;
import edu.hhu.wa_knowledgemap_updating.entity.RespBean;
import edu.hhu.wa_knowledgemap_updating.repository.ReservoirRepository;
import edu.hhu.wa_knowledgemap_updating.service.ReservoirService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@CrossOrigin
public class ReservoirNodeController {
    @Autowired
    ReservoirService reservoirService;
    @Autowired
    ReservoirRepository repository;
    @RequestMapping("/reservoir/map/list")
    @ResponseBody
    public RespBean getAllReservoirNode(){
       return   reservoirService.getAllReservoirNode();
    }
    @RequestMapping("/reservoir/map/find")
    @ResponseBody
    public RespBean findByKeyWord(HttpServletRequest request, HttpServletResponse response){
        String  keyWord=request.getParameter("keyword");
        if( keyWord==null){
            response.setStatus(400);
            return  new RespBean(400L,"error:keyword is null",null);
        }
        return reservoirService.selectNodeByKeyWord( keyWord);
    }
}
