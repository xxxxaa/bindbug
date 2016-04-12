package com.bindbug.service;

import com.github.pagehelper.PageHelper;
import com.bindbug.dao.ArticleMapper;
import com.bindbug.model.Article;
import com.bindbug.model.ArticleExample;
import com.bindbug.util.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yan on 16/1/27.
 */
@Service
public class ArticleService {

    @Resource
    private ArticleMapper articleMapper;


    public List<Article> findArticleByPage(Page page, Integer userId){
        ArticleExample articleExample = new ArticleExample();
        ArticleExample.Criteria criteria = articleExample.createCriteria();
        articleExample.setOrderByClause("create_time desc");
        criteria.andUserIdEqualTo(userId);
        PageHelper.startPage(page.getPageNo(), page.getPageSize());
        List<Article> articleList = articleMapper.selectByExample(articleExample);
        return articleList;
    }

    public List<Article> findArticle(Integer pageNum, Integer pageSize, String orderField, Boolean isDel){
        ArticleExample articleExample = new ArticleExample();
        articleExample.createCriteria().andIsDelEqualTo(isDel);
        articleExample.setOrderByClause(orderField);
        PageHelper.startPage(pageNum, pageSize);
        List<Article> articleList = articleMapper.selectByExampleWithBLOBs(articleExample);
        return articleList;
    }

    /**
     * 不取文章内容
     * @param pageNum
     * @param pageSize
     * @param orderField
     * @param isDel
     * @return
     */
    public List<Article> findArticleNotContent(Integer pageNum, Integer pageSize, String orderField, Boolean isDel){
        ArticleExample articleExample = new ArticleExample();
        articleExample.createCriteria().andIsDelEqualTo(isDel);
        articleExample.setOrderByClause(orderField);
        PageHelper.startPage(pageNum, pageSize);
        List<Article> articleList = articleMapper.selectByExample(articleExample);
        return articleList;
    }


    /**
     * 根据用户ID,统计文章总数
     * @param userId
     * @param isDel
     * @return
     */
    public Integer countArticleByUserId(Integer userId, Boolean isDel){
        ArticleExample articleExample = new ArticleExample();
        articleExample.createCriteria().andUserIdEqualTo(userId).andIsDelEqualTo(isDel);
        Integer count = articleMapper.countByExample(articleExample);
        return count;
    }

    /**
     * 统计文章
     * @param isDel
     * @return
     */
    public Integer countArticle(Boolean isDel){
        ArticleExample articleExample = new ArticleExample();
        articleExample.createCriteria().andIsDelEqualTo(isDel);
        Integer count = articleMapper.countByExample(articleExample);
        return count;
    }

    public void addArticle(Article article){
        articleMapper.insert(article);
    }

    public Article findArticleById(Integer id){
        Article article = articleMapper.selectByPrimaryKey(id);
        return article;
    }

}
