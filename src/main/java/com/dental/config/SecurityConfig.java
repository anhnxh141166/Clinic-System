package com.dental.config;


import com.dental.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return new UserDetailsServiceImpl();
    }

    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        //set UserDetailsServiceImpl
        authenticationProvider.setUserDetailsService(userDetailsService());
        //set BCryptPasswordEncoder
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return authenticationProvider;
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }







    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http.csrf().disable()
                .authorizeHttpRequests()
//                    .requestMatchers("/", "assets/**", "/user/**").permitAll() // Allowing access to home page and static assets without authentication
                    .requestMatchers("/","/user/register/**", "assets/**", "/user/homeLanding").permitAll() // Allowing access to home page and static assets without authentication
                    .requestMatchers("/user/checkEmailExists").permitAll() // Allowing access to home page and static assets without authentication
//                    .requestMatchers("/register/**").permitAll() // Allowing access to home page and static assets without authentication
//                    .requestMatchers("/user/homeLanding").permitAll() // Allowing access to /user/homeLanding without authentication
                    .requestMatchers("/admin/**", "/admin/blog/blogs").hasAuthority("ADMIN") // Require ADMIN authority for admin pages
//                    .anyRequest().authenticated() // Require authentication for other URLs
//                .requestMatchers("/doLogout").authenticated()
                .requestMatchers("/*").authenticated()
//                .requestMatchers("/*").permitAll()
                .anyRequest().authenticated() // Require authentication for other URLs
                .and()
                    .formLogin()
                    .loginPage("/login")
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .loginProcessingUrl("/login")
                    .defaultSuccessUrl("/redirect", true)
                    .failureUrl("/login?success=fail")
                    .permitAll()
                .and()
                    .logout()
                    .logoutUrl("/doLogout")
                    .logoutSuccessUrl("/user/homeLanding")
//                    .deleteCookies("JSESSIONID")
//                    .invalidateHttpSession(true)
                    .permitAll()
                .and()
                    .exceptionHandling()
                    .accessDeniedHandler(accessDeniedHandler())
                .and()
                .build();
    }


}
