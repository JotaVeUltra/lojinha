package br.com.joao.lojinha.bean;

import br.com.joao.lojinha.dao.VendaDao;
import br.com.joao.lojinha.domain.Venda;
import org.omnifaces.util.Messages;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class VendaBean implements Serializable {


    private Venda venda;
    private List<Venda> vendas;

    public void excluir(ActionEvent event) {
        try {
            venda = (Venda) event.getComponent().getAttributes().get("vendaExcluir");
            VendaDao dao = new VendaDao();
            dao.excluir(venda);
            Messages.addGlobalInfo("Venda Excluida com sucesso");
            listar();
        } catch (Exception exception) {
            Messages.addGlobalInfo("Erro ao excluir");
            exception.printStackTrace();
        }
    }

    public void alterar(ActionEvent event) {
        venda = (Venda) event.getComponent().getAttributes().get("vendaAlterar");

    }

    @PostConstruct
    public void listar() {
        try {
            VendaDao dao = new VendaDao();
            vendas = dao.listarTodos();
        } catch (Exception exception) {
            Messages.addGlobalError("Erro ao listar Vendas");
            exception.printStackTrace();
        }
    }

    public void novo() {
        this.venda = new Venda();
    }

    public void salvar() {
        try {
            VendaDao dao = new VendaDao();
            dao.merge(venda);
            Messages.addGlobalInfo("Venda cadastrado com sucesso");
            novo();
            listar();
        } catch (Exception exception) {
            Messages.addGlobalError("Erro ao cadastar Venda");
            exception.printStackTrace();
        }
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public List<Venda> getVendas() {
        return vendas;
    }

    public void setVendas(List<Venda> vendas) {
        this.vendas = vendas;
    }

}
