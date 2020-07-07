package br.com.joao.lojinha.dao;

import br.com.joao.lojinha.domain.Produto;
import br.com.joao.lojinha.domain.Usuario;
import br.com.joao.lojinha.domain.Wishlist;
import br.com.joao.lojinha.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class WishlistDao extends GenericDao<Wishlist> {

    public Wishlist getWish(Produto produto, Usuario usuario) {
        Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
        try {
            Criteria consulta = sessao.createCriteria(Wishlist.class);
            consulta.add(Restrictions.eq("produto", produto));
            consulta.add(Restrictions.eq("usuario", usuario));
            return (Wishlist) consulta.uniqueResult();
        } finally {
            sessao.close();
        }
    }
}
