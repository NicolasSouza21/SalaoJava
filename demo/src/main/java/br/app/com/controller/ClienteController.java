package br.app.com.controller;

import br.app.com.model.Cliente;
import br.app.com.service.ClienteService;

import java.util.List;

public class ClienteController {
    private final ClienteService clienteService;

    public ClienteController() {
        this.clienteService = new ClienteService();
    }

    // Método para cadastrar um cliente
    public boolean cadastrarCliente(Cliente cliente) {
        boolean sucesso = clienteService.cadastrarCliente(cliente);
        if (sucesso) {
            System.out.println("Cliente cadastrado com sucesso: " + cliente.getNome());
        } else {
            System.out.println("Erro ao cadastrar cliente: operação falhou.");
        }
        return sucesso;
    }

    // Método para listar todos os clientes
    public List<Cliente> listarClientes() {
        List<Cliente> clientes = clienteService.listarClientes();
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
        } else {
            for (Cliente cliente : clientes) {
                System.out.println(cliente);
            }
        }
        return clientes;
    }

    // Método para buscar um cliente pelo ID
    public Cliente buscarCliente(int id) {
        Cliente cliente = clienteService.buscarCliente(id);
        if (cliente != null) {
            System.out.println("Cliente encontrado: " + cliente);
        } else {
            System.out.println("Cliente não encontrado.");
        }
        return cliente;
    }

    // Método para editar um cliente
    public boolean editarCliente(Cliente cliente) {
        boolean sucesso = clienteService.editarCliente(cliente);
        if (sucesso) {
            System.out.println("Cliente editado com sucesso: " + cliente.getNome());
        } else {
            System.out.println("Erro ao editar cliente: operação falhou.");
        }
        return sucesso;
    }

    // Método para deletar um cliente
    public boolean deletarCliente(int id) {
        boolean sucesso = clienteService.deletarCliente(id);
        if (sucesso) {
            System.out.println("Cliente deletado com sucesso: ID " + id);
        } else {
            System.out.println("Erro ao deletar cliente: operação falhou.");
        }
        return sucesso;
    }
}