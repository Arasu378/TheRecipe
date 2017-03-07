
package com.app.kyrostechnologies.recipe.pojocovergoogle;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CoverInfo {

    @SerializedName("topImageOffset")
    @Expose
    private int topImageOffset;
    @SerializedName("leftImageOffset")
    @Expose
    private int leftImageOffset;

    public int getTopImageOffset() {
        return topImageOffset;
    }

    public void setTopImageOffset(int topImageOffset) {
        this.topImageOffset = topImageOffset;
    }

    public int getLeftImageOffset() {
        return leftImageOffset;
    }

    public void setLeftImageOffset(int leftImageOffset) {
        this.leftImageOffset = leftImageOffset;
    }

}
