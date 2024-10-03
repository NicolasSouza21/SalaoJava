package br.app.com.service;

import br.app.com.model.Produto;
import br.app.com.database.DatabaseConnection; // Certifique-se de ter a conexão do banco no pacote correto.

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoService {
    // Método para cadastrar um produto
    public boolean cadastrarProduto(Produto produto) {
        String sql = "INSERT INTO produtos (nome, quantidade, preco) VALUES (?, ?, ?)";
        boolean sucesso = false; // Variável para controlar o sucesso

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, produto.getNome());
            statement.setInt(2, produto.getQuantidade());
            statement.setDouble(3, produto.getPreco());
            int rowsAffected = statement.executeUpdate(); // Captura o número de linhas afetadas

            sucesso = rowsAffected > 0; // Se linhas foram afetadas, o cadastro foi bem-sucedido

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar produto: " + e.getMessage());
        }

        return sucesso; // Retorna o resultado do cadastro
    }

    // Método para listar todos os produtos
    public List<Produto> listarProdutos() {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM produtos";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Produto produto = new Produto();
                produto.setId(resultSet.getInt("id"));
                produto.setNome(resultSet.getString("nome"));
                produto.setQuantidade(resultSet.getInt("quantidade"));
                produto.setPreco(resultSet.getDouble("preco"));
                produtos.add(produto);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar produtos: " + e.getMessage());
        }

        return produtos;
    }

    // Método para buscar um produto pelo ID
    public Produto buscarProduto(int id) {
        Produto produto = null;
        String sql = "SELECT * FROM produtos WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                produto = new Produto();
                produto.setId(resultSet.getInt("id"));
                produto.setNome(resultSet.getString("nome"));
                produto.setQuantidade(resultSet.getInt("quantidade"));
                produto.setPreco(resultSet.getDouble("preco"));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar produto: " + e.getMessage());
        }

        return produto;
    }

    // Método para editar um produto existente
    public boolean editarProduto(Produto produto) {
        String sql = "UPDATE produtos SET nome = ?, quantidade = ?, preco = ? WHERE id = ?";
        boolean sucesso = false;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, produto.getNome());
            statement.setInt(2, produto.getQuantidade());
            statement.setDouble(3, produto.getPreco());
            statement.setInt(4, produto.getId());
            int rowsAffected = statement.executeUpdate();

            sucesso = rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao editar produto: " + e.getMessage());
        }

        return sucesso;
    }

    // Método para deletar um produto
    public boolean deletarProduto(int id) {
        String sql = "DELETE FROM produtos WHERE id = ?";
        boolean sucesso = false;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();

            sucesso = rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao deletar produto: " + e.getMessage());
        }

        return sucesso;
    }
}
