package br.app.com.ui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private JTabbedPane tabbedPane;

    public MainFrame() {
        setTitle("Sistema de Salão de Beleza");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Criar o JTabbedPane
        tabbedPane = new JTabbedPane();
        
        // Adicionar as guias
        tabbedPane.addTab("Cadastro de Clientes", new ClientRegistrationPanel());
        tabbedPane.addTab("Outras Funcionalidades", createOtherFeaturesPanel());
        
        // Adicionar o JTabbedPane ao frame
        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createOtherFeaturesPanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Funcionalidade em desenvolvimento!"));
        return panel;
    }

    public static void main(String[] args) {
       
    }
}
