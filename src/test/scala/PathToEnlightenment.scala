import org.scalatest._

import subscript.koans._

class PathToEnlightenment extends Sequential(
  new AboutSubScript
, new AboutScriptDefinitions

, new AboutSequentialOperators
//, new AboutExclusiveChoice
, new AboutEpsilonAndDelta

, new AboutAndParallelism
, new AboutOrParallelism
, new AboutAlternativeAndDisruption // to be split
//, new AboutDisruption

, new AboutConditionalOperators
//, new AboutHereAndThere
//, new AboutIterations // incl break, break?, ..., ..?
, new AboutAdvancedSyntax // ? still needed
//, new AboutParentheses // how to get rid of those: call syntax, semicolon, method calls.

//, new AboutAnnotations

//, new AboutCodeFragments // tiny, threaded, unsure, event handling
//, new AboutTerms // wrap up

, new AboutResultValues
, new AboutAdvancedResultValues
, new AboutDataflow

//, new AboutACPAxioms // their relation with SubScript
)