package com.bindbug.controller;

import com.bindbug.model.Article;
import com.bindbug.model.ArticleExample;
import com.bindbug.model.Tag;
import com.bindbug.model.User;
import com.bindbug.service.ArticleService;
import com.bindbug.service.TagService;
import com.bindbug.util.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
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

    @RequestMapping(value = "/admin/addArticle.json")
    @ResponseBody
    public String addArticle(Model model, String content, String title, String markdownContent, HttpSession session){
        User user = (User)session.getAttribute("loginUser");
        Article article = new Article();
        article.setUserId(user.getId());
        article.setContent(content);
        article.setTitle(title);
        article.setCreateTime(new Date());
        article.setReadCount(0);
        article.setScore(0F);
        article.setCommentCount(0);
        article.setIsDel(false);
        try{
            articleService.addArticle(article);
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            return "failed";
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

    public String preUpdateArticle(Model model,
                                   @RequestParam(value = "id",required = true) Integer id){
        try{
            Article article = articleService.findArticleById(id);
        }catch (Exception e){
            logger.error("查询失败", e);
        }
        return "/admin/article/edit";
    }


    @RequestMapping(value = "/admin/article/add")
    public String addArticle(){
        return "/admin/article/markdownedit";
//        return "admin/article/edit";
    }

}
