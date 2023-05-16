package edu.hhu.wa_knowledgemap_updating.controller;

import edu.hhu.wa_knowledgemap_updating.entity.RespBean;
import edu.hhu.wa_knowledgemap_updating.service.OperateRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.Column;

@Controller
@CrossOrigin
public class OperateRecordController {
    @Autowired
    OperateRecordService operateRecordService;
    @RequestMapping(value = "/operate_record/list",method = RequestMethod.GET)
    @ResponseBody
    public RespBean list(){
        return operateRecordService.list();
    }
}
