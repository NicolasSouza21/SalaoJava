package br.app.com.dao;

import br.app.com.model.Venda;
import br.app.com.database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VendaDAO {

    // Método para cadastrar venda com base no clienteId
    public boolean cadastrarVenda(Venda venda) {
        String sql = "INSERT INTO vendas (data, total, forma_pagamento, cliente_id) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Utilizando o clienteId em vez de tentar acessar um método getCliente()
            statement.setDate(1, new java.sql.Date(venda.getData().getTime()));
            statement.setDouble(2, venda.getTotal());
            statement.setString(3, venda.getFormaPagamento());
            statement.setInt(4, venda.getClienteId());  // Alterado para getClienteId()

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar venda: " + e.getMessage());
            return false;
        }
    }

    // Método para buscar vendas por data
    public List<Venda> buscarVendasPorData(Date data) {
        List<Venda> vendas = new ArrayList<>();
        String sql = "SELECT * FROM vendas WHERE data = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDate(1, new java.sql.Date(data.getTime()));
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                // Criando um objeto Venda e preenchendo seus dados
                Venda venda = new Venda();
                venda.setId(resultSet.getInt("id"));
                venda.setData(resultSet.getDate("data"));
                venda.setTotal(resultSet.getDouble("total"));
                venda.setFormaPagamento(resultSet.getString("forma_pagamento"));
                venda.setClienteId(resultSet.getInt("cliente_id"));  // Armazenando o clienteId

                // Adiciona a venda na lista
                vendas.add(venda);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar vendas: " + e.getMessage());
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
            System.out.println("Erro ao cadastrar item de venda: " + e.getMessage());
            return false;
        }
    }
}
