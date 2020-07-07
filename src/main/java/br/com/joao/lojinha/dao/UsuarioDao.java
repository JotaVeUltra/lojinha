package br.com.joao.lojinha.dao;

import br.com.joao.lojinha.domain.Usuario;
import br.com.joao.lojinha.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

import javax.faces.context.FacesContext;

public class UsuarioDao extends GenericDao<Usuario> {

    public Usuario getUsuario(String login) {
        Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
        String hql = "FROM Usuario u where u.nome = :username";
        Query query = sessao.createQuery(hql);

        query.setParameter("username", login);

        return (Usuario) query.uniqueResult();
    }

    public Usuario getCurrentUser() {
        return (Usuario) FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getSessionMap()
                .get("current_user");
    }
}