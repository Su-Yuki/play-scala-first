package controllers

import java.sql._
import javax.inject._
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.db._

// 
import akka.util._
import play.api.http._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
// class HomeController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {
class HomeController @Inject()(db: Database, cc: MessagesControllerComponents) extends MessagesAbstractController(cc) {
  // import MyForm._
  import PersonForm._

  /**
  * Create an Action to render an HTML page.
  *
  * The configuration in the `routes` file means that this method
  * will be called when the application receives a `GET` request with
  * a path of `/`.
  */

  def add() = Action { implicit request =>
    Ok(views.html.add(
      "フォームを入力してください"
      , form
    ))
  }

  def create() = Action {implicit request =>
    val formdata = form.bindFromRequest
    val data = formdata.get
    try
      db.withConnection { conn =>
        val ps = conn.prepareStatement("Insert into people value (default, ?, ?, ?)")
        ps.setString(1, data.name)
        ps.setString(2, data.mail)
        ps.setString(3, data.tel)
        ps.executeUpdate
      }
    catch {
      case e: SQLException =>
      Ok(views.html.add(
        "フォームを入力してください"
        , form
      ))
    }
    Redirect(routes.HomeController.index)
  }

  def index() = Action { implicit request => 
    var msg = "database record:<br><ul>"
    try
      db.withConnection { conn => 
        val stmt = conn.createStatement
        val rs = stmt.executeQuery("Select * from people")
        while (rs.next) {
          msg += "<li>" + rs.getInt("id") + ":" + rs.getString("name") + "</li>"
        }
        msg += "</ul>"
      }
    catch {
      case e:SQLException => msg = "<li>no record....<li>"
    }
    Ok(views.html.index(msg))
  }

  def edit(id:Int) = Action { implicit request =>
    var formdata = form.bindFromRequest
    try {
      db.withConnection { conn =>
        val stmt = conn.createStatement
        val rs = stmt.executeQuery("Select * from prople where id = " + id)
        rs.next
        val name = rs.getString("name")
        val mail = rs.getString("mail")
        val tel = rs.getString("tel")
        val data = Data(name, mail, tel)
        formdata = form.fill(data)
      }
    }catch {
      case e:SQLException => Redirect(routes.HomeController.index)
    }
    Ok(views.html.edit(
      "フォームを編集してください"
      , formdata
      , id
    ))
  }

  def update(id:Int) = Action { implicit request =>
    val formdata = form.bindFromRequest
    val data = formdata.get
    try 
    db.withConnection{ conn =>
      val ps  = conn.prepareStatement("Update people set name=?, mail=?, tel=? where id=?")
      ps.setString(1, data.name)
      ps.setString(2, data.mail)
      ps.setString(3, data.tel)
      ps.setInt(4, id)
      ps.executeUpdate
    }
    catch {
      case e: SQLException => 
      Ok(views.html.add(
        "フォームに入力してください"
        , form
      ))
    }
    Redirect(routes.HomeController.index)
  }

  def delete(id:Int) = Action { implicit request =>
    var pdata:Data = null;
    try 
    db.withConnection{ conn => 
      val stmt = conn.createStatement
      val rs = stmt.executeQuery("select * from people where id = " + id)
      rs.next
      val name = rs.getString("name")
      val mail = rs.getString("mail")
      val tel = rs.getString("tel")
      pdata = Data(name, mail, tel)
    }
    catch{
      case e: SQLException => Redirect(routes.HomeController.index)
    }
    Ok(views.html.delete(
      "このレコードを削除します"
      , pdata
      , id
    ))
  }

  def remove(id:Int) = Action { implicit request =>
    try
    db.withConnection{ conn =>
      val ps = conn.prepareStatement("delete from people where id = ?")
      ps.setInt(1, id)
      ps.executeUpdate
    }
    catch{
      case e: SQLException => Redirect(routes.HomeController.index)
    }
    Redirect(routes.HomeController.index)
  }

  // def index() = Action {
  //   Ok(views.html.index(123, "sample-name", "sample-pass", Calendar.getInstance))
  // }

  // // とりあえずページに表示を出す（テストなど）
  // def index() = TODO

  // <Result>.as(　コンテンツタイプ　)
  // def index() = Action {
  //   Ok("<h1>hello!</h1><p>This is sam</p>").as("text/html")
  // }

  // // xml
  // def index() = Action {
  //   Ok("<root><title>Hello!</title><message>This is sample message.</message></root>").as("text/xml")
  // }

  // // json
  // def index() = Action {
  //   Ok("<root><title>Hello!</title><message>This is sample message.</message></root>").as("text/json")
  // }

  // // Result
  // def index() = Action {
  //   Result(
  //     // ヘッダ情報　Result(header(整数) = <ResponsHeader>, body(map) = <HttpEntity>)
  //     header = ResponseHeader(200, Map.empty), 
  //     // レスポンスボディ　HttpEntity.Strict(<ByteString>, <Some>)
  //     body = HttpEntity.Strict(
  //       ByteString("This is sample text."),
  //       Some("text/plain")
  //     )
  //   )
  // }

  // // ヘッダ情報(show <AbstractController>)
  // def index() = Action {
  //   Ok("<title>Hello!</title><h1>Hello!</h1><p>sample</p>")
  //     .withHeaders(
  //       ACCEPT_CHARSET->"utf-8",
  //       ACCEPT_LANGUAGE->"ja-JP"
  //     )
  //     .as("text/html")
  // }

  // パラ
  // def index(id:Int, name:Option[String]) = Action {
  //   Ok("<title>Hello!</title><h1>Hello!</h1><p>ID = " + id + ",name = " + name.getOrElse("no-name") + "</p>")
  //     .withHeaders(
  //       ACCEPT_CHARSET->"utf-8",
  //       ACCEPT_LANGUAGE->"ja-JP"
  //     )
  //     .as("text/html")
  // }

  // // cookie
  // def index(name:Option[String]) = Action {request =>
  //   val param:String = name.getOrElse("");
  //   var message = "<p>no name</p>"
  //   if(param != ""){
  //     message = "<p>send message</p>"
  //   }
  //   val cookie = request.cookies.get("name")
  //   message += "<p>cookie: " + cookie.getOrElse(Cookie("name", "no-cookie.")).value + "</p>"
  //   val res = Ok("<title>Hello!</title><h1>hello!</h1>" + message).as("text/html")
  //   if(param != ""){
  //     res.withCookies(Cookie("name", param)).bakeCookies()
  //   } else {
  //     res
  //   }
  // }

  // // session
  // def index(name:Option[String]) = Action { request =>
  //   val param:String = name.getOrElse("");
  //   var message = "<p>no message</p>"
  //   if (param != ""){
  //     message = "<p>send message</p>"
  //   }
  //   val session = request.session.get("name")
  //   val sessionvalue = session.getOrElse("no-session")
  //   message += "<p>session: " + sessionvalue + "</p>"
  //   val res = Ok("<title>Hello!</title><h1>Hello!</h1>" + message).as("text/html")
  //   if(param != "") {
  //     res.withSession(request.session + ("name" -> param))
  //   } else {
  //     res
  //   }
  // }

  // def form() = Action { implicit request =>
  //   val form = myform.bindFromRequest
  //   val data = form.get
  //   Ok(views.html.index(
  //     "name: " + data.name + ", pass: " + data.pass + ", radio: " + data.radio, 
  //     form
  //   ))
  // }

}
