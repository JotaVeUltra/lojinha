package br.com.joao.lojinha.dao;

import br.com.joao.lojinha.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.lang.reflect.ParameterizedType;
import java.util.List;

public class GenericDao<Entidade> {

    private Class<Entidade> classe;

    GenericDao() {
        classe = (Class<Entidade>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public void excluir(Entidade entidade) {
        Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
        Transaction t = null;
        try {
            t = sessao.beginTransaction();
            sessao.delete(entidade);
            t.commit();
        } catch (Exception exception) {
            if (t != null) {
                t.rollback();
            }
            throw exception;
        } finally {
            sessao.close();
        }
    }

    public Entidade buscar(Long codigo) {
        Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
        try {
            Criteria consulta = sessao.createCriteria(classe);
            consulta.add(Restrictions.idEq(codigo));
            return (Entidade) consulta.uniqueResult();
        } finally {
            sessao.close();
        }
    }

    public Entidade merge(Entidade entidade) {
        Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
        Transaction t = null;
        try {
            t = sessao.beginTransaction();
            Entidade retorno = (Entidade) sessao.merge(entidade);
            t.commit();
            return retorno;
        } catch (Exception exception) {
            if (t != null) {
                t.rollback();
            }
            throw exception;
        } finally {
            sessao.close();
        }
    }

    public List<Entidade> listarTodos() {
        Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
        try {
            Criteria consulta = sessao.createCriteria(classe);
            return (List<Entidade>) consulta.list();
        } finally {
            sessao.close();
        }
    }
}
