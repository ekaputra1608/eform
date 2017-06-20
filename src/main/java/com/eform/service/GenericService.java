package com.eform.service;

import com.eform.util.DataTables;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.*;
import org.hibernate.internal.CriteriaImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

@Service
@Transactional
public class GenericService {

	@Autowired
	private SessionFactory sessionFactory;
	
	public DataTables search(Class clazz, DataTables dataTables) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(clazz);
		
		if (dataTables.getProjections() != null) {
			ProjectionList projectionList = Projections.projectionList();
			for (String field : dataTables.getProjections()) {
				if (field.contains(".")) {
					String[] args = field.split("\\.");
					for (int i=0; i<args.length - 1; i++) {
						criteria.createAlias(args[i], args[i]);
					}
				}
				projectionList.add(Projections.property(field));
			}
			criteria.setProjection(projectionList);
		}
		
		if (dataTables.getFilters() != null) {
			for (String field : dataTables.getFilters().keySet()) {
                Object value = dataTables.getFilters().get(field);
                if (field.contains(":")) {
                	String[] args = field.split(":");
                	try {
                		Method method = Restrictions.class.getMethod(args[0], String.class, Object.class);
                    	Object[] params = { args[1], value };
                    	criteria.add((SimpleExpression) method.invoke(null, params));
                	} catch (Exception e) {
                		e.printStackTrace();
                	}
                } else {
                	criteria.add(Restrictions.eq(field, value));
                }
            }
		}
		
		if (dataTables.getiSortingCols() != null && dataTables.getiSortingCols() > 0) {
            for (int i = 0; i < dataTables.getiSortingCols(); i++) {
                String property = dataTables.getmDataProp().get(dataTables.getiSortCol().get(i));
                if ("asc".equals(dataTables.getsSortDir().get(i))) {
                    criteria.addOrder(Order.asc(property));
                } else {
                    criteria.addOrder(Order.desc(property));
                }
            }
        }
		
		if (dataTables.getiDisplayLength() != null) {
            criteria.setFirstResult(dataTables.getiDisplayStart())
                    .setMaxResults(dataTables.getiDisplayLength());
        }
        List data = criteria.list();
        
        long total = 0;
        if (!data.isEmpty()) {
            if (dataTables.getiDisplayLength() == null) {
                total = data.size();
            } else {
                Iterator<CriteriaImpl.OrderEntry> orders = ((CriteriaImpl) criteria).iterateOrderings();
                while (orders.hasNext()) {
                    orders.next();
                    orders.remove();
                }
                total = (Long) criteria.setProjection(new RowCountProjection()).setFirstResult(0).uniqueResult();
            }
        }
        
        return dataTables.extract(data, total);
	}
	
	public List list(Class clazz) {
        return sessionFactory.getCurrentSession().createQuery("from " + clazz.getName()).list();
    }

    public List list(Class clazz, String field, String value) {
        return sessionFactory.getCurrentSession()
                .createQuery("from " + clazz.getName() + " where " + field + " = :param")
                .setParameter("param", value)
                .list();
    }

	public Object get(Class clazz, Serializable id) {
        return sessionFactory.getCurrentSession().get(clazz, id);
    }

    public Object get(Class clazz, String field, String value) {
        List list = sessionFactory.getCurrentSession()
                .createQuery("from " + clazz.getName() + " where " + field + " = :param")
                .setParameter("param", value)
                .list();

        if (!list.isEmpty()) return list.get(0);

        return null;
    }

	public void saveOrUpdate(Object entity) {
        sessionFactory.getCurrentSession().saveOrUpdate(entity);
    }

    public void delete(Object entity) {
        sessionFactory.getCurrentSession().delete(entity);
    }
	
    public List autoComplete(Class clazz, String id, String field, String search, String q, int max) {
        return sessionFactory.getCurrentSession()
                .createQuery("select " + id + ", " + field + " from " + clazz.getName() + " where " + search + " like :q")
                .setParameter("q", q + "%")
                .setMaxResults(max)
                .list();
    }
}
