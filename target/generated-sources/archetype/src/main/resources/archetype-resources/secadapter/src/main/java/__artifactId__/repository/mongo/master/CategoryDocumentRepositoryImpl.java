#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${artifactId}.repository.mongo.master;

import ${package}.application.domain.model.domain.mongo.master.CategoryDocument;
import ${package}.application.domain.ports.secondary.repository.DocumentMongoSpecification;
import ${package}.application.domain.ports.secondary.repository.MongoCriteriaPageableQuery;
import ${package}.application.domain.ports.secondary.repository.mongo.master.CategoryDocumentRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryDocumentRepositoryImpl implements CategoryDocumentRepository {

  @Autowired
  private MongoTemplate mongoTemplate;

  @Autowired
  private MongoCriteriaPageableQuery mongoCriteriaPageableQuery;

  @Override
  @Cacheable(cacheNames = "categoryDocument",
      key = "{ ${symbol_pound}root.methodName, ${symbol_pound}code }",
      unless = "${symbol_pound}result == null")
  public CategoryDocument findByCode(String code) {
    Query query = new Query();
    query.addCriteria(Criteria.where("data.code").is(code));

    return mongoTemplate.findOne(query, CategoryDocument.class);
  }

  @Override
  @Cacheable(cacheNames = "categoryDocument",
      key = "{ ${symbol_pound}root.methodName, ${symbol_pound}id }",
      unless = "${symbol_pound}result == null")
  public CategoryDocument findById(String id) {
    return mongoTemplate.findById(id, CategoryDocument.class);
  }

  @Override
  @Cacheable(cacheNames = "categoryDocument",
      key = "{ ${symbol_pound}root.methodName}",
      unless = "${symbol_pound}result == null")
  public List<CategoryDocument> findAll() {
    return mongoTemplate.findAll(CategoryDocument.class);
  }

  @Override
  public Page<CategoryDocument> findAll(DocumentMongoSpecification documentMongoSpecification,
      Pageable pageable) {
    return mongoCriteriaPageableQuery.find(documentMongoSpecification, pageable);
  }

  @Override
  @Caching(put = {
      @CachePut(cacheNames = "categoryDocument",
          key = "{ 'findByCode', ${symbol_pound}result.code }",
          unless = "${symbol_pound}result == null"),
      @CachePut(cacheNames = "categoryDocument",
          key = "{ 'findById', ${symbol_pound}result.id }",
          unless = "${symbol_pound}result == null")},
      evict = {
          @CacheEvict(cacheNames = "categoryDocument", key = "{ 'findAll' }")})
  public <S extends CategoryDocument> S save(S entity) {
    return mongoTemplate.save(entity);
  }

  @Override
  @Caching(evict = {
      @CacheEvict(cacheNames = "categoryDocument",
          key = "{ 'findByCode', ${symbol_pound}entity.code }"),
      @CacheEvict(cacheNames = "categoryDocument",
          key = "{ 'findById', ${symbol_pound}entity.id }"),
      @CacheEvict(cacheNames = "categoryDocument", key = "{ 'findAll' }")
  })
  public void delete(CategoryDocument entity) {
    Query query = new Query();
    query.addCriteria(Criteria.where("id").is(entity.getId()));

    mongoTemplate.remove(query, CategoryDocument.class);
  }

  @Override
  @CacheEvict(cacheNames = "categoryDocument", allEntries = true)
  public void deleteAll() {
    mongoTemplate.remove(new Query(), CategoryDocument.class);
  }
}
