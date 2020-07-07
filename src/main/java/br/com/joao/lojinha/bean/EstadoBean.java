package br.com.joao.lojinha.bean;

import br.com.joao.lojinha.dao.EstadoDao;
import br.com.joao.lojinha.domain.Estado;
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
public class EstadoBean implements Serializable {

    private Estado estado;
    private List<Estado> estados;

    public void excluir(ActionEvent event) {
        try {
            estado = (Estado) event.getComponent().getAttributes().get("ufExcluir");
            EstadoDao dao = new EstadoDao();
            dao.excluir(estado);
            Messages.addGlobalInfo(estado.getNome() + " Excluido com sucesso");
            listar();
        } catch (Exception exception) {
            Messages.addGlobalInfo("Erro ao excluir");
            exception.printStackTrace();
        }
    }

    public void alterar(ActionEvent event) {
        estado = (Estado) event.getComponent().getAttributes().get("ufAlterar");

    }

    @PostConstruct
    public void listar() {
        try {
            EstadoDao dao = new EstadoDao();
            estados = dao.listarTodos();
        } catch (Exception exception) {
            Messages.addGlobalError("Erro ao cadastrar UFs");
            exception.printStackTrace();
        }
    }

    public void novo() {
        this.estado = new Estado();
    }

    public void salvar() {
        try {
            EstadoDao dao = new EstadoDao();
            dao.merge(estado);
            Messages.addGlobalInfo("UF cadastrada com sucesso");
            novo();
            listar();
        } catch (Exception exception) {
            Messages.addGlobalError("Erro ao cadastar UF");
            exception.printStackTrace();
        }
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public List<Estado> getEstados() {
        return estados;
    }

    public void setEstados(List<Estado> estados) {
        this.estados = estados;
    }

}
