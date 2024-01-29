package com.example.myapplication.ui.products;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapters.ProductsAdapter;
import com.example.myapplication.databinding.FragmentProductsBinding;
import com.example.myapplication.models.Products;
import com.example.myapplication.repositories.ProductRepository;
import com.example.myapplication.ui.enums.ProductStatus;
import com.example.myapplication.utils.ToastHelper;

import java.util.ArrayList;
import java.util.List;

public class ProductsFragment extends Fragment {

    private FragmentProductsBinding binding;
    private ProductsAdapter productsAdapter;
    private RecyclerView recyclerView;
    private Button buttonFilter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentProductsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        buttonFilter = root.findViewById(R.id.button_filter_products);
        recyclerView = root.findViewById(R.id.recycler_products);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        productsAdapter = new ProductsAdapter();
        recyclerView.setAdapter(productsAdapter);

        List<String> statusLabels = getStatusLabels();
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                statusLabels
        );
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerStatus.setAdapter(statusAdapter);

        productsAdapter.setOnProductClickListener(this::showProductDialog);

        buttonFilter.setOnClickListener(v -> {
            String selectedStatus = (String) binding.spinnerStatus.getSelectedItem();
            ProductStatus status = getProductStatus(selectedStatus);
            assert status != null;
            searchInDatabase(status);
        });

        return root;
    }

    private void searchInDatabase(ProductStatus status) {
        List<Products> products;
        try {
            ProductRepository productRepository = new ProductRepository(getContext());
            products = productRepository.getProductsByStatus(status);
            productsAdapter.setProductsList(products);
        } catch (Exception e) {
            ToastHelper.showCustomToast(getContext(), "failure", "Erro ao buscar produtos");
        }
    }

    private ProductStatus getProductStatus(String label) {
        for (ProductStatus status : ProductStatus.values()) {
            if (status.getLabel().equals(label)) {
                return status;
            }
        }
        return null;
    }

    private List<String> getStatusLabels() {
        List<String> labels = new ArrayList<>();
        for (ProductStatus status : ProductStatus.values()) {
            labels.add(status.getLabel());
        }
        return labels;
    }

    private void showProductDialog(Products product) {
        ProductRepository productRepository = new ProductRepository(getContext());
        List<Products> products = productRepository.getProductsDetailsByCodigo(product.getCodigo());

        StringBuilder message = new StringBuilder();
        for (Products p : products) {
            message.append("Preço R$: ").append(p.getPreco()).append("\n");
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Preços do Produto: " + product.getCodigo());
        builder.setMessage(message.toString());
        builder.setPositiveButton("Fechar", null);
        builder.show();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
