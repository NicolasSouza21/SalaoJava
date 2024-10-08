package br.app.com.service;

import br.app.com.model.Cliente;
import br.app.com.model.Produto;
import br.app.com.model.ProdutoCarrinho;
import br.app.com.model.Venda;
import br.app.com.dao.VendaDAO;

import java.time.LocalDate;
import java.util.List;

public class VendaService {
    private final VendaDAO vendaDAO = new VendaDAO();

    // Método para registrar venda
    public boolean registrarVenda(Cliente cliente, List<ProdutoCarrinho> produtosCarrinho, String formaPagamento) {
        // Calcula o total da venda com base nos produtos do carrinho
        double total = produtosCarrinho.stream()
            .mapToDouble(pc -> pc.getProduto().getPreco() * pc.getQuantidade())
            .sum();

        // Cria o objeto Venda com LocalDate.now() para a data
        Venda venda = new Venda(0, LocalDate.now(), total, formaPagamento, cliente.getId());

        // Registra a venda no banco de dados
        boolean sucesso = vendaDAO.cadastrarVenda(venda);

        // Se a venda foi registrada com sucesso, registra os itens da venda
        if (sucesso) {
            registrarItensVenda(venda.getId(), produtosCarrinho);
        }

        return sucesso;
    }

    // Método para registrar os itens da venda
    private void registrarItensVenda(int vendaId, List<ProdutoCarrinho> produtosCarrinho) {
        for (ProdutoCarrinho pc : produtosCarrinho) {
            // Registra cada item da venda chamando o DAO
            vendaDAO.cadastrarItemVenda(vendaId, pc.getProduto().getId(), pc.getQuantidade());
        }
    }

    // Método para listar vendas do dia com LocalDate como parâmetro
    public List<Venda> listarVendasDoDia(LocalDate data) {
        return vendaDAO.buscarVendasPorData(data); // Certifique-se de que o DAO também está usando LocalDate
    }

    // Método para listar todas as vendas
    public List<Venda> listarVendas() {
        return vendaDAO.buscarTodasVendas();
    }

    // Método para excluir uma venda
    public boolean excluirVenda(int vendaId) {
        return vendaDAO.excluirVenda(vendaId);
    }

    // Método para atualizar uma venda
    public boolean atualizarVenda(Venda venda) {
        return vendaDAO.atualizarVenda(venda);
    }
}
