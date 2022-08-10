package no.kristiania.pg5100finalexam.unittests

import io.mockk.every
import io.mockk.mockk
import no.kristiania.pg5100finalexam.controllers.NewUserInfo
import no.kristiania.pg5100finalexam.models.AuthorityEntity
import no.kristiania.pg5100finalexam.models.UserEntity
import no.kristiania.pg5100finalexam.repos.AuthorityRepo
import no.kristiania.pg5100finalexam.repos.UserRepo
import no.kristiania.pg5100finalexam.services.UserService
import org.junit.jupiter.api.Test

class UserServiceUnitTest {

    private val userRepo = mockk<UserRepo>()
    private val authorityRepo = mockk<AuthorityRepo>()
    private val userService = UserService(userRepo, authorityRepo)

    @Test
    fun getAuthorityTest(){
        val userJess = UserEntity(email = "jess@user.com", password = "pizza")
        val userIca = UserEntity(email = "ica@user.com", password = "cake")
        every { userRepo.findAll() } answers {
            mutableListOf(userJess, userIca )
        }

        val users = userService.getUsers()
        assert(users.size == 2)
        assert(users.first { it.email == "jess@user.com" }.password == "pizza")
    }

    @Test
    fun shouldRegisterNewUser(){
        every { userRepo.save(any()) } answers {
            firstArg()
        }

        every { authorityRepo.getByAuthorityName(any()) } answers {
            AuthorityEntity(authorityName = "USER")
        }

        val createUser = userService.registerUser(NewUserInfo(email = "bigfeet@user.com", password = "hotchocolate"))
        assert(createUser.email == "bigfeet@user.com")
    }
}