package com.nguyenhuunghia.nguyenhuunghia_2123110281;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ProductRecyclerAdapter extends RecyclerView.Adapter<ProductRecyclerAdapter.ProductViewHolder> {

    private ArrayList<Product> products;

    public ProductRecyclerAdapter(ArrayList<Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = products.get(position);
        holder.tvName.setText(product.getName());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), ProductDetailActivity.class);
            intent.putExtra("name", product.getName());
            intent.putExtra("price", product.getPrice());
            intent.putExtra("description", product.getPrice());
            v.getContext().startActivity(intent);
        });


        // Format giá tiền VNĐ
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        holder.tvPrice.setText(formatter.format(product.getPrice()));

        // Gắn ảnh sản phẩm đơn giản (theo tên hoặc mặc định)
        String name = product.getName().toLowerCase();
        if (name.contains("promax")) {
            holder.imgProduct.setImageResource(R.drawable.iphone15promax);
        } else if (name.contains("15")) {
            holder.imgProduct.setImageResource(R.drawable.iphone15);
        }else if (name.contains("gaming")) {
            holder.imgProduct.setImageResource(R.drawable.laptopasusgaming);
        } else if (name.contains("vivobook")) {
            holder.imgProduct.setImageResource(R.drawable.laptopasusvivobook);
        } else  {
            holder.imgProduct.setImageResource(R.drawable.iphone15);
        }
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView tvName, tvPrice;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgCartProduct);
            tvName = itemView.findViewById(R.id.tvProductName);
            tvPrice = itemView.findViewById(R.id.tvProductPrice);
        }
    }
}
