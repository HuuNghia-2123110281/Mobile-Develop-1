package com.nguyenhuunghia.nguyenhuunghia_2123110281;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CategoryProductAdapter extends RecyclerView.Adapter<CategoryProductAdapter.ProductViewHolder> {

    private List<Product> productList;
    private Context context;

    public CategoryProductAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        if (product == null) return;

        // set tên sản phẩm
        holder.tvName.setText(product.getName());

        // format giá tiền (VNĐ)
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        holder.tvPrice.setText(formatter.format(product.getPrice()));

        // load ảnh bằng Glide (check null hoặc rỗng)
        String imageUrl = product.getImageUrl();
        if (imageUrl != null && !imageUrl.trim().isEmpty()) {
            Glide.with(context)
                    .load(imageUrl)
                    .into(holder.img);
        } else {
            holder.img.setImageResource(R.drawable.ic_placehoder); // ảnh mặc định
        }

        // Click item -> mở ProductDetailActivity
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailActivity.class);
            intent.putExtra("product_name", product.getName());
            intent.putExtra("product_price", product.getPrice());
            intent.putExtra("product_image", product.getImageUrl());
            intent.putExtra("product_desc", product.getDesc());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return productList != null ? productList.size() : 0;
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tvName, tvPrice;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgCartProduct);
            tvName = itemView.findViewById(R.id.tvProductName);
            tvPrice = itemView.findViewById(R.id.tvProductPrice);
        }
    }
}
