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
    public boolean cadastrarCliente(Cliente cliente) {  // Alterado para retornar boolean
        boolean sucesso = clienteService.cadastrarCliente(cliente); // Agora funciona corretamente
        if (sucesso) {
            System.out.println("Cliente cadastrado com sucesso: " + cliente.getNome());
        } else {
            System.out.println("Erro ao cadastrar cliente: operação falhou.");
        }
        return sucesso; // Retorna o resultado do cadastro
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
}
