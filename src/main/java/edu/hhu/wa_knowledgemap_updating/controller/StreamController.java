package edu.hhu.wa_knowledgemap_updating.controller;

import edu.hhu.wa_knowledgemap_updating.common.Common;
import edu.hhu.wa_knowledgemap_updating.dto.StreamDto;
import edu.hhu.wa_knowledgemap_updating.entity.RespBean;
import edu.hhu.wa_knowledgemap_updating.service.StreamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@CrossOrigin
@RequestMapping("/stream")
public class StreamController {
        @Autowired
        StreamService streamService;
        @Autowired
        Common common;

        @RequestMapping(value = "/create",method = RequestMethod.POST)
        @ResponseBody
        public RespBean create(@RequestBody StreamDto streamDto){
                RespBean respBean = checkParam(streamDto);
                if(respBean!=null){
                    return  respBean;
                }
                return streamService.create(streamDto);
        }
        @RequestMapping("/list")
        @ResponseBody
        public RespBean list(){
            RespBean respBean = streamService.list();
            return respBean;

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

        @RequestMapping(value = "/update",method = RequestMethod.PUT)
        @ResponseBody
        public RespBean update(@RequestBody StreamDto streamDto){
                if(streamDto.getId()==0){
                        return  new RespBean(400L,"error:id is 0",null);
                }
                RespBean respBean = checkParam(streamDto);
                if(respBean!=null){
                        return  respBean;
                }
                return streamService.update(streamDto);
        }

        @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
        @ResponseBody
        public RespBean delete(@PathVariable("id") long id) {
                if(id==0){
                        return  new RespBean(400L,"error:id is 0",null);
                }
                return  streamService.delete(id);
        }
        //校验请求参数是否合法
        public  RespBean checkParam(StreamDto streamDto ) {
             String name=streamDto.getName();
             String type=streamDto.getType();
             int length=streamDto.getLength();
             int level=streamDto.getLevel();
             if(name==null||name.trim().length()==0) {
                return  new RespBean(400L,"error:name is null",null);
             }
             if(!common.getStreamTypeSet().contains(type)){
                return  new RespBean(400L,"不存在该类型，应为大型河、中型河、小型河",null);
             }
             if (length<=0){
                     return  new RespBean(400L,"error:length <=0",null);
             }
             if(level<0){
                     return  new RespBean(400L,"error:level 小于0",null);
             }
             return  null;
    }
}
