package com.example.myapplication.utils;

public class UserFilters {
    private String name;
    private String cpf;
    private String fantasyName;

    // Pagination
    private int  limit;

    public UserFilters(String name, String cpf, String fantasyName) {
        this.name = name;
        this.cpf = cpf;
        this.fantasyName = fantasyName;
        this.limit = 100;
    }

    public UserFilters() {
        this.name = null;
        this.cpf = null;
        this.fantasyName = null;
        this.limit = 100;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getFantasyName() {
        return fantasyName;
    }

    public void setFantasyName(String fantasyName) {
        this.fantasyName = fantasyName;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}

