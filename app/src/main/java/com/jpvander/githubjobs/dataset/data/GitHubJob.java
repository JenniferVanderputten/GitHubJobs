package com.jpvander.githubjobs.dataset.data;

import android.annotation.SuppressLint;

import com.loopj.android.http.RequestParams;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

@SuppressWarnings("unused")
@SuppressLint("unused")
public class GitHubJob {

    // Static definitions for a unified UX across screens
    public final static String ALL_JOBS = "All Jobs";
    public final static String FULL_TIME = "Full Time";
    public final static String PART_TIME = "Part Time";
    public final static String FULL_OR_PART_TIME = "Full or Part Time";
    public final static String UNKNOWN = "Unknown";

    public final static String LOCATION_LABEL = "Location";
    public final static String DESCRIPTION_LABEL = "Description";
    public final static String ID_LABEL = "ID";
    public final static String COMPANY_LABEL = "Company";
    public final static String LOGO_URL_LABEL = "LogoURL";
    public final static String WEBSITE_LABEL = "Website";
    public final static String TITLE_LABEL = "Title";
    public final static String SAVED_SEARCH_ID_LABEL = "Search";
    public final static String FULL_TIME_LABEL = "Full_Time";
    public final static String PART_TIME_LABEL = "Part_Time";
    public final static String TYPE_LABEL = "Type";

    // Expected fields from the response on GET position(s)
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

    // The following properties are not part of the expected response for GET on position(s)
    private Boolean part_time;
    private long savedSearchId;

    // Constructors and their helpers
    public GitHubJob() {
        setDefaults();
    }

    public GitHubJob(String description, String location, boolean full_time, boolean part_time) {
        setDefaults();
        this.description = description;
        this.location = location;
        setFullAndPartTime(full_time, part_time);
    }

    private void setDefaults() {
        id = "";
        created_at = "";
        title = "";
        location = "";
        latitude = "";
        longitude = "";
        full_time = true;
        part_time = true;
        type = FULL_OR_PART_TIME;
        description = "";
        how_to_apply = "";
        company = "";
        company_url = "";
        company_logo = "";
        url = "";
    }

    // Methods
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

    public Boolean isFullTime() {
        return (type.equalsIgnoreCase(FULL_TIME) ||
                type.equalsIgnoreCase(FULL_OR_PART_TIME));
    }

    public Boolean isFullTimeOnly() { return type.equalsIgnoreCase(FULL_TIME); }

    public Boolean isPartTime() {
        return (type.equalsIgnoreCase(PART_TIME) ||
                type.equalsIgnoreCase(FULL_OR_PART_TIME));
    }

    public Boolean isPartTimeOnly() { return type.equalsIgnoreCase(PART_TIME); }

    public Boolean isFullOrPartTime() {

        return (type.equalsIgnoreCase(PART_TIME) ||
                type.equalsIgnoreCase(FULL_TIME) ||
                type.equalsIgnoreCase(FULL_OR_PART_TIME));
    }

    public void setFullAndPartTime(Boolean full_time, Boolean part_time) {
        this.full_time = full_time;
        this.part_time = part_time;

        if (full_time && part_time) {
            this.type = FULL_OR_PART_TIME;
        }
        else if (full_time) {
            this.type = FULL_TIME;
        }
        else if (part_time) {
            this.type = PART_TIME;
        }
        else {
            this.type = UNKNOWN;
        }
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

    public long getSavedSearchId() {
        return savedSearchId;
    }

    public void setSavedSearchId(long savedSearchId) {
        this.savedSearchId = savedSearchId;
    }

    public String getSearchTitle() {
        ArrayList<String> titleComponents = new ArrayList<>();
        titleComponents.add(getLocation());
        titleComponents.add(getDescription());

        if (isFullTimeOnly()) {
            titleComponents.add(FULL_TIME);
        }
        else if (isPartTimeOnly()) {
            titleComponents.add(PART_TIME);
        }
        else if (isFullOrPartTime()){
            titleComponents.add(FULL_OR_PART_TIME);
        }
        else {
            titleComponents.add(UNKNOWN);
        }

        return join(titleComponents, " - ");
    }

    public String getJobTitle() {
        ArrayList<String> titleComponents = new ArrayList<>();
        titleComponents.add(getTitle());
        titleComponents.add(getCompany());

        if (isFullTimeOnly()) {
            titleComponents.add(FULL_TIME);
        }
        else {
            titleComponents.add(FULL_OR_PART_TIME);
        }

        return join(titleComponents, " - ");
    }

    private String join(ArrayList<String> components, String separator) {
        ArrayList<String> nonEmptyComponents = new ArrayList<>();
        boolean hasWildCard = false;

        for (String component : components) {
            if (null != component && !component.isEmpty()) {
                nonEmptyComponents.add(component);
            }
            else {
                if (!hasWildCard) {
                    nonEmptyComponents.add(ALL_JOBS);
                    hasWildCard = true;
                }
            }
        }

        return StringUtils.join(nonEmptyComponents, separator);
    }

    public RequestParams getRequestParams() {
        RequestParams params = new RequestParams();
        params.add(LOCATION_LABEL.toLowerCase(), location);
        params.add(DESCRIPTION_LABEL.toLowerCase(), description);
        params.add(FULL_TIME_LABEL.toLowerCase(), full_time.toString());
        return params;
    }
}
