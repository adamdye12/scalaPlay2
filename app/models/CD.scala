package models

import java.awt.Image

import play.api.data._
import play.api.data.Forms._

import scala.collection.mutable.ArrayBuffer

case class Item(name: String,
                description: String,
                maker: String,
                warranty: Int,
                price: Int,
                discount: Int,
                seller: String)

object Item {

  val createItemForm = Form(
    mapping(
      "name" -> nonEmptyText,
      "description" -> nonEmptyText,
      "maker" -> nonEmptyText,
      "warranty" -> number(min = 0, max = 10),
        "price" -> number(min = 0, max = 100),
        "discount" -> number(min = 0, max = 100),
        "seller" -> nonEmptyText)
      (Item.apply)(Item.unapply)
    )

  val items = ArrayBuffer(
    Item("Coca Cola", "can of cool drink", "coke", 0, 123, 0, "Spar"),
    Item("Socks","Thick foot under garments", "Sock co", 1, 456, 10, "Sock shop"),
    Item("Table","Put things on it", "Tablez", 5, 789, 20, "Table shop")
  )
}