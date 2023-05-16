package edu.hhu.wa_knowledgemap_updating.controller;

import edu.hhu.wa_knowledgemap_updating.dto.StreamInflowDto;
import edu.hhu.wa_knowledgemap_updating.entity.RespBean;
import edu.hhu.wa_knowledgemap_updating.service.StreamInflowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@CrossOrigin
@RequestMapping("/stream_inflow")
@Slf4j
public class StreamInflowController {
    @Autowired
    StreamInflowService streamInflowService;
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    @ResponseBody
    public RespBean create(@RequestBody StreamInflowDto streamInflowDto){
        String inflowStartName=streamInflowDto.getInflowStartName();
        String inflowEndName=streamInflowDto.getInflowEndName();
        if(inflowStartName==null||inflowStartName.trim().length()==0){
            return new RespBean(400,"error:inflow start name is null",null);
        }
        if(inflowEndName==null||inflowStartName.trim().length()==0){
            return new RespBean(400,"error:inflow end name is null",null);
        }
       return streamInflowService.create(streamInflowDto);
    }
    @RequestMapping("/list")
    @ResponseBody
    public RespBean list(){
        RespBean respBean = streamInflowService.list();
        return respBean;
    }


    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    @ResponseBody
    public RespBean update(@RequestBody StreamInflowDto streamInflowDto){
        if(streamInflowDto.getId()==0){
            return new RespBean(400,"error:id is 0",null);
        }
        RespBean respBean = checkParam(streamInflowDto);
        if(respBean!=null){
            return  respBean;
        }
        return  streamInflowService.update(streamInflowDto);
    }

    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public RespBean delete(@PathVariable("id") long id) {
        if(id==0){
            return  new RespBean(400L,"error:id is 0",null);
        }
        return  streamInflowService.delete(id);
    }
    @RequestMapping("/find")
    @ResponseBody
    public RespBean findByKeyWord(HttpServletRequest request, HttpServletResponse response){
        String keyWord=request.getParameter("keyword");
        log.info(keyWord);
        if(keyWord==null){
            return  new RespBean(400L,"error: keyword is null",null);
        }
        return  streamInflowService.findByKeyWord(keyWord);

    }


    //校验请求参数是否合法
    public RespBean checkParam(StreamInflowDto streamInflowDto){
        String inflowStartName=streamInflowDto.getInflowStartName();
        String inflowEndName=streamInflowDto.getInflowEndName();
        if(streamInflowDto.getInflowStartId()==0){
            return new RespBean(400,"error:inflow start id is 0",null);
        }
        if(streamInflowDto.getInflowEndId()==0){
            return new RespBean(400,"error:inflow end id is 0",null);
        }
        if(inflowStartName==null||inflowStartName.trim().length()==0){
            return new RespBean(400,"error:inflow start name is null",null);
        }
        if(inflowStartName==null||inflowStartName.trim().length()==0){
            return new RespBean(400,"error:inflow end name is null",null);
        }
        return  null;
    }
}
