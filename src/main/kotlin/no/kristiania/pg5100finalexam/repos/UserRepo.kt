package no.kristiania.pg5100finalexam.repos

import no.kristiania.pg5100finalexam.models.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepo: JpaRepository<UserEntity, Long> {

    fun findByEmail(email: String): UserEntity?
}