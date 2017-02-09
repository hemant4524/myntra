package com.ob.myntra.ui.vo;

import com.google.firebase.database.IgnoreExtraProperties;

/***
 * Created by Hemant4524 on 19/Jan/2017.
 */
@IgnoreExtraProperties
public class Category {
    public String categoryId;
    public String categoryName;
    public String logoUrl;

    public Category() {
    }

    public Category(String categoryId, String categoryName, String categoryLogo) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.logoUrl = categoryLogo;
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryId='" + categoryId + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", logoUrl='" + logoUrl + '\'' +
                '}';
    }
}
