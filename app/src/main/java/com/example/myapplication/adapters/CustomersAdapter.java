package com.example.myapplication.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.models.Customers;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class CustomersAdapter extends RecyclerView.Adapter<CustomersAdapter.CustomerViewHolder> {

    private List<Customers> customersList = new ArrayList<>();

    private OnCostumerClickListener onCostumerClickListener;

    public void setOnCostumerClickListener(OnCostumerClickListener listener) {
        this.onCostumerClickListener = listener;
    }

    public interface OnCostumerClickListener {
        void onCustomerClick(Customers customers);
    }

    public void setCustomersList(List<Customers> customersList) {
        this.customersList = customersList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customer_item, parent, false);
        return new CustomerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        Customers currentCustomer = customersList.get(position);
        holder.bind(currentCustomer);
    }

    @Override
    public int getItemCount() {
        return customersList.size();
    }

    class CustomerViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewCustomerId;
        private TextView textViewCustomerRazaoSocial;
        private TextView textViewCustomerCgcCpf;

        public CustomerViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCustomerId = itemView.findViewById(R.id.text_view_customer_id);
            textViewCustomerRazaoSocial = itemView.findViewById(R.id.text_view_customer_razao_social);
            textViewCustomerCgcCpf = itemView.findViewById(R.id.text_view_customer_cgc_cpf);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && onCostumerClickListener != null) {
                    onCostumerClickListener.onCustomerClick(customersList.get(position));
                }
            });

        }

        public void bind(Customers customer) {
            textViewCustomerId.setText(customer.getId());
            textViewCustomerRazaoSocial.setText(customer.getRazaoSocial());
            textViewCustomerCgcCpf.setText(customer.getCgcCpf());
        }
    }
}