package net.leloubil.clonecordserver.security;

import net.leloubil.clonecordserver.authentication.JWTAuthenticationFilter;
import net.leloubil.clonecordserver.authentication.JWTAuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static net.leloubil.clonecordserver.security.SecurityConstants.SIGN_UP_URL;

/**
 * Configuration for the spring security module
 */
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {



    private UserDetailsServiceImpl userDetailsService;
    private PasswordEncoder passwordEncoder;

    public WebSecurity(UserDetailsServiceImpl userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                //allow sign up url
                .antMatchers(HttpMethod.POST,SIGN_UP_URL).permitAll()
                // spring health endpoints
                .antMatchers("/actuator/*").permitAll()
                //everything else needs to be logged in
                .anyRequest().authenticated()
                .and()
                //Filter to create JWT on successfull login
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))

                //Filter to make sure JWT is valid
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))

                //No session since we use JWT
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }


    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
}
