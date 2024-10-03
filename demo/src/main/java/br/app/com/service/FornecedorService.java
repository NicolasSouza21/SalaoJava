package br.app.com.service;

import br.app.com.model.Fornecedor;
import br.app.com.database.DatabaseConnection; // Certifique-se de ter a conexão do banco no pacote correto.

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FornecedorService {
    // Método para cadastrar um fornecedor
    public boolean cadastrarFornecedor(Fornecedor fornecedor) {
        String sql = "INSERT INTO fornecedor (nome, cnpj, contato) VALUES (?, ?, ?)";
        boolean sucesso = false; // Variável para controlar o sucesso

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, fornecedor.getNome());
            statement.setString(2, fornecedor.getCnpj());
            statement.setString(3, fornecedor.getContato());
            int rowsAffected = statement.executeUpdate(); // Captura o número de linhas afetadas

            sucesso = rowsAffected > 0; // Se linhas foram afetadas, o cadastro foi bem-sucedido

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar fornecedor: " + e.getMessage());
        }

        return sucesso; // Retorna o resultado do cadastro
    }

    // Método para listar todos os fornecedores
    public List<Fornecedor> listarFornecedores() {
        List<Fornecedor> fornecedores = new ArrayList<>();
        String sql = "SELECT * FROM fornecedor";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Fornecedor fornecedor = new Fornecedor();
                fornecedor.setId(resultSet.getInt("id"));
                fornecedor.setNome(resultSet.getString("nome"));
                fornecedor.setCnpj(resultSet.getString("cnpj"));
                fornecedor.setContato(resultSet.getString("contato"));
                fornecedores.add(fornecedor);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar fornecedores: " + e.getMessage());
        }

        return fornecedores;
    }

    // Método para buscar um fornecedor pelo ID
    public Fornecedor buscarFornecedor(int id) {
        Fornecedor fornecedor = null;
        String sql = "SELECT * FROM fornecedor WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                fornecedor = new Fornecedor();
                fornecedor.setId(resultSet.getInt("id"));
                fornecedor.setNome(resultSet.getString("nome"));
                fornecedor.setCnpj(resultSet.getString("cnpj"));
                fornecedor.setContato(resultSet.getString("contato"));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar fornecedor: " + e.getMessage());
        }

        return fornecedor;
    }

    // Método para editar um fornecedor existente
    public boolean editarFornecedor(Fornecedor fornecedor) {
        String sql = "UPDATE fornecedor SET nome = ?, cnpj = ?, contato = ? WHERE id = ?";
        boolean sucesso = false;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, fornecedor.getNome());
            statement.setString(2, fornecedor.getCnpj());
            statement.setString(3, fornecedor.getContato());
            statement.setInt(4, fornecedor.getId());
            int rowsAffected = statement.executeUpdate();

            sucesso = rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao editar fornecedor: " + e.getMessage());
        }

        return sucesso;
    }

    // Método para deletar um fornecedor
    public boolean deletarFornecedor(int id) {
        String sql = "DELETE FROM fornecedor WHERE id = ?";
        boolean sucesso = false;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();

            sucesso = rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao deletar fornecedor: " + e.getMessage());
        }

        return sucesso;
    }
}
