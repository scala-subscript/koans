import subscript.language
import subscript.Predef._

object Main {
  def main(args: Array[String]) {
    var i = 0
    script..
      s1 = {!i += 1!}
      s2 = s1 s1
      s3 = s2 s2
      s4 = s3 println: i

    var y = 3
    runScript(s4)
  }
  

}
