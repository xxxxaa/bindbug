package com.bindbug.controller;

import com.bindbug.model.Article;
import com.bindbug.model.ArticleExample;
import com.bindbug.service.ArticleService;
import com.bindbug.util.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yan on 16/1/27.
 */
@Controller
@RequestMapping(value = "/")
public class IndexController {

    @Resource
    private ArticleService articleService;

    @RequestMapping(value = "/")
    public String index(Model model){
        try {
            ArticleExample articleExample = new ArticleExample();
            articleExample.createCriteria();
            List<Article> articleList = articleService.findArticle(0, 10, "create_time", false);
            makeArticleContentShort(articleList);
            Page<Article> articlePage = new Page<>();
            articlePage.setPageNo(1);
            articlePage.setPageSize(Page.DEFAULT_PAGE_SIZE);
            articlePage.setResult(articleList);
            Integer totalCount = articleService.countArticle(false);
            articlePage.setTotalCount(totalCount);
            model.addAttribute("articlePage", articlePage);
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("articlePage", articlePage);
//            modelAndView.setViewName("article/list");
//            return "login.html";
            return "article/list";
//            return modelAndView;
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    private static void makeArticleContentShort(List<Article> articles){
        for(Article article : articles){
            if(article.getContent().length() > 100){
                article.setContent(article.getContent().substring(0,100));
            }
        }
    }
}
