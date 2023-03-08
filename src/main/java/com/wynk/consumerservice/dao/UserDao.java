package com.wynk.consumerservice.dao;

import com.wynk.consumerservice.annotation.TimeIt;
import com.wynk.consumerservice.entity.User;
import com.wynk.consumerservice.exception.WynkErrorType;
import com.wynk.consumerservice.exception.WynkRuntimeException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao extends MongoAbstractDao<User> {

  private static final int USER_REDIS_TTL = 12 * 60;
  private static final int USER_DTO_REDIS_TTL = 6 * 60;
  private static final String USER_DTO_REDIS_KEY = "'uid:dto:' + #uid";

  private static final Logger logger = LoggerFactory.getLogger(UserDao.class.getCanonicalName());

  @Override
  protected Class<User> getEntityClass() {
    return User.class;
  }

 @Autowired
 private  UserDao(MongoTemplate mongoTemplate) {
    super(mongoTemplate);
 };

  @TimeIt
  /*@WynkCacheable(source = CacheSource.REDIS,
    ttl = USER_REDIS_TTL,
    key = "'uid:' + #uid",
    cacheName = RedisCacheName.USER
  )*/
  public User getUserByUid(String uid) {
    logger.info("Uid in user dao. uid : {}", uid);
    if (StringUtils.isBlank(uid)) throw new WynkRuntimeException(WynkErrorType.MUS020);
    Criteria criteria = Criteria.where("uid").is(uid);
    User user = findOne(criteria);

    if (user == null) {
      throw new WynkRuntimeException(WynkErrorType.MUS020);
    }
    return user;
  }
  /*

  @WynkCacheable(source = CacheSource.REDIS,
          ttl = USER_REDIS_TTL,
          key = "'uid:' + #uid",
          cacheName = RedisCacheName.USER
  )
  public User getUserEntityFromCache(String uid) {
    return null;
  }

  @WynkCacheable(source = CacheSource.REDIS,
          ttl = USER_DTO_REDIS_TTL,
          key = USER_DTO_REDIS_KEY,
          cacheName = RedisCacheName.USER
  )
  public UserResponseDTO getUserDTOFromCache(String uid) {
    return null;
  }

  @WynkCacheable(source = CacheSource.REDIS,
          ttl = USER_DTO_REDIS_TTL,
          key = USER_DTO_REDIS_KEY,
          cacheName = RedisCacheName.USER
  )
  public UserResponseDTO setUserDTOInCache(String uid, UserResponseDTO dto) {
    return dto;
  }


  @TimeIt
  public User updateUser(User user) {
    if (user == null) {
      throw new WynkRuntimeException(WynkErrorType.MUS001);
    }
    User returnUser = insert(user);
    flushCache(user.uid);
    return returnUser;
  }

  public void updateUser(String uid, Map<String, Object> params) {

    super.update("uid",uid,params);
    flushCache(uid);
  }


*//*

  @CacheFlush(
          source = CacheSource.REDIS, key = "'uid:' + #uid",
          cacheName = RedisCacheName.USER)
  public void flushCache(String uid) {
    flushUserDTOCache(uid);
  }
*//*


  @TimeIt
  public boolean updateUserPodcastCategories(
          String uid, Set<String> podcastCategories) {
    try {
      Map<String, Object> updateFields =
              ImmutableMap.of(
                      User.MongoUserEntityKey.podcastCategories, podcastCategories);
      boolean result = updateWithResult(User.MongoUserEntityKey.uid, uid, updateFields);
      if (result) {
        logger.info("Update Selected podcast categories for uid : {} to podcastCategories", uid, podcastCategories);
        flushCache(uid);
      } else {
        logger.error("Unable to update selected podcastCategories for uid : {} ", uid);
      }
      return result;
    } catch (Exception e) {
      logger.error(
              LoggingMarker.MONGO_ERROR,
              "Error while updating Mongo user Attributes for userid: {}, ERROR: {}",
              uid,
              e.getMessage(),
              e);
      throw new WynkRuntimeException(WynkErrorType.MUS999);
    }
  }*/


}
