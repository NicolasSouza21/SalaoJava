package br.app.com.ui;

import br.app.com.model.Produto;
import br.app.com.service.ProdutoService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ProductListPanel extends JPanel {
    private final ProdutoService produtoService; // Serviço de produtos
    private JTable table; // Tabela para exibir produtos

    public ProductListPanel() {
        this.produtoService = new ProdutoService(); // Inicializa o serviço de produtos
        setLayout(new BorderLayout());

        // Cria e configura a tabela
        String[] columnNames = {"ID", "Nome", "Quantidade", "Preço"};
        atualizarTabela(); // Inicializa a tabela com os produtos

        // Adiciona um botão para atualizar a lista
        JButton refreshButton = new JButton("Atualizar");
        refreshButton.addActionListener(e -> atualizarTabela()); // Atualiza a lista ao clicar no botão
        add(refreshButton, BorderLayout.SOUTH);
    }

    // Método para atualizar a lista de produtos
    private void atualizarTabela() {
        List<Produto> produtos = produtoService.listarProdutos(); // Recupera a lista atualizada de produtos
        Object[][] data = new Object[produtos.size()][4]; // Inicializa os dados da tabela

        // Preenche os dados da tabela com os produtos
        for (int i = 0; i < produtos.size(); i++) {
            Produto produto = produtos.get(i);
            data[i][0] = produto.getId();
            data[i][1] = produto.getNome();
            data[i][2] = produto.getQuantidade();
            data[i][3] = produto.getPreco();
        }

        // Verifica se a tabela já existe e atualiza seu modelo
        if (table == null) {
            // Cria a tabela pela primeira vez
            table = new JTable(data, new String[]{"ID", "Nome", "Quantidade", "Preço"});
            JScrollPane scrollPane = new JScrollPane(table);
            add(scrollPane, BorderLayout.CENTER);
        } else {
            // Atualiza o modelo da tabela existente
            table.setModel(new javax.swing.table.DefaultTableModel(data, new String[]{"ID", "Nome", "Quantidade", "Preço"}));
        }

        // Atualiza a interface
        revalidate();
        repaint();
    }
}
