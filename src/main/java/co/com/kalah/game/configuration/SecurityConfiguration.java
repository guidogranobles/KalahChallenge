package co.com.kalah.game.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import co.com.kalah.game.component.LogoutHandler;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private LogoutHandler logoutHandler;

	@Autowired
	private DataSource dataSource;

	@Value("${spring.queries.users-query}")
	private String usersQuery;

	@Value("${spring.queries.roles-query}")
	private String rolesQuery;


	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().usersByUsernameQuery(usersQuery).authoritiesByUsernameQuery(rolesQuery)
				.dataSource(dataSource).passwordEncoder(bCryptPasswordEncoder);
		
			
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers("/").permitAll().antMatchers("/login").permitAll()
				.antMatchers("/registration").permitAll().antMatchers("/admin/**").hasAuthority("ADMIN")
				.antMatchers("/game/**").hasAuthority("PLAYER").anyRequest().authenticated().and().csrf().disable()
				.formLogin().loginPage("/login").failureUrl("/login?error=true").defaultSuccessUrl("/game/dashboard")
				.usernameParameter("username").passwordParameter("password").and().logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessHandler(logoutHandler).and()
				.exceptionHandling().accessDeniedPage("/access-denied");
		
		http.sessionManagement()
		  .invalidSessionUrl("/login");
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**", "/webjars/**");
	}

}