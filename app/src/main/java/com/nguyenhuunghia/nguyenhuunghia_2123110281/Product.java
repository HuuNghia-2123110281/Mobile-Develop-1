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
    private String ImageUrl;

    @SerializedName("desc")
    private String desc;

    public String getDesc() {
        return desc != null ? desc : "";
    }

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
        // tránh null pointer
        return ImageUrl != null ? ImageUrl : "";
    }

}
