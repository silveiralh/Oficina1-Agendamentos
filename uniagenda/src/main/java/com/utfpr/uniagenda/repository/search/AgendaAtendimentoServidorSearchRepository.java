package com.utfpr.uniagenda.repository.search;
import com.utfpr.uniagenda.domain.AgendaAtendimentoServidor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link AgendaAtendimentoServidor} entity.
 */
public interface AgendaAtendimentoServidorSearchRepository extends ElasticsearchRepository<AgendaAtendimentoServidor, Long> {
}
