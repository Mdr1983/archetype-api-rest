#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${artifactId}.util.redis;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Slf4j
public class CustomKeyPrefixSerializer<T> implements RedisSerializer<T> {

  private final String prefix;
  private final RedisSerializer<String> serializer = new StringRedisSerializer();

  public CustomKeyPrefixSerializer(String prefix) {
    this.prefix = prefix;
  }

  public byte[] serialize(T t)
      throws SerializationException {
    log.debug("Init serialization: {}", t);

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    try {
      if (t == null) {
        return null;
      }

      outputStream.write(this.prefix.getBytes());
      outputStream.write(this.serializer.serialize(t.toString()));
    } catch (IOException ex) {
      log.error("Error serialization: {}", ex.getMessage(), ex);
    }

    return outputStream.toByteArray();
  }

  public T deserialize(byte[] bytes)
      throws SerializationException {
    String stringValue = new String(bytes);

    log.debug("Init deserialization: {}", stringValue);

    if (bytes != null && bytes.length != 0) {
      stringValue = stringValue.replace(this.prefix, "");

      return (T) this.serializer.deserialize(stringValue.getBytes());
    } else {
      return null;
    }
  }
}
