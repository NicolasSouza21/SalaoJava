package br.app.com.ui;

import br.app.com.controller.FornecedorController;
import br.app.com.model.Fornecedor;

import javax.swing.*;
import java.awt.*;

public class SupplierRegistrationPanel extends JPanel {
    private JTextField txtNome, txtCnpj, txtContato;
    private FornecedorController fornecedorController;
    private SupplierListPanel supplierListPanel; // Painel de listagem de fornecedores para atualizar

    public SupplierRegistrationPanel(SupplierListPanel supplierListPanel) {
        this.supplierListPanel = supplierListPanel; // Recebe o painel de listagem
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Inicializando o controlador de fornecedores
        fornecedorController = new FornecedorController();

        // Campos para cadastro de fornecedor
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JLabel lblNome = new JLabel("Nome:");
        txtNome = new JTextField(20);
        gbc.gridwidth = 1;
        add(lblNome, gbc);
        
        gbc.gridx = 1;
        add(txtNome, gbc);
        
        gbc.gridy++;
        JLabel lblCnpj = new JLabel("CNPJ:");
        txtCnpj = new JTextField(20);
        gbc.gridx = 0;
        add(lblCnpj, gbc);
        
        gbc.gridx = 1;
        add(txtCnpj, gbc);
        
        gbc.gridy++;
        JLabel lblContato = new JLabel("Contato:");
        txtContato = new JTextField(20);
        gbc.gridx = 0;
        add(lblContato, gbc);
        
        gbc.gridx = 1;
        add(txtContato, gbc);
        
        gbc.gridy++;
        JButton btnCadastrar = new JButton("Cadastrar");
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        add(btnCadastrar, gbc);

        // Ação do botão de cadastrar com validação
        btnCadastrar.addActionListener(e -> {
            String nome = txtNome.getText().trim();
            String cnpj = txtCnpj.getText().trim();
            String contato = txtContato.getText().trim();

            // Validações básicas
            if (nome.isEmpty()) {
                JOptionPane.showMessageDialog(this, "O nome é obrigatório.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (cnpj.isEmpty()) {
                JOptionPane.showMessageDialog(this, "O CNPJ é obrigatório.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (contato.isEmpty()) {
                JOptionPane.showMessageDialog(this, "O contato é obrigatório.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Criação do fornecedor
            Fornecedor fornecedor = new Fornecedor();
            fornecedor.setNome(nome);
            fornecedor.setCnpj(cnpj);
            fornecedor.setContato(contato);

            // Tentativa de cadastro
            boolean sucesso = fornecedorController.cadastrarFornecedor(fornecedor);
            if (sucesso) {
                JOptionPane.showMessageDialog(this, "Fornecedor cadastrado com sucesso: " + nome);

                // Limpar campos após cadastro
                txtNome.setText("");
                txtCnpj.setText("");
                txtContato.setText("");

                // Atualizar a lista de fornecedores no painel de listagem
                supplierListPanel.atualizarLista();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar o fornecedor.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
