package controllers

import javax.inject.Inject

import models.Item
import play.api.i18n.{I18nSupport, MessagesApi}

import play.api._
import play.api.mvc._
import scala.concurrent.Future

class Application  @Inject()(val messagesApi: MessagesApi) extends Controller with I18nSupport{

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def listItems = Action { implicit request =>
    Ok(views.html.listItems(Item.items, Item.createItemForm))
  }

  def createItem = Action { implicit request =>

    val formValidationResult = Item.createItemForm.bindFromRequest
    formValidationResult.fold({ formWithErrors =>
      BadRequest(views.html.listItems(Item.items, formWithErrors))
    }, { item =>
      Item.items.append(item)
      Redirect(routes.Application.listItems)
    })
  }

  def viewDeleteItem = Action{
    Ok(views.html.deleteItems(Item.items))
  }

  def deleteItem(name: String) = Action{ implicit request =>
      Ok(Item.items.delete(name).map(num => Ok(s"$num items deleted")))
    Redirect(routes.Application.viewDeleteItem)
  }
  def viewUpload = Action {
    Ok(views.html.uploadImage())
  }

  def upload = Action(parse.multipartFormData) { request =>
    request.body.file("picture").map { picture =>
      import java.io.File
      val filename = picture.filename
      val contentType = picture.contentType
      picture.ref.moveTo(new File(s"C:/Users/Administrator/Desktop/pics/$filename"))
      Ok("File uploaded")
    }.getOrElse {
      Redirect(routes.Application.index).flashing(
        "error" -> "Missing file")
    }
  }

}