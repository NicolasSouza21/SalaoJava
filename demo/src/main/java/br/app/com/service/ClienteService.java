package br.app.com.service;

import br.app.com.model.Cliente;
import br.app.com.database.DatabaseConnection; // Certifique-se de ter a conexão do banco no pacote correto.

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteService {
    public boolean cadastrarCliente(Cliente cliente) {  // Alterado para retornar boolean
        String sql = "INSERT INTO cliente (nome, telefone, email, endereco) VALUES (?, ?, ?, ?)";
        boolean sucesso = false; // Variável para controlar o sucesso

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, cliente.getNome());
            statement.setString(2, cliente.getTelefone());
            statement.setString(3, cliente.getEmail());
            statement.setString(4, cliente.getEndereco());
            int rowsAffected = statement.executeUpdate(); // Captura o número de linhas afetadas

            sucesso = rowsAffected > 0; // Se linhas foram afetadas, o cadastro foi bem-sucedido

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar cliente: " + e.getMessage());
        }

        return sucesso; // Retorna o resultado do cadastro
    }

    public List<Cliente> listarClientes() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM cliente";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(resultSet.getInt("id"));
                cliente.setNome(resultSet.getString("nome"));
                cliente.setTelefone(resultSet.getString("telefone"));
                cliente.setEmail(resultSet.getString("email"));
                cliente.setEndereco(resultSet.getString("endereco"));
                clientes.add(cliente);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar clientes: " + e.getMessage());
        }

        return clientes;
    }

    public Cliente buscarCliente(int id) {
        Cliente cliente = null;
        String sql = "SELECT * FROM cliente WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                cliente = new Cliente();
                cliente.setId(resultSet.getInt("id"));
                cliente.setNome(resultSet.getString("nome"));
                cliente.setTelefone(resultSet.getString("telefone"));
                cliente.setEmail(resultSet.getString("email"));
                cliente.setEndereco(resultSet.getString("endereco"));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar cliente: " + e.getMessage());
        }

        return cliente;
    }
}
