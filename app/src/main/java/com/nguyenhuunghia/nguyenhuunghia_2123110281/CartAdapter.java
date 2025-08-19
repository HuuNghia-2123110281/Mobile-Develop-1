package com.nguyenhuunghia.nguyenhuunghia_2123110281;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import com.bumptech.glide.Glide;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.VH> {

    public interface OnCartChange {
        void onRemove(int position);
        void onDataChanged();
    }

    private final List<CartItem> data;
    private final OnCartChange cb;

    public CartAdapter(List<CartItem> data, OnCartChange cb) {
        this.data = data;
        this.cb = cb;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        CartItem ci = data.get(position);

        // Load ảnh từ URL bằng Glide
        Glide.with(h.img.getContext())
                .load(ci.getProduct().getImageUrl())
                .into(h.img);

        h.tvName.setText(ci.getProduct().getName());
        h.tvQty.setText("x" + ci.getQuantity());
        h.tvPrice.setText(formatVND(ci.getSubtotal()));

        h.btnMinus.setOnClickListener(v -> {
            ci.decrease();
            notifyItemChanged(h.getBindingAdapterPosition());
            if (cb != null) cb.onDataChanged();
        });

        h.btnPlus.setOnClickListener(v -> {
            ci.increase();
            notifyItemChanged(h.getBindingAdapterPosition());
            if (cb != null) cb.onDataChanged();
        });

        h.btnRemove.setOnClickListener(v -> {
            if (cb != null) cb.onRemove(h.getBindingAdapterPosition());
        });
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tvName, tvQty, tvPrice;
        ImageButton btnMinus, btnPlus, btnRemove;

        VH(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgCartProduct);
            tvName = itemView.findViewById(R.id.tvCartName);
            tvQty = itemView.findViewById(R.id.tvCartQty);
            tvPrice = itemView.findViewById(R.id.tvCartPrice);
            btnMinus = itemView.findViewById(R.id.btnMinus);
            btnPlus = itemView.findViewById(R.id.btnPlus);
            btnRemove = itemView.findViewById(R.id.btnRemove);
        }
    }

    private String formatVND(long v) {
        NumberFormat f = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        return f.format(v);
    }
}
