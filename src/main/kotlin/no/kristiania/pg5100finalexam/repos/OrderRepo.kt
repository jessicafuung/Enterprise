package no.kristiania.pg5100finalexam.repos

import no.kristiania.pg5100finalexam.models.OrderEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepo: JpaRepository<OrderEntity, Long>