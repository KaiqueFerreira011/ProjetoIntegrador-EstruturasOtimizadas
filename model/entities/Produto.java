package model.entities;

import java.util.Objects;

public class Produto implements Comparable<Produto> {

    private String idProduto; // Chave para o HashMap
    private String nome;
    private int quantidadeEstoque;
    private double precoUnitario;
    private int estoqueMinimo; // Adicionado para lógica de estoque crítico

    // Construtor
    public Produto(String idProduto, String nome, int quantidadeEstoque, double precoUnitario, int estoqueMinimo) {
        this.idProduto = idProduto;
        this.nome = nome;
        this.quantidadeEstoque = quantidadeEstoque;
        this.precoUnitario = precoUnitario;
        this.estoqueMinimo = estoqueMinimo;
    }

    // Getters e Setters
    public String getIdProduto() {
        return idProduto;
    }

    public String getNome() {
        return nome;
    }

    // Setters são importantes para atualização
    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(int quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public int getEstoqueMinimo() {
        return estoqueMinimo;
    }

    // Método para calcular o valor total do item no estoque
    public double calcularValorEstoque() {
        return this.quantidadeEstoque * this.precoUnitario;
    }

    // Método hashCode e equals: Cruciais para o funcionamento correto do HashMap
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Produto produto = (Produto) o;
        return Objects.equals(idProduto, produto.idProduto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProduto);
    }

    // Método toString para facilitar a impressão
    @Override
    public String toString() {
        return String.format("[ID: %s] %s | Estoque: %d | Preço: R$%.2f | Valor Total: R$%.2f",
                idProduto, nome, quantidadeEstoque, precoUnitario, calcularValorEstoque());
    }

    // Implementação da interface Comparable: Ordena por nome alfabeticamente
    @Override
    public int compareTo(Produto outro) {
        return this.nome.compareToIgnoreCase(outro.nome);
    }
}
