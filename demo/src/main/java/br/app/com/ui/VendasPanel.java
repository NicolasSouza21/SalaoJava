package br.app.com.ui;

import br.app.com.model.Venda;
import br.app.com.service.VendaService;
import javax.swing.table.DefaultTableModel; 
import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class VendasPanel extends JPanel {
    private final VendaService vendaService;
    private JTable vendasTable;

    public VendasPanel() {
        this.vendaService = new VendaService();
        setLayout(new BorderLayout());
        initializeComponents(); 
    }

    // Inicializa os componentes do painel de vendas
    private void initializeComponents() {
        // Painel da tabela de vendas
        JPanel vendasPanel = new JPanel(new BorderLayout());
        vendasPanel.setBorder(BorderFactory.createTitledBorder("Vendas Registradas"));
        
        // Criação inicial da tabela
        vendasTable = new JTable();
        atualizarTabelaVendas(); // Preenche a tabela com dados

        // Adiciona a tabela de vendas ao painel
        vendasPanel.add(new JScrollPane(vendasTable), BorderLayout.CENTER);

        // Botão de atualizar para recarregar as vendas
        JButton atualizarButton = new JButton("Atualizar");
        atualizarButton.addActionListener(e -> atualizarTabelaVendas());

        // Botão de excluir
        JButton excluirButton = new JButton("Excluir");
        excluirButton.addActionListener(e -> excluirVenda());

        // Botão de editar
        JButton editarButton = new JButton("Editar");
        editarButton.addActionListener(e -> editarVenda());

        // Botão de exportar
        JButton exportarButton = new JButton("Exportar para .txt");
        exportarButton.addActionListener(e -> exportarVendasParaTxt());

        // Painel de botão na parte inferior
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(atualizarButton);
        buttonPanel.add(excluirButton);
        buttonPanel.add(editarButton);
        buttonPanel.add(exportarButton); // Adicionando o botão de exportar

        // Adiciona os componentes ao painel principal
        add(vendasPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Método para atualizar a tabela de vendas com dados
    private void atualizarTabelaVendas() {
        List<Venda> vendas = vendaService.listarVendas(); 
        Object[][] data = new Object[vendas.size()][5]; // 5 colunas para exibição

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        for (int i = 0; i < vendas.size(); i++) {
            Venda venda = vendas.get(i);

            data[i][0] = venda.getId();
            
            if (venda.getData() != null) {
                data[i][1] = venda.getData().format(dateFormatter); 
            } else {
                data[i][1] = ""; 
            }
            
            data[i][2] = String.format("R$ %.2f", venda.getTotal()); 
            data[i][3] = venda.getFormaPagamento(); 
            data[i][4] = venda.getClienteId(); 
        }

        vendasTable.setModel(new DefaultTableModel(data, new String[]{"ID", "Data", "Total", "Forma de Pagamento", "ID Cliente"}));
        vendasTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    // Método para excluir uma venda
    private void excluirVenda() {
        int selectedRow = vendasTable.getSelectedRow();
        if (selectedRow >= 0) {
            int vendaId = (int) vendasTable.getValueAt(selectedRow, 0); 
            if (vendaService.excluirVenda(vendaId)) {
                JOptionPane.showMessageDialog(this, "Venda excluída com sucesso!");
                atualizarTabelaVendas(); 
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao excluir venda.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma venda para excluir.");
        }
    }

    // Método para editar uma venda
    private void editarVenda() {
        int selectedRow = vendasTable.getSelectedRow();
        if (selectedRow >= 0) {
            int vendaId = (int) vendasTable.getValueAt(selectedRow, 0); 
            Venda venda = new Venda();
            venda.setId(vendaId);

            String dataString = (String) vendasTable.getValueAt(selectedRow, 1);
            try {
                LocalDate dataVenda = LocalDate.parse(dataString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                venda.setData(dataVenda); 
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao converter a data: " + e.getMessage());
                return;
            }

            venda.setTotal(Double.parseDouble(((String) vendasTable.getValueAt(selectedRow, 2)).replace("R$ ", "").replace(",", ".")));
            venda.setFormaPagamento((String) vendasTable.getValueAt(selectedRow, 3));
            venda.setClienteId((int) vendasTable.getValueAt(selectedRow, 4));

            String novaFormaPagamento = JOptionPane.showInputDialog(this, "Forma de Pagamento:", venda.getFormaPagamento());
            if (novaFormaPagamento != null) {
                venda.setFormaPagamento(novaFormaPagamento);
                if (vendaService.atualizarVenda(venda)) {
                    JOptionPane.showMessageDialog(this, "Venda atualizada com sucesso!");
                    atualizarTabelaVendas(); 
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao atualizar venda.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma venda para editar.");
        }
    }

    // Método para exportar as vendas para um arquivo .txt
    private void exportarVendasParaTxt() {
        List<Venda> vendas = vendaService.listarVendas(); 
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        String caminhoArquivo = "vendas.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            writer.write("ID | Data | Total | Forma de Pagamento | ID Cliente");
            writer.newLine();
            writer.write("-----------------------------------------------------");
            writer.newLine();

            for (Venda venda : vendas) {
                String dataVenda = venda.getData() != null ? venda.getData().format(dateFormatter) : "N/A";
                String linha = String.format("%d | %s | R$ %.2f | %s | %d",
                        venda.getId(),
                        dataVenda,
                        venda.getTotal(),
                        venda.getFormaPagamento(),
                        venda.getClienteId());
                writer.write(linha);
                writer.newLine();
            }

            JOptionPane.showMessageDialog(this, "Vendas exportadas com sucesso para " + caminhoArquivo);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao exportar vendas: " + e.getMessage());
        }
    }
}
