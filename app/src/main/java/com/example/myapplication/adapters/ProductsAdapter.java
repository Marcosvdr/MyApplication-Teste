package com.example.myapplication.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.models.Products;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> {

    private List<Products> productsList = new ArrayList<>();
    private OnProductClickListener onProductClickListener;

    public void setOnProductClickListener(OnProductClickListener listener) {
        this.onProductClickListener = listener;
    }

    public interface OnProductClickListener {
        void onProductClick(Products product);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setProductsList(List<Products> productsList) {
        this.productsList = productsList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item, parent, false);
        return new ProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Products currentProduct = productsList.get(position);
        holder.bind(currentProduct);
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewProductCodigo;
        private TextView textViewProductDescricao;
        private TextView textViewProductEstoque;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewProductCodigo = itemView.findViewById(R.id.text_view_product_codigo);
            textViewProductDescricao = itemView.findViewById(R.id.text_view_product_descricao);
            textViewProductEstoque = itemView.findViewById(R.id.text_view_product_estoque);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && onProductClickListener != null) {
                    onProductClickListener.onProductClick(productsList.get(position));
                }
            });
        }

        public void bind(Products product) {
            textViewProductCodigo.setText(product.getCodigo());
            textViewProductDescricao.setText(product.getDescricao());
            textViewProductEstoque.setText(product.getEstoque());
        }
    }
}
