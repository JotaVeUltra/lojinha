package br.com.joao.lojinha.bean;

import br.com.joao.lojinha.dao.CategoriaDao;
import br.com.joao.lojinha.dao.ProdutoDao;
import br.com.joao.lojinha.dao.WishlistDao;
import br.com.joao.lojinha.domain.Categoria;
import br.com.joao.lojinha.domain.Produto;
import br.com.joao.lojinha.domain.Usuario;
import br.com.joao.lojinha.domain.Wishlist;
import org.omnifaces.util.Messages;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
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
public class ProdutoBean implements Serializable {
    // TODO de-hardcode
    private final String uploadPath = "/home/joao/Projetos/Java/lojinha/uploads/produtos/";
    private Produto produto;
    private List<Produto> produtos;
    private List<Categoria> categorias;

    public void upload(FileUploadEvent fileUploadEvent) {
        try {
            UploadedFile x = fileUploadEvent.getFile();
            Path t = Files.createTempFile(null, null);
            Files.copy(x.getInputstream(), t, StandardCopyOption.REPLACE_EXISTING);

            produto.setCaminhoImg(t.toString());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void carregaCategorias() {
        try {
            CategoriaDao dao = new CategoriaDao();
            categorias = dao.listarTodos();

        } catch (Exception e) {
            Messages.addGlobalInfo("Erro ao carregar estados");
            e.printStackTrace();
        }
    }

    public void removeFromWishlist(ActionEvent event) {
        try {
            WishlistDao wishlistDao = new WishlistDao();
            produto = (Produto) event.getComponent().getAttributes().get("produtoRemoveWish");
            FacesContext context = FacesContext.getCurrentInstance();
            Usuario usuario = (Usuario) context.getExternalContext().getSessionMap().get("current_user");
            Wishlist wishlist = wishlistDao.getWish(produto, usuario);
            wishlistDao.excluir(wishlist);
        } catch (Exception exception) {
            Messages.addGlobalInfo("Erro ao remover da Wishlist");
            exception.printStackTrace();
        }
    }

    public void excluir(ActionEvent event) {
        try {
            produto = (Produto) event.getComponent().getAttributes().get("produtoExcluir");
            ProdutoDao dao = new ProdutoDao();
            dao.excluir(produto);

            Path caminho = Paths.get(uploadPath + produto.getCodigo());
            Files.deleteIfExists(caminho);

            Messages.addGlobalInfo(produto.getNome() + " Excluido com sucesso");
            listar();
        } catch (Exception exception) {
            Messages.addGlobalInfo("Erro ao excluir");
            exception.printStackTrace();
        }
    }

    public void alterar(ActionEvent event) {
        produto = (Produto) event.getComponent().getAttributes().get("produtoAlterar");
        produto.setCaminhoImg(uploadPath + produto.getCodigo());
        carregaCategorias();
    }

    @PostConstruct
    public void listar() {
        try {
            ProdutoDao dao = new ProdutoDao();
            produtos = dao.listarTodos();
        } catch (Exception exception) {
            Messages.addGlobalError("Erro ao listar Produtos");
            exception.printStackTrace();
        }
    }

    public void novo() {
        this.produto = new Produto();
        this.produto.setCategoria(new Categoria());
        carregaCategorias();
    }

    public boolean isOnWishlist(Produto pro) {
        FacesContext context = FacesContext.getCurrentInstance();
        Usuario usuario = (Usuario) context.getExternalContext().getSessionMap().get("current_user");
        produto = pro;
        WishlistDao wishlistDao = new WishlistDao();
        return wishlistDao.getWish(produto, usuario) != null;
    }

    public void addToWishlist(ActionEvent event) {
        produto = (Produto) event.getComponent().getAttributes().get("produtoAddWish");
        FacesContext context = FacesContext.getCurrentInstance();
        Wishlist wishlist = new Wishlist();
        wishlist.setUsuario((Usuario) context.getExternalContext().getSessionMap().get("current_user"));
        wishlist.setProduto(produto);
        try {
            WishlistDao wishlistDao = new WishlistDao();
            wishlistDao.merge(wishlist);
            Messages.addGlobalInfo(produto.getNome() + " adicionado à Wishlist de " + wishlist.getUsuario().getNome());
            novo();
            listar();
        } catch (Exception exception) {
            Messages.addGlobalError("Erro ao adicionar à Wishlist");
            exception.printStackTrace();
        }
    }

    public void salvar() {
        try {
            ProdutoDao dao = new ProdutoDao();
            CategoriaDao categoriaDao = new CategoriaDao();
            FacesContext context = FacesContext.getCurrentInstance();
            produto.setCategoria(categoriaDao.merge(produto.getCategoria()));
            produto.setProprietario((Usuario) context.getExternalContext().getSessionMap().get("current_user"));
            Produto nProduto = dao.merge(produto);

            Path origem = Paths.get(produto.getCaminhoImg());
            Path destino = Paths.get(uploadPath + nProduto.getCodigo());
            Files.copy(origem, destino, StandardCopyOption.REPLACE_EXISTING);

            Messages.addGlobalInfo("Produto cadastrado com sucesso");
            novo();
            listar();
        } catch (Exception exception) {
            Messages.addGlobalError("Erro ao cadastar Produto");
            exception.printStackTrace();
        }
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }
}
