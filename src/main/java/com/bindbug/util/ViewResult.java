package com.bindbug.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yan on 16/4/13.
 */
public class ViewResult implements Serializable {

    /**  */
    private static final long serialVersionUID = -4275686887114285196L;

    private Logger logger = LoggerFactory.getLogger(ViewResult.class);

    /** 成功、失败 的附加提示 */
    String message;

    /** 操作状态 1：成功，其他：失败 根据业务定义失败原因*/
    int state;

    /** 结果数据 */
    Map<String, Object> data;

    /** 列表行数据 */
    List<? extends Object> rows = new ArrayList<Object>();

    /** 自定义的json解析器 */
    private static GsonBuilder GSON_BUILDER = new GsonBuilder().disableHtmlEscaping().setDateFormat("yyyyMMdd'T'HHmmssZ");

    /** 创建一个实例 */
    public static ViewResult newInstance() {
        return new ViewResult();
    }

    /**
     * 向输出中写入数据
     * @param key
     * @param Object
     * @return
     */
    public ViewResult put(String key, Object Object) {
        if (data == null) {
            data = new HashMap<String, Object>();
        }
        Assert.isTrue(data instanceof Map, "使用put 方法时 原始数据data 必须为 map类型");
        data.put(key, Object);
        return this;
    }

    /**
     * 直接向页面写输出对象
     * @param data
     * @return
     */
    public ViewResult data(Map<String, Object> data) {
        Assert.isNull(this.data, "data 数据已经赋值，不能重复赋值" + this.data);
        this.data = data;
        return this;
    }

    /**
     * 向输出中加入数据列表
     * @param list 要加入的数据列表
     * @return 统一输出对象
     */
    public ViewResult rows(List<? extends Object> list) {
        rows = list;
        return this;
    }

    /**
     * 输出成json格式
     * @return
     * @throws Exception
     */
    public String json(Gson gson) {
        Map<String, Object> content = new HashMap<String, Object>();
        content.put("data", data);
        content.put("rows", rows);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("message", message);
        result.put("state", state);
        result.put("content", content);
        final String out = gson.toJson(result);
        return out;
    }

    /**
     * 输出成json格式
     * @return
     * @throws Exception
     */
    public String json() {
        return json(GSON_BUILDER.create());
    }

    public ViewResult success() {
        return state(1, "操作成功");
    }

    public ViewResult fail() {
        return fail("操作失败");
    }

    public ViewResult fail(String message) {
        return state(-1, message);
    }

    public ViewResult state(int state, String message) {
        this.message = message;
        this.state = state;
        return this;
    }

    public ViewResult fail(Exception e) {
        state(500, e.getMessage());
        return this;
    }

}
