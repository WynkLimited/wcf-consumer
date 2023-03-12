package in.wynk.consumerservice.dao;

import in.wynk.consumerservice.annotation.TimeIt;
import in.wynk.consumerservice.entity.User;
import in.wynk.consumerservice.exception.WynkErrorType;
import in.wynk.consumerservice.exception.WynkRuntimeException;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
@NoArgsConstructor
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
  @Cacheable(value = "user", key = "'user:' + #uid")
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


  @CacheEvict(value = "user", key = "'user:' + #uid", allEntries = true)
  public void updateUser(String uid, Map<String, Object> params) {
        super.update("uid",uid,params);
    }
}
