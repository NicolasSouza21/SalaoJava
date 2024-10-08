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
    private JSpinner quantidadeSpinner;

    public CaixaPanel() {
        this.produtoService = new ProdutoService();
        this.clienteService = new ClienteService();
        this.vendaService = new VendaService();
        this.carrinho = new ArrayList<>();

        setLayout(new BorderLayout());
        initializeComponents();
    }

    private void initializeComponents() {
        JPanel clientePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        clienteComboBox = new JComboBox<>();
        carregarClientes();
        clientePanel.add(new JLabel("Selecione o Cliente:"));
        clientePanel.add(clienteComboBox);

        JPanel produtosPanel = new JPanel(new BorderLayout());
        produtosPanel.setBorder(BorderFactory.createTitledBorder("Produtos Disponíveis"));
        atualizarTabelaProdutos();

        JPanel quantidadePanel = new JPanel();
        quantidadeSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        quantidadePanel.add(new JLabel("Quantidade:"));
        quantidadePanel.add(quantidadeSpinner);

        JButton addProdutoButton = new JButton("Adicionar ao Carrinho");
        addProdutoButton.addActionListener(e -> adicionarProdutoAoCarrinho());
        produtosPanel.add(new JScrollPane(produtosTable), BorderLayout.CENTER);
        produtosPanel.add(quantidadePanel, BorderLayout.NORTH);
        produtosPanel.add(addProdutoButton, BorderLayout.SOUTH);

        JPanel carrinhoPanel = new JPanel(new BorderLayout());
        carrinhoPanel.setBorder(BorderFactory.createTitledBorder("Carrinho de Compras"));
        carrinhoTable = new JTable();
        totalLabel = new JLabel("Total: R$ 0.00");

        // Botão de remover item do carrinho
        JButton removerProdutoButton = new JButton("Remover do Carrinho");
        removerProdutoButton.addActionListener(e -> removerProdutoDoCarrinho());
        
        carrinhoPanel.add(new JScrollPane(carrinhoTable), BorderLayout.CENTER);
        carrinhoPanel.add(totalLabel, BorderLayout.SOUTH);
        carrinhoPanel.add(removerProdutoButton, BorderLayout.NORTH);  // Adiciona o botão de remoção no carrinho

        JButton finalizarCompraButton = new JButton("Finalizar Compra");
        finalizarCompraButton.addActionListener(e -> finalizarCompra());

        JButton atualizarButton = new JButton("Atualizar");
        atualizarButton.addActionListener(e -> atualizarInformacoes());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(finalizarCompraButton);
        buttonPanel.add(atualizarButton);

        add(clientePanel, BorderLayout.NORTH);
        add(produtosPanel, BorderLayout.WEST);
        add(carrinhoPanel, BorderLayout.EAST);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void carregarClientes() {
        clienteComboBox.removeAllItems();
        List<Cliente> clientes = clienteService.listarClientes();
        for (Cliente cliente : clientes) {
            clienteComboBox.addItem(cliente);
        }
    }

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

    private void adicionarProdutoAoCarrinho() {
        int selectedRow = produtosTable.getSelectedRow();
        if (selectedRow >= 0) {
            Produto produto = produtoService.buscarProduto((int) produtosTable.getValueAt(selectedRow, 0));
            int quantidadeComprada = (int) quantidadeSpinner.getValue();

            if (produto.getQuantidade() < quantidadeComprada) {
                JOptionPane.showMessageDialog(this, "Quantidade insuficiente em estoque.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            for (int i = 0; i < quantidadeComprada; i++) {
                carrinho.add(produto);
            }

            produto.setQuantidade(produto.getQuantidade() - quantidadeComprada);
            produtoService.editarProduto(produto);
            atualizarTabelaCarrinho();
            atualizarTabelaProdutos();
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um produto.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Novo método para remover produto do carrinho
    private void removerProdutoDoCarrinho() {
        int selectedRow = carrinhoTable.getSelectedRow();
        if (selectedRow >= 0) {
            Produto produtoRemovido = carrinho.get(selectedRow);
            carrinho.remove(selectedRow);
            produtoRemovido.setQuantidade(produtoRemovido.getQuantidade() + 1);  // Devolvendo o produto ao estoque
            produtoService.editarProduto(produtoRemovido);
            atualizarTabelaCarrinho();
            atualizarTabelaProdutos();
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um produto para remover.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarTabelaCarrinho() {
        Object[][] data = new Object[carrinho.size()][4];
        double total = 0.0;
        for (int i = 0; i < carrinho.size(); i++) {
            Produto produto = carrinho.get(i);
            data[i][0] = produto.getId();
            data[i][1] = produto.getNome();
            data[i][2] = 1;
            data[i][3] = produto.getPreco();
            total += produto.getPreco();
        }
        carrinhoTable.setModel(new javax.swing.table.DefaultTableModel(data, new String[]{"ID", "Nome", "Quantidade", "Preço"}));
        totalLabel.setText("Total: R$ " + String.format("%.2f", total));
    }

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
            carrinho.clear();
            atualizarTabelaCarrinho();
            atualizarTabelaProdutos();
            totalLabel.setText("Total: R$ 0.00");
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao registrar venda.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void atualizarInformacoes() {
        carregarClientes();
        atualizarTabelaProdutos();
        atualizarTabelaCarrinho();
    }
}
