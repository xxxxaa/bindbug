package com.bindbug.controller;

import com.bindbug.converter.ArticleConverter;
import com.bindbug.exception.ArticleNotFindException;
import com.bindbug.model.*;
import com.bindbug.model.to.ArticleTo;
import com.bindbug.service.ArticleService;
import com.bindbug.service.ArticleTagService;
import com.bindbug.service.TagService;
import com.bindbug.util.Page;
import com.bindbug.util.ViewResult;
import com.fasterxml.jackson.databind.node.BooleanNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.View;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yan on 16/2/1.
 */
@Controller
public class ArticleController {

    private Logger logger = LoggerFactory.getLogger(ArticleController.class);

    @Resource
    private ArticleService articleService;

    @Resource
    private TagService tagService;

    @Resource
    private ArticleTagService articleTagService;

    @Resource
    private ArticleConverter articleConverter;

    @RequestMapping(value = "/admin/article/addArticle.json")
    @ResponseBody
    public String addArticle(@RequestParam(value = "markdownContent", required = true, defaultValue = "")String markdownContent,
                             @RequestParam(value = "title", required = true, defaultValue = "") String title,
                             @RequestParam(value = "content", required = true, defaultValue = "") String content,
                             @RequestParam(value = "tagId[]", required = false) List<Integer> tagIdList,
                             @RequestParam(value = "isPublish", required = false, defaultValue = "0") Boolean isPublish,
                             HttpSession session){

        User user = (User)session.getAttribute("loginUser");
        ArticleWithBLOBs article = new ArticleWithBLOBs();
        article.setUserId(user.getId());
        article.setContent(content);
        article.setMarkdownContent(markdownContent);
        article.setTitle(title);
        article.setCreateTime(new Date());
        article.setReadCount(0);
        article.setScore(0F);
        article.setCommentCount(0);
        article.setIsDel(false);
        article.setIsPublish(isPublish);
        try{
            articleService.addArticle(article);
            for(Integer tagId : tagIdList){
                articleTagService.insertArticleTag(article.getId(), tagId);
            }
            return ViewResult.newInstance().success().json();
        }catch (Exception e){
            e.printStackTrace();
            return ViewResult.newInstance().fail().json();
        }
    }


    @RequestMapping(value = "/article/list")
    public String index(Model model,
                        @RequestParam(value = "pageNo",required = false, defaultValue = "1") Integer pageNo,
                        @RequestParam(value = "pageSize", required = false,defaultValue = "15") Integer pageSize){
        try {
            List<ArticleWithBLOBs> articleList = articleService.findArticle(pageNo - 1, pageSize, "create_time", false);
            List<ArticleTo> articleToList = articleConverter.makeArticleContentShort(articleList);
            Page<ArticleTo> articlePage = new Page<>();
            articlePage.setPageNo(pageNo);
            articlePage.setPageSize(pageSize);
            articlePage.setResult(articleToList);
            Integer totalCount = articleService.countArticle(false);
            articlePage.setTotalCount(totalCount);
            model.addAttribute("articlePage", articlePage);
            return "article/list";
        }catch (Exception e){
            e.printStackTrace();
            return "/common/500";
        }
    }

    @RequestMapping(value = "/article/{id}")
    public String articleDetail(Model model, @PathVariable Integer id){
        Article article = articleService.findArticleById(id);
        List<Tag> tagList = tagService.findTagByArticleId(id);
        //todo 评论列表
//        List<Comment>
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("article", article);
        model.addAttribute("article", article);
        model.addAttribute("tagList", tagList);
        return "/article/detail";
    }

    @RequestMapping(value = "/admin/article/list")
    public String adminArticleList(Model model,
                                   @RequestParam(value = "pageNo",required = false, defaultValue = "1") Integer pageNo,
                                   @RequestParam(value = "pageSize", required = false,defaultValue = "15") Integer pageSize){
        try {
            ArticleExample articleExample = new ArticleExample();
            articleExample.createCriteria();
            List<Article> articleList = articleService.findArticleNotContent(pageNo, pageSize, "create_time", false);
            Page<Article> articlePage = new Page<>();
            articlePage.setPageNo(pageNo);
            articlePage.setPageSize(pageSize);
            articlePage.setResult(articleList);
            Integer totalCount = articleService.countArticle(false);
            articlePage.setTotalCount(totalCount);
            model.addAttribute("articlePage", articlePage);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "admin/article/list";
    }

    @RequestMapping(value = "/admin/article/preEdit/{id}")
    public String preUpdateArticle(Model model,
                                   @PathVariable Integer id){
        try{
            Article article = articleService.findArticleById(id);
            //该文章的标签
            List<Tag> tagList = tagService.findTagByArticleId(id);
            List<Tag> tags = this.tagService.findTags();
            model.addAttribute("article", article);
            model.addAttribute("tagList", tagList);
            model.addAttribute("tags", tags);
        }catch (Exception e){
            logger.error("查询失败", e);
        }
        return "/admin/article/edit";
    }


    @RequestMapping(value = "/admin/article/add")
    public String addArticle(Model model){
        List<Tag> tagList = this.tagService.findTags();
        model.addAttribute("tags", tagList);
        return "admin/article/add";
    }

    @RequestMapping(value = "/admin/article/removeArticle.json")
    @ResponseBody
    public String removeArticle(Model model, Integer articleId){
       if(articleId == null){
           return ViewResult.newInstance().state(201, "文章ID为null").json();
       }
        try {
            this.articleService.removeArticle(articleId);
        } catch (ArticleNotFindException e) {
            e.printStackTrace();
            return ViewResult.newInstance().state(202, "文章移除失败").json();
        }
        return ViewResult.newInstance().success().json();
    }

}
