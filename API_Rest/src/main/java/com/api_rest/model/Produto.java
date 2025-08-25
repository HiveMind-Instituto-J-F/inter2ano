package com.api_rest.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "O campo nome não pode ser nulo")
    private String nome;
    @NotNull(message = "O campo descrição não pode ser nulo")
    private String descricao;
    @NotNull(message = "O campo preço não pode ser nulo")
    @DecimalMin(value = "0.01", message = "O preço deve ser maior que 0")
    private Double preco;
    @NotNull(message = "O campo estoque não pode ser nulo")
    private Integer quantidadedeestoque;

    public Produto() {

    }

    public Produto(String nome, String descricao, Double preco, Integer quantidadedeestoque) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidadedeestoque = quantidadedeestoque;
    }

    public Long getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPreco() {
        return this.preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getQuantidadedeestoque() {
        return this.quantidadedeestoque;
    }

    public void setQuantidadedeestoque(int quantidadedeestoque) {
        this.quantidadedeestoque = quantidadedeestoque;
    }

    public String toString() {
        return "Nome: " + this.nome +
                "\nDescricao: " + this.descricao +
                "\nPreço: " + this.preco +
                "\nQuantidade no estoque: " + this.quantidadedeestoque;
    }
}
