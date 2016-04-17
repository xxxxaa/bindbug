package com.bindbug.service;

import com.bindbug.dao.ArticleTagMapper;
import com.bindbug.dao.TagMapper;
import com.bindbug.dic.TagSort;
import com.bindbug.model.ArticleTag;
import com.bindbug.model.ArticleTagExample;
import com.bindbug.model.Tag;
import com.bindbug.model.TagExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yan on 16/2/5.
 */
@Service
public class TagService {

    Logger logger = LoggerFactory.getLogger(TagService.class);

    @Resource
    private TagMapper tagMapper;

    @Resource
    private ArticleTagMapper articleTagMapper;

    public Tag addTag(Tag tag){
        try {
            int row = tagMapper.insert(tag);
            if(row > 0){
                return tag;
            }else{
                return null;
            }
        }catch (Exception e){
            logger.error("添加tag失败,tag内容:{},异常:{}", tag.getContent(), e);
            return null;
        }
    }

    public Tag findTagById(Integer id){
        Tag tag = null;
        try {
            tag = tagMapper.selectByPrimaryKey(id);
            return tag;
        }catch (Exception e){
            e.printStackTrace();
            return tag;
        }
    }


    public List<Tag> findTags(){
        TagExample tagExample = new TagExample();
        tagExample.setOrderByClause(TagSort.TAG_CREATETIME_DESC.getOrderField());
        List<Tag> tagList = this.tagMapper.selectByExample(tagExample);
        return tagList;
    }

    public List<Tag> findTagByArticleId(Integer articleId){
        List<Tag> tagList = new ArrayList<>();
        ArticleTagExample articleTagExample = new ArticleTagExample();
        ArticleTagExample.Criteria criteria = articleTagExample.createCriteria();
        criteria.andArticleIdEqualTo(articleId);
        List<ArticleTag> articleTagList = articleTagMapper.selectByExample(articleTagExample);
        List<Integer> tagIdList = new ArrayList<>();
        for(ArticleTag articleTag : articleTagList){
            tagIdList.add(articleTag.getTagId());
        }
        TagExample tagExample = new TagExample();
        TagExample.Criteria tagExampleCriteria = tagExample.createCriteria();
        if(tagIdList.size() > 0){
            tagExampleCriteria.andIdIn(tagIdList);
            tagList = tagMapper.selectByExample(tagExample);
        }
        return tagList;
    }
}
