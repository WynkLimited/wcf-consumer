package in.wynk.consumerservice.dao;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Collection;
import java.util.List;
import java.util.Map;


public interface MongoBaseDao<T> {

    T insert(T entity);

    List<String> insert(List<T> entities);

    T find(String id);

    List<T> find(List<String> ids);

    List<T> find(Collection<String> ids, Map<String, String> params);

    List<T> find(Map<String, String> params, Map<String, String> inParams, Integer limit);

    List<T> find(Map<String, String> params, Integer limit);

    List<T> findAll(Integer page);

    List<T> findAll();

    List<T> find(Query query, Integer page, Integer limit);

    List<T> find(Criteria criteria);

    boolean remove(String id);

    List<T> find(Map<String, Object> params);

}
