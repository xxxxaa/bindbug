package com.bindbug.service;

import com.bindbug.exception.ArticleNotFindException;
import com.bindbug.model.ArticleWithBLOBs;
import com.github.pagehelper.PageHelper;
import com.bindbug.dao.ArticleMapper;
import com.bindbug.model.Article;
import com.bindbug.model.ArticleExample;
import com.bindbug.util.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yan on 16/1/27.
 */
@Service
public class ArticleService {

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private ArticleTagService articleTagService;


    public List<Article> findArticleByPage(Page page, Integer userId){
        ArticleExample articleExample = new ArticleExample();
        ArticleExample.Criteria criteria = articleExample.createCriteria();
        articleExample.setOrderByClause("create_time desc");
        criteria.andUserIdEqualTo(userId);
        PageHelper.startPage(page.getPageNo(), page.getPageSize());
        List<Article> articleList = articleMapper.selectByExample(articleExample);
        return articleList;
    }

    public List<ArticleWithBLOBs> findArticle(Integer pageNum, Integer pageSize, String orderField, Boolean isDel){
        ArticleExample articleExample = new ArticleExample();
        articleExample.createCriteria().andIsDelEqualTo(isDel);
        articleExample.setOrderByClause(orderField);
        PageHelper.startPage(pageNum, pageSize);
        List<ArticleWithBLOBs> articleList = articleMapper.selectByExampleWithBLOBs(articleExample);
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

    public void addArticle(ArticleWithBLOBs articleWithBLOBs, List<Integer> tagIdList){
        articleMapper.insert(articleWithBLOBs);
        if(tagIdList != null && tagIdList.size() > 0) {
            for (Integer tagId : tagIdList) {
                articleTagService.insertArticleTag(articleWithBLOBs.getId(), tagId);
            }
        }
    }

    public ArticleWithBLOBs findArticleById(Integer id){
        ArticleWithBLOBs articleWithBLOBs = articleMapper.selectByPrimaryKey(id);
        return articleWithBLOBs;
    }

    /**
     * 移除文章
     * @param articleId
     * @throws ArticleNotFindException
     */
    public void removeArticle(Integer articleId) throws ArticleNotFindException {
        Assert.notNull(articleId, "articleId is not null");
        Article article = this.articleMapper.selectByPrimaryKey(articleId);
        if(article == null){
            throw new ArticleNotFindException();
        }
        article.setIsDel(true);
        this.articleMapper.updateByPrimaryKey(article);
    }

}
