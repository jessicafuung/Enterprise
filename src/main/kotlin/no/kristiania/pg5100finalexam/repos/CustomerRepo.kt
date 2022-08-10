package no.kristiania.pg5100finalexam.repos

import no.kristiania.pg5100finalexam.models.CustomerEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepo: JpaRepository<CustomerEntity, Long> {

    override fun findAll():List<CustomerEntity>

    fun deleteById(customerId: CustomerEntity?)
}