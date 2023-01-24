package com.example.mds.entity;

import java.util.HashMap;
import java.util.Map;

public class ModelAndView {
    private String url;
    private Map<String, Object> parameterMap = new HashMap<>();

    public ModelAndView() {
    }

    public ModelAndView(String url, Map<String, Object> parameterMap) {
        this.url = url;
        this.parameterMap = parameterMap;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, Object> getParameterMap() {
        return parameterMap;
    }

    public void setParameterMap(Map<String, Object> parameterMap) {
        this.parameterMap = parameterMap;
    }
}
