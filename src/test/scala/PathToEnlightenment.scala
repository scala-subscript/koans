import org.scalatest._

import subscript.koans._

class PathToEnlightenment extends Sequential(
  new AboutSubScript
, new AboutScriptDefinitions

, new AboutSequentialOperators
, new AboutEpsilonAndDelta

, new AboutAndParallelism
, new AboutOrParallelism
, new AboutAlternativeAndDisruption
, new AboutConditionalOperators
, new AboutMoreSpecialOperands

, new AboutResultValues
, new AboutAdvancedResultValues
)