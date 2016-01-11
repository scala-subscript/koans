import subscript.language

object Main {
  def main(args: Array[String]): Unit = subscript.DSL._execute(live)

  script..
    live = [a b || c d] e

    a = {!println("Foo")!}
    b = {!println("Foo")!}
    c = {!println("Foo")!}
    d = {!println("Foo")!}
    e = {!println("Foo")!}
    f = {!println("Foo")!}
}
