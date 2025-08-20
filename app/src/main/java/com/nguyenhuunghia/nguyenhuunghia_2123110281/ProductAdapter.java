package com.nguyenhuunghia.nguyenhuunghia_2123110281;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context context;
    private List<Product> productList;
    private OnProductActionListener listener;

    // Constructor
    public ProductAdapter(Context context, List<Product> productList, OnProductActionListener listener) {
        this.context = context;
        this.productList = productList != null ? productList : new ArrayList<>();
        this.listener = listener;
    }

    // Constructor khác (cho MainActivity đang dùng)
    public ProductAdapter(ArrayList<Product> products, OnProductActionListener listener) {
        this.context = null;
        this.productList = products != null ? products : new ArrayList<>();
        this.listener = listener;
    }

    // Interface callback
    public interface OnProductActionListener {
        void onAddToCart(Product p);
        void onOpenDetail(Product p);
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (context == null) context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);

        // Set dữ liệu
        holder.tvName.setText(product.getName());
        holder.tvPrice.setText(
                NumberFormat.getCurrencyInstance(new Locale("vi", "VN"))
                        .format(product.getPrice())
        );

        // Load ảnh từ URL
        Glide.with(context)
                .load(product.getImageUrl())
                .placeholder(R.drawable.ic_placehoder)
                .error(R.drawable.ic_error)
                .into(holder.img);


        // Click mở chi tiết
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onOpenDetail(product);
        });

        // Click thêm vào giỏ hàng
        holder.btnAdd.setOnClickListener(v -> {
            if (listener != null) listener.onAddToCart(product);
        });
    }

    @Override
    public int getItemCount() {
        return productList != null ? productList.size() : 0;
    }

    public void setProducts(List<Product> products) {
        this.productList = products != null ? products : new ArrayList<>();
        notifyDataSetChanged();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tvName, tvPrice;
        Button btnAdd;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgCartProduct);
            tvName = itemView.findViewById(R.id.tvProductName);
            tvPrice = itemView.findViewById(R.id.tvProductPrice);
            btnAdd = itemView.findViewById(R.id.btnAddToCart);
        }
    }
}
