package edu.hhu.wa_knowledgemap_updating.controller;

import edu.hhu.wa_knowledgemap_updating.dto.ReservoirDto;
import edu.hhu.wa_knowledgemap_updating.entity.Reservoir;
import edu.hhu.wa_knowledgemap_updating.entity.RespBean;
import edu.hhu.wa_knowledgemap_updating.service.ReservoirService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@CrossOrigin
@RequestMapping("/reservoir")
@Slf4j
public class ReservoirController {
    @Autowired
    ReservoirService reservoirService;

    @RequestMapping(value = "/create",method = RequestMethod.POST)
    @ResponseBody
    public RespBean create(@RequestBody ReservoirDto reservoirDto){
        RespBean respBean = checkParam(reservoirDto);
        if(respBean!=null) return  respBean;
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
    public RespBean findByKeyWord(HttpServletRequest request, HttpServletResponse response){
        String keyWord=request.getParameter("keyword");
        log.info(keyWord);
        if(keyWord==null){
            return  new RespBean(400L,"error: keyword is null",null);
        }
        return   reservoirService.findByKeyWord(keyWord);

    }

    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    @ResponseBody
    public RespBean update(@RequestBody ReservoirDto reservoirDto){
        if(reservoirDto.getId()<=0){
            return  new RespBean(400L,"error:id <= 0",null);
        }
        RespBean respBean = checkParam(reservoirDto);
        if(respBean!=null) return  respBean;
        return reservoirService.update(reservoirDto);
    }

    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public RespBean delete(@PathVariable("id") long id) {
        if(id<=0){
            return  new RespBean(400L,"error:id <=0",null);
        }
        return  reservoirService.delete(id);
    }
    //校验请求参数是否合法
    public  RespBean checkParam(ReservoirDto reservoirDto) {
        String name= reservoirDto.getName();
        double maxWaterLevel= reservoirDto.getMaxWaterLevel();
        double longitude = reservoirDto.getLongitude();
        double latitude= reservoirDto.getLatitude();
        int elevation= reservoirDto.getElevation();
        if(name==null||name.trim().length()==0) {
            return  new RespBean(400L,"error:name is null",null);
        }
        if (maxWaterLevel<0){
            return  new RespBean(400L,"error:最高水位小于0",null);
        }
        if(elevation<0){
            return  new RespBean(400L,"error:高程 小于0",null);
        }
        if(longitude<-180||longitude>180){
            return  new RespBean(400L,"error: 经度应该在-180.00~180.00",null);
        }
        if(latitude<-90||latitude>90){
            return  new RespBean(400L,"error: 纬度应该在-90.00~90.00",null);
        }
        return  null;
    }
}
