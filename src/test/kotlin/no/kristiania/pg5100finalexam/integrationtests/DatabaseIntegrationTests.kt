package no.kristiania.pg5100finalexam.integrationtests

import no.kristiania.pg5100finalexam.controllers.NewUserInfo
import no.kristiania.pg5100finalexam.services.UserService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Import(UserService::class)
class DatabaseIntegrationTests(@Autowired private val userService: UserService) {

    @Test
    fun shouldGetUsers() {
        val result = userService.getUsers()
        assert(result.size == 2)
    }

    @Test
    fun registerAndFindUser() {
        val newUserInfo = NewUserInfo(email = "jess@user.com", password = "omg")
        val createdUser = userService.registerUser(newUserInfo)
        assert(createdUser.email == "jess@user.com")

        val foundUser = userService.loadUserByUsername("jess@user.com")
        assert(foundUser.username == createdUser.email)
    }

    @Test
    fun createUserThenGetAuthorityAndCompareWithUser() {
        val newUserInfo = NewUserInfo(email = "noodle@user.com", password = "soup")
        val createdUser = userService.registerUser(newUserInfo)
        assert(createdUser.email == "noodle@user.com")

        val authority = userService.getAuthority("USER")
        assert(createdUser.authorities.first().equals(authority))
    }
}