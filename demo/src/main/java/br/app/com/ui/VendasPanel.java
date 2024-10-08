    package br.app.com.ui;

    import br.app.com.model.Venda;
    import br.app.com.service.VendaService;
    import javax.swing.table.DefaultTableModel; // Importando DefaultTableModel
    import javax.swing.*;
    import java.awt.*;
    import java.text.SimpleDateFormat;
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

            // Painel de botão na parte inferior
            JPanel buttonPanel = new JPanel();
            buttonPanel.add(atualizarButton);
            buttonPanel.add(excluirButton);
            buttonPanel.add(editarButton);

            // Adiciona os componentes ao painel principal
            add(vendasPanel, BorderLayout.CENTER);
            add(buttonPanel, BorderLayout.SOUTH);
        }

        // Método para atualizar a tabela de vendas com dados
        private void atualizarTabelaVendas() {
            List<Venda> vendas = vendaService.listarVendas(); // Busca todas as vendas registradas
            Object[][] data = new Object[vendas.size()][5]; // 5 colunas para exibição
        
            // Formata a data de maneira mais legível com DateTimeFormatter
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
            // Populando a tabela com os dados das vendas
            for (int i = 0; i < vendas.size(); i++) {
                Venda venda = vendas.get(i);
        
                data[i][0] = venda.getId();
                
                // Verifica se a data não é nula e formata
                if (venda.getData() != null) {
                    data[i][1] = venda.getData().format(dateFormatter); // Formata a data
                } else {
                    data[i][1] = ""; // Se a data for nula, exibe vazio
                }
                
                data[i][2] = String.format("R$ %.2f", venda.getTotal()); // Formata o total
                data[i][3] = venda.getFormaPagamento(); // Exibe a forma de pagamento
                data[i][4] = venda.getClienteId(); // Exibe o ID do cliente
            }
        
            // Atualiza a tabela com os dados
            vendasTable.setModel(new DefaultTableModel(data, new String[]{"ID", "Data", "Total", "Forma de Pagamento", "ID Cliente"}));
            vendasTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        }
        

        // Método para excluir uma venda
        private void excluirVenda() {
            int selectedRow = vendasTable.getSelectedRow();
            if (selectedRow >= 0) {
                int vendaId = (int) vendasTable.getValueAt(selectedRow, 0); // Pega o ID da venda
                if (vendaService.excluirVenda(vendaId)) {
                    JOptionPane.showMessageDialog(this, "Venda excluída com sucesso!");
                    atualizarTabelaVendas(); // Atualiza a tabela após a exclusão
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
        int vendaId = (int) vendasTable.getValueAt(selectedRow, 0); // Pega o ID da venda
        Venda venda = new Venda();
        venda.setId(vendaId);

        // Pega a data da venda no formato String e converte para LocalDate
        String dataString = (String) vendasTable.getValueAt(selectedRow, 1);
        try {
            LocalDate dataVenda = LocalDate.parse(dataString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            venda.setData(dataVenda); // Usa o método setData(LocalDate)
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao converter a data: " + e.getMessage());
            return;
        }

        // Pega o total da venda e converte
        venda.setTotal(Double.parseDouble(((String) vendasTable.getValueAt(selectedRow, 2)).replace("R$ ", "").replace(",", ".")));

        // Pega a forma de pagamento
        venda.setFormaPagamento((String) vendasTable.getValueAt(selectedRow, 3));

        // Pega o ID do cliente
        venda.setClienteId((int) vendasTable.getValueAt(selectedRow, 4));

        // Diálogo para editar a forma de pagamento
        String novaFormaPagamento = JOptionPane.showInputDialog(this, "Forma de Pagamento:", venda.getFormaPagamento());
        if (novaFormaPagamento != null) {
            venda.setFormaPagamento(novaFormaPagamento);
            if (vendaService.atualizarVenda(venda)) {
                JOptionPane.showMessageDialog(this, "Venda atualizada com sucesso!");
                atualizarTabelaVendas(); // Atualiza a tabela após a edição
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar venda.");
            }
        }
    } else {
        JOptionPane.showMessageDialog(this, "Selecione uma venda para editar.");
    }
}

    }