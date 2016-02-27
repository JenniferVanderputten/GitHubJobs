package com.jpvander.githubjobs.datasets.data;

import android.graphics.Bitmap;

import com.loopj.android.http.RequestParams;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

@SuppressWarnings("unused")
public class GitHubJob {

    private String id;
    private String created_at;
    private String title;

    private String location;    // Cannot be used with latitude or longitude
    private String latitude;    // Must be paired with longitude; cannot be used with location
    private String longitude;   // Must be paired with latitude; cannot be used with location

    private Boolean full_time;  // The request uses a boolean that corresponds to String type
    private String type;        // The response returns a string corresponding to boolean full_time

    private String description;
    private String how_to_apply;
    private String company;
    private String company_url;
    private String company_logo;
    private String url;

    private Bitmap logo;        // Not part of the response; used for performance enhancement
    private String displayTitle;// Not part of the response; used for recycler and toolbar text

    public GitHubJob() {
        setDefaults();
    }

    public GitHubJob(String description, String location) {
        setDefaults();
        this.description = description;
        this.location = location;
    }

    private void setDefaults() {
        id = "";
        created_at = "";
        title = "";
        location = "";
        latitude = "";
        longitude = "";
        full_time = false;
        type = "";
        description = "";
        how_to_apply = "";
        company = "";
        company_url = "";
        company_logo = "";
        url = "";
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;

        if (null == this.description || this.description.isEmpty()) {
            this.description = "All jobs";
        }
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

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Bitmap getLogo() {
        return logo;
    }

    public void setLogo(Bitmap logo) {
        this.logo = logo;
    }

    public String getModifiedLocation() {
        if (null == this.location || this.location.isEmpty()) {
            return "All locations";
        }

        return this.location;
    }

    public String getModifiedDescription() {
        if (null == this.description || this.description.isEmpty()) {
            return "All jobs";
        }

        return this.description;
    }

    public void setDisplayTitle(ArrayList<String> fieldValues) {
        this.displayTitle = StringUtils.join(fieldValues, " - ");
    }

    public String getDisplayTitle() {
        if (null == displayTitle) { displayTitle = ""; }
        return displayTitle;
    }

    public RequestParams getRequestParams() {
        RequestParams params = new RequestParams();

        for (Field field : getClass().getDeclaredFields()) {
            if (!Modifier.isStatic(field.getModifiers())) {
                try {
                    String name = field.getName();
                    Object value = field.get(this);
                    params.put(name, value);
                }
                catch (IllegalAccessException exception) {
                    // We must catch this to satisfy the compiler. Ignore anything inaccessible.
                }
            }
        }

        return params;
    }
}
