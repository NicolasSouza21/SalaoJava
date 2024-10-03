package br.app.com.ui;

import br.app.com.controller.ProdutoController;
import br.app.com.model.Produto;

import javax.swing.*;
import java.awt.*;

public class ProductRegistrationPanel extends JPanel {
    private JTextField txtNome, txtQuantidade, txtPreco;
    private ProdutoController produtoController;
    private ProductListPanel productListPanel; // Painel de listagem de produtos para atualizar

    public ProductRegistrationPanel(ProductListPanel productListPanel) {
        this.productListPanel = productListPanel; // Recebe o painel de listagem
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Inicializando o controlador de produtos
        produtoController = new ProdutoController();

        // Campos para cadastro de produto
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
        JLabel lblQuantidade = new JLabel("Quantidade:");
        txtQuantidade = new JTextField(20);
        gbc.gridx = 0;
        add(lblQuantidade, gbc);

        gbc.gridx = 1;
        add(txtQuantidade, gbc);

        gbc.gridy++;
        JLabel lblPreco = new JLabel("Preço:");
        txtPreco = new JTextField(20);
        gbc.gridx = 0;
        add(lblPreco, gbc);

        gbc.gridx = 1;
        add(txtPreco, gbc);

        gbc.gridy++;
        JButton btnCadastrar = new JButton("Cadastrar");
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        add(btnCadastrar, gbc);

        // Ação do botão de cadastrar com validação
        btnCadastrar.addActionListener(e -> {
            String nome = txtNome.getText().trim();
            String quantidadeStr = txtQuantidade.getText().trim();
            String precoStr = txtPreco.getText().trim();

            // Validações básicas
            if (nome.isEmpty()) {
                JOptionPane.showMessageDialog(this, "O nome é obrigatório.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int quantidade;
            double preco;
            try {
                quantidade = Integer.parseInt(quantidadeStr);
                preco = Double.parseDouble(precoStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Quantidade e Preço devem ser numéricos.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Criação do produto
            Produto produto = new Produto();
            produto.setNome(nome);
            produto.setQuantidade(quantidade);
            produto.setPreco(preco);

            // Tentativa de cadastro
            boolean sucesso = produtoController.cadastrarProduto(produto);
            if (sucesso) {
                JOptionPane.showMessageDialog(this, "Produto cadastrado com sucesso: " + nome);

                // Limpar campos após cadastro
                txtNome.setText("");
                txtQuantidade.setText("");
                txtPreco.setText("");

                // Atualizar a lista de produtos no painel de listagem
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar o produto.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
