package com.nguyenhuunghia.nguyenhuunghia_2123110281;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class User implements Serializable {

    @SerializedName("id")
    private String id;   // ✅ String cho id (MockAPI thường để String)

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("phone")
    private String phone;

    @SerializedName("password")
    private String password;

    @SerializedName("avatar")
    private String avatar;

    public User() {}

    // ✅ Constructor đầy đủ
    public User(String id, String name, String email, String phone, String password, String avatar) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.avatar = avatar;
    }

    // ✅ Constructor khi update có email
    public User(String id, String name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    // ✅ Constructor khi update chỉ có name + phone
    public User(String id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public User(int id, String name, String phone) {
    }

    // Getter
    public String getId() {
        return id != null ? id : "";
    }

    public String getName() {
        return name != null ? name : "";
    }

    public String getEmail() {
        return email != null ? email : "";
    }

    public String getPhone() {
        return phone != null ? phone : "";
    }

    public String getPassword() {
        return password != null ? password : "";
    }

    public String getAvatar() {
        return avatar != null ? avatar : "";
    }

    // Setter
    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setPassword(String password) { this.password = password; }
    public void setAvatar(String avatar) { this.avatar = avatar; }
}
