package com.dhmusic.DHMusic.Security;


import com.dhmusic.DHMusic.entities.account.entities.AccountStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.Filter;

//@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity ( // https://docs.spring.io/spring-security/site/docs/3.0.x/reference/el-access.html
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    @Bean // metti il risultato di questo metodo dentro ad un bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    protected void configure(HttpSecurity http) throws  Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests()

                .antMatchers("/auth/**").permitAll()
                .antMatchers("/h2-console/**").permitAll() //da eliminare in produzione
                .antMatchers("/admin/**").hasAnyRole("ROLE_"+ AccountStatus.Admin)
                //.antMatchers("/app/**").hasAnyRole("ROLE_"+ Roles.ADMIN, "ROLE_"+ Roles.REGISTERED)
                /*.antMatchers("/blog/**").hasRole("ROLE:EDITOR")
                .antMatchers("/dev-tools/**").hasAnyAuthority("DEV_READ", "DEV_DELETE")
                .antMatchers("/dev-tools/-bis/**").hasAuthority("DO_DEV_TOOLS_READ")*/
                .anyRequest().authenticated();

        http.csrf().disable();// sistema di sicurezza
        http.headers().frameOptions().disable();// sistema di sicurezza per frame

        // add JWT token filter
        http.addFilterBefore(
                (Filter) jwtTokenFilter,
                UsernamePasswordAuthenticationFilter.class
        );
    }
}


