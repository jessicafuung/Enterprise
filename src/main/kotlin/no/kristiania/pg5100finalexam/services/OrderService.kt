package no.kristiania.pg5100finalexam.services

import no.kristiania.pg5100finalexam.controllers.NewDeliveryInfo
import no.kristiania.pg5100finalexam.models.CustomerEntity
import no.kristiania.pg5100finalexam.models.ItemEntity
import no.kristiania.pg5100finalexam.models.OrderEntity
import no.kristiania.pg5100finalexam.repos.CustomerRepo
import no.kristiania.pg5100finalexam.repos.ItemRepo
import no.kristiania.pg5100finalexam.repos.OrderRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class OrderService(
    @Autowired private val orderRepo: OrderRepo,
    @Autowired private val customerRepo: CustomerRepo
    ) {

    // Retrieve all deliveries
    fun getOrders(): List<OrderEntity> {
        return orderRepo.findAll()
    }

    // Retrieve a single delivery by order ID
    fun getOrderById(id: Long): OrderEntity? {
        return orderRepo.findById(id).orElse(null)
    }

    // Create a new delivery
    fun registerOrder(newDeliveryInfo: NewDeliveryInfo): OrderEntity {
        val customer = registerCustomer(newDeliveryInfo)
        val newOrder = OrderEntity(orderDelivery = newDeliveryInfo.deliveryDate, customerId = customer.id?.let {
            customerRepo.getReferenceById(
                it
            )
        })
        return orderRepo.save(newOrder)
    }

    // Register customer
    fun registerCustomer(newDeliveryInfo: NewDeliveryInfo): CustomerEntity {
        val customer = CustomerEntity(email = newDeliveryInfo.email, address = newDeliveryInfo.address, phone = newDeliveryInfo.phone)
        return customerRepo.save(customer)
    }

    // Delete a delivery (include customer) by order ID
    fun deleteOrder(orderId: Long): Boolean {
        if (orderRepo.existsById(orderId)) {
            // Dunno if necessary to delete the customer too
            val customerId = orderRepo.findById(orderId).get().customerId
            customerRepo.deleteById(customerId)
            orderRepo.deleteById(orderId)
            return true
        }
        return false
    }
}