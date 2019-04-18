package secure.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import secure.filter.ValidateCodeFilter;
import secure.handler.SelfAAuthFailureHandler;
import secure.handler.SelfAuthSucessHandler;
import secure.service.ReaderSerivce;

/**
 * Desciption
 *
 * @author Claire.Chen
 * @create_time 2019 -03 - 28 9:20
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private ValidateCodeFilter validateCodeFilter;
    @Autowired
    private SelfAAuthFailureHandler authenticationFailureHandler;
    @Autowired
    private SelfAuthSucessHandler authenticationSucessHandler;

    @Bean
    UserDetailsService customeUserService() {
        return new ReaderSerivce();
    }

    /**
     * 以下为了支持明文方便测试，使用一个过期的NoOpPasswordEncoder.getInstance()
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customeUserService())
                .passwordEncoder(NoOpPasswordEncoder.getInstance());
        //passwordEncoder(new BCryptPasswordEncoder())
    }


    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class) // 添加验证码校验过滤器
                .formLogin() // 表单登录
                // http.httpBasic() // HTTP Basic
                .loginPage("/auth") // 登录跳转// URL
                .loginProcessingUrl("/login") // 处理表单登录 URL
                .successHandler(authenticationSucessHandler) // 处理登录成功
                .failureHandler(authenticationFailureHandler) // 处理登录失败
                .and()
                .authorizeRequests() // 授权配置
                .antMatchers("/auth",
                        "/login.html",
                        "/code/image",
                        "/css/**").permitAll() // 无需认证的请求路径
                .anyRequest()  // 所有请求
                .authenticated() // 都需要认证
                .and().csrf().disable();
    }



}
