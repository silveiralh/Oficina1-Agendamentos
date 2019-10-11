package com.utfpr.uniagenda.repository.search;
import com.utfpr.uniagenda.domain.Servidor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Servidor} entity.
 */
public interface ServidorSearchRepository extends ElasticsearchRepository<Servidor, Long> {
}
