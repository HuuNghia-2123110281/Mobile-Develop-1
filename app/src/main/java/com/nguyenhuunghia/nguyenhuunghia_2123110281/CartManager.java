package com.nguyenhuunghia.nguyenhuunghia_2123110281;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static CartManager instance;
    private List<CartItem> cartItems;

    private CartManager() {
        cartItems = new ArrayList<>();
    }

    public static CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    // ================== QUẢN LÝ GIỎ HÀNG ==================

    // Thêm sản phẩm với số lượng mặc định = 1
    public void addToCart(Product product) {
        addToCart(product, 1);
    }

    // Thêm sản phẩm với số lượng chỉ định
    public void addToCart(Product product, int quantity) {
        for (CartItem item : cartItems) {
            if (item.getProduct().getId().equals(product.getId())) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        cartItems.add(new CartItem(product, quantity));
    }

    // Tăng số lượng sản phẩm trong giỏ
    public void increaseQuantity(Product product) {
        for (CartItem item : cartItems) {
            if (item.getProduct().getId().equals(product.getId())) {
                item.setQuantity(item.getQuantity() + 1);
                return;
            }
        }
    }

    // Giảm số lượng sản phẩm trong giỏ
    public void decreaseQuantity(Product product) {
        for (CartItem item : cartItems) {
            if (item.getProduct().getId().equals(product.getId())) {
                if (item.getQuantity() > 1) {
                    item.setQuantity(item.getQuantity() - 1);
                } else {
                    cartItems.remove(item);
                }
                return;
            }
        }
    }

    // Xóa sản phẩm khỏi giỏ
    public void removeFromCart(Product product) {
        for (CartItem item : cartItems) {
            if (item.getProduct().getId().equals(product.getId())) {
                cartItems.remove(item);
                return;
            }
        }
    }

    // Lấy toàn bộ giỏ hàng
    public List<CartItem> getCartItems() {
        return cartItems;
    }

    // Xóa toàn bộ giỏ hàng
    public void clearCart() {
        cartItems.clear();
    }

    // Tính tổng tiền giỏ hàng
    public double getTotalPrice() {
        double total = 0;
        for (CartItem item : cartItems) {
            total += item.getQuantity() * item.getProduct().getPrice();
        }
        return total;
    }
}
