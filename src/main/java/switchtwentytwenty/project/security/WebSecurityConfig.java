package switchtwentytwenty.project.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import switchtwentytwenty.project.security.jwt.AuthEntryPointJwt;
import switchtwentytwenty.project.security.jwt.AuthTokenFilter;
import switchtwentytwenty.project.security.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        // securedEnabled = true,
        // jsr250Enabled = true,
        prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests().antMatchers("/api/auth/**").permitAll()
                .antMatchers("/api/test/**").permitAll()
                .antMatchers("/members/**").permitAll()
                .antMatchers(HttpMethod.POST,"/families").access("hasRole('ROLE_SYSTEM_MANAGER')")
                .antMatchers("/families/**").access("hasRole('ROLE_FAMILY_ADMIN')")
                .antMatchers(HttpMethod.PUT,"/families/**").access("hasRole('ROLE_FAMILY_ADMIN')")
                .antMatchers(HttpMethod.GET,"/families/**").access("hasRole('ROLE_FAMILY_ADMIN')")
                .antMatchers(HttpMethod.GET,"/families/**").access("hasRole('ROLE_FAMILY_MEMBER')")
                .antMatchers(HttpMethod.GET,"/accounts/**").access("hasRole('ROLE_FAMILY_MEMBER')")
                .antMatchers("/accounts/**").permitAll()
                .antMatchers("/families/**").permitAll()
                .antMatchers("/transactions/**").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers(HttpMethod.POST, "/categories").access("hasRole('ROLE_SYSTEM_MANAGER')")
                .antMatchers(HttpMethod.GET, "/categories/standard/all").access("hasRole('ROLE_SYSTEM_MANAGER')")
                .antMatchers("/categories/**").permitAll()
                .anyRequest().authenticated()
                .and().headers().frameOptions().sameOrigin();


        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
