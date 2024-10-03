package br.app.com.controller;

import br.app.com.model.Fornecedor;
import br.app.com.service.FornecedorService;

import java.util.List;

public class FornecedorController {
    private final FornecedorService fornecedorService;

    public FornecedorController() {
        this.fornecedorService = new FornecedorService();
    }

    // Método para cadastrar um fornecedor
    public boolean cadastrarFornecedor(Fornecedor fornecedor) {
        boolean sucesso = fornecedorService.cadastrarFornecedor(fornecedor);
        if (sucesso) {
            System.out.println("Fornecedor cadastrado com sucesso: " + fornecedor.getNome());
        } else {
            System.out.println("Erro ao cadastrar fornecedor: operação falhou.");
        }
        return sucesso;
    }

    // Método para listar todos os fornecedores
    public List<Fornecedor> listarFornecedores() {
        List<Fornecedor> fornecedores = fornecedorService.listarFornecedores();
        if (fornecedores.isEmpty()) {
            System.out.println("Nenhum fornecedor cadastrado.");
        } else {
            for (Fornecedor fornecedor : fornecedores) {
                System.out.println(fornecedor);
            }
        }
        return fornecedores;
    }

    // Método para buscar um fornecedor pelo ID
    public Fornecedor buscarFornecedor(int id) {
        Fornecedor fornecedor = fornecedorService.buscarFornecedor(id);
        if (fornecedor != null) {
            System.out.println("Fornecedor encontrado: " + fornecedor);
        } else {
            System.out.println("Fornecedor não encontrado.");
        }
        return fornecedor;
    }

    // Método para editar um fornecedor
    public boolean editarFornecedor(Fornecedor fornecedor) {
        boolean sucesso = fornecedorService.editarFornecedor(fornecedor);
        if (sucesso) {
            System.out.println("Fornecedor editado com sucesso: " + fornecedor.getNome());
        } else {
            System.out.println("Erro ao editar fornecedor: operação falhou.");
        }
        return sucesso;
    }

    // Método para deletar um fornecedor
    public boolean deletarFornecedor(int id) {
        boolean sucesso = fornecedorService.deletarFornecedor(id);
        if (sucesso) {
            System.out.println("Fornecedor deletado com sucesso: ID " + id);
        } else {
            System.out.println("Erro ao deletar fornecedor: operação falhou.");
        }
        return sucesso;
    }
}
