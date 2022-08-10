package no.kristiania.pg5100finalexam.security

import no.kristiania.pg5100finalexam.security.filter.CustomAuthenticationFilter
import no.kristiania.pg5100finalexam.security.filter.CustomAuthorizationFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@EnableWebSecurity
@Configuration
class SecurityConfig(
    @Autowired private val userDetailsService: UserDetailsService,
    @Autowired private val passwordEncoder: BCryptPasswordEncoder
    ): WebSecurityConfigurerAdapter() {

    // Compare user from request with database
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder)
    }

    override fun configure(http: HttpSecurity) {
        val authFilter = CustomAuthenticationFilter(authenticationManagerBean())
        authFilter.setFilterProcessesUrl("/api/authentication")
        http.csrf().disable()
        http.sessionManagement().disable()  // auth every request
        http.authorizeRequests()
            .antMatchers("/api/authentication").permitAll()
            .antMatchers("/api/delivery/all").hasAnyAuthority("USER", "ADMIN")
            .antMatchers("/api/delivery/get/**").hasAnyAuthority("USER", "ADMIN")
            .antMatchers("/api/delivery/register").hasAnyAuthority("USER", "ADMIN")
            .antMatchers("/api/delivery/update/**}").hasAnyAuthority("USER", "ADMIN")
            .antMatchers("/api/authority/all").hasAuthority("ADMIN")
            .antMatchers("/api/users/all").hasAuthority("ADMIN")
            .antMatchers("/api/delivery/delete").hasAuthority("ADMIN")
        http.authorizeRequests().anyRequest().authenticated()
        http.addFilter(authFilter)
        http.addFilterBefore(CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter::class.java)
    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }
}