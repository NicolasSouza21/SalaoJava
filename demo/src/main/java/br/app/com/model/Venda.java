package br.app.com.model;
import java.util.Date;

public class Venda {
    private int id;
    private Date data;
    private double total;
    private String formaPagamento;

    // Construtores
    public Venda() {}

    public Venda(int id, Date data, double total, String formaPagamento) {
        this.id = id;
        this.data = data;
        this.total = total;
        this.formaPagamento = formaPagamento;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Date getData() { return data; }
    public void setData(Date data) { this.data = data; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public String getFormaPagamento() { return formaPagamento; }
    public void setFormaPagamento(String formaPagamento) { this.formaPagamento = formaPagamento; }
}
