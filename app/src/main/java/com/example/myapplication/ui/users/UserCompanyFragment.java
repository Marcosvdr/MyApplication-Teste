package com.example.myapplication.ui.users;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.models.Customers;
import com.example.myapplication.repositories.UserRepository;
import com.example.myapplication.utils.ToastHelper;

public class UserCompanyFragment extends Fragment {

    private EditText editRazaoSocial;
    private EditText editNomeFantasia;
    private EditText editCnpj;

    private TextView textError;

    public UserCompanyFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_company, container, false);

        editRazaoSocial = rootView.findViewById(R.id.edit_razao_social);
        editNomeFantasia = rootView.findViewById(R.id.edit_nome_fantasia);
        editCnpj = rootView.findViewById(R.id.edit_cnpj);

        textError = rootView.findViewById(R.id.text_error);

        Button buttonCadastrar = rootView.findViewById(R.id.button_cadastrar);


        buttonCadastrar.setOnClickListener(v -> {
            Customers customers = new Customers(editRazaoSocial.getText().toString(),
                    editCnpj.getText().toString(),
                    editNomeFantasia.getText().toString());
            cadastrarUsuario(customers);
        });

        return rootView;
    }

    private void cadastrarUsuario(Customers customers) {
        boolean created = false;
        try {
            if (validarCampos()) {
                UserRepository userRepository = new UserRepository(getContext());
                created = userRepository.saveUsers(customers);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (created) {
            ToastHelper.showCustomToast(getContext(), "success", "Cadastrado!");
            clearTexts();
        }
    }

    private void clearTexts() {
        editRazaoSocial.setText("");
        editCnpj.setText("");
        editNomeFantasia.setText("");
    }

    private boolean validarCampos() {
        boolean isValid = true;
        String[] camposNaoPreenchidos = new String[3];
        int index = 0;

        if (editRazaoSocial.getText().toString().trim().isEmpty()) {
            isValid = false;
            camposNaoPreenchidos[index++] = "Razão Social";
        }
        if (editNomeFantasia.getText().toString().trim().isEmpty()) {
            isValid = false;
            camposNaoPreenchidos[index++] = "Nome Fantasia";
        }
        if (editCnpj.getText().toString().trim().isEmpty()) {
            isValid = false;
            camposNaoPreenchidos[index++] = "CNPJ";
        }

        if (!isValid) {
            exibirMensagemErro(formatarMensagemErro(camposNaoPreenchidos, index));
        } else {
            textError.setVisibility(View.GONE);
        }

        return isValid;
    }

    private String formatarMensagemErro(String[] camposNaoPreenchidos, int length) {
        StringBuilder mensagemErro = new StringBuilder("Por favor, preencha: ");
        for (int i = 0; i < length; i++) {
            mensagemErro.append(camposNaoPreenchidos[i]);
            if (i < length - 2) {
                mensagemErro.append(", ");
            } else if (i == length - 2) {
                mensagemErro.append(" e ");
            } else {
                mensagemErro.append(".");
            }
        }
        return mensagemErro.toString();
    }

    private void exibirMensagemErro(String mensagem) {
        textError.setVisibility(View.VISIBLE);
        textError.setText(mensagem);
    }
}
