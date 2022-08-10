package no.kristiania.pg5100finalexam.models

import javax.persistence.*

@Entity
@Table(name = "items")
class ItemEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "items_item_id_seq")
    @SequenceGenerator(
        name = "items_item_id_seq",
        allocationSize = 1
    )
    @Column(name = "item_id")
    val id: Long? = null,

    @Column(name = "item_name")
    val itemName: String?
) {
    override fun toString(): String {
        return "ItemEntity(id=$id, itemName=$itemName)"
    }
}