package com.aula.mobile_hivemind.recyclerViewParadas;

public class ParadaModel {
    private String nome;
    private String setor;
    private String hora;

    public ParadaModel(String nome, String setor, String hora) {
        this.nome = nome;
        this.setor = setor;
        this.hora = hora;
    }

    public String getNome() {
        return nome;
    }

    public String getSetor() {
        return setor;
    }

    public String getHora() {
        return hora;
    }
}

