package com.utfpr.uniagenda.repository.search;
import com.utfpr.uniagenda.domain.AgendaReservaSala;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link AgendaReservaSala} entity.
 */
public interface AgendaReservaSalaSearchRepository extends ElasticsearchRepository<AgendaReservaSala, Long> {
}
