import org.scalatest._

import subscript.koans._

class PathToEnlightenment extends Sequential(
  new AboutSubScript
, new AboutScriptDefinitions

, new AboutSequentialOperators
, new AboutAlternativeComposition
, new AboutEpsilonAndDelta

, new AboutAndParallelism
, new AboutOrParallelism
, new AboutDisruption

, new AboutConditionalOperators
//, new AboutHereAndThere
//, new AboutIterations // incl break, break?, ..., ..?
//, new AboutParentheses // how to get rid of those: plus(a,b), call:syntax, semicolon, method calls, let.
, new AboutAdvancedSyntax // ? still needed

//, new AboutAnnotations

//, new AboutCodeFragments // tiny, threaded, unsure, event handling
//, new AboutTerms // wrap up

, new AboutResultValues
, new AboutAdvancedResultValues
, new AboutDataflow

//, new AboutACPAxioms // their relation with SubScript
)