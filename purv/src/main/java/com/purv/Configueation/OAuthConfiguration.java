package com.purv.Configueation;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
@Configuration
@EnableAuthorizationServer
public class OAuthConfiguration extends AuthorizationServerConfigurerAdapter{



	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;
	
	@Autowired
	BCryptPasswordEncoder PasswordEncoder;
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	UserDetailsService userDetailsService;

	/*
	 * @Override public void configure(final AuthorizationServerSecurityConfigurer
	 * oauthServer) throws Exception {
	 * oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess(
	 * "isAuthenticated()"); }
	 */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
    	// TODO Auto-generated method stub
    	security.passwordEncoder(PasswordEncoder);
    	
    }
	

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
		.withClient("fooClientId").secret(PasswordEncoder.encode("secret"))
		.authorizedGrantTypes("access_token","password", "authorization_code", "refresh_token").scopes("read","write")
		.authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT","ROLE_ADMIN","ADMIN","ROLE_USER","USER")
		.autoApprove(true)
		.accessTokenValiditySeconds(180)//Access token is only valid for 3 minutes.
        .refreshTokenValiditySeconds(600);//Refresh token is only valid for 10 minutes.;
	}

	/*
	 * @Override public void configure(final AuthorizationServerEndpointsConfigurer
	 * endpoints) throws Exception {
	 * endpoints.tokenStore(tokenStore()).authenticationManager(
	 * authenticationManager).accessTokenConverter(defaultAccessTokenConverter())
	 * .userDetailsService(userDetailsService); }
	 */
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		
		 endpoints.authorizationCodeServices(authorizationCodeServices())
         .tokenStore(tokenStore())
         .tokenEnhancer(jwtTokenEnhancer())
         .authenticationManager(authenticationManager);
	}

	private AuthorizationCodeServices authorizationCodeServices() {
		// TODO Auto-generated method stub
		return new JdbcAuthorizationCodeServices(dataSource);
	}


	/*
	 * @Bean public TokenStore tokenStore(){ return new
	 * JwtTokenStore(jwtTokenEnhancer()); }
	 */
	@Bean
    public TokenStore tokenStore() {
        return new InMemoryTokenStore();
    }

	private JwtAccessTokenConverter jwtTokenEnhancer() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		 
	    return converter;
		//return new JwtAccessTokenConverter();
	}


}
