package edu.hhu.wa_knowledgemap_updating.controller;

import edu.hhu.wa_knowledgemap_updating.entity.StreamNode;
import edu.hhu.wa_knowledgemap_updating.service.StreamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/stream/map")
public class StreamNodeController {
    @Autowired
    StreamService streamService;

    @RequestMapping("/list")
    @ResponseBody
    public List<StreamNode> list(){
        return  streamService.getAllNode();
    }

    @RequestMapping("/find")
    @ResponseBody
    public StreamNode findByName(HttpServletRequest request, HttpServletResponse response){
        String name=request.getParameter("name");
        if(name==null||name.trim().length()==0){
            return null;
        }
        return  streamService.findNodeByName(name);
    }
}
