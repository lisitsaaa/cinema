package com.example.cinema.configuration;

import com.example.cinema.configuration.jwt.JWTTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JWTTokenFilter jwtTokenFilter;

    private static final String ADMIN_ENDPOINT = "/**/admin/**";
    private static final String LOGIN_ENDPOINT = "/user/login";
    private static final String REG_ENDPOINT = "/user";

    private static final String[] PUBLIC_URLS = {
            "/v2/api-docs",
            "/swagger-ui/index.html",
            "/swagger-resources/**",
            "configuration/**",
            "webjars/**",
            "/*.html",
            "/**/*.html",
            "/**/*.css",
            "/**/*.js"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                .and()
                .authorizeRequests()
                .antMatchers(LOGIN_ENDPOINT).permitAll()
                .antMatchers(REG_ENDPOINT).permitAll()
                .antMatchers(ADMIN_ENDPOINT).hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, PUBLIC_URLS).permitAll()
                .antMatchers("/db/**").permitAll()
                .antMatchers("/swagger-ui/**", "/javainuse-openapi/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        http
                .headers().frameOptions().sameOrigin();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("lisitsaaa")
                .password(passwordEncoder().encode("ADMIN"))
                .authorities("ADMIN");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}