package com.bindbug.service;

import com.bindbug.model.Comment;
import com.bindbug.model.CommentExample;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yan on 16/2/9.
 */
@Service
public class CommentService {


    public List<Comment> findCommentByArticleId(Integer id, Integer start, Integer pageSize, String orderField, Boolean isDel){
        CommentExample commentExample = new CommentExample();
        CommentExample.Criteria criteria = commentExample.createCriteria();
        return null;
    }
}
