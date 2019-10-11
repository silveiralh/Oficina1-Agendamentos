package com.utfpr.uniagenda.repository.search;
import com.utfpr.uniagenda.domain.Sala;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Sala} entity.
 */
public interface SalaSearchRepository extends ElasticsearchRepository<Sala, Long> {
}
