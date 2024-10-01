package br.app.com;  // Certifique-se de que está nesta linha

import java.sql.Connection;
import javax.swing.*;
import br.app.com.controller.ClienteController;
import br.app.com.database.DatabaseConnection;
import br.app.com.database.DatabaseInitializer;
import br.app.com.model.Cliente;
import br.app.com.ui.MainFrame;

public class Main {
    public static void main(String[] args) {
      
        // Inicializar o banco de dados e criar as tabelas
        DatabaseInitializer.initializeDatabase();
        DatabaseInitializer.createTables();
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
}
}

