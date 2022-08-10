package no.kristiania.pg5100finalexam.models

import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "orders")
class OrderEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_order_id_seq")
    @SequenceGenerator(
        name = "orders_order_id_seq",
        allocationSize = 1
    )
    @Column(name = "order_id")
    val id: Long? = null,

    @Column(name = "order_date")
    val orderDate: LocalDateTime? = LocalDateTime.now(),

    @Column(name = "order_delivery")
    val orderDelivery: String?,

    @ManyToMany
    @JsonIgnore
    @JoinTable(
        name = "orders_items",
        joinColumns = [JoinColumn(name = "item_id")],
        inverseJoinColumns = [JoinColumn(name = "order_id")]
    )
    val items: MutableList<ItemEntity> = mutableListOf(),

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    val customerId: CustomerEntity? = null
) {
    override fun toString(): String {
        return "OrderEntity(id=$id, orderDate=$orderDate, orderDelivery=$orderDelivery, items=$items, customerId=$customerId)"
    }
}