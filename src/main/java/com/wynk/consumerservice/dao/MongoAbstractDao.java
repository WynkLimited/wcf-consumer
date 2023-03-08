package com.wynk.consumerservice.dao;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.wynk.consumerservice.entity.BaseEntity;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.ObjectUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class MongoAbstractDao<T extends BaseEntity> implements MongoBaseDao<T> {

  protected MongoTemplate mongoTemplate;

  protected abstract Class<T> getEntityClass();

  protected final int findAllLimit() {
    return 50;
  }

  protected final String getEntityName() {
    return getEntityClass().getSimpleName();
  }

  protected MongoAbstractDao (MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }
  @Override
  public T insert(T entity) {
    mongoTemplate.save(entity);
    return entity;
  }

  @Override
  public List<String> insert(List<T> entities) {
    mongoTemplate.insertAll(entities);
    return entities.parallelStream().map(T::getId).collect(Collectors.toList());
  }

  @Override
  public T find(String id) {
    return mongoTemplate.findById(id, getEntityClass());
  }

  @Override
  public List<T> find(List<String> ids) {
    return mongoTemplate.find(new Query(Criteria.where("id").in(ids)), getEntityClass());
  }

  @Override
  public List<T> find(Criteria criteria) {
    return mongoTemplate.find(new Query(criteria), getEntityClass());
  }

  @Override
  public List<T> find(Collection<String> ids, Map<String, String> params) {
    Criteria criteria = Criteria.where("id").in(ids);
    params
        .keySet()
        .forEach(
            key -> {
              criteria.and(key).is(params.get(key));
            });
    return mongoTemplate.find(new Query(criteria), getEntityClass());
  }

  @Override
  public List<T> find(Map<String, String> params, Map<String, String> inParams, Integer limit) {
    Criteria criteria = new Criteria();
    params
        .keySet()
        .forEach(
            key -> {
              criteria.and(key).is(params.get(key));
            });
    inParams
        .keySet()
        .forEach(
            key -> {
              criteria.and(key).in(inParams.get(key));
            });
    if (limit == null) {
      limit = findAllLimit();
    }
    return mongoTemplate.find(new Query(criteria).limit(limit), getEntityClass());
  }

  @Override
  public List<T> find(Map<String, String> params, Integer limit) {
    Criteria criteria = new Criteria();
    params
        .keySet()
        .forEach(
            key -> {
              criteria.and(key).is(params.get(key));
            });
    if (limit == null) {
      limit = findAllLimit();
    }
    return mongoTemplate.find(new Query(criteria).limit(limit), getEntityClass());
  }

  @Override
  public List<T> find(Map<String, Object> params) {
    Criteria criteria = new Criteria();
    params
        .keySet()
        .forEach(
            key -> {
              criteria.and(key).is(params.get(key));
            });
    return mongoTemplate.find(new Query(criteria), getEntityClass());
  }

  public T findOne(Map<String, String> params) {
    Criteria criteria = new Criteria();
    params
        .keySet()
        .forEach(
            key -> {
              criteria.and(key).is(params.get(key));
            });
    return mongoTemplate.findOne(new Query(criteria), getEntityClass());
  }

  public T findOne(Criteria criteria) {
    return mongoTemplate.findOne(new Query(criteria), getEntityClass());
  }

  @Override
  public List<T> findAll(Integer page) {
    if (!ObjectUtils.isEmpty(page) && page >= 0) {
      return mongoTemplate.find(
          new Query().limit(findAllLimit()).with(PageRequest.of(page, findAllLimit())),
          getEntityClass());
    }
    if (page == -1) // return all
    return mongoTemplate.findAll(getEntityClass());
    return mongoTemplate.find(new Query().limit(findAllLimit()), getEntityClass());
  }

  @Override
  public List<T> findAll() {
    return findAll(0);
  }

  public List<T> find(Query query, Integer page, Integer limit) {

    if (page == -1) // return all that lies under this criteria
      return mongoTemplate.find(query, getEntityClass());

    if (Objects.isNull(limit) || limit <= 0) limit = findAllLimit();

    query.limit(limit);

    if (!ObjectUtils.isEmpty(page) && page >= 0)
      query.with(PageRequest.of(page, limit));

    return mongoTemplate.find(query, getEntityClass());
  }

  @Override
  public boolean remove(String id) {
    DeleteResult result =
        mongoTemplate.remove(new Query(Criteria.where("id").is(id)), getEntityClass());
    if (result.getDeletedCount() != 0) return true;
    return false;
  }

  public void update(String idKey, String id, Map<String, Object> params) {
    Update update = new Update();
    if (MapUtils.isEmpty(params)) {
      return;
    }
    for (String key : params.keySet()) {
      update.set(key, params.get(key));
    }
    mongoTemplate.updateFirst(new Query(Criteria.where(idKey).is(id)), update, getEntityClass());
  }

  /**
   * Update selected field in first document matching having given key
   * @param idKey
   * @param id
   * @param params
   * @return true if this write resulted in an update of an existing document
   */
  protected boolean updateWithResult(String idKey, String id, Map<String, Object> params) {
    if (StringUtils.isEmpty(idKey) || MapUtils.isEmpty(params)) {
      return false;
    }
    Update update = new Update();
    for (String key : params.keySet()) {
      update.set(key, params.get(key));
    }
    UpdateResult result = mongoTemplate.updateFirst(new Query(Criteria.where(idKey).is(id)), update, getEntityClass());
    return result.wasAcknowledged();

  }

  public void addToSet(List<String> ids, Map<String, Object> params) {
    Update update = new Update();
    if (MapUtils.isEmpty(params) || CollectionUtils.isEmpty(ids)) {
      return;
    }
    for (String key : params.keySet()) {
      update.set(key, params.get(key));
    }

    update.set("ua", System.currentTimeMillis());
    mongoTemplate.updateMulti(new Query(Criteria.where("id").in(ids)), update, getEntityClass());
  }

}
