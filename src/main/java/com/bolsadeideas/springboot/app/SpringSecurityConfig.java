package com.bolsadeideas.springboot.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    // Seguridad HTTP -> Secure
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/", "/css/**","/js/**","/images/**", "/listar").permitAll()
                .antMatchers("/ver/**").hasAnyRole("USER")
                .antMatchers("/uploads/**").hasAnyRole("USER")
                .antMatchers("/form/**").hasAnyRole("ADMIN")
                .antMatchers("/eliminar/**").hasAnyRole("ADMIN")
                .antMatchers("/factura/**").hasAnyRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                        .formLogin().loginPage("/login")
                        .permitAll()
                .and()
                .logout().permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/error_403");
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // Configuracion Global del login Es necesario en BCrypt que es lo que se usa actualmente
    // TODO: revisar que metodos de encriptación son mejores para spring Security
    @Autowired
    public void configurerGlobal( AuthenticationManagerBuilder builder ) throws Exception{

        PasswordEncoder enconder = passwordEncoder();
        UserBuilder users = User.builder().passwordEncoder(  enconder::encode  );

        builder.inMemoryAuthentication()
                .withUser( users.username("admin").password("12345").roles("ADMIN","USER") )
                .withUser( users.username("daniel").password("12345").roles("USER") );
    }



}
