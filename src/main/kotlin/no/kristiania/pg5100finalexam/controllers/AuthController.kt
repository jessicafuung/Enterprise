package no.kristiania.pg5100finalexam.controllers

import no.kristiania.pg5100finalexam.models.AuthorityEntity
import no.kristiania.pg5100finalexam.models.UserEntity
import no.kristiania.pg5100finalexam.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

data class NewUserInfo(val email: String, val password: String)

@RestController
@RequestMapping("/api")
class AuthController (@Autowired private val userService: UserService){

    // Retrieve all authorities
    @GetMapping("/authority/all")
    fun getAuthorities(): ResponseEntity<List<AuthorityEntity>> {
        return ResponseEntity.ok().body(userService.getAuthorities())
    }

    // Retrieve all users
    @GetMapping("/users/all")
    fun getUsers(): ResponseEntity<List<UserEntity>> {
        return ResponseEntity.ok().body(userService.getUsers())
    }

    // Create new user (data class)
    @PostMapping("/user")
    fun registerUser(@RequestBody newUserInfo: NewUserInfo): ResponseEntity<UserEntity> {
        val createdUser = userService.registerUser(newUserInfo)
        val uri = URI.create(
            ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/register/authority").toUriString()
        )
        return ResponseEntity.created(uri).body(createdUser)
    }
}