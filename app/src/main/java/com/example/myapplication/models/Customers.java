package com.example.myapplication.models;

public class Customers {

    private String id;
    private String razaoSocial;
    private String cgcCpf;
    private String endereco;
    private String email;
    private String nomeFantasia;

    // User CPF
    public Customers(String razaoSocial, String cgcCpf, String endereco, String email) {
        this.razaoSocial = razaoSocial;
        this.cgcCpf = cgcCpf;
        this.endereco = endereco;
        this.email = email;
    }

    // User CNPJ
    public Customers(String razaoSocial, String cgcCpf, String nomeFantasia) {
        this.razaoSocial = razaoSocial;
        this.cgcCpf = cgcCpf;
        this.nomeFantasia = nomeFantasia;
    }

    public Customers() {
    }

    // Getters e Setters
    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getCgcCpf() {
        return cgcCpf;
    }

    public void setCgcCpf(String cgcCpf) {
        this.cgcCpf = cgcCpf;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

