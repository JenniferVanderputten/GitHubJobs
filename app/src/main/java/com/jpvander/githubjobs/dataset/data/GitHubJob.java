package com.jpvander.githubjobs.dataset.data;

import android.annotation.SuppressLint;

import com.loopj.android.http.RequestParams;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

@SuppressWarnings("unused")
@SuppressLint("unused")
public class GitHubJob {

    public final static String ALL_JOBS_TITLE = "All jobs";
    public final static String ALL_LOCATIONS_TITLE = "All locations";
    public final static String LOCATION_LABEL = "Location";
    public final static String DESCRIPTION_LABEL = "Description";
    public final static String ID_LABEL = "ID";
    public final static String COMPANY_LABEL = "Company";
    public final static String LOGO_URL_LABEL = "LogoURL";
    public final static String WEBSITE_LABEL = "Website";
    public final static String TITLE_LABEL = "Title";
    public final static String SAVED_SEARCH_ID_LABEL = "Search";

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

    private String displayTitle;// Not part of the response; used for recycler and toolbar text
    private long savedSearchId; // Not part of the response; for DB reference with search results

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
            this.description = ALL_JOBS_TITLE;
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

    public String getModifiedLocation() {
        if (null == this.location || this.location.isEmpty()) {
            return ALL_LOCATIONS_TITLE;
        }

        return this.location;
    }

    public String getModifiedDescription() {
        if (null == this.description || this.description.isEmpty()) {
            return ALL_JOBS_TITLE;
        }

        return this.description;
    }

    public long getSavedSearchId() {
        return savedSearchId;
    }

    public void setSavedSearchId(long savedSearchId) {
        this.savedSearchId = savedSearchId;
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
