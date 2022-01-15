package controllers

import javax.inject._
import play.api._
import play.api.mvc._

// 
import akka.util._
import play.api.http._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

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
  //   Ok("Welcome!!!")
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
  def index(id:Int, name:String) = Action {
    Ok("<title>Hello!</title><h1>Hello!</h1><p>ID = " + id + ",name = " + name + "</p>")
      .withHeaders(
        ACCEPT_CHARSET->"utf-8",
        ACCEPT_LANGUAGE->"ja-JP"
      )
      .as("text/html")
  }



}
