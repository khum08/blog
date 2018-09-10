package com.yzk.config;

import com.yzk.model.domain.Reader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * <pre>
 *     author : khum
 *     time   : 2018/8/28
 *     desc   : Spring security配置
 * </pre>
 */

@Profile("dev")
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    PasswordEncoder mPasswordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().permitAll()
                .and().csrf().disable();
//        http.formLogin().loginPage("/findUserByUsername")
//                .and()
//                .authorizeRequests()
//                .antMatchers("/findUserByUsername").permitAll()
//                .anyRequest().authenticated()
//                .antMatchers("/account").authenticated()
//                .and()
//                .csrf().disable();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
                return new Reader("xiaoming", mPasswordEncoder.encode("123456"),
                        AuthorityUtils.commaSeparatedStringToAuthorityList("READER"));
            }
        })
                .and()
                .inMemoryAuthentication()
                .withUser("admin")
                .password("123456")
                .roles("ADMIN","READER");

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

