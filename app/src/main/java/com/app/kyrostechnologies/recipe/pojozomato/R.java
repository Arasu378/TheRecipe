
package com.app.kyrostechnologies.recipe.pojozomato;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class R {

    @SerializedName("res_id")
    @Expose
    private int resId;

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

}
