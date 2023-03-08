package com.wynk.consumerservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import in.wynk.lib.cache.guava.CacheGuavaConfig;
import org.springframework.data.mongodb.core.mapping.Document;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "wapusers")
@CacheGuavaConfig(ttl = 60, size = 30000)
public class WapUser extends User{
  private WapUser() {
    super();
  }
}
