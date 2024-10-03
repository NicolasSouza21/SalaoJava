package br.app.com.controller;

import br.app.com.model.Produto;
import br.app.com.service.ProdutoService;

import java.util.List;

public class ProdutoController {
    private final ProdutoService produtoService;

    public ProdutoController() {
        this.produtoService = new ProdutoService();
    }

    // Método para cadastrar um produto
    public boolean cadastrarProduto(Produto produto) {
        boolean sucesso = produtoService.cadastrarProduto(produto);
        if (sucesso) {
            System.out.println("Produto cadastrado com sucesso: " + produto.getNome());
        } else {
            System.out.println("Erro ao cadastrar produto: operação falhou.");
        }
        return sucesso;
    }

    // Método para listar todos os produtos
    public List<Produto> listarProdutos() {
        List<Produto> produtos = produtoService.listarProdutos();
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
        } else {
            for (Produto produto : produtos) {
                System.out.println(produto); // Exibe a representação do produto
            }
        }
        return produtos;
    }

    // Método para buscar um produto pelo ID
    public Produto buscarProduto(int id) {
        Produto produto = produtoService.buscarProduto(id); // Corrigido para chamar o método existente
        if (produto != null) {
            System.out.println("Produto encontrado: " + produto);
        } else {
            System.out.println("Produto não encontrado.");
        }
        return produto;
    }

    // Método para editar um produto
    public boolean editarProduto(Produto produto) {
        boolean sucesso = produtoService.editarProduto(produto); // Corrigido para chamar o método correto
        if (sucesso) {
            System.out.println("Produto editado com sucesso: " + produto.getNome());
        } else {
            System.out.println("Erro ao editar produto: operação falhou.");
        }
        return sucesso;
    }

    // Método para deletar um produto
    public boolean deletarProduto(int id) {
        boolean sucesso = produtoService.deletarProduto(id); // Corrigido para chamar o método correto
        if (sucesso) {
            System.out.println("Produto deletado com sucesso: ID " + id);
        } else {
            System.out.println("Erro ao deletar produto: operação falhou.");
        }
        return sucesso;
    }
}
