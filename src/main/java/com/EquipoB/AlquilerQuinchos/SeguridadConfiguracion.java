package com.EquipoB.AlquilerQuinchos;

import com.EquipoB.AlquilerQuinchos.Servicios.ServicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SeguridadConfiguracion extends WebSecurityConfigurerAdapter {


    @Autowired
    public ServicioUsuario usuarioServicio;



    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usuarioServicio)
            .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/admin/*").hasRole("ADMIN")
                .antMatchers("/css/*", "/js/*", "/img/*", "/**").permitAll()
                .antMatchers("/inicio", "/403.html").permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/logincheck")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/inicio")
                .failureUrl("/usuario/login?error=true")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/inicio")
                .permitAll()
                .and()
                .csrf()
                .disable();
    }


}










//@Configuration
//public class SeguridadConfiguracion {

  //  @Autowired
    //private ServicioUsuario usuarioServicio;

    //@Bean
    //public PasswordEncoder passwordEncoder() {
     //   return new BCryptPasswordEncoder();
    //}

    //@Bean
    //public UserDetailsService userDetailsService() {
      //  return usuarioServicio;
    //}

   // @Bean
    //public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
      //  http
        //    .authorizeRequests(authorizeRequests ->
          //      authorizeRequests
            //        .antMatchers("/admin/*").hasRole("ADMIN")
              //      .antMatchers("/css/**", "/js/**", "/img/**", "/**").permitAll()
            //)
            //.formLogin(loginConfigurer -> {
              //  loginConfigurer
                //    .loginPage("/login")
                  //  .loginProcessingUrl("/logincheck")
                    //.usernameParameter("email")
                    //.passwordParameter("password")
                    //.defaultSuccessUrl("/inicio")
                    //.permitAll();
            //})
            //.logout(logoutConfigurer -> {
              //  logoutConfigurer
                //    .logoutUrl("/logout")
                  //  .logoutSuccessUrl("/inicio")
                    //.permitAll();
            //})
            //.csrf()
            //.disable();

        //return http.build();
    //}
//}









