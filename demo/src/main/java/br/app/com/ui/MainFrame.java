package br.app.com.ui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private JTabbedPane tabbedPane;

    public MainFrame() {
        setTitle("Sistema de Salão de Beleza");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Criar o JTabbedPane
        tabbedPane = new JTabbedPane();

        // Painéis de listagem
        ClientListPanel clientListPanel = new ClientListPanel();
        SupplierListPanel supplierListPanel = new SupplierListPanel();
        ProductListPanel productListPanel = new ProductListPanel(); // Painel de listagem de produtos

        // Adicionar as guias para clientes e fornecedores
        tabbedPane.addTab("Cadastro de Clientes", new ClientRegistrationPanel(clientListPanel)); // Cadastro de clientes
        tabbedPane.addTab("Listagem de Clientes", clientListPanel); // Listagem de clientes
        tabbedPane.addTab("Cadastro de Fornecedores", new SupplierRegistrationPanel(supplierListPanel)); // Cadastro de fornecedores
        tabbedPane.addTab("Listagem de Fornecedores", supplierListPanel); // Listagem de fornecedores

        // Adicionar as guias para produtos
        tabbedPane.addTab("Cadastro de Produtos", new ProductRegistrationPanel(productListPanel)); // Cadastro de produtos
        tabbedPane.addTab("Listagem de Produtos", productListPanel); // Listagem de produtos

        // Adicionar o painel de outras funcionalidades
    

        // Adicionar o JTabbedPane ao frame
        add(tabbedPane, BorderLayout.CENTER);

        // Define o tamanho da janela
        setSize(1360, 768); // Define a resolução desejada
        setVisible(true); // Exibe a janela
    }

    private JPanel createOtherFeaturesPanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Funcionalidade em desenvolvimento!"));
        return panel;
    }
}