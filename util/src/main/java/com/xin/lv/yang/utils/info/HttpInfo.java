package com.xin.lv.yang.utils.info;


public class HttpInfo  {
    private String key;
    private String values;

    public HttpInfo(String key,String values){
        this.key=key;
        this.values=values;
    }
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }
}
