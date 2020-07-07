package br.com.joao.lojinha.bean;

import br.com.joao.lojinha.dao.UsuarioDao;
import br.com.joao.lojinha.domain.Usuario;
import org.omnifaces.util.Messages;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

@ManagedBean
public class LoginBean {

    private UsuarioDao usuarioDao = new UsuarioDao();
    private Usuario usuario = new Usuario();

    private String username, password;

    private FacesContext context = FacesContext.getCurrentInstance();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void enviar() {
        try {
            usuario = usuarioDao.getUsuario(username);

            if (usuario == null) {
                Messages.addGlobalError("Login ou senha incorreto!");
            } else {
                context.getExternalContext().getSessionMap().put("current_user", usuario);
                Messages.addGlobalInfo("Bem vindo " + usuario.getNome());
                context.getExternalContext().redirect("index.xhtml");
            }
        } catch (Exception e) {
            Messages.addGlobalError("Ops :(");
            e.printStackTrace();
        }
    }

//	public void excluirConta(){
//		usuario = getUsuario();
//		if(usuario.getNome().equals(username)){
//			UsuarioBean usuarioBean = new UsuarioBean();
//			usuarioBean.excluir(usuario);
//			sair();
//		}
//	}

    public void sair() {
        this.usuario = null;
        this.username = null;
        this.password = null;
        context.getExternalContext().getSessionMap().put("current_user", null);
    }

    public boolean isLogged() {
        usuario = (Usuario) context.getExternalContext().getSessionMap().get("current_user");
        return usuario != null;

    }

    public Usuario getUsuario() {
        return (Usuario) context.getExternalContext().getSessionMap().get("current_user");
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
