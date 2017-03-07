
package com.app.kyrostechnologies.recipe.pojocovergoogle;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cover {

    @SerializedName("layout")
    @Expose
    private String layout;
    @SerializedName("coverPhoto")
    @Expose
    private CoverPhoto coverPhoto;
    @SerializedName("coverInfo")
    @Expose
    private CoverInfo coverInfo;

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public CoverPhoto getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(CoverPhoto coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

    public CoverInfo getCoverInfo() {
        return coverInfo;
    }

    public void setCoverInfo(CoverInfo coverInfo) {
        this.coverInfo = coverInfo;
    }

}
