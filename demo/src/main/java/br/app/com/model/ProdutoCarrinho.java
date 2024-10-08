package br.app.com.model;
public class ProdutoCarrinho {
    private Produto produto;
    private int quantidade;

    public ProdutoCarrinho(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getPrecoTotal() {
        return produto.getPreco() * quantidade;
    }
}