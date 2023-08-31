package com.proxy.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddressLineDetail {
    @JsonProperty("line1")
    String line1;
    @JsonProperty("line2")
    String line2;

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    @Override
    public String toString() {
        return "AddressLineDetail{" +
                "line1='" + line1 + '\'' +
                ", line2='" + line2 + '\'' +
                '}';
    }
}
