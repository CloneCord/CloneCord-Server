package net.leloubil.clonecordserver.security;

import net.leloubil.clonecordserver.authentication.filters.JWTAuthenticationFilter;
import net.leloubil.clonecordserver.authentication.filters.JWTAuthorizationFilter;
import net.leloubil.clonecordserver.services.LoginUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Configuration for the spring security module
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurity extends WebSecurityConfigurerAdapter {



    private UserDetailsServiceImpl userDetailsService;
    private LoginUserService loginUserService;
    private PasswordEncoder passwordEncoder;

    public WebSecurity(UserDetailsServiceImpl userDetailsService, LoginUserService loginUserService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.loginUserService = loginUserService;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                //allow sign up url
                .antMatchers(HttpMethod.POST, "/auth/*").permitAll()
                .antMatchers(HttpMethod.GET, "/info").permitAll()
                // spring health endpoints
                .antMatchers("/actuator/*").permitAll()
                // swagger
                .antMatchers("/v3/api-docs/**").permitAll()
                // swagger ui
                .antMatchers("/swagger-ui/**").permitAll()
                //everything else needs to be logged in
                .anyRequest().authenticated()
                .and()
                //Filter to create JWT on successfull login
                .addFilter(getJWTAuthenticationFilter())

                //Filter to make sure JWT is valid
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), loginUserService))

                //No session since we use JWT
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    private JWTAuthenticationFilter getJWTAuthenticationFilter() throws Exception {
        JWTAuthenticationFilter jwtAuthenticationFilter = new JWTAuthenticationFilter(authenticationManager(), loginUserService);
        jwtAuthenticationFilter.setFilterProcessesUrl("/auth/login");
        return jwtAuthenticationFilter;
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
