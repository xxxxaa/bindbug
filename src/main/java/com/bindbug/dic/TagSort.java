package com.bindbug.dic;

/**
 * Created by yan on 16/4/15.
 */
public enum TagSort {
    TAG_CREATETIME_DESC(" create_time desc "),
    TAG_CREATETIME_ASC(" create_time asc "),
    TAG_UPDATETIME_DESC(" update_time desc "),
    TAG_UPDATETIME_ASC(" update_time asc ");


    private String sortField;

    private TagSort(String sortField){
        this.sortField = sortField;
    }
    /**
     * 获取排序字符串
     * @return
     */
    public String getOrderField(){
        return this.sortField;
    }

    /**
     * 根据排序字段，获取排序字段拼接后的值
     * 如 createTime asc, offlineTime desc
     * @param tagSortFields
     * @return
     */
    public static String getOrderByStr(TagSort[] tagSortFields){
        if(tagSortFields == null || tagSortFields.length == 0){
            return null;
        }
        StringBuffer sb = new StringBuffer();
        boolean first = true;
        for (TagSort orderField : tagSortFields) {
            if(!first){
                sb.append(", ");
            }
            sb.append(orderField.getOrderField());
            first = false;
        }
        return sb.toString();
    }
}
