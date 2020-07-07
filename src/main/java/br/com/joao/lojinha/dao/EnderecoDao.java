package br.com.joao.lojinha.dao;

import br.com.joao.lojinha.domain.Endereco;
import br.com.joao.lojinha.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import java.util.List;

public class EnderecoDao extends GenericDao<Endereco> {

    public List<Endereco> listarPorCodigo() {
        Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
        try {
            Criteria consulta = sessao.createCriteria(Endereco.class);
            consulta.addOrder(Order.asc("codigo"));
            return (List<Endereco>) consulta.list();
        } catch (Exception exception) {
            throw exception;
        } finally {
            sessao.close();
        }
    }
}
