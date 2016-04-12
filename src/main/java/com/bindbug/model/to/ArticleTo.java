package com.bindbug.model.to;

import com.bindbug.model.Article;
import com.bindbug.model.Tag;

import java.util.List;

/**
 * Created by yan on 16/4/9.
 * article transport object
 */
public class ArticleTo extends Article{
    private List<Tag> tagList;

    public List<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
    }
}
