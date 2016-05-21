package com.bindbug.controller;

import com.bindbug.model.Tag;
import com.bindbug.service.TagService;
import com.bindbug.util.ViewResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * Created by yan on 16/4/13.
 */
@Controller
@RequestMapping(value = "/admin/tag")
public class TagController {

    private Logger logger = LoggerFactory.getLogger(TagController.class);

    @Autowired
    private TagService tagService;

    @RequestMapping(value = "addTag.json")
    @ResponseBody
    public String addTag(@RequestParam(value = "tagContent", required = true, defaultValue = "") String tagContent){

        /**
         * 判断标签是否已存在,存在则不重复添加
         */
        if(tagContent != null && !tagContent.equals("")){
            Tag tag = this.tagService.findTagByContent(tagContent);
            if(tag != null){
                return ViewResult.newInstance().state(201, "标签已存在,不能重复添加").json();
            }
        }

        Tag tag = new Tag();
        try{
            tag.setContent(tagContent);
            tag.setCreateTime(new Date());
            tagService.addTag(tag);
        }catch (Exception e){
            logger.error("添加标签失败,content:{},异常:{}", tagContent, e);
            return ViewResult.newInstance().fail().json();
        }
        return ViewResult.newInstance().put("tag", tag).success().json();
    }
}
