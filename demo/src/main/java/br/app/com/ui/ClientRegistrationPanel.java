package br.app.com.ui;

import br.app.com.controller.ClienteController;
import br.app.com.model.Cliente;

import javax.swing.*;
import java.awt.*;

public class ClientRegistrationPanel extends JPanel {
    private final ClienteController clienteController;

    public ClientRegistrationPanel() {
        clienteController = new ClienteController();
        setLayout(new GridLayout(5, 2));

        // Campos para cadastro de cliente
        JLabel lblNome = new JLabel("Nome:");
        JTextField txtNome = new JTextField();
        JLabel lblTelefone = new JLabel("Telefone:");
        JTextField txtTelefone = new JTextField();
        JLabel lblEmail = new JLabel("Email:");
        JTextField txtEmail = new JTextField();
        JLabel lblEndereco = new JLabel("Endereço:");
        JTextField txtEndereco = new JTextField();

        JButton btnCadastrar = new JButton("Cadastrar");

        // Adicionar componentes ao painel
        add(lblNome);
        add(txtNome);
        add(lblTelefone);
        add(txtTelefone);
        add(lblEmail);
        add(txtEmail);
        add(lblEndereco);
        add(txtEndereco);
        add(new JLabel()); // espaço vazio
        add(btnCadastrar);

        // Ação do botão de cadastrar
        btnCadastrar.addActionListener(e -> {
            String nome = txtNome.getText();
            String telefone = txtTelefone.getText();
            String email = txtEmail.getText();
            String endereco = txtEndereco.getText();

            // Criar cliente e cadastrar
            Cliente cliente = new Cliente();
            cliente.setNome(nome);
            cliente.setTelefone(telefone);
            cliente.setEmail(email);
            cliente.setEndereco(endereco);

            // Chamar o método do controlador para cadastrar
            clienteController.cadastrarCliente(cliente);

            // Feedback ao usuário
            JOptionPane.showMessageDialog(this, "Cliente cadastrado: " + nome);
        });
    }
}
