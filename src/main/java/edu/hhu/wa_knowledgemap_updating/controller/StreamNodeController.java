package edu.hhu.wa_knowledgemap_updating.controller;

import edu.hhu.wa_knowledgemap_updating.entity.RespBean;
import edu.hhu.wa_knowledgemap_updating.entity.StreamNode;
import edu.hhu.wa_knowledgemap_updating.service.StreamService;
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
@RequestMapping("/stream/map")
public class StreamNodeController {
    @Autowired
    StreamService streamService;

    @RequestMapping("/list")
    @ResponseBody
    public RespBean list(){
        return  streamService.getAllNode();
    }

    @RequestMapping("/find")
    @ResponseBody
    public RespBean findByKeyWord(HttpServletRequest request, HttpServletResponse response){
        String  keyWord=request.getParameter("keyword");
        if( keyWord==null){
            response.setStatus(400);
            return  new RespBean(400L,"error:keyword is null",null);
        }
        return  streamService.selectByKeyWord( keyWord);
    }
}
