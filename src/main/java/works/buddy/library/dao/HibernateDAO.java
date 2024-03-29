package works.buddy.library.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public abstract class HibernateDAO<T extends Serializable> implements GenericDAO<T> {

    protected DetachedCriteria createCriteria() {
        return DetachedCriteria.forClass(getEntityClass());
    }

    @Autowired
    protected SessionFactory sessionFactory;

    public void save(final T entity) {
        getCurrentSession().persist(entity);
    }

    public void update(final T entity) {
        getCurrentSession().update(entity);
    }

    public void delete(final T entity) {
        getCurrentSession().delete(entity);
    }

    protected abstract Class<T> getEntityClass();

    @Override
    public T findOne(final Integer id) {
        return (T) getCurrentSession().get(getEntityClass(), id);
    }

    protected Collection<T> find(DetachedCriteria criteria) {
        return criteria.getExecutableCriteria(getCurrentSession()).list();
    }

    protected T findOne(DetachedCriteria criteria) {
        return (T) criteria.getExecutableCriteria(getCurrentSession()).uniqueResult();
    }

    @Override
    public List<T> findAll() {
        return getCurrentSession().createQuery("from " + getEntityClass().getName()).list();
    }

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
}
