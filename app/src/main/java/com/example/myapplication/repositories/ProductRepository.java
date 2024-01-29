package com.example.myapplication.repositories;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.database.BancoMovelDBHelper;
import com.example.myapplication.models.Products;
import com.example.myapplication.ui.enums.ProductStatus;

import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

    private SQLiteDatabase database;
    private BancoMovelDBHelper dbHelper;

    public ProductRepository(Context context) {
        dbHelper = new BancoMovelDBHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    @SuppressLint("Range")
    public List<Products> getProductsByStatus(ProductStatus status) {
        List<Products> products = new ArrayList<>();
        open();

        String selection;
        List<String> selectionArgs = new ArrayList<>();

        selection = "GUA_PRODUTOS.PRO_STATUS = ?";
        selectionArgs.add(status.toString());

        String query = "SELECT DISTINCT GUA_PRODUTOS.PRO_CODIGO, " +
                "GUA_PRODUTOS.PRO_DESCRICAO, " +
                "GUA_ESTOQUEEMPRESA.ESE_ESTOQUE " +
                "FROM GUA_PRODUTOS " +
                "INNER JOIN GUA_PRECOS ON GUA_PRODUTOS.PRO_CODIGO = GUA_PRECOS.PRP_CODIGO " +
                "INNER JOIN GUA_ESTOQUEEMPRESA ON GUA_PRODUTOS.PRO_CODIGO = GUA_ESTOQUEEMPRESA.ESE_CODIGO " +
                "WHERE " + selection +
                " LIMIT 100";

        Cursor cursor = database.rawQuery(query, selectionArgs.toArray(new String[0]));

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Products product = new Products();
                product.setCodigo(cursor.getString(cursor.getColumnIndex("PRO_CODIGO")));
                product.setDescricao(cursor.getString(cursor.getColumnIndex("PRO_DESCRICAO")));
                product.setEstoque(cursor.getString(cursor.getColumnIndex("ESE_ESTOQUE")));
                products.add(product);
            } while (cursor.moveToNext());
            cursor.close();
        }

        close();
        return products;
    }

    @SuppressLint("Range")
    public List<Products> getProductsDetailsByCodigo(String codigo) {
        List<Products> productsList = new ArrayList<>();
        open();

        String query = "SELECT GUA_PRECOS.PRP_PRECOS " +
                "FROM GUA_PRODUTOS " +
                "INNER JOIN GUA_PRECOS ON GUA_PRODUTOS.PRO_CODIGO = GUA_PRECOS.PRP_CODIGO " +
                "WHERE GUA_PRODUTOS.PRO_CODIGO = ?" +
                "ORDER by 1 ASC";

        Cursor cursor = database.rawQuery(query, new String[]{codigo});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Products product = new Products();
                product.setPreco(cursor.getString(cursor.getColumnIndex("PRP_PRECOS")));
                productsList.add(product);
            } while (cursor.moveToNext());
            cursor.close();
        }

        close();
        return productsList;
    }
}
