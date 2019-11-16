package vn.edu.topica.edumall.api.lms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import vn.edu.topica.edumall.security.core.UnAuthorizedEntryPoint;
import vn.edu.topica.edumall.security.core.filter.TokenRequestFilter;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private TokenRequestFilter tokenRequestFilter;

    @Autowired
    private UnAuthorizedEntryPoint unauthorizedHandler;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable().logout().disable().formLogin().disable().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().anonymous().and().exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler).and()
                .addFilterBefore(tokenRequestFilter, BasicAuthenticationFilter.class).antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/files/*/download").permitAll()
                .antMatchers("/user/kelley").permitAll()
                .antMatchers("/teacher/revenue").permitAll()
                .antMatchers("/course-version/process-after-tool-validate").permitAll()
                .antMatchers("/course-version/update-approve-result").permitAll()
                .antMatchers("/course-version/send-email-to-teacher").permitAll()
                .antMatchers("/courses/create-full-course").permitAll()
                .antMatchers("/course-version/disappear-course-from-kelley").permitAll()
                .anyRequest().authenticated();
        httpSecurity.cors();
    }

//	@Override
//	protected void configure(HttpSecurity httpSecurity) throws Exception {
//		httpSecurity.csrf().disable().authorizeRequests().antMatchers("/**").permitAll().anyRequest().authenticated()
//				.and().exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and().sessionManagement()
//				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//	}
}
