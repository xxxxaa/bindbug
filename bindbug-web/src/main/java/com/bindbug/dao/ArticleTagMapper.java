package com.bindbug.dao;

import com.bindbug.model.ArticleTag;
import com.bindbug.model.ArticleTagExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleTagMapper {
    int countByExample(ArticleTagExample example);

    int deleteByExample(ArticleTagExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ArticleTag record);

    int insertSelective(ArticleTag record);

    List<ArticleTag> selectByExample(ArticleTagExample example);

    ArticleTag selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ArticleTag record, @Param("example") ArticleTagExample example);

    int updateByExample(@Param("record") ArticleTag record, @Param("example") ArticleTagExample example);

    int updateByPrimaryKeySelective(ArticleTag record);

    int updateByPrimaryKey(ArticleTag record);
}