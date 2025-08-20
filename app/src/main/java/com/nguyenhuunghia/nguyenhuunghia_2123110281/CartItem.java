package com.nguyenhuunghia.nguyenhuunghia_2123110281;

public class CartItem {
    private Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    // Getter & Setter
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Tăng số lượng
    public void increase() {
        this.quantity++;
    }

    // Giảm số lượng
    public void decrease() {
        if (this.quantity > 1) {
            this.quantity--;
        }
    }

    // Tính tổng giá cho item này
    public double getTotalPrice() {
        return product.getPrice() * quantity;
    }
}
