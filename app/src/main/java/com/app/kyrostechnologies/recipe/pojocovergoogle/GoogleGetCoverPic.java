
package com.app.kyrostechnologies.recipe.pojocovergoogle;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GoogleGetCoverPic {

    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("etag")
    @Expose
    private String etag;
    @SerializedName("skills")
    @Expose
    private String skills;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("objectType")
    @Expose
    private String objectType;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("displayName")
    @Expose
    private String displayName;
    @SerializedName("name")
    @Expose
    private Name name;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("image")
    @Expose
    private Image image;
    @SerializedName("placesLived")
    @Expose
    private List<PlacesLived> placesLived = null;
    @SerializedName("isPlusUser")
    @Expose
    private boolean isPlusUser;
    @SerializedName("circledByCount")
    @Expose
    private int circledByCount;
    @SerializedName("verified")
    @Expose
    private boolean verified;
    @SerializedName("cover")
    @Expose
    private Cover cover;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public List<PlacesLived> getPlacesLived() {
        return placesLived;
    }

    public void setPlacesLived(List<PlacesLived> placesLived) {
        this.placesLived = placesLived;
    }

    public boolean isIsPlusUser() {
        return isPlusUser;
    }

    public void setIsPlusUser(boolean isPlusUser) {
        this.isPlusUser = isPlusUser;
    }

    public int getCircledByCount() {
        return circledByCount;
    }

    public void setCircledByCount(int circledByCount) {
        this.circledByCount = circledByCount;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public Cover getCover() {
        return cover;
    }

    public void setCover(Cover cover) {
        this.cover = cover;
    }

}
