package com.lining.spring.security.learning.config;

import com.lining.spring.security.learning.service.MyAutheticationProvider;
import com.lining.spring.security.learning.service.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.Collections;

/**
 * 使用类定义的方式自定义用户名和密码
 */
@Configuration
@EnableWebSecurity
public class MyConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource ;
    @Autowired
    private MyUserDetailService userDetailService ;
    @Autowired
    private MyAutheticationProvider myAutheticationProvider ;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        super.configure(auth);
       /* auth.inMemoryAuthentication().withUser("root").password(new BCryptPasswordEncoder().encode("root")).roles("admin")
                .and().withUser("user").password(new BCryptPasswordEncoder().encode("123456")).roles("people");*/

        /*JdbcUserDetailsManager manager = auth.jdbcAuthentication().dataSource(dataSource).getUserDetailsService();

        manager.createUser(User.withUsername("lining").password(new BCryptPasswordEncoder().encode("lining")).roles("admin","super_admin").build());*/

        // 自定义userDetailService实现自己的登录逻辑
//       auth.userDetailsService(userDetailService);
        auth.authenticationProvider(myAutheticationProvider);
    }
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 默认开启了csrf 所以需要在登录页面上将token携带在表单上，以通过csrf认证
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);

        http
                //请求登录验证
                .authorizeRequests()
                .antMatchers("/img1/**").permitAll() //忽略某些请求（比如静态请求）
                //任何请求都需要验证
//                .anyRequest().authenticated()
                .and()
                //自定义登录页
                .formLogin().loginPage("/login.html")
                //自定义登录表单提交路径
                .loginProcessingUrl("/login")
                //自定义登录表单参数名
                .usernameParameter("username")
                .passwordParameter("password")
                //登录成功默认跳转页
                .defaultSuccessUrl("/hello/hi")
                //失败页面
                .failureForwardUrl("/login.html")
                //所有用户都能进入
                .permitAll()
                //失败处理器
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                        e.printStackTrace();
                    }
                })
                .and()
                .sessionManagement()
                //设置最大用户最大登录次数，限制单点登录
                .maximumSessions(1)
                //已经有用户登录的情况下，不允许重复登录
                .maxSessionsPreventsLogin(true)
                .and()
                .and()
                //默认情况下所有的post请求都会被拦截，进行csrf()认证
                .csrf().csrfTokenRepository(new HttpSessionCsrfTokenRepository());
    }

//    @Bean
   /* public UserDetailsService userDetailsService(){
        // 存储再内存中

        PasswordEncoder encoder = new BCryptPasswordEncoder();

        *//*InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();


        User user = new User("root",encoder.encode("root"),true,true,true,true, Collections.singletonList(new SimpleGrantedAuthority("admin")));

        manager.createUser(user);

        manager.createUser(User.withUsername("guest").password(encoder.encode("guest")).roles("guest").build());

        return manager ;*//*

        // 基于jdbc的用户存储
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);

        User user = new User("root",encoder.encode("root"),true,true,true,true, Collections.singletonList(new SimpleGrantedAuthority("admin")));

        manager.createUser(user);

        manager.createUser(User.withUsername("guest").password(encoder.encode("guest")).roles("guest").build());

        return manager;
    }*/


    @Override
    public void configure(WebSecurity web) throws Exception {
//        super.configure(web);

        //忽略某些请求不做校验
        web.ignoring().antMatchers("/img/**");
    }


    @Bean
    HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }
}
