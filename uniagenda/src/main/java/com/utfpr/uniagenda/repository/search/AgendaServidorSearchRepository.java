package com.utfpr.uniagenda.repository.search;
import com.utfpr.uniagenda.domain.AgendaServidor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link AgendaServidor} entity.
 */
public interface AgendaServidorSearchRepository extends ElasticsearchRepository<AgendaServidor, Long> {
}
