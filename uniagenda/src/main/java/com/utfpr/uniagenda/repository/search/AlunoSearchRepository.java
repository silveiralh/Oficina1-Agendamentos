package com.utfpr.uniagenda.repository.search;
import com.utfpr.uniagenda.domain.Aluno;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Aluno} entity.
 */
public interface AlunoSearchRepository extends ElasticsearchRepository<Aluno, Long> {
}
