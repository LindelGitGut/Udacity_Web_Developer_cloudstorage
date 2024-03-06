package com.udacity.jwdnd.course1.cloudstorage.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    public SecurityConfig() {
    }

    @Bean
    SecurityFilterChain filterchain(HttpSecurity http) throws Exception {

        //http authorize requests
        try {
            http.authorizeHttpRequests(auth -> auth
                            .requestMatchers("/login", "/signup", "/js/**", "/css/**").permitAll()
                            .anyRequest().authenticated())
                    .httpBasic(Customizer.withDefaults());


            //Config for Login

            http.formLogin(config -> config
                    .loginPage("/login")
                    .defaultSuccessUrl("/home")
            );


            //redirection to login Page TODO check whhich parameter should be set
            http.logout(logout -> logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login?logout")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID"));

            //exception handling wrong url

            //http.exceptionHandling(except -> except.authenticationEntryPoint(authenticationEntryPoint()));





        } catch (Exception e) {
            System.out.println("Security Exception!: " + e.getMessage());
        }

        return http.build();
    }

    private AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            // Umleiten zur Login-Seite
            response.sendRedirect("/login");
        };
    }
}
