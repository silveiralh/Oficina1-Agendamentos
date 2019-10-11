package com.utfpr.uniagenda.repository.search;
import com.utfpr.uniagenda.domain.AgendaAluno;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link AgendaAluno} entity.
 */
public interface AgendaAlunoSearchRepository extends ElasticsearchRepository<AgendaAluno, Long> {
}
