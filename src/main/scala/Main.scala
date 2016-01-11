import subscript.language

object Main {
  def main(args: Array[String]): Unit = subscript.DSL._execute(live)

  script live = {!println("Hello")!} {!println("World")!}
}
