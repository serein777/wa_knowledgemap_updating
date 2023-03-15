package edu.hhu.wa_knowledgemap_updating.controller;

import edu.hhu.wa_knowledgemap_updating.kettle.KettleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class TestController {
    @Autowired
    KettleService kettleService;
    @ResponseBody
    @RequestMapping("/testktr")
    public  String  test(){
       kettleService.runKtr("reservior",null,null);     //   kettleService.runKjb("stream",null,null);
        return "hello kettle";
    }
    @ResponseBody
    @RequestMapping("/testkjb")
    public  String  testKjb(HttpServletRequest request, HttpServletResponse response){
       // kettleService.runKtr("get_max_water_level",null,null);
        String  name=request.getParameter("filename");
        if(name==null){
            return  "error";
        }
           kettleService.runKjb(name,null,null);
        return "hello kettle";
    }
}
