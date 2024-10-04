package br.app.com.model;

import java.util.Date;

public class Venda {
    private int id;
    private Date data;
    private double total;
    private String formaPagamento;
    private int clienteId; // Armazena o ID do cliente

    // Construtores
    public Venda() {}

    public Venda(int id, Date data, double total, String formaPagamento, int clienteId) {
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

    public Date getData() { 
        return data; 
    }
    
    public void setData(Date data) { 
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
        return clienteId; // Usando o ID do cliente 
    }
    
    public void setClienteId(int clienteId) { 
        this.clienteId = clienteId; 
    }

    // toString atualizado
    @Override
    public String toString() {
        return "Venda{" +
                "id=" + id +
                ", data=" + data +
                ", total=" + total +
                ", formaPagamento='" + formaPagamento + '\'' +
                ", clienteId=" + clienteId + // Mostra o ID do cliente
                '}';
    }
}
