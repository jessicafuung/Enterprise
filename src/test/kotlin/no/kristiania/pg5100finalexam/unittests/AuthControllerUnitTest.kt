package no.kristiania.pg5100finalexam.unittests

import io.mockk.every
import io.mockk.mockk
import no.kristiania.pg5100finalexam.models.UserEntity
import no.kristiania.pg5100finalexam.services.OrderService
import no.kristiania.pg5100finalexam.services.UserService
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@WebMvcTest
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerUnitTest {

    @TestConfiguration
    class ControllerTestConfig {
        @Bean
        fun userService() = mockk<UserService>()
    }

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun shouldGetUsers() {
        val userJess = UserEntity(email = "jess@user.com", password = "pizza")
        every { userService.getUsers() } answers {
            mutableListOf(userJess)
        }
        mockMvc.get("/users/all"){

        }
            .andExpect { status { isOk() } }
    }
}