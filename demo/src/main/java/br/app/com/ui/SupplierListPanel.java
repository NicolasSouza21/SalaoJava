package br.app.com.ui;

import br.app.com.model.Fornecedor;
import br.app.com.service.FornecedorService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SupplierListPanel extends JPanel {
    private final FornecedorService fornecedorService; // Serviço de fornecedor
    private JTable table; // Tabela para exibir fornecedores

    public SupplierListPanel() {
        this.fornecedorService = new FornecedorService(); // Inicializa o serviço de fornecedor
        setLayout(new BorderLayout());

        // Cria e configura a tabela
        String[] columnNames = {"ID", "Nome", "CNPJ", "Contato"};
        List<Fornecedor> fornecedores = fornecedorService.listarFornecedores(); // Lista de fornecedores
        Object[][] data = new Object[fornecedores.size()][4]; // Inicializa os dados da tabela

        // Preenche os dados da tabela com fornecedores
        for (int i = 0; i < fornecedores.size(); i++) {
            Fornecedor fornecedor = fornecedores.get(i);
            data[i][0] = fornecedor.getId();
            data[i][1] = fornecedor.getNome();
            data[i][2] = fornecedor.getCnpj();
            data[i][3] = fornecedor.getContato();
        }

        // Cria a tabela
        table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        
        // Adiciona um botão para atualizar a lista
        JButton refreshButton = new JButton("Atualizar");
        refreshButton.addActionListener(e -> atualizarLista());
        add(refreshButton, BorderLayout.SOUTH);
    }

    // Método público para atualizar a lista de fornecedores
    public void atualizarLista() { // Mudança de "void" para "public void"
        List<Fornecedor> fornecedores = fornecedorService.listarFornecedores(); // Atualiza a lista
        Object[][] data = new Object[fornecedores.size()][4]; // Inicializa os dados da tabela

        // Preenche os dados da tabela com fornecedores
        for (int i = 0; i < fornecedores.size(); i++) {
            Fornecedor fornecedor = fornecedores.get(i);
            data[i][0] = fornecedor.getId();
            data[i][1] = fornecedor.getNome();
            data[i][2] = fornecedor.getCnpj();
            data[i][3] = fornecedor.getContato();
        }

        // Atualiza o modelo da tabela
        table.setModel(new javax.swing.table.DefaultTableModel(data, new String[]{"ID", "Nome", "CNPJ", "Contato"}));
    }
}
