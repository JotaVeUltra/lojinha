package br.com.joao.lojinha.bean;

import br.com.joao.lojinha.dao.WishlistDao;
import br.com.joao.lojinha.domain.Wishlist;
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
public class WishlistBean implements Serializable {


    private Wishlist wishlist;
    private List<Wishlist> wishlists;

    public void excluir(ActionEvent event) {
        try {
            wishlist = (Wishlist) event.getComponent().getAttributes().get("wishlistExcluir");
            WishlistDao dao = new WishlistDao();
            dao.excluir(wishlist);
            Messages.addGlobalInfo("Wishlist Excluida com sucesso");
            listar();
        } catch (Exception exception) {
            Messages.addGlobalInfo("Erro ao excluir");
            exception.printStackTrace();
        }
    }

    public void alterar(ActionEvent event) {
        wishlist = (Wishlist) event.getComponent().getAttributes().get("wishlistAlterar");

    }

    @PostConstruct
    public void listar() {
        try {
            WishlistDao dao = new WishlistDao();
            wishlists = dao.listarTodos();
        } catch (Exception exception) {
            Messages.addGlobalError("Erro ao listar Wishlists");
            exception.printStackTrace();
        }
    }

    public void novo() {
        this.wishlist = new Wishlist();
    }

    public void salvar() {
        try {

            WishlistDao dao = new WishlistDao();
            dao.merge(wishlist);
            Messages.addGlobalInfo("Wishlist cadastrado com sucesso");
            novo();
            listar();
        } catch (Exception exception) {
            Messages.addGlobalError("Erro ao cadastar Wishlist");
            exception.printStackTrace();
        }
    }

    public Wishlist getWishlist() {
        return wishlist;
    }

    public void setWishlist(Wishlist wishlist) {
        this.wishlist = wishlist;
    }

    public List<Wishlist> getWishlists() {
        return wishlists;
    }

    public void setWishlists(List<Wishlist> wishlists) {
        this.wishlists = wishlists;
    }

}
