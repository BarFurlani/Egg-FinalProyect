package com.EquipoB.AlquilerQuinchos;

import com.EquipoB.AlquilerQuinchos.Servicios.ServicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SeguridadConfiguracion {

    @Autowired
    private ServicioUsuario usuarioServicio;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return usuarioServicio;
    }

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests(authorizeRequests ->
                authorizeRequests
                    .antMatchers("/admin/*").hasRole("ADMIN")
                    .antMatchers("/css/**", "/js/**", "/img/**", "/**").permitAll()
            )
            .formLogin(loginConfigurer -> {
                loginConfigurer
                    .loginPage("/login")
                    .loginProcessingUrl("/logincheck")
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/inicio")
                    .permitAll();
            })
            .logout(logoutConfigurer -> {
                logoutConfigurer
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/inicio")
                    .permitAll();
            })
            .csrf()
            .disable();

        return http.build();
    }
}









