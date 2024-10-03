package br.app.com.ui;

import br.app.com.controller.ClienteController;
import br.app.com.model.Cliente;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.stream.Collectors;

public class ClientListPanel extends JPanel {
    private JTextField txtBusca; // Campo de busca
    private JList<String> clientList; // Lista de clientes
    private DefaultListModel<String> listModel; // Modelo para a JList
    private ClienteController clienteController;
    private List<Cliente> clientes; // Armazenar todos os clientes para facilitar a filtragem

    public ClientListPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Inicializando o controlador de clientes
        clienteController = new ClienteController();

        // Campo de busca
        JLabel lblBusca = new JLabel("Buscar Cliente:");
        txtBusca = new JTextField(15);

        // Configurando o layout para o campo de busca
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        add(lblBusca, gbc);

        gbc.gridx = 1;
        add(txtBusca, gbc);

        // Modelo e JList para exibir clientes
        listModel = new DefaultListModel<>();
        clientList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(clientList);
        scrollPane.setPreferredSize(new Dimension(300, 150));

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        add(scrollPane, gbc);

        // Botão Editar
        JButton btnEditar = new JButton("Editar");
        btnEditar.addActionListener(e -> editarCliente());
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        add(btnEditar, gbc);

        // Botão Deletar
        JButton btnDeletar = new JButton("Deletar");
        btnDeletar.addActionListener(e -> deletarCliente());
        gbc.gridx = 1;
        add(btnDeletar, gbc);

        // Listener para atualizar a lista conforme a busca
        txtBusca.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filtrarClientes();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filtrarClientes();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filtrarClientes();
            }
        });

        // Adiciona um MouseListener para mostrar informações do cliente ao clicar
        clientList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Verifica se foi um clique duplo
                    int index = clientList.locationToIndex(e.getPoint());
                    if (index >= 0) {
                        Cliente cliente = clientes.get(index);
                        mostrarDetalhesCliente(cliente);
                    }
                }
            }
        });

        // Listar todos os clientes cadastrados ao iniciar
        listarClientes();
    }

    // Método para listar todos os clientes
    private void listarClientes() {
        clientes = clienteController.listarClientes(); // Obter todos os clientes

        // Debug: Verifique quantos clientes foram retornados
        System.out.println("Número de clientes retornados: " + clientes.size());

        if (clientes != null && !clientes.isEmpty()) {
            atualizarLista(clientes);
        } else {
            // Se não houver clientes, limpar a lista e informar
            listModel.clear();
            JOptionPane.showMessageDialog(this, "Nenhum cliente cadastrado.", "Informação", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Método para filtrar clientes com base na busca
    private void filtrarClientes() {
        String busca = txtBusca.getText().toLowerCase();

        // Filtrar clientes pelo nome com base no texto da busca
        List<Cliente> filtrados = clientes.stream()
                .filter(c -> c.getNome().toLowerCase().contains(busca))
                .collect(Collectors.toList());

        // Atualizar a lista com o resultado da busca
        atualizarLista(filtrados);
    }

    // Método para atualizar a JList com os clientes fornecidos
    private void atualizarLista(List<Cliente> clientes) {
        listModel.clear();
        for (Cliente cliente : clientes) {
            listModel.addElement(cliente.getNome());
        }
    }

    // Método para exibir os detalhes do cliente em um PopUp
    private void mostrarDetalhesCliente(Cliente cliente) {
        String detalhes = "Nome: " + cliente.getNome() + "\n"
                + "Telefone: " + cliente.getTelefone() + "\n"
                + "Email: " + cliente.getEmail() + "\n"
                + "Endereço: " + cliente.getEndereco();

        JOptionPane.showMessageDialog(this, detalhes, "Detalhes do Cliente", JOptionPane.INFORMATION_MESSAGE);
    }

    // Método para editar um cliente
    private void editarCliente() {
        int index = clientList.getSelectedIndex();
        if (index >= 0) {
            Cliente cliente = clientes.get(index);
            String novoNome = JOptionPane.showInputDialog(this, "Editar Nome:", cliente.getNome());
            String novoTelefone = JOptionPane.showInputDialog(this, "Editar Telefone:", cliente.getTelefone());
            String novoEmail = JOptionPane.showInputDialog(this, "Editar Email:", cliente.getEmail());
            String novoEndereco = JOptionPane.showInputDialog(this, "Editar Endereço:", cliente.getEndereco());

            // Verifica se alguma entrada está vazia
            if (novoNome == null || novoNome.trim().isEmpty() ||
                novoTelefone == null || novoTelefone.trim().isEmpty() ||
                novoEmail == null || novoEmail.trim().isEmpty() ||
                novoEndereco == null || novoEndereco.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            cliente.setNome(novoNome);
            cliente.setTelefone(novoTelefone);
            cliente.setEmail(novoEmail);
            cliente.setEndereco(novoEndereco);

            boolean sucesso = clienteController.editarCliente(cliente);
            if (sucesso) {
                JOptionPane.showMessageDialog(this, "Cliente editado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                listarClientes(); // Atualiza a lista após a edição
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao editar cliente.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Método para deletar um cliente
    private void deletarCliente() {
        int index = clientList.getSelectedIndex();
        if (index >= 0) {
            int confirmacao = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja deletar este cliente?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (confirmacao == JOptionPane.YES_OPTION) {
                Cliente cliente = clientes.get(index);
                boolean sucesso = clienteController.deletarCliente(cliente.getId());
                if (sucesso) {
                    JOptionPane.showMessageDialog(this, "Cliente deletado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    listarClientes(); // Atualiza a lista após a exclusão
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao deletar cliente.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para deletar.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Método para atualizar a lista de clientes quando um novo cliente é cadastrado
    public void atualizarListaClientes() {
        listarClientes(); // Recarrega a lista de clientes
    }
}
