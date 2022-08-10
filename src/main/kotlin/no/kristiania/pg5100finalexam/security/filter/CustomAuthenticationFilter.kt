package no.kristiania.pg5100finalexam.security.filter

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import no.kristiania.pg5100finalexam.security.jwt.JwtUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.util.stream.Collectors
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import javax.servlet.FilterChain
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

data class LoginInfo(val username: String, val password: String)

class CustomAuthenticationFilter(
    @Autowired private val authManager: AuthenticationManager
    ): UsernamePasswordAuthenticationFilter() {

    // Handle username and password in the body/ payload of a POST request
    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        val body = request.reader.lines().collect(Collectors.joining())
        val userRequest = jacksonObjectMapper().readValue(body, LoginInfo::class.java)
        val auth = UsernamePasswordAuthenticationToken(userRequest.username, userRequest.password)
        return authManager.authenticate(auth)   // authenticate
    }

    // When user auth ok, create a jwt token and put in to cookie
    override fun successfulAuthentication(
        request: HttpServletRequest?,
        response: HttpServletResponse,
        chain: FilterChain?,
        authResult: Authentication
    ) {
        val token = JwtUtil.createToken(authResult.principal as User, request?.requestURI.toString())
        val cookie = Cookie("access_token", token)
        response.contentType = APPLICATION_JSON_VALUE
        response.addCookie(cookie)
    }
}
