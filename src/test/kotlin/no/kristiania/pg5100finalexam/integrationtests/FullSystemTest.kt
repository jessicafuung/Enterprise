package no.kristiania.pg5100finalexam.integrationtests

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@AutoConfigureMockMvc()
class FullSystemTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun shouldGetAllAuthoritiesIntegrationTest(){

        val loggedInUser = mockMvc.post("/api/authentication") {
            contentType = APPLICATION_JSON
            content = "{\n" +
                    "      \"username\":\"admin@admin.com\",\n" +
                    "      \"password\":\"pirate\"\n" +
                    "    }"
        }

            .andExpect { status { isOk() } }
            .andReturn()

        val theCookie = loggedInUser.response.getCookie("access_token")
        mockMvc.get("/api/users/all") {
            theCookie?.let { cookie(it) }
        }

            .andExpect { status { isOk() } }
            .andExpect { content { contentType(APPLICATION_JSON) } }
            .andExpect { jsonPath("$") {isArray() } }

    }
}