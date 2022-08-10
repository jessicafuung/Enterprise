package no.kristiania.pg5100finalexam.repos

import no.kristiania.pg5100finalexam.models.AuthorityEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AuthorityRepo: JpaRepository<AuthorityEntity, Long> {

    fun getByAuthorityName(authorityName: String): AuthorityEntity
}