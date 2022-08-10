package no.kristiania.pg5100finalexam.unittests

import io.mockk.every
import io.mockk.mockk
import no.kristiania.pg5100finalexam.controllers.NewDeliveryInfo
import no.kristiania.pg5100finalexam.models.CustomerEntity
import no.kristiania.pg5100finalexam.models.ItemEntity
import no.kristiania.pg5100finalexam.models.OrderEntity
import no.kristiania.pg5100finalexam.repos.CustomerRepo
import no.kristiania.pg5100finalexam.repos.ItemRepo
import no.kristiania.pg5100finalexam.repos.OrderRepo
import no.kristiania.pg5100finalexam.services.OrderService
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class OrderServiceTest {

    private val orderRepo = mockk<OrderRepo>()
    private val customerRepo = mockk<CustomerRepo>()
    private val orderService = OrderService(orderRepo, customerRepo)

    @Test
    fun getOrdersTest() {
        val order1 = OrderEntity(id = 1, orderDate = LocalDateTime.now(), orderDelivery = "2022-09-05")
        val order2 = OrderEntity(id = 2, orderDate = LocalDateTime.now(), orderDelivery = "2022-12-24")

        every { orderRepo.findAll() } answers {
            mutableListOf(order1, order2)
        }

        val orders = orderService.getOrders()
        assert(orders.size == 2)
    }

    @Test
    fun createItemAndRegisterANewCustomer() {
        every { customerRepo.save(any()) } answers {
            firstArg()
        }

        val item1 = ItemEntity(1, "orange")
        val list = mutableListOf(item1)
        val registeredCostumer = orderService.registerCustomer(NewDeliveryInfo(email = "kebab@user.com", address = "sleepy street 12", phone = "82134", deliveryDate = "2022-10-09", items = list))

        assert(registeredCostumer.email == "kebab@user.com")
    }
}