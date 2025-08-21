package com.nguyenhuunghia.nguyenhuunghia_2123110281;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Product implements Serializable {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("price")
    private double price;

    @SerializedName("imageUrl")
    private String imageUrl;

    @SerializedName("desc")
    private String desc;

    @SerializedName("category")  // thêm trường category
    private String category;

    public String getId() {
        return id;
    }

    public String getName() {
        return name != null ? name : "";
    }

    public double getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl != null ? imageUrl : "";
    }

    public String getDesc() {
        return desc != null ? desc : "";
    }

    public String getCategory() {
        return category != null ? category : "";
    }
}
