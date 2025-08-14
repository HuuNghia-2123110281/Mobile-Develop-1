package com.nguyenhuunghia.nguyenhuunghia_2123110281;

public class Product {
    private String name;
    private int price;
    private String description; // ✅ Thêm mô tả

    public Product(String name, int price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    // Getter
    public String getName() { return name; }
    public int getPrice() { return price; }
    public String getDescription() { return description; }
}

