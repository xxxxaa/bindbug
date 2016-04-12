package com.bindbug.service;

import com.bindbug.dao.ArticleTagMapper;
import com.bindbug.dao.TagMapper;
import com.bindbug.model.ArticleTag;
import com.bindbug.model.ArticleTagExample;
import com.bindbug.model.Tag;
import com.bindbug.model.TagExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yan on 16/2/5.
 */
@Service
public class TagService {

    @Resource
    private TagMapper tagMapper;

    @Resource
    private ArticleTagMapper articleTagMapper;

    public Boolean addTag(Tag tag){
        try {
            int row = tagMapper.insert(tag);
            if(row > 0){
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
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

    public List<Tag> findTagByArticleId(Integer articleId){
        List<Tag> tagList = new ArrayList<Tag>();
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
