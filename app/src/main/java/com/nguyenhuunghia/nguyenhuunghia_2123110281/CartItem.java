package com.nguyenhuunghia.nguyenhuunghia_2123110281;

public class CartItem {
    private final Product product;
    private int quantity;

    public CartItem(Product product) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void increase() {
        quantity++;
    }

    public void decrease() {
        if (quantity > 1) {
            quantity--;
        }
    }

    public long getSubtotal() {
        return (long) (product.getPrice() * quantity);
    }
}
