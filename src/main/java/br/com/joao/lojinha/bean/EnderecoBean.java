package br.com.joao.lojinha.bean;

import br.com.joao.lojinha.dao.EnderecoDao;
import br.com.joao.lojinha.domain.Endereco;
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
public class EnderecoBean implements Serializable {


    private Endereco endereco;
    private List<Endereco> enderecos;

    public void excluir(ActionEvent event) {
        try {
            endereco = (Endereco) event.getComponent().getAttributes().get("enderecoExcluir");
            EnderecoDao dao = new EnderecoDao();
            dao.excluir(endereco);
            Messages.addGlobalInfo("Endereço Excluido com sucesso");
            listar();
        } catch (Exception exception) {
            Messages.addGlobalInfo("Erro ao excluir");
            exception.printStackTrace();
        }
    }

    public void alterar(ActionEvent event) {
        endereco = (Endereco) event.getComponent().getAttributes().get("enderecoAlterar");

    }

    @PostConstruct
    public void listar() {
        try {
            EnderecoDao dao = new EnderecoDao();
            enderecos = dao.listarTodos();
        } catch (Exception exception) {
            Messages.addGlobalError("Erro ao listar Enderecos");
            exception.printStackTrace();
        }
    }

    public void novo() {
        this.endereco = new Endereco();
    }

    public void salvar() {
        try {
            EnderecoDao dao = new EnderecoDao();
            dao.merge(endereco);
            Messages.addGlobalInfo("Endereco cadastrado com sucesso");
            novo();
            listar();
        } catch (Exception exception) {
            Messages.addGlobalError("Erro ao cadastar Endereco");
            exception.printStackTrace();
        }
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
    }

}
