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

public class UserPersonFragment extends Fragment {

    private EditText editNome;
    private EditText editCpf;
    private EditText editEmail;
    private EditText editEndereco;

    private TextView textError;

    public UserPersonFragment() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_person, container, false);

        editNome = rootView.findViewById(R.id.edit_nome);
        editCpf = rootView.findViewById(R.id.edit_cpf);
        editEmail = rootView.findViewById(R.id.edit_email);
        editEndereco = rootView.findViewById(R.id.edit_endereco);

        textError = rootView.findViewById(R.id.text_error);



        Button buttonCadastrar = rootView.findViewById(R.id.button_cadastrar);
        buttonCadastrar.setOnClickListener(v -> {

            Customers customers = new Customers(editNome.getText().toString(),
                    editCpf.getText().toString(),
                    editEndereco.getText().toString(),
                    editEmail.getText().toString());
            createUser(customers);
        });

        return rootView;
    }

    private void createUser(Customers customers) {
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
        editNome.setText("");
        editCpf.setText("");
        editEmail.setText("");
        editEndereco.setText("");
    }

    private boolean validarCampos() {
        boolean isValid = true;
        String[] camposNaoPreenchidos = new String[4];
        int index = 0;

        if (editNome.getText().toString().trim().isEmpty()) {
            isValid = false;
            camposNaoPreenchidos[index++] = "Nome";
        }
        if (editCpf.getText().toString().trim().isEmpty()) {
            isValid = false;
            camposNaoPreenchidos[index++] = "CPF";
        }
        if (editEmail.getText().toString().trim().isEmpty()) {
            isValid = false;
            camposNaoPreenchidos[index++] = "Email";
        }
        if (editEndereco.getText().toString().trim().isEmpty()) {
            isValid = false;
            camposNaoPreenchidos[index++] = "Endereço";
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
