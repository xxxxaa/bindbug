package com.bindbug.converter;

import com.bindbug.model.ArticleWithBLOBs;
import com.bindbug.model.Tag;
import com.bindbug.model.to.ArticleTo;
import com.bindbug.service.TagService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yan on 16/5/20.
 */
@Service
public class ArticleConverter {

    @Resource
    private TagService tagService;

    public List<ArticleTo> makeArticleContentShort(List<ArticleWithBLOBs> articles){
        List<ArticleTo> articleToList = new ArrayList<>();
        for(ArticleWithBLOBs article : articles){
            ArticleTo articleTo = new ArticleTo();
            List<Tag> tagList = this.tagService.findTagByArticleId(article.getId());
            articleTo.setTagList(tagList);
            articleTo.setId(article.getId());
            articleTo.setTitle(article.getTitle());
            articleTo.setCreateTime(article.getCreateTime());
            articleTo.setUpdateTime(article.getUpdateTime());
            articleTo.setReadCount(article.getReadCount());
            articleTo.setCommentCount(article.getCommentCount());
            articleTo.setScore(article.getScore());
            articleTo.setIsDel(article.getIsDel());
            articleTo.setContent(article.getContent());
//            if(article.getContent().length() > 100){
//                articleTo.setContent(article.getContent().substring(0,100));
//            }else{
//                articleTo.setContent(article.getContent());
//            }
            articleToList.add(articleTo);
        }
        return articleToList;
    }

}
