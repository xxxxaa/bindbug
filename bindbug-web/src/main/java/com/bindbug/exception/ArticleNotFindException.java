package com.bindbug.exception;

/**
 * Created by yan on 16/5/17.
 */
public class ArticleNotFindException extends BaseException{

    public ArticleNotFindException() {
        super();
    }

    public ArticleNotFindException(int state, String message) {
        super(state, message);
    }

    public ArticleNotFindException(String message) {
        super(message);
    }

}
