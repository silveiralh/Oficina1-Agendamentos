package com.utfpr.uniagenda.repository.search;
import com.utfpr.uniagenda.domain.AgendaSala;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link AgendaSala} entity.
 */
public interface AgendaSalaSearchRepository extends ElasticsearchRepository<AgendaSala, Long> {
}
