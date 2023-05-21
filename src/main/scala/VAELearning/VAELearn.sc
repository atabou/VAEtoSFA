import VAELearning.ForeignMembershipOracle
import VAELearning.ForeignEquivalenceOracle
import VAELearning.VAEDataParser
import algebralearning.sfa.SFAAlgebraLearner
import theory.BooleanAlgebra
import theory.cartesian.CartesianProduct
import theory.intervals.{IntPred, RealPred}
import utilities.Pair
import theory.intervals.IntegerSolver
import theory.intervals.RealSolver
import theory.ProductAlgebra

import java.lang

// Define the Predicate and Domain types

type PredType = CartesianProduct[
    CartesianProduct[CartesianProduct[IntPred, IntPred], CartesianProduct[IntPred, IntPred]],
    CartesianProduct[CartesianProduct[RealPred, RealPred], IntPred]
]

type DomType = Pair[
    Pair[Pair[Integer, Integer], Pair[Integer, Integer]],
    Pair[Pair[java.lang.Double, java.lang.Double], Integer]
]

// Setup the the algebra of the sfa to learn.

val integerSolver = new IntegerSolver()
val realSolver = new RealSolver()

var algebra1 = new ProductAlgebra(integerSolver, integerSolver)
var algebra2 = new ProductAlgebra(algebra1, algebra1)
var algebra3 = new ProductAlgebra(realSolver, realSolver)
var algebra4 = new ProductAlgebra(algebra3, integerSolver)

var algebra : BooleanAlgebra[PredType, DomType] = new ProductAlgebra(algebra2, algebra4)

val example : DomType = new Pair(
    new Pair( new Pair(1,2), new Pair(2,1) ),
    new Pair( new Pair(1.2, 5.3), 3 )
)

val data = new VAEDataParser[DomType]().parse(
    "/Users/atabou/IdeaProjects/VAEtoSFA/res/Producer_Consumer.csv",
    lst => new Pair(
        new Pair(
            new Pair(
                new java.lang.Integer(lst(0).toInt),
                new java.lang.Integer(lst(1).toInt)
            ),
            new Pair(
                new java.lang.Integer(lst(2).toInt),
                new java.lang.Integer(lst(3).toInt)
            )
        ),
        new Pair(
            new Pair(
                new java.lang.Double(lst(4).toDouble),
                new java.lang.Double(lst(5).toDouble)
            ),
            new java.lang.Integer(lst(6).toInt),
        ),
    ),
)


val mOracle = new ForeignMembershipOracle[DomType]("127.0.0.1", 3526)

val eOracle = new ForeignEquivalenceOracle[PredType, DomType](algebra, data)

val algLearner = new Learner

val sfaLearner = new SFAAlgebraLearner[PredType, DomType](mOracle, )

