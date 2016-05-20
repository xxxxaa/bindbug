package com.bindbug.controller;

import com.bindbug.converter.ArticleConverter;
import com.bindbug.model.Article;
import com.bindbug.model.ArticleExample;
import com.bindbug.model.ArticleWithBLOBs;
import com.bindbug.model.Tag;
import com.bindbug.model.to.ArticleTo;
import com.bindbug.service.ArticleService;
import com.bindbug.service.TagService;
import com.bindbug.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleConverter articleConverter;


    @RequestMapping(value = "/")

    public String index(Model model){
        try {
            List<ArticleWithBLOBs> articleList = articleService.findArticle(0, 10, "create_time", false);
            List<ArticleTo> articleToList = articleConverter.makeArticleContentShort(articleList);
            Page<ArticleTo> articlePage = new Page<>();
            articlePage.setPageNo(1);
            articlePage.setPageSize(Page.DEFAULT_PAGE_SIZE);
            articlePage.setResult(articleToList);
            Integer totalCount = articleService.countArticle(false);
            articlePage.setTotalCount(totalCount);

            model.addAttribute("articlePage", articlePage);
            return "article/list";
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

}
