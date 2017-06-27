package com.zhny.gr.wisdomcity.bean;

import java.util.List;

/**
 * Created by tong on 2017/5/9.
 */

public class RecordRoot {
    private String name;
    private String id;
    private List<Results> results;
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }

    public void setResults(List<Results> results) {
        this.results = results;
    }
    public List<Results> getResults() {
        return results;
    }

}
