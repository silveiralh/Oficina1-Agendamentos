package com.utfpr.uniagenda.config;

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
            createCache(cm, com.utfpr.uniagenda.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.utfpr.uniagenda.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.utfpr.uniagenda.domain.User.class.getName());
            createCache(cm, com.utfpr.uniagenda.domain.Authority.class.getName());
            createCache(cm, com.utfpr.uniagenda.domain.User.class.getName() + ".authorities");
            createCache(cm, com.utfpr.uniagenda.domain.PersistentToken.class.getName());
            createCache(cm, com.utfpr.uniagenda.domain.User.class.getName() + ".persistentTokens");
            createCache(cm, com.utfpr.uniagenda.domain.Cargo.class.getName());
            createCache(cm, com.utfpr.uniagenda.domain.Sala.class.getName());
            createCache(cm, com.utfpr.uniagenda.domain.Servidor.class.getName());
            createCache(cm, com.utfpr.uniagenda.domain.Servidor.class.getName() + ".nomeCargos");
            createCache(cm, com.utfpr.uniagenda.domain.AgendaAtendimentoServidor.class.getName());
            createCache(cm, com.utfpr.uniagenda.domain.AgendaAtendimentoServidor.class.getName() + ".codSiapes");
            createCache(cm, com.utfpr.uniagenda.domain.AgendaAtendimentoServidor.class.getName() + ".horarios");
            createCache(cm, com.utfpr.uniagenda.domain.AgendaAtendimentoServidor.class.getName() + ".mes");
            createCache(cm, com.utfpr.uniagenda.domain.AgendaAtendimentoServidor.class.getName() + ".diaMes");
            createCache(cm, com.utfpr.uniagenda.domain.AgendaAtendimentoServidor.class.getName() + ".statuses");
            createCache(cm, com.utfpr.uniagenda.domain.AgendaAtendimentoServidor.class.getName() + ".raAlunos");
            createCache(cm, com.utfpr.uniagenda.domain.Aluno.class.getName());
            createCache(cm, com.utfpr.uniagenda.domain.AgendaServidor.class.getName());
            createCache(cm, com.utfpr.uniagenda.domain.AgendaServidor.class.getName() + ".codSiapes");
            createCache(cm, com.utfpr.uniagenda.domain.AgendaServidor.class.getName() + ".mes");
            createCache(cm, com.utfpr.uniagenda.domain.AgendaServidor.class.getName() + ".diaMes");
            createCache(cm, com.utfpr.uniagenda.domain.AgendaServidor.class.getName() + ".statusDias");
            createCache(cm, com.utfpr.uniagenda.domain.AgendaAluno.class.getName());
            createCache(cm, com.utfpr.uniagenda.domain.AgendaAluno.class.getName() + ".mes");
            createCache(cm, com.utfpr.uniagenda.domain.AgendaAluno.class.getName() + ".diaMes");
            createCache(cm, com.utfpr.uniagenda.domain.AgendaAluno.class.getName() + ".statusDias");
            createCache(cm, com.utfpr.uniagenda.domain.AgendaAluno.class.getName() + ".raAlunos");
            createCache(cm, com.utfpr.uniagenda.domain.AgendaSala.class.getName());
            createCache(cm, com.utfpr.uniagenda.domain.AgendaSala.class.getName() + ".mes");
            createCache(cm, com.utfpr.uniagenda.domain.AgendaSala.class.getName() + ".diaMes");
            createCache(cm, com.utfpr.uniagenda.domain.AgendaSala.class.getName() + ".statusDias");
            createCache(cm, com.utfpr.uniagenda.domain.AgendaSala.class.getName() + ".codigoSalas");
            createCache(cm, com.utfpr.uniagenda.domain.AgendaReservaSala.class.getName());
            createCache(cm, com.utfpr.uniagenda.domain.AgendaReservaSala.class.getName() + ".horarios");
            createCache(cm, com.utfpr.uniagenda.domain.AgendaReservaSala.class.getName() + ".mes");
            createCache(cm, com.utfpr.uniagenda.domain.AgendaReservaSala.class.getName() + ".diaMes");
            createCache(cm, com.utfpr.uniagenda.domain.AgendaReservaSala.class.getName() + ".statuses");
            createCache(cm, com.utfpr.uniagenda.domain.AgendaReservaSala.class.getName() + ".raAlunos");
            createCache(cm, com.utfpr.uniagenda.domain.AgendaReservaSala.class.getName() + ".codigoSalas");
            createCache(cm, com.utfpr.uniagenda.domain.DiasAtendimento.class.getName());
            createCache(cm, com.utfpr.uniagenda.domain.DiasAtendimento.class.getName() + ".codSiapes");
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
