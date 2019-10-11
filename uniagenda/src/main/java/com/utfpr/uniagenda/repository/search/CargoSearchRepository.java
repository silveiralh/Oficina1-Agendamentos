package com.utfpr.uniagenda.repository.search;
import com.utfpr.uniagenda.domain.Cargo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Cargo} entity.
 */
public interface CargoSearchRepository extends ElasticsearchRepository<Cargo, Long> {
}
