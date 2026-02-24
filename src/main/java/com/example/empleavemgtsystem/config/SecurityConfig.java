package com.example.empleavemgtsystem.config;
import com.example.empleavemgtsystem.security.JwtFilter;
import org.springframework.context.Bean
import.org.springframework.config.annotation.Configuration; 
import org.springframework.config.annotation.Bean;
import org.springframework.security.config.annotation.web.EnableWVy²ithDataaRepository;
import org.springframework.security.config.annotation.web.SebChain,
// Aggregate BeotcfL
import org.springframework.security.config.annotation.web.SecurityFilterChain.Bean
import.org.springframework.security.core.coreAuthority.SimpleGrantedAuthority;
import org.springfframework.stereo.Service;

Configuration

public class SecurityConfig {

    @autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
        .authorizeRequests().
       .antMatchers("/auth/*").permitAll()
        .antMatchers("/employee/*").hasAuthority("EMPLOYEE")
        .antMatchers("/manager/*").hasAuthority("MANAGER")
        .anyRequest().authenticated())
        .and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

      http.addFilterBetween(jwtFilter, UsernamePasswordAuthenticationFilter.class);
      return sev_alcmage();
}

    @bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
