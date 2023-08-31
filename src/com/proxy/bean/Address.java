package com.proxy.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;

public class Address {
    @JsonProperty("id")
    String id;
    @JsonProperty("type")
    Type type;
    @JsonProperty("addressLineDetail")
    AddressLineDetail addressLineDetail;
    @JsonProperty("provinceOrState")
    ProvinceOrState provinceOrState;
    @JsonProperty("cityOrTown")
    String cityOrTown;
    @JsonProperty("country")
    Country country;
    @JsonProperty("postalCode")
    String postalCode;
    @JsonProperty("lastUpdated")
    String lastUpdated;
    @JsonProperty("suburbOrDistrict")
    String suburbOrDistrict;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public AddressLineDetail getAddressLineDetail() {
        return addressLineDetail;
    }

    public void setAddressLineDetail(AddressLineDetail addressLineDetail) { this.addressLineDetail = addressLineDetail; }

    public ProvinceOrState getProvinceOrState() {
        return provinceOrState;
    }

    public void setProvinceOrState(ProvinceOrState provinceOrState) {
        this.provinceOrState = provinceOrState;
    }

    public String getCityOrTown() {
        return cityOrTown;
    }

    public void setCityOrTown(String cityOrTown) {
        this.cityOrTown = cityOrTown;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getSuburbOrDistrict() {
        return suburbOrDistrict;
    }

    public void setSuburbOrDistrict(String suburbOrDistrict) {
        this.suburbOrDistrict = suburbOrDistrict;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", addressLineDetail=" + addressLineDetail +
                ", provinceOrState=" + provinceOrState +
                ", cityOrTown='" + cityOrTown + '\'' +
                ", country=" + country +
                ", postalCode='" + postalCode + '\'' +
                ", lastUpdated='" + lastUpdated + '\'' +
                ", suburbOrDistrict='" + suburbOrDistrict + '\'' +
                '}';
    }
}