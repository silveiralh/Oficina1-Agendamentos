package com.utfpr.uniagenda.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link AgendaReservaSalaSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class AgendaReservaSalaSearchRepositoryMockConfiguration {

    @MockBean
    private AgendaReservaSalaSearchRepository mockAgendaReservaSalaSearchRepository;

}