package com.utfpr.uniagenda.repository.search;
import com.utfpr.uniagenda.domain.DiasAtendimento;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link DiasAtendimento} entity.
 */
public interface DiasAtendimentoSearchRepository extends ElasticsearchRepository<DiasAtendimento, Long> {
}
