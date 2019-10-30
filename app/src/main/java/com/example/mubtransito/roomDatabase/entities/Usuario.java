package com.example.mubtransito.roomDatabase.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Usuario {
    @PrimaryKey(autoGenerate = true)
    @NonNull()
    private Long id;
    private Long idUsuarioAPI;
    private String nome;
    private String email;
    private String cidade;
    private boolean manterLogado;
    private boolean stAtividade;
    private boolean agente;
    private int nvAcesso;

    public Usuario(Long id, Long idUsuarioAPI, String nome, String email, String cidade, boolean manterLogado, boolean stAtividade, boolean agente, int nvAcesso) {
        this.id = id;
        this.idUsuarioAPI = idUsuarioAPI;
        this.nome = nome;
        this.email = email;
        this.cidade = cidade;
        this.manterLogado = manterLogado;
        this.stAtividade = stAtividade;
        this.agente = agente;
        this.nvAcesso = nvAcesso;
    }

    @Ignore
    public Usuario(){

    }
    @NonNull
    public Long getId() {
        return id;
    }

    public void setId(@NonNull Long id) {
        this.id = id;
    }

    public Long getIdUsuarioAPI() {
        return idUsuarioAPI;
    }

    public void setIdUsuarioAPI(Long idUsuarioAPI) {
        this.idUsuarioAPI = idUsuarioAPI;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public boolean isManterLogado() {
        return manterLogado;
    }

    public void setManterLogado(boolean manterLogado) {
        this.manterLogado = manterLogado;
    }

    public boolean isStAtividade() {
        return stAtividade;
    }

    public void setStAtividade(boolean stAtividade) {
        this.stAtividade = stAtividade;
    }

    public boolean isAgente() {
        return agente;
    }

    public void setAgente(boolean agente) {
        this.agente = agente;
    }

    public int getNvAcesso() {
        return nvAcesso;
    }

    public void setNvAcesso(int nvAcesso) {
        this.nvAcesso = nvAcesso;
    }
}
