package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

// 
import akka.util._
import play.api.http._


/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
// class HomeController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {
class HomeController @Inject()(cc: MessagesControllerComponents) extends MessagesAbstractController(cc) {
  import MyForm._

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */

  // def index() = Action { implicit request: Request[AnyContent] =>
  //   Ok(views.html.index())
  // }

  // def index() = Action {
  //   Ok(views.html.index(123, "sample-name", "sample-pass", Calendar.getInstance))
  // }

  // def index() = Action {
  //   // Ok(views.html.index("Welcome!!!"))
  //   Ok(views.html.index("これはコントローラ用意した要素です。"));
  // }

  // とりあえずページに表示を出す（テストなど）
  // def index() = TODO

  // <Result>.as(　コンテンツタイプ　)
  // def index() = Action {
  //   Ok("<h1>hello!</h1><p>This is sam</p>").as("text/html")
  // }

  // xml
  // def index() = Action {
  //   Ok("<root><title>Hello!</title><message>This is sample message.</message></root>").as("text/xml")
  // }

  // json
  // def index() = Action {
  //   Ok("<root><title>Hello!</title><message>This is sample message.</message></root>").as("text/json")
  // }

  // Result
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

  // ヘッダ情報(show <AbstractController>)
  // def index() = Action {
  //   Ok("<title>Hello!</title><h1>Hello!</h1><p>sample</p>")
  //     .withHeaders(
  //       ACCEPT_CHARSET->"utf-8",
  //       ACCEPT_LANGUAGE->"ja-JP"
  //     )
  //     .as("text/html")
  // }

  // パラ
  // def index(id:Int, name:String) = Action {
  //   Ok("<title>Hello!</title><h1>Hello!</h1><p>ID = " + id + ",name = " + name + "</p>")
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

  // cookie
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

  // session
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

  def index() = Action { implicit request =>
    Ok(views.html.index(
      "これはコントローラー用意したメッセージです。",
      myform
    ))
  }

  // def form() = Action { request => 
  //   val form:Option[Map[String, Seq[String]]] =request.body.asFormUrlEncoded
  //   val param:Map[String, Seq[String]] = form.getOrElse(Map())
  //   val name:String = param.get("name").get(0)
  //   val password:String = param.get("pass").get(0)
  //   Ok(views.html.index(
  //     "name: " + name + ", password: " + password
  //   ))
  // }
  def form() = Action { implicit request =>
    val form = myform.bindFromRequest
    val data = form.get
    Ok(views.html.index(
      "name: " + data.name + ", pass: " + data.pass + ", radio: " + data.radio, 
      form
    ))
  }
  
}
