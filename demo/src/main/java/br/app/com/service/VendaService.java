package br.app.com.service;

import br.app.com.model.Cliente;
import br.app.com.model.Produto;
import br.app.com.model.Venda;
import br.app.com.dao.VendaDAO;

import java.util.Date;
import java.util.List;

public class VendaService {
    private VendaDAO vendaDAO = new VendaDAO();

    // Método para registrar venda
    public boolean registrarVenda(Cliente cliente, List<Produto> produtos, String formaPagamento) {
        // Calcula o total da venda com base nos produtos
        double total = produtos.stream().mapToDouble(Produto::getPreco).sum();

        // Cria o objeto Venda, passando o clienteId em vez do objeto Cliente
        Venda venda = new Venda(0, new Date(), total, formaPagamento, cliente.getId());

        // Registra a venda no banco de dados
        boolean sucesso = vendaDAO.cadastrarVenda(venda);

        // Aqui você pode adicionar lógica para registrar os produtos da venda, se necessário
        if (sucesso) {
            registrarItensVenda(venda.getId(), produtos);
        }

        return sucesso;
    }

    // Método para registrar os itens da venda
    private void registrarItensVenda(int vendaId, List<Produto> produtos) {
        for (Produto produto : produtos) {
            // Aqui você deve implementar a lógica para registrar os produtos na venda
            // Por exemplo, pode haver um método na vendaDAO para inserir os itens
            vendaDAO.cadastrarItemVenda(vendaId, produto.getId(), produto.getQuantidade());
        }
    }

    // Método para listar vendas do dia
    public List<Venda> listarVendasDoDia(Date data) {
        return vendaDAO.buscarVendasPorData(data);
    }
}
