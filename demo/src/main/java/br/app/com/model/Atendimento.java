package br.app.com.model;
import java.util.Date;

public class Atendimento {
    private int id;
    private Cliente cliente;
    private Servico servico;
    private Date data;

    // Construtores
    public Atendimento() {}

    public Atendimento(int id, Cliente cliente, Servico servico, Date data) {
        this.id = id;
        this.cliente = cliente;
        this.servico = servico;
        this.data = data;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public Servico getServico() { return servico; }
    public void setServico(Servico servico) { this.servico = servico; }

    public Date getData() { return data; }
    public void setData(Date data) { this.data = data; }
}
