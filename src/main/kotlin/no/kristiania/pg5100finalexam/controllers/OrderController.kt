package no.kristiania.pg5100finalexam.controllers

import no.kristiania.pg5100finalexam.models.CustomerEntity
import no.kristiania.pg5100finalexam.models.ItemEntity
import no.kristiania.pg5100finalexam.models.OrderEntity
import no.kristiania.pg5100finalexam.services.CustomerService
import no.kristiania.pg5100finalexam.services.OrderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.lang.RuntimeException
import java.net.URI
import java.security.InvalidParameterException

data class NewDeliveryInfo(
    val email: String,
    val address: String,
    val phone: String,
    val deliveryDate: String,
    val items: MutableList<ItemEntity>
)

@RestController
@RequestMapping("/api")
class OrderController(
    @Autowired private val orderService: OrderService,
    @Autowired private val customerService: CustomerService
    ) {

    // Retrieve all deliveries
    @GetMapping("/delivery/all")
    fun getDeliveries(): ResponseEntity<List<OrderEntity>> {
        return ResponseEntity.ok().body(orderService.getOrders())
    }

    // Retrieve a single delivery by order ID
    @GetMapping("/delivery/get/{orderId}")
    fun getDeliveryByOrderId(@PathVariable orderId: Long?): ResponseEntity<OrderEntity?> {
        orderId?.let {
            orderService.getOrderById(it).let { order ->
                return ResponseEntity.ok().body(order)
            }
        }
        throw OrderNotFound()
    }

    // Create a new delivery
    @PostMapping("/delivery/register")
    fun registerDelivery(@RequestBody newDeliveryInfo: NewDeliveryInfo): ResponseEntity<OrderEntity> {
        val createdDelivery = orderService.registerOrder(newDeliveryInfo)
        val uri = URI.create(
            ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/delivery/register").toUriString()
        )
        return ResponseEntity.created(uri).body(createdDelivery)
    }

    // Update a customer
    @PutMapping("/delivery/update/{customerId}")
    fun updateCostumer(@PathVariable customerId: Long?, @RequestBody customer: CustomerEntity?): ResponseEntity<CustomerEntity?> {
        when {
            customerId == null -> throw InvalidParameterException()
            customer == null -> throw InvalidParameterException()
            else -> {
                customerService.updateCustomer(customerId, customer)?.let {
                    return ResponseEntity.ok().body(it)
                }
            }
        }
        throw CustomerNotFound()
    }

    // Delete a delivery by order ID
    @DeleteMapping("/delivery/delete/{orderId}")
    fun deleteDelivery(@PathVariable orderId: Long?) {
        orderId?.let {
            if (!orderService.deleteOrder(it)) throw OrderNotFound()
        }
    }
}

// Custom response status

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Order not found")
class OrderNotFound: RuntimeException()

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Customer not found")
class CustomerNotFound: RuntimeException()

