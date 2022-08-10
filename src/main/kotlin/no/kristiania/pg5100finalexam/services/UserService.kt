package no.kristiania.pg5100finalexam.services

import no.kristiania.pg5100finalexam.controllers.NewUserInfo
import no.kristiania.pg5100finalexam.models.AuthorityEntity
import no.kristiania.pg5100finalexam.models.UserEntity
import no.kristiania.pg5100finalexam.repos.AuthorityRepo
import no.kristiania.pg5100finalexam.repos.UserRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    @Autowired private val userRepo: UserRepo,
    @Autowired private val authorityRepo: AuthorityRepo
    ): UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {
        username?.let {
            val user = userRepo.findByEmail(it)
            return User(user?.email, user?.password, user?.authorities?.map { SimpleGrantedAuthority(it.authorityName) })
        }
        throw Exception("Wooopsi ")
    }

    // Retrieve all authorities
    fun getAuthorities(): List<AuthorityEntity> {
        return authorityRepo.findAll()
    }

    // Retrieve all users
    fun getUsers(): List<UserEntity> {
        return userRepo.findAll()
    }

    // Create a new user
    fun registerUser(newUserInfo: NewUserInfo): UserEntity {
        val newUser = UserEntity(email = newUserInfo.email, password = BCryptPasswordEncoder().encode(newUserInfo.password))

        // look up the authority before adding adding it
        newUser.authorities.add(getAuthority("USER"))

        return userRepo.save(newUser)
    }

    // Does this authority name exist?
    fun getAuthority(name: String): AuthorityEntity {
        return authorityRepo.getByAuthorityName(name)
    }
}