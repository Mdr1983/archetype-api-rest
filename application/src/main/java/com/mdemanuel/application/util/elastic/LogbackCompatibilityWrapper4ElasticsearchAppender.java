package com.mdemanuel.application.util.elastic;

import com.internetitem.logback.elasticsearch.ElasticsearchAppender;
import com.internetitem.logback.elasticsearch.config.ElasticsearchProperties;
import com.internetitem.logback.elasticsearch.config.Property;

public class LogbackCompatibilityWrapper4ElasticsearchAppender extends ElasticsearchAppender {

  public void setAttributes(Attributes attributes) {
    super.setProperties(attributes);
  }

  public static class Attribute extends Property {

    public Attribute() {
    }

    public Attribute(
        String name, String value, boolean allowEmpty) {
      super(name, value, allowEmpty);
    }
  }

  public static class Attributes extends ElasticsearchProperties {

    public void addAttribute(Attribute attribute) {
      super.addProperty(attribute);
    }
  }
}