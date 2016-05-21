package com.bindbug.exception;

/**
 * Created by yan on 16/5/17.
 */
public class BaseException extends Exception {
    private static final long serialVersionUID = -6784291639216132019L;
    protected int state;

    public BaseException() {
        this.state = 500;
    }

    public BaseException(String message) {
        super(message);
        this.state = 500;
    }

    public BaseException(int state, String message) {
        super(message);
        this.state = state;
    }

    public int getState() {
        return this.state;
    }

    public Throwable fillInStackTrace() {
        return this;
    }
}
