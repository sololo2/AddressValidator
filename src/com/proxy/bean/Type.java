package com.proxy.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Type {
    @JsonProperty("code")
    String code;
    @JsonProperty("name")
    String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Type{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
