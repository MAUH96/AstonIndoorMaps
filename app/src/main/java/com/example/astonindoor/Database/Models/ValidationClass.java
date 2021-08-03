package com.example.astonindoor.Database.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * need to be revisited
 */
public class ValidationClass {
    @SerializedName("isSelected")
    @Expose
    private String isSelected;


    public String getIsSelected() {
        return isSelected;
    }


}

