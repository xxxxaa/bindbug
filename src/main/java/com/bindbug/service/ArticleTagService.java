package com.bindbug.service;

import com.bindbug.dao.ArticleTagMapper;
import com.bindbug.model.ArticleTag;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;

/**
 * Created by yan on 16/4/12.
 */
@Service
public class ArticleTagService {
    @Resource
    private ArticleTagMapper articleTagMapper;

    public Integer insertArticleTag(Integer articleId, Integer tagId){
        Assert.notNull(articleId, "articleId is not null");
        Assert.notNull(tagId, "tagId is not null");
        ArticleTag articleTag = new ArticleTag();
        articleTag.setArticleId(articleId);
        articleTag.setTagId(tagId);
        return articleTagMapper.insert(articleTag);
    }
}
