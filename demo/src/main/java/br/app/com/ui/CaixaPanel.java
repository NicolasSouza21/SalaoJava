package br.app.com.ui;

import br.app.com.model.Cliente;
import br.app.com.model.Produto;
import br.app.com.service.ClienteService;
import br.app.com.service.ProdutoService;
import br.app.com.service.VendaService;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CaixaPanel extends JPanel {
    private final ProdutoService produtoService;
    private final ClienteService clienteService;
    private final VendaService vendaService;
    private JTable produtosTable;
    private JTable carrinhoTable;
    private JComboBox<Cliente> clienteComboBox;
    private List<Produto> carrinho;
    private JLabel totalLabel;

    public CaixaPanel() {
        this.produtoService = new ProdutoService();
        this.clienteService = new ClienteService();
        this.vendaService = new VendaService();
        this.carrinho = new ArrayList<>();

        setLayout(new BorderLayout());
        initializeComponents();
    }

    // Inicializa os componentes
    private void initializeComponents() {
        // Seção para seleção de cliente
        JPanel clientePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        clienteComboBox = new JComboBox<>();
        carregarClientes();
        clientePanel.add(new JLabel("Selecione o Cliente:"));
        clientePanel.add(clienteComboBox);

        // Tabela de produtos disponíveis
        JPanel produtosPanel = new JPanel(new BorderLayout());
        produtosPanel.setBorder(BorderFactory.createTitledBorder("Produtos Disponíveis"));
        atualizarTabelaProdutos();
        JButton addProdutoButton = new JButton("Adicionar ao Carrinho");
        addProdutoButton.addActionListener(e -> adicionarProdutoAoCarrinho());
        produtosPanel.add(new JScrollPane(produtosTable), BorderLayout.CENTER);
        produtosPanel.add(addProdutoButton, BorderLayout.SOUTH);

        // Tabela de produtos no carrinho
        JPanel carrinhoPanel = new JPanel(new BorderLayout());
        carrinhoPanel.setBorder(BorderFactory.createTitledBorder("Carrinho de Compras"));
        carrinhoTable = new JTable();
        totalLabel = new JLabel("Total: R$ 0.00");
        carrinhoPanel.add(new JScrollPane(carrinhoTable), BorderLayout.CENTER);
        carrinhoPanel.add(totalLabel, BorderLayout.SOUTH);

        // Botão de finalizar compra
        JButton finalizarCompraButton = new JButton("Finalizar Compra");
        finalizarCompraButton.addActionListener(e -> finalizarCompra());

        // Botão de atualizar
        JButton atualizarButton = new JButton("Atualizar");
        atualizarButton.addActionListener(e -> atualizarInformacoes());

        // Painel para botões na parte inferior
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(finalizarCompraButton);
        buttonPanel.add(atualizarButton);

        // Adiciona os painéis à interface
        add(clientePanel, BorderLayout.NORTH);
        add(produtosPanel, BorderLayout.WEST);
        add(carrinhoPanel, BorderLayout.EAST);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Método para carregar os clientes na ComboBox
    private void carregarClientes() {
        clienteComboBox.removeAllItems(); // Limpa os itens antigos
        List<Cliente> clientes = clienteService.listarClientes();
        for (Cliente cliente : clientes) {
            clienteComboBox.addItem(cliente);
        }
    }

    // Método para atualizar a tabela de produtos disponíveis
    private void atualizarTabelaProdutos() {
        List<Produto> produtos = produtoService.listarProdutos();
        Object[][] data = new Object[produtos.size()][4];
        for (int i = 0; i < produtos.size(); i++) {
            Produto produto = produtos.get(i);
            data[i][0] = produto.getId();
            data[i][1] = produto.getNome();
            data[i][2] = produto.getQuantidade();
            data[i][3] = produto.getPreco();
        }
        produtosTable = new JTable(data, new String[]{"ID", "Nome", "Quantidade", "Preço"});
        produtosTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    // Método para adicionar produto ao carrinho
    private void adicionarProdutoAoCarrinho() {
        int selectedRow = produtosTable.getSelectedRow();
        if (selectedRow >= 0) {
            Produto produto = produtoService.buscarProduto((int) produtosTable.getValueAt(selectedRow, 0));
            
            // Verifica se há estoque suficiente
            int quantidadeComprada = 1; // Defina a quantidade a ser comprada
            if (produto.getQuantidade() < quantidadeComprada) {
                JOptionPane.showMessageDialog(this, "Quantidade insuficiente em estoque.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Atualiza a quantidade do produto
            produto.setQuantidade(produto.getQuantidade() - quantidadeComprada);
            carrinho.add(produto);
            atualizarTabelaCarrinho();
            atualizarTabelaProdutos(); // Atualiza a tabela de produtos para refletir a quantidade reduzida
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um produto.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para atualizar a tabela de produtos no carrinho
    private void atualizarTabelaCarrinho() {
        Object[][] data = new Object[carrinho.size()][4];
        double total = 0.0;
        for (int i = 0; i < carrinho.size(); i++) {
            Produto produto = carrinho.get(i);
            data[i][0] = produto.getId();
            data[i][1] = produto.getNome();
            data[i][2] = 1; // Definindo quantidade como 1, pois estamos adicionando um de cada vez
            data[i][3] = produto.getPreco();
            total += produto.getPreco(); // Totaliza o preço do produto
        }
        carrinhoTable.setModel(new javax.swing.table.DefaultTableModel(data, new String[]{"ID", "Nome", "Quantidade", "Preço"}));
        totalLabel.setText("Total: R$ " + String.format("%.2f", total));
    }

    // Método para finalizar a compra
    private void finalizarCompra() {
        Cliente cliente = (Cliente) clienteComboBox.getSelectedItem();
        if (cliente == null) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (carrinho.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Adicione produtos ao carrinho.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String formaPagamento = JOptionPane.showInputDialog(this, "Informe a forma de pagamento:");
        boolean sucesso = vendaService.registrarVenda(cliente, carrinho, formaPagamento);

        if (sucesso) {
            JOptionPane.showMessageDialog(this, "Venda registrada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            // Resetar o carrinho e as tabelas após a venda
            carrinho.clear();
            atualizarTabelaCarrinho();
            atualizarTabelaProdutos(); // Atualiza a tabela de produtos para refletir o estoque atual
            
            // Resetar o cliente selecionado
            clienteComboBox.setSelectedIndex(-1); // Desmarca o cliente selecionado
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao registrar venda.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para atualizar informações do painel
    public void atualizarInformacoes() {
        carregarClientes();
        atualizarTabelaProdutos();
        atualizarTabelaCarrinho();
    }
}
