package com.example.myapplication.repositories;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.database.BancoMovelDBHelper;
import com.example.myapplication.models.Customers;
import com.example.myapplication.utils.UserFilters;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class UserRepository {

    private SQLiteDatabase database;
    private BancoMovelDBHelper dbHelper;

    public UserRepository(Context context) {
        dbHelper = new BancoMovelDBHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public boolean saveUsers(Customers customers) {
        open();

        ContentValues values = new ContentValues();
        values.put("CLI_CODIGOCLIENTE", generateClientCode());
        values.put("CLI_RAZAOSOCIAL", customers.getRazaoSocial());
        values.put("CLI_CGCCPF", customers.getCgcCpf());
        values.put("CLI_ENDERECO", customers.getEndereco());
        values.put("CLI_EMAIL", customers.getEmail());

        long result = database.insert("GUA_CLIENTES", null, values);
        close();
        return result != -1;
    }


    @SuppressLint("Range")
    public List<Customers> getAllClientes(UserFilters filters) {
        List<Customers> clientes = new ArrayList<>();
        open();

        String selection = null;
        List<String> selectionArgs = new ArrayList<>();

        if (filters != null) {
            StringBuilder whereClause = new StringBuilder();
            List<String> whereArgs = new ArrayList<>();

            if (filters.getName() != null && !filters.getName().isEmpty()) {
                whereClause.append("CLI_RAZAOSOCIAL LIKE ?");
                whereArgs.add("%" + filters.getName() + "%");
            }

            if (filters.getCpf() != null && !filters.getCpf().isEmpty()) {
                if (whereClause.length() > 0) {
                    whereClause.append(" AND ");
                }
                whereClause.append("CLI_CGCCPF LIKE ?");
                whereArgs.add("%" + filters.getCpf() + "%");
            }

            if (filters.getFantasyName() != null && !filters.getFantasyName().isEmpty()) {
                if (whereClause.length() > 0) {
                    whereClause.append(" AND ");
                }
                whereClause.append("CLI_NOMEFANTASIA LIKE ?");
                whereArgs.add("%" + filters.getFantasyName() + "%");
            }

            if (!whereArgs.isEmpty()) {
                selection = whereClause.toString();
                selectionArgs.addAll(whereArgs);
            }
        }

        Cursor cursor = database.query("GUA_CLIENTES", null, selection,
                selectionArgs.isEmpty() ? null : selectionArgs.toArray(new String[0]),
                null, null, "CLI_CODIGOCLIENTE DESC", String.valueOf(filters.getLimit()));


        if (cursor != null && cursor.moveToFirst()) {
            do {
                Customers cliente = new Customers();
                cliente.setId(cursor.getString(cursor.getColumnIndex("CLI_CODIGOCLIENTE")));
                cliente.setRazaoSocial(cursor.getString(cursor.getColumnIndex("CLI_RAZAOSOCIAL")));
                cliente.setCgcCpf(cursor.getString(cursor.getColumnIndex("CLI_CGCCPF")));
                cliente.setEndereco(cursor.getString(cursor.getColumnIndex("CLI_ENDERECO")));
                clientes.add(cliente);
            } while (cursor.moveToNext());
            cursor.close();
        }

        close();
        return clientes;
    }

    public boolean deleteUserByCode(String code) {
        open();
        int result = database.delete("GUA_CLIENTES", "CLI_CODIGOCLIENTE = ?", new String[]{code});
        close();
        return result > 0;
    }

    public boolean updateUserByCode(String code, Customers updatedCustomer) {
        if (updatedCustomer == null) {
            return false;
        }
        open();

        ContentValues values = new ContentValues();

        if (updatedCustomer.getRazaoSocial() != null && !updatedCustomer.getRazaoSocial().isEmpty()) {
            values.put("CLI_RAZAOSOCIAL", updatedCustomer.getRazaoSocial());
        }
        if (updatedCustomer.getCgcCpf() != null && !updatedCustomer.getCgcCpf().isEmpty()) {
            values.put("CLI_CGCCPF", updatedCustomer.getCgcCpf());
        }
        if (updatedCustomer.getEndereco() != null && !updatedCustomer.getEndereco().isEmpty()) {
            values.put("CLI_ENDERECO", updatedCustomer.getEndereco());
        }
        if (updatedCustomer.getEmail() != null && !updatedCustomer.getEmail().isEmpty()) {
            values.put("CLI_EMAIL", updatedCustomer.getEmail());
        }

        int result = database.update("GUA_CLIENTES", values, "CLI_CODIGOCLIENTE = ?", new String[]{code});
        close();
        return result > 0;
    }

    private String generateClientCode() {
        int randomNum = ThreadLocalRandom.current().nextInt(900000, 1000000);
        return String.valueOf(randomNum);
    }
}
