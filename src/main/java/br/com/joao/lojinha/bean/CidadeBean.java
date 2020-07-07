package br.com.joao.lojinha.bean;

import br.com.joao.lojinha.dao.CidadeDao;
import br.com.joao.lojinha.dao.EstadoDao;
import br.com.joao.lojinha.domain.Cidade;
import br.com.joao.lojinha.domain.Estado;
import org.omnifaces.util.Messages;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class CidadeBean implements Serializable {
    // TODO de-hardcode
    private final String uploadPath = "/home/joao/Projetos/Java/lojinha/uploads/cidades/";
    private Cidade cidade;
    private List<Cidade> cidades;
    private List<Estado> estados;

    public void upload(FileUploadEvent fileUploadEvent) {
        try {
            UploadedFile x = fileUploadEvent.getFile();
            Path t = Files.createTempFile(null, null);
            Files.copy(x.getInputstream(), t, StandardCopyOption.REPLACE_EXISTING);

            cidade.setCaminhoImg(t.toString());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void excluir(ActionEvent event) {
        try {
            cidade = (Cidade) event.getComponent().getAttributes().get("cidadeExcluir");
            CidadeDao dao = new CidadeDao();
            dao.excluir(cidade);

            Path caminho = Paths.get(uploadPath + cidade.getCodigo());
            Files.deleteIfExists(caminho);

            Messages.addGlobalInfo(cidade.getNome() + " Excluido com sucesso");
            listar();
        } catch (Exception exception) {
            Messages.addGlobalInfo("Erro ao excluir");
            exception.printStackTrace();
        }
    }

    private void carregaUfs() {
        try {
            EstadoDao dao = new EstadoDao();
            estados = dao.listarTodos();

        } catch (Exception e) {
            Messages.addGlobalInfo("Erro ao carregar estados");
            e.printStackTrace();
        }
    }

    public void alterar(ActionEvent event) {
        cidade = (Cidade) event.getComponent().getAttributes().get("cidadeAlterar");
        cidade.setCaminhoImg(uploadPath + cidade.getCodigo());
        carregaUfs();
    }

    @PostConstruct
    public void listar() {
        try {
            CidadeDao dao = new CidadeDao();
            cidades = dao.listarTodos();
        } catch (Exception exception) {
            Messages.addGlobalError("Erro ao cadastrar Cidades");
            exception.printStackTrace();
        }
    }

    public void novo() {
        this.cidade = new Cidade();
        carregaUfs();
    }

    public void salvar() {
        try {
            CidadeDao dao = new CidadeDao();
            Cidade cidadeN = dao.merge(cidade);

            Path origem = Paths.get(cidade.getCaminhoImg());
            Path destino = Paths.get(uploadPath + cidadeN.getCodigo());
            Files.copy(origem, destino, StandardCopyOption.REPLACE_EXISTING);

            Messages.addGlobalInfo("Cidade cadastrada com sucesso");
            novo();
            listar();
        } catch (Exception exception) {
            Messages.addGlobalError("Erro ao cadastar Cidade");
            exception.printStackTrace();
        }
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public List<Cidade> getCidades() {
        return cidades;
    }

    public void setCidades(List<Cidade> cidades) {
        this.cidades = cidades;
    }

    public List<Estado> getEstados() {
        return estados;
    }

    public void setEstados(List<Estado> estados) {
        this.estados = estados;
    }
}
