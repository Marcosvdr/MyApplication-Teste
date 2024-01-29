package com.example.myapplication.ui.users;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapters.CustomersAdapter;
import com.example.myapplication.databinding.FragmentUsersBinding;
import com.example.myapplication.models.Customers;
import com.example.myapplication.repositories.UserRepository;
import com.example.myapplication.utils.ToastHelper;
import com.example.myapplication.utils.UserFilters;

import java.util.List;

public class UsersFragment extends Fragment {

    private FragmentUsersBinding binding;
    private EditText editTextName;
    private EditText editTextCpfCnpj;
    private EditText editTextFantasyName;
    private Button buttonFilter;
    private RecyclerView recyclerView;
    private CustomersAdapter customersAdapter;

    private ProgressBar progressBar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentUsersBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        editTextName = root.findViewById(R.id.edit_text_name);
        editTextCpfCnpj = root.findViewById(R.id.edit_text_cpf_cnpj);
        editTextFantasyName = root.findViewById(R.id.edit_text_fantasy_name);
        buttonFilter = root.findViewById(R.id.button_filter);
        recyclerView = root.findViewById(R.id.recycler_users);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        customersAdapter = new CustomersAdapter();
        recyclerView.setAdapter(customersAdapter);

        customersAdapter.setOnCostumerClickListener(this::showConsumerDialog);

        buttonFilter.setOnClickListener(v -> {

            InputMethodManager inputMethodManager = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);

            String name = editTextName.getText().toString().trim();
            String cpfCnpj = editTextCpfCnpj.getText().toString().trim();
            String fantasyName = editTextFantasyName.getText().toString().trim();

            progressBar = root.findViewById(R.id.progress_bar);
            progressBar.setVisibility(View.VISIBLE);

            UserFilters filters = new UserFilters(name, cpfCnpj, fantasyName);

            searchInDatabase(filters);
        });

        return root;
    }

    private void searchInDatabase(UserFilters filters) {

        List<Customers> clientes;
        try {
            UserRepository userRepository = new UserRepository(getContext());
            clientes = userRepository.getAllClientes(filters);
            customersAdapter.setCustomersList(clientes);
        } catch (Exception e) {
            ToastHelper.showCustomToast(getContext(), "failure", "Erro ao buscar clientes!");
        } finally {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void showConsumerDialog(final Customers customer) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Nome: " + customer.getRazaoSocial());

        builder.setPositiveButton("Editar", (dialog, which) -> {
            ToastHelper.showCustomToast(getContext(), "alert", "Erro ao editar usuário.");
        });

        builder.setNegativeButton("Excluir", (dialog, which) -> deleteUser(customer.getId()));

        builder.setNeutralButton("Fechar", null);
        builder.show();
    }

    private void deleteUser(String code) {
        UserRepository userRepository = new UserRepository(getContext());
        boolean deleted = userRepository.deleteUserByCode(code);

        if (deleted) {
            ToastHelper.showCustomToast(getContext(), "success", "Usuário deletado do sistema.");
            searchInDatabase(new UserFilters());
        } else {
            ToastHelper.showCustomToast(getContext(), "failure", "Erro ao excluir usuário.");
        }
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
