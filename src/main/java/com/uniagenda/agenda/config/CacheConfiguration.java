package com.uniagenda.agenda.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.uniagenda.agenda.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.uniagenda.agenda.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.uniagenda.agenda.domain.User.class.getName());
            createCache(cm, com.uniagenda.agenda.domain.Authority.class.getName());
            createCache(cm, com.uniagenda.agenda.domain.User.class.getName() + ".authorities");
            createCache(cm, com.uniagenda.agenda.domain.PersistentToken.class.getName());
            createCache(cm, com.uniagenda.agenda.domain.User.class.getName() + ".persistentTokens");
            createCache(cm, com.uniagenda.agenda.domain.Cargo.class.getName());
            createCache(cm, com.uniagenda.agenda.domain.Sala.class.getName());
            createCache(cm, com.uniagenda.agenda.domain.Servidor.class.getName());
            createCache(cm, com.uniagenda.agenda.domain.Servidor.class.getName() + ".nomeCargos");
            createCache(cm, com.uniagenda.agenda.domain.AgendaAtendimentoServidor.class.getName());
            createCache(cm, com.uniagenda.agenda.domain.AgendaAtendimentoServidor.class.getName() + ".codSiapes");
            createCache(cm, com.uniagenda.agenda.domain.AgendaAtendimentoServidor.class.getName() + ".raAlunos");
            createCache(cm, com.uniagenda.agenda.domain.Aluno.class.getName());
            createCache(cm, com.uniagenda.agenda.domain.AgendaServidor.class.getName());
            createCache(cm, com.uniagenda.agenda.domain.AgendaServidor.class.getName() + ".codSiapes");
            createCache(cm, com.uniagenda.agenda.domain.AgendaAluno.class.getName());
            createCache(cm, com.uniagenda.agenda.domain.AgendaAluno.class.getName() + ".statusDias");
            createCache(cm, com.uniagenda.agenda.domain.AgendaAluno.class.getName() + ".raAlunos");
            createCache(cm, com.uniagenda.agenda.domain.AgendaSala.class.getName());
            createCache(cm, com.uniagenda.agenda.domain.AgendaSala.class.getName() + ".codigoSalas");
            createCache(cm, com.uniagenda.agenda.domain.AgendaReservaSala.class.getName());
            createCache(cm, com.uniagenda.agenda.domain.AgendaReservaSala.class.getName() + ".raAlunos");
            createCache(cm, com.uniagenda.agenda.domain.AgendaReservaSala.class.getName() + ".codigoSalas");
            createCache(cm, com.uniagenda.agenda.domain.DiasAtendimento.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cm.destroyCache(cacheName);
        }
        cm.createCache(cacheName, jcacheConfiguration);
    }
}
