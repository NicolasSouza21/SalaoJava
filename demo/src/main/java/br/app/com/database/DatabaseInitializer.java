package br.app.com.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {
    public static void initializeDatabase() {
        String sqlCreateDatabase = "CREATE DATABASE salao"; // Nome do banco de dados

        // Registrar o driver do PostgreSQL manualmente
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver JDBC do PostgreSQL não encontrado: " + e.getMessage());
            return; // Impedir execução se o driver não for encontrado
        }

        try (Connection connection = DatabaseConnection.getConnection()) {
            // Se a conexão for bem-sucedida, o banco de dados já existe
            System.out.println("Banco de dados já existe.");
        } catch (SQLException e) {
            // Se não conseguir se conectar, provavelmente o banco de dados não existe, então criá-lo
            if (e.getMessage().contains("database \"salao\" does not exist")) {
                try (Connection connection = DatabaseConnection.getConnectionWithoutDatabase();
                     Statement statement = connection.createStatement()) {
                    // Criação do banco de dados
                    statement.executeUpdate(sqlCreateDatabase);
                    System.out.println("Banco de dados criado com sucesso.");

                    // Agora vamos criar as tabelas
                    createTables();
                } catch (SQLException ex) {
                    System.out.println("Erro ao criar o banco de dados: " + ex.getMessage());
                }
            } else {
                System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            }
        }
    }

    public static void createTables() {
        String sqlCreateCliente = "CREATE TABLE IF NOT EXISTS cliente (" +
                "id SERIAL PRIMARY KEY," +
                "nome VARCHAR(100) NOT NULL," +
                "telefone VARCHAR(15)," +
                "email VARCHAR(100)," +
                "endereco VARCHAR(255)" +
                ");";

        String sqlCreateFornecedor = "CREATE TABLE IF NOT EXISTS fornecedor (" +
                "id SERIAL PRIMARY KEY," +
                "nome VARCHAR(100) NOT NULL," +
                "cnpj VARCHAR(20)," +
                "contato VARCHAR(100)" +
                ");";

        String sqlCreateProduto = "CREATE TABLE IF NOT EXISTS produto (" +
                "id SERIAL PRIMARY KEY," +
                "nome VARCHAR(100) NOT NULL," +
                "quantidade INT NOT NULL," +
                "preco DECIMAL(10, 2) NOT NULL" +
                ");";

        String sqlCreateVenda = "CREATE TABLE IF NOT EXISTS venda (" +
                "id SERIAL PRIMARY KEY," +
                "data TIMESTAMP NOT NULL," +
                "total DECIMAL(10, 2) NOT NULL," +
                "forma_pagamento VARCHAR(50)" +
                ");";

        String sqlCreateServico = "CREATE TABLE IF NOT EXISTS servico (" +
                "id SERIAL PRIMARY KEY," +
                "nome VARCHAR(100) NOT NULL," +
                "preco DECIMAL(10, 2) NOT NULL" +
                ");";

        String sqlCreateAtendimento = "CREATE TABLE IF NOT EXISTS atendimento (" +
                "id SERIAL PRIMARY KEY," +
                "cliente_id INT REFERENCES cliente(id)," +
                "servico_id INT REFERENCES servico(id)," +
                "data TIMESTAMP NOT NULL" +
                ");";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {

            // Criando as tabelas
            statement.executeUpdate(sqlCreateCliente);
            statement.executeUpdate(sqlCreateFornecedor);
            statement.executeUpdate(sqlCreateProduto);
            statement.executeUpdate(sqlCreateVenda);
            statement.executeUpdate(sqlCreateServico);
            statement.executeUpdate(sqlCreateAtendimento);

            System.out.println("Tabelas criadas com sucesso.");

        } catch (SQLException e) {
            System.out.println("Erro ao criar tabelas: " + e.getMessage());
        }
    }
}
