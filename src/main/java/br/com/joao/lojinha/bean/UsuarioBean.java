package br.com.joao.lojinha.bean;

import br.com.joao.lojinha.dao.EnderecoDao;
import br.com.joao.lojinha.dao.UsuarioDao;
import br.com.joao.lojinha.dao.WishlistDao;
import br.com.joao.lojinha.domain.Endereco;
import br.com.joao.lojinha.domain.Usuario;
import br.com.joao.lojinha.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
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
public class UsuarioBean implements Serializable {

    private Usuario usuario;
    private List<Usuario> usuarios;

    public void excluir(ActionEvent event) {
        try {
            usuario = (Usuario) event.getComponent().getAttributes().get("userExcluir");
            UsuarioDao dao = new UsuarioDao();
            dao.excluir(usuario);
            Messages.addGlobalInfo(usuario.getNome() + " Excluido com sucesso");
            listar();
        } catch (Exception exception) {
            Messages.addGlobalInfo("Erro ao excluir");
            exception.printStackTrace();
        }
    }

    public void alterar(ActionEvent event) {
        usuario = (Usuario) event.getComponent().getAttributes().get("userAlterar");

    }

    @PostConstruct
    public void listar() {
        try {
            UsuarioDao dao = new UsuarioDao();
            usuarios = dao.listarTodos();
        } catch (Exception exception) {
            Messages.addGlobalError("Erro ao listar Usuarios");
            exception.printStackTrace();
        }
    }

    public void novo() {
        this.usuario = new Usuario();
        this.usuario.setEndereco(new Endereco());
    }


    public Endereco buscaEndereco() throws Exception {
        Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
        try {
            Criteria criteria = sessao.createCriteria(Endereco.class);
            criteria.addOrder(Order.asc("codigo"));
            return (Endereco) criteria.setMaxResults(1).uniqueResult();
        } catch (Exception e) {
            throw e;
        } finally {
            sessao.close();
        }
    }

//	public Endereco buscaEndereco() throws Exception{
//		try {
//			Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
//			System.out.println("1");
//			Criteria criteria = sessao.createCriteria(Endereco.class);
//			System.out.println("1");
//			criteria.addOrder(Order.asc("codigo"));
//			System.out.println("1");
//			return (Endereco) criteria.setMaxResults(1).uniqueResult();
//		} catch (Exception e) {
//			throw e;
//		}
//	}

    public void salvar() {
        try {
            WishlistDao wishlistDao = new WishlistDao();
            EnderecoDao enderecoDao = new EnderecoDao();
            UsuarioDao dao = new UsuarioDao();

//			usuario.setWishlist(buscaWishlist());
            usuario.setEndereco(enderecoDao.merge(usuario.getEndereco()));
            dao.merge(usuario);
            Messages.addGlobalInfo("Usuario cadastrado com sucesso");
            novo();
            listar();
            LoginBean loginBean = new LoginBean();
            loginBean.setUsername(usuario.getNome());
            ;
            loginBean.setPassword(usuario.getSenha());
            loginBean.enviar();
        } catch (Exception exception) {
            Messages.addGlobalError("Erro ao cadastar Usuario");
            exception.printStackTrace();
        }
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

}
