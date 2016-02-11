package com.jpvander.githubjobs.datasets;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class GitHubJob {

    private String id;
    private String created_at;
    private String title;

    private String location;   // Cannot be used with latitude or longitude
    private String latitude;   // Must be paired with longitude; cannot be used with location
    private String longitude;  // Must be paired with latitude; cannot be used with location
    //TODO: Enforce the rules of location/latitude/longitude

    private Boolean full_time; // The request uses a boolean that corresponds to String type
    private String type;       // The response returns a string corresponding to boolean full_time

    private String description;
    private String how_to_apply;
    private String company;
    private String company_url;
    private String company_logo;
    private String url;

    public GitHubJob() {
        this.description = "PHP";
        this.location = "San Francisco";
    }

    public GitHubJob(String description, String location) {
        this.description = description;
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Boolean getFull_time() {
        return full_time;
    }

    public void setFull_time(Boolean full_time) {
        this.full_time = full_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getHow_to_apply() {
        return how_to_apply;
    }

    public void setHow_to_apply(String how_to_apply) {
        this.how_to_apply = how_to_apply;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompany_url() {
        return company_url;
    }

    public void setCompany_url(String company_url) {
        this.company_url = company_url;
    }

    public String getCompany_logo() {
        return company_logo;
    }

    public void setCompany_logo(String company_logo) {
        this.company_logo = company_logo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public String getDisplayTitle( ArrayList<String> fieldValues) {
        return (StringUtils.join(fieldValues, " - "));
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
