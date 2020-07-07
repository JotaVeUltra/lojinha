package br.com.joao.lojinha.bean;

import br.com.joao.lojinha.dao.CategoriaDao;
import br.com.joao.lojinha.domain.Categoria;
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
public class CategoriaBean implements Serializable {


    private Categoria categoria;
    private List<Categoria> categorias;

    public void excluir(ActionEvent event) {
        try {
            categoria = (Categoria) event.getComponent().getAttributes().get("categoriaExcluir");
            CategoriaDao dao = new CategoriaDao();
            dao.excluir(categoria);
            Messages.addGlobalInfo(categoria.getNome() + " Excluido com sucesso");
            listar();
        } catch (Exception exception) {
            Messages.addGlobalInfo("Erro ao excluir");
            exception.printStackTrace();
        }
    }

    public void alterar(ActionEvent event) {
        categoria = (Categoria) event.getComponent().getAttributes().get("categoriaAlterar");

    }

    @PostConstruct
    public void listar() {
        try {
            CategoriaDao dao = new CategoriaDao();
            categorias = dao.listarTodos();

        } catch (Exception exception) {
            Messages.addGlobalError("Erro ao listar Categorias");
            exception.printStackTrace();
        }
    }

    public void salvar() {
        try {
            CategoriaDao dao = new CategoriaDao();
            dao.merge(categoria);
            Messages.addGlobalInfo("Categoria cadastrado com sucesso");
            novo();
            listar();
        } catch (Exception exception) {
            Messages.addGlobalError("Erro ao cadastar Categoria");
            exception.printStackTrace();
        }
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public void novo() {
        this.categoria = new Categoria();
    }


}
