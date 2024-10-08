package br.app.com.dao;

import br.app.com.model.Venda;
import br.app.com.database.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VendaDAO {

    // Método para cadastrar venda com base no clienteId
    public boolean cadastrarVenda(Venda venda) {
        String sql = "INSERT INTO vendas (data, total, forma_pagamento, cliente_id) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Convertendo LocalDate para java.sql.Date
            statement.setDate(1, java.sql.Date.valueOf(venda.getData()));
            statement.setDouble(2, venda.getTotal());
            statement.setString(3, venda.getFormaPagamento());
            statement.setInt(4, venda.getClienteId());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                // Obter o ID gerado da venda e setar na venda
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        venda.setId(generatedKeys.getInt(1));
                    }
                }
            }
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar venda: " + e.getMessage());
            return false;
        }
    }

    // Método para buscar vendas por data
    public List<Venda> buscarVendasPorData(LocalDate data) {
        List<Venda> vendas = new ArrayList<>();
        String sql = "SELECT * FROM vendas WHERE data = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Convertendo LocalDate para java.sql.Date
            statement.setDate(1, java.sql.Date.valueOf(data));
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Venda venda = new Venda();
                venda.setId(resultSet.getInt("id"));
                venda.setData(resultSet.getDate("data").toLocalDate()); // Convertendo para LocalDate
                venda.setTotal(resultSet.getDouble("total"));
                venda.setFormaPagamento(resultSet.getString("forma_pagamento"));
                venda.setClienteId(resultSet.getInt("cliente_id")); // Armazenando o clienteId

                // Adiciona a venda na lista
                vendas.add(venda);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar vendas por data: " + e.getMessage());
        }

        return vendas;
    }

    // Método para cadastrar item de venda
    public boolean cadastrarItemVenda(int vendaId, int produtoId, int quantidade) {
        String sql = "INSERT INTO itens_venda (venda_id, produto_id, quantidade) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, vendaId);
            statement.setInt(2, produtoId);
            statement.setInt(3, quantidade);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar item de venda: " + e.getMessage());
            return false;
        }
    }

    // Método para buscar todas as vendas
    public List<Venda> buscarTodasVendas() {
        List<Venda> vendas = new ArrayList<>();
        String sql = "SELECT * FROM vendas"; // Ajuste o nome da tabela conforme necessário

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Venda venda = new Venda();
                venda.setId(resultSet.getInt("id"));
                venda.setData(resultSet.getDate("data").toLocalDate()); // Convertendo para LocalDate
                venda.setTotal(resultSet.getDouble("total"));
                venda.setFormaPagamento(resultSet.getString("forma_pagamento"));
                venda.setClienteId(resultSet.getInt("cliente_id")); // Armazenando o clienteId

                vendas.add(venda); // Adiciona a venda à lista
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar todas as vendas: " + e.getMessage());
        }

        return vendas;
    }

    public boolean excluirVenda(int vendaId) {
        String sql = "DELETE FROM vendas WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, vendaId);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao excluir venda: " + e.getMessage());
            return false;
        }
    }

    // Método para atualizar uma venda
    public boolean atualizarVenda(Venda venda) {
        String sql = "UPDATE vendas SET data = ?, total = ?, forma_pagamento = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Convertendo LocalDate para java.sql.Date
            statement.setDate(1, java.sql.Date.valueOf(venda.getData()));
            statement.setDouble(2, venda.getTotal());
            statement.setString(3, venda.getFormaPagamento());
            statement.setInt(4, venda.getId());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar venda: " + e.getMessage());
            return false;
        }
    }
}
