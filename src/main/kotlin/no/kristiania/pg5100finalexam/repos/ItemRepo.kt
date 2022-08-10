package no.kristiania.pg5100finalexam.repos

import no.kristiania.pg5100finalexam.models.ItemEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ItemRepo: JpaRepository<ItemEntity, Long>