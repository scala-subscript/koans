import org.scalatest._

import subscript.koans._

class PathToEnlightenment extends Suites(
  new AboutSubScript
, new AboutSequentialOperators
) {

  override def runNestedSuites(args: Args): Status =
    if (args.distributor.isDefined) runNestedSuites(args.copy(distributor = None))
    else nestedSuites.foldLeft(SucceededStatus: Status) {(status, suite) =>
      if  (status == SucceededStatus) suite.run(None, args)
      else status
    }

}