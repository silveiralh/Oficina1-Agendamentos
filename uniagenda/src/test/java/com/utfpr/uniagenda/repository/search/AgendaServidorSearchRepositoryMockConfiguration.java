package com.utfpr.uniagenda.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link AgendaServidorSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class AgendaServidorSearchRepositoryMockConfiguration {

    @MockBean
    private AgendaServidorSearchRepository mockAgendaServidorSearchRepository;

}
