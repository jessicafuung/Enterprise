package no.kristiania.pg5100finalexam.services

import no.kristiania.pg5100finalexam.models.CustomerEntity
import no.kristiania.pg5100finalexam.repos.CustomerRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CustomerService(@Autowired private val customerRepo: CustomerRepo) {

    // Update a customer by ID
    fun updateCustomer(customerId: Long, customer: CustomerEntity): CustomerEntity? {
        if(customerRepo.existsById(customerId)){
            return customerRepo.save(customer)
        }
        return null
    }
}