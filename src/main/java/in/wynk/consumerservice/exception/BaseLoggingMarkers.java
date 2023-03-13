package in.wynk.consumerservice.exception;

import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public class BaseLoggingMarkers {

  public static final Marker APPLICATION_ERROR = MarkerFactory.getMarker("APPLICATION_ERROR");
  public static final Marker APPLICATION_INVALID_USECASE =
      MarkerFactory.getMarker("APPLICATION_INVALID_USECASE");
  public static final Marker HTTP_ERROR = MarkerFactory.getMarker("HTTP_ERROR");
  public static final Marker CASSANDRA_ERROR = MarkerFactory.getMarker("CASSANDRA_ERROR");
  public static final Marker REDIS_ERROR = MarkerFactory.getMarker("REDIS_ERROR");
  public static final Marker GUAVA_ERROR = MarkerFactory.getMarker("GUAVA_ERROR");
  public static final Marker CACHE_EXCEPTION = MarkerFactory.getMarker("CACHE_EXCEPTION");
  public static final Marker MONGO_ERROR = MarkerFactory.getMarker("MONGO_ERROR");
  public static final Marker JMS_ERROR = MarkerFactory.getMarker("JMS_ERROR");
  public static final Marker OTP_ERROR = MarkerFactory.getMarker("OTP_ERROR");
  public static final Marker SOLACE_QUEUE_ERROR = MarkerFactory.getMarker("SOLACE_QUEUE_ERROR");
  public static final Marker CONTENT_META_NOT_FOUND = MarkerFactory.getMarker("CONTENT_META_NOT_FOUND");
  public static final Marker USER_PLAYBACK_LOG = MarkerFactory.getMarker("USER_PLAYBACK_LOG");
  public static final Marker CONTENT_NOT_MODIFIED = MarkerFactory.getMarker("CONTENT_NOT_MODIFIED");

  public static final Marker MO_ENGAGE_PUSH_ERROR = MarkerFactory.getMarker("MO_ENGAGE_PUSH_ERROR");
  public static final Marker MO_ENGAGE_PUSH_SUCCESS = MarkerFactory.getMarker("MO_ENGAGE_PUSH_SUCCESS");

}
