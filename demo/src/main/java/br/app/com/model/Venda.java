package br.app.com.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Venda {
    private int id;
    private LocalDate data; // Usando LocalDate
    private double total;
    private String formaPagamento;
    private int clienteId; // Armazena o ID do cliente

    // Construtores
    public Venda() {}

    public Venda(int id, LocalDate data, double total, String formaPagamento, int clienteId) {
        this.id = id;
        this.data = data;
        this.total = total;
        this.formaPagamento = formaPagamento;
        this.clienteId = clienteId;
    }

    // Getters e Setters
    public int getId() { 
        return id; 
    }
    
    public void setId(int id) { 
        this.id = id; 
    }

    public LocalDate getData() { 
        return data; 
    }
    
    public void setData(LocalDate data) { 
        this.data = data; 
    }

    public double getTotal() { 
        return total; 
    }
    
    public void setTotal(double total) { 
        this.total = total; 
    }

    public String getFormaPagamento() { 
        return formaPagamento; 
    }
    
    public void setFormaPagamento(String formaPagamento) { 
        this.formaPagamento = formaPagamento; 
    }

    public int getClienteId() { 
        return clienteId; 
    }
    
    public void setClienteId(int clienteId) { 
        this.clienteId = clienteId; 
    }

    // toString atualizado para LocalDate
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return "Venda{" +
                "id=" + id +
                ", data=" + data.format(formatter) + // Formata a data para String
                ", total=" + total +
                ", formaPagamento='" + formaPagamento + '\'' +
                ", clienteId=" + clienteId +
                '}';
    }
}
