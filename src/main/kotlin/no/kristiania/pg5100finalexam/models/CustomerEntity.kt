package no.kristiania.pg5100finalexam.models

import javax.persistence.*

@Entity
@Table(name = "customers")
class CustomerEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customers_customer_id_seq")
    @SequenceGenerator(
        name = "customers_customer_id_seq",
        allocationSize = 1
    )
    @Column(name = "customer_id")
    val id: Long? = null,

    @Column(name = "customer_email")
    val email: String?,

    @Column(name = "customer_address")
    val address: String?,

    @Column(name = "customer_phone")
    val phone: String?
) {
    // A customer can have multiple orders. Let's collect them
    @OneToMany
    @JoinColumn(name = "customer_id")
    val orders: MutableSet<OrderEntity> = mutableSetOf()  // unordered

    override fun toString(): String {
        return "CustomerEntity(id=$id, email=$email, address=$address, phone=$phone)"
    }
}