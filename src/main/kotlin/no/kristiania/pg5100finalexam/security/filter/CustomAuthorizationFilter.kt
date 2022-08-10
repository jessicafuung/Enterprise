package no.kristiania.pg5100finalexam.security.filter

import no.kristiania.pg5100finalexam.security.jwt.JwtUtil
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CustomAuthorizationFilter: OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = request.cookies?.firstOrNull { it.name == "access_token" }?.value
        when {
            // no token
            token.isNullOrBlank() -> filterChain.doFilter(request, response)
            request.servletPath.contains("/api/authentication") -> filterChain.doFilter(request, response)
            else -> {
                // bingo, let's decode
                val decodedJWT = JwtUtil.decodeToken(token)
                val email = decodedJWT.subject
                val authority = decodedJWT
                    .getClaim("authorities")
                    .asList(String::class.java)
                    .map { SimpleGrantedAuthority(it) }
                val authenticationToken = UsernamePasswordAuthenticationToken(email, null, authority)
                SecurityContextHolder.getContext().authentication = authenticationToken
                filterChain.doFilter(request, response)
            }
        }
    }
}