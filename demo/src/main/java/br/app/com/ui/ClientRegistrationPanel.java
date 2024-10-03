package br.app.com.ui;

import br.app.com.controller.ClienteController;
import br.app.com.model.Cliente;

import javax.swing.*;
import java.awt.*;

public class ClientRegistrationPanel extends JPanel {
    private JTextField txtNome, txtTelefone, txtEmail, txtEndereco;
    private ClienteController clienteController;
    private ClientListPanel clientListPanel; // Painel de listagem de clientes para atualizar

    public ClientRegistrationPanel(ClientListPanel clientListPanel) {
        this.clientListPanel = clientListPanel; // Recebe o painel de listagem
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Inicializando o controlador de clientes
        clienteController = new ClienteController();

        // Campos para cadastro de cliente
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
        JLabel lblTelefone = new JLabel("Telefone:");
        txtTelefone = new JTextField(20);
        gbc.gridx = 0;
        add(lblTelefone, gbc);
        
        gbc.gridx = 1;
        add(txtTelefone, gbc);
        
        gbc.gridy++;
        JLabel lblEmail = new JLabel("Email:");
        txtEmail = new JTextField(20);
        gbc.gridx = 0;
        add(lblEmail, gbc);
        
        gbc.gridx = 1;
        add(txtEmail, gbc);
        
        gbc.gridy++;
        JLabel lblEndereco = new JLabel("Endereço:");
        txtEndereco = new JTextField(20);
        gbc.gridx = 0;
        add(lblEndereco, gbc);
        
        gbc.gridx = 1;
        add(txtEndereco, gbc);
        
        gbc.gridy++;
        JButton btnCadastrar = new JButton("Cadastrar");
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        add(btnCadastrar, gbc);

        // Ação do botão de cadastrar com validação
        btnCadastrar.addActionListener(e -> {
            String nome = txtNome.getText().trim();
            String telefone = txtTelefone.getText().trim();
            String email = txtEmail.getText().trim();
            String endereco = txtEndereco.getText().trim();

            // Validações básicas
            if (nome.isEmpty()) {
                JOptionPane.showMessageDialog(this, "O nome é obrigatório.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (telefone.isEmpty()) {
                JOptionPane.showMessageDialog(this, "O telefone é obrigatório.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (email.isEmpty()) {
                JOptionPane.showMessageDialog(this, "O e-mail é obrigatório.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!email.contains("@") || !email.contains(".")) {
                JOptionPane.showMessageDialog(this, "O e-mail fornecido não é válido.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Criação do cliente
            Cliente cliente = new Cliente();
            cliente.setNome(nome);
            cliente.setTelefone(telefone);
            cliente.setEmail(email);
            cliente.setEndereco(endereco);

            // Tentativa de cadastro
            boolean sucesso = clienteController.cadastrarCliente(cliente);
            if (sucesso) {
                JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso: " + nome);

                // Limpar campos após cadastro
                txtNome.setText("");
                txtTelefone.setText("");
                txtEmail.setText("");
                txtEndereco.setText("");

                // Atualizar a lista de clientes no painel de listagem
                clientListPanel.atualizarListaClientes();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar o cliente.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}