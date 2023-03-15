package edu.hhu.wa_knowledgemap_updating.controller;

import edu.hhu.wa_knowledgemap_updating.dto.ReservoirDto;
import edu.hhu.wa_knowledgemap_updating.entity.Reservoir;
import edu.hhu.wa_knowledgemap_updating.entity.ReservoirNode;
import edu.hhu.wa_knowledgemap_updating.entity.RespBean;
import edu.hhu.wa_knowledgemap_updating.service.ReservoirService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/reservoir")
@Slf4j
public class ReservoirController {
    @Autowired
    ReservoirService reservoirService;

    @RequestMapping(value = "/create",method = RequestMethod.POST)
    @ResponseBody
    public RespBean create(@RequestBody ReservoirDto reservoirDto){
        if(reservoirDto.getName()==null||reservoirDto.getName().trim().length()==0){
            return  new RespBean(400L,"name is null",null);
        }
        if(reservoirDto.getMaxWaterLevel()<0){
            return  new RespBean(400L,"最高水位不能小于0",null);
        }
       return reservoirService.create(reservoirDto);
    }
    @RequestMapping("/list")
    @ResponseBody
    public RespBean list(){
        log.info("enter list controller");
        return reservoirService.list();
    }

    @RequestMapping("/find")
    @ResponseBody
    public RespBean findByName(HttpServletRequest request, HttpServletResponse response){
        String name=request.getParameter("name");
        if(name==null||name.trim().length()==0){
            response.setStatus(400);
            return  new RespBean(400L,"error name is null",null);
        }
        return   reservoirService.findByName(name);

    }

    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    @ResponseBody
    public RespBean update(@RequestBody ReservoirDto reservoirDto){
        if(reservoirDto.getName()==null||reservoirDto.getName().trim().length()==0){
            return  new RespBean(400L,"error: name is null",null);
        }
        if(reservoirDto.getMaxWaterLevel()<0){
            return  new RespBean(400L,"error:最高水位不能小于0",null);
        }
        if(reservoirDto.getId()==0){
            return  new RespBean(400L,"error:id is 0",null);
        }
        return reservoirService.update(reservoirDto);
    }

    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public RespBean delete(@PathVariable("id") long id) {
        if(id==0){
            return  new RespBean(400L,"error:id is 0",null);
        }
        return  reservoirService.delete(id);
    }
}
