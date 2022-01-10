package com.example.teste;

import android.net.Uri;

public class Contato {
    private String _nome, _telefone, _endereco, _email;
    private Uri _imagemURI;
    private long _id;

    public Contato(String nome, String telefone, String endereco, String email, Uri imagemURI){
        _nome = nome;
        _telefone = telefone;
        _endereco = endereco;
        _email = email;
        _imagemURI = imagemURI;
    }

    public String get_nome() {
        return _nome;
    }

    public String get_telefone() {
        return _telefone;
    }

    public String get_endereco() {
        return _endereco;
    }

    public String get_email() {
        return _email;
    }

    public Uri get_imagemURI() {
        return _imagemURI;
    }

    public long get_id() {
        return _id;
    }

    public void set_nome(String _nome) {
        this._nome = _nome;
    }

    public void set_telefone(String _telefone) {
        this._telefone = _telefone;
    }

    public void set_endereco(String _endereco) {
        this._endereco = _endereco;
    }

    public void set_email(String _email) {
        this._email = _email;
    }

    public void set_imagemURI(Uri _imagemURI) {
        this._imagemURI = _imagemURI;
    }

    public void set_id(long _id) {
        this._id = _id;
    }
}
