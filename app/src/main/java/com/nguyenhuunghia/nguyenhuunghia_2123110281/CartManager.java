package com.nguyenhuunghia.nguyenhuunghia_2123110281;

import com.nguyenhuunghia.nguyenhuunghia_2123110281.CartItem;
import com.nguyenhuunghia.nguyenhuunghia_2123110281.Product;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static CartManager instance;
    private final List<CartItem> items = new ArrayList<>();

    private CartManager() {}

    public static synchronized CartManager getInstance() {
        if (instance == null) instance = new CartManager();
        return instance;
    }

    public void add(Product product) {
        for (CartItem ci : items) {
            if (ci.getProduct().getName().equals(product.getName())) {
                ci.increase();
                return;
            }
        }
        items.add(new CartItem(product));
    }

    public void removeAt(int position) {
        if (position >= 0 && position < items.size()) items.remove(position);
    }

    public List<CartItem> getItems() { return items; }

    public long getTotal() {
        long sum = 0;
        for (CartItem ci : items) sum += ci.getSubtotal();
        return sum;
    }

    public void clear() { items.clear(); }

    public void addToCart(Product currentProduct, int i) {

    }
}
