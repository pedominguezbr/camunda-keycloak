package org.camunda.bpm.extension.keycloak.showcase.rest;

import javax.inject.Inject;

import org.camunda.bpm.engine.IdentityService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.jwk.JwkTokenStore;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
/**
 * Optional Security Configuration for Camunda REST Api.
 */
@Configuration
@EnableResourceServer
@EnableWebSecurity
@Order(SecurityProperties.BASIC_AUTH_ORDER - 20)
@ConditionalOnProperty(name = "rest.security.enabled", havingValue = "true", matchIfMissing = true)
public class RestApiSecurityConfig extends ResourceServerConfigurerAdapter {

	/** Configuration for REST Api security. */
	@Inject
	private RestApiSecurityConfigurationProperties configProps;
	
	/** Access to Camunda's Identity Service. */
	@Inject
	private IdentityService identityService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void configure(final HttpSecurity http) throws Exception {
        http
    	.csrf().ignoringAntMatchers("/api/**", "/engine-rest/**", "/engine-rest/engine/default/task/**")
    	.and()
        .antMatcher("/engine-rest/**")
		.antMatcher("/engine-rest/engine/default/task/**")
        .authorizeRequests()
          .anyRequest()
          .authenticated()
		;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void configure(final ResourceServerSecurityConfigurer config) {
		config
			.tokenServices(tokenServices())
			.resourceId(configProps.getRequiredAudience());
	}

	/**
	 * Configures the JWKS bases TokenStore.
	 */
	@Bean
	public TokenStore tokenStore() {
		return new JwkTokenStore(configProps.getJwkSetUrl());
	}

	/**
	 * Creates JWKS based TokenServices.
	 * @return DefaultTokenServices
	 */
	public ResourceServerTokenServices tokenServices() {
		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore());
		return defaultTokenServices;
	}

    /**
     * Registers the REST Api Keycloak Authentication Filter.
     * @return filter registration
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Bean
    public FilterRegistrationBean keycloakAuthenticationFilter(){
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new KeycloakAuthenticationFilter(identityService));
        filterRegistration.setOrder(102); // make sure the filter is registered after the Spring Security Filter Chain
        filterRegistration.addUrlPatterns("/engine-rest/*");
        return filterRegistration;
    }

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowCredentials(true);
		configuration.addAllowedOrigin("*");
		configuration.addAllowedHeader("*");
		configuration.addAllowedMethod("*");
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
   
}
