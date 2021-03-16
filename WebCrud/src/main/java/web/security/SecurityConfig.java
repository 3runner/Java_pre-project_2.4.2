package web.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final LoginSuccessHandler successHandler;

    public SecurityConfig(UserDetailsService userDetailsService, LoginSuccessHandler successHandler) {
        this.userDetailsService = userDetailsService;
        this.successHandler = successHandler;
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userDetailsService);
        return authenticationProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/login")                                                   // указываем страницу с формой логина
                .loginProcessingUrl("/login")                                          // указываем action с формы логина
                .usernameParameter("j_username")                                       // Указываем параметры логина и пароля с формы логина
                .passwordParameter("j_password")                                       // даем доступ к форме логина всем
                .successHandler(successHandler)                                        // указываем логику обработки при логине
                .permitAll();

        http
                .authorizeRequests()                                                   // делаем страницу регистрации недоступной для авторизированных пользователей
                .antMatchers("/login").anonymous()                         //страницы аутентификаци доступна всем
                // защищенные URL
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/user").hasAnyRole("ADMIN", "USER")
                .antMatchers("/user/new").hasRole("ADMIN")
                .antMatchers("/user/*/edit").hasRole("ADMIN");

        http.logout()
                .permitAll()                                                           // разрешаем делать логаут всем
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))    // указываем URL логаута
                .logoutSuccessUrl("/login?logout")                                     // указываем URL при удачном логауте
                .and().csrf().disable();                                               //выключаем кроссдоменную секьюрность (на этапе обучения неважна)
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
