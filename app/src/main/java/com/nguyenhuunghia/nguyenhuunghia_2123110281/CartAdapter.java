package com.nguyenhuunghia.nguyenhuunghia_2123110281;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context context;
    private List<CartItem> cartItems;
    private OnCartChangeListener listener;

    public interface OnCartChangeListener {
        void onCartChanged();
    }

    public CartAdapter(Context context, List<CartItem> cartItems, OnCartChangeListener listener) {
        this.context = context;
        this.cartItems = cartItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem item = cartItems.get(position);

        holder.tvName.setText(item.getProduct().getName());

        // Hiển thị giá theo VNĐ
        String formattedPrice = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"))
                .format(item.getProduct().getPrice());
        holder.tvPrice.setText(formattedPrice);

        holder.tvQty.setText(String.valueOf(item.getQuantity()));

        // Nút tăng
        holder.btnPlus.setOnClickListener(v -> {
            item.increase();
            holder.tvQty.setText(String.valueOf(item.getQuantity()));
            if (listener != null) listener.onCartChanged();
            notifyItemChanged(position);
        });

        // Nút giảm
        holder.btnMinus.setOnClickListener(v -> {
            item.decrease();
            if (item.getQuantity() <= 0) {
                cartItems.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, cartItems.size());
            } else {
                holder.tvQty.setText(String.valueOf(item.getQuantity()));
                notifyItemChanged(position);
            }
            if (listener != null) listener.onCartChanged();
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice, tvQty;
        Button btnPlus, btnMinus;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvProductName);
            tvPrice = itemView.findViewById(R.id.tvProductPrice);
            tvQty = itemView.findViewById(R.id.tvQuantity);
            btnPlus = itemView.findViewById(R.id.btnPlus);
            btnMinus = itemView.findViewById(R.id.btnMinus);
        }
    }
}
