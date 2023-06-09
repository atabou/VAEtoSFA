
//import VAELearning.ForeignMembershipOracle
//import VAELearning.ForeignEquivalenceOracle
//import ForeignMembershipOracle
//import VAEDataParser
import VAELearning.{ForeignMembershipOracle, VAEDataParser}
import utilities.Pair
//import algebralearning.sfa.SFAAlgebraLearner
import theory.BooleanAlgebra
import theory.cartesian.CartesianProduct
import theory.intervals.{IntPred, RealPred}
import utilities.Pair
import theory.intervals.IntegerSolver
import theory.intervals.RealSolver
import theory.ProductAlgebra
// Define the Predicate and Domain types

type PredType = CartesianProduct[ // 24
    CartesianProduct[ // 16
        CartesianProduct[ // 8
            CartesianProduct[ // 4
                CartesianProduct[ // 2
                    RealPred,
                    RealPred
                ],
                CartesianProduct[ // 2
                    RealPred,
                    RealPred
                ]
            ],
            CartesianProduct[ // 4
                CartesianProduct[ // 2
                    RealPred,
                    RealPred
                ],
                CartesianProduct[ // 2
                    RealPred,
                    RealPred
                ]
            ]
        ],
        CartesianProduct[ // 8
            CartesianProduct[ // 4
                CartesianProduct[ // 2
                    RealPred,
                    RealPred
                ],
                CartesianProduct[ // 2
                    RealPred,
                    RealPred
                ]
            ],
            CartesianProduct[ // 4
                CartesianProduct[ // 2
                    RealPred,
                    RealPred
                ],
                CartesianProduct[ // 2
                    RealPred,
                    RealPred
                ]
            ]
        ]
    ],
    CartesianProduct[ // 8
        CartesianProduct[ // 4
            CartesianProduct[ // 2
                RealPred,
                RealPred
            ],
            CartesianProduct[ // 2
                RealPred,
                RealPred
            ]
        ],
        CartesianProduct[ // 4
            CartesianProduct[ // 2
                RealPred,
                RealPred
            ],
            CartesianProduct[ // 2
                RealPred,
                RealPred
            ]
        ]
    ]
]

type DomType = Pair[ // 24
    Pair[ // 16
        Pair[ // 8
            Pair[ // 4
                Pair[ // 2
                    java.lang.Double,
                    java.lang.Double
                ],
                Pair[
                    java.lang.Double,
                    java.lang.Double
                ]
            ],
            Pair[ // 4
                Pair[ // 2
                    java.lang.Double,
                    java.lang.Double
                ],
                Pair[ // 2
                    java.lang.Double,
                    java.lang.Double
                ]
            ]
        ],
        Pair[ // 8
            Pair[ // 4
                Pair[ // 2
                    java.lang.Double,
                    java.lang.Double
                ],
                Pair[ // 2
                    java.lang.Double,
                    java.lang.Double
                ]
            ],
            Pair[ // 4
                Pair[ // 2
                    java.lang.Double,
                    java.lang.Double
                ],
                Pair[ // 2
                    java.lang.Double,
                    java.lang.Double
                ]
            ]
        ]
    ],
    Pair[ // 8
        Pair[ // 4
            Pair[ // 2
                java.lang.Double,
                java.lang.Double
            ],
            Pair[ // 2
                java.lang.Double,
                java.lang.Double
            ]
        ],
        Pair[ // 4
            Pair[ // 2
                java.lang.Double,
                java.lang.Double
            ],
            Pair[ // 2
                java.lang.Double,
                java.lang.Double
            ]
        ]
    ]
]

// Setup the the algebra of the sfa to learn.

val realSolver1 = new RealSolver()
val realSolver2 = new RealSolver()

var firstLevel = new ProductAlgebra(realSolver1, realSolver2) // 2 leaves
var secondLevel = new ProductAlgebra(firstLevel, firstLevel) // 4 leaves
var thirdLevel = new ProductAlgebra(secondLevel, secondLevel) // 8 leaves
var fourthLevel = new ProductAlgebra(thirdLevel, thirdLevel) // 16 leaves

var algebra = new ProductAlgebra(fourthLevel, thirdLevel) // 24 leaves

val data = new VAEDataParser[DomType]().parse(
    "/Users/atabou/IdeaProjects/VAEtoSFA/res/PM_train.txt",
    lst => {
        new Pair( // 24
            new Pair( // 16
                new Pair( // 8
                    new Pair( // 4
                        new Pair( // 2
                            new java.lang.Double(lst(2).toDouble),
                            new java.lang.Double(lst(3).toDouble)
                        ),
                        new Pair( // 2
                            new java.lang.Double(lst(4).toDouble),
                            new java.lang.Double(lst(5).toDouble)
                        )
                    ),
                    new Pair( // 4
                        new Pair( // 2
                            new java.lang.Double(lst(6).toDouble),
                            new java.lang.Double(lst(7).toDouble)
                        ),
                        new Pair( // 2
                            new java.lang.Double(lst(8).toDouble),
                            new java.lang.Double(lst(9).toDouble)
                        )
                    )
                ),
                new Pair( // 8
                    new Pair( // 4
                        new Pair( // 2
                            new java.lang.Double(lst(10).toDouble),
                            new java.lang.Double(lst(11).toDouble)
                        ),
                        new Pair( // 2
                            new java.lang.Double(lst(12).toDouble),
                            new java.lang.Double(lst(13).toDouble)
                        )
                    ),
                    new Pair( // 4
                        new Pair( // 2
                            new java.lang.Double(lst(14).toDouble),
                            new java.lang.Double(lst(15).toDouble)
                        ),
                        new Pair( // 2
                            new java.lang.Double(lst(16).toDouble),
                            new java.lang.Double(lst(17).toDouble)
                        )
                    )
                )
            ),
            new Pair( // 8
                new Pair( // 4
                    new Pair( // 2
                        new java.lang.Double(lst(18).toDouble),
                        new java.lang.Double(lst(19).toDouble)
                    ),
                    new Pair( // 2
                        new java.lang.Double(lst(20).toDouble),
                        new java.lang.Double(lst(21).toDouble)
                    )
                ),
                new Pair( // 4
                    new Pair( // 2
                        new java.lang.Double(lst(22).toDouble),
                        new java.lang.Double(lst(23).toDouble)
                    ),
                    new Pair( // 2
                        new java.lang.Double(lst(24).toDouble),
                        new java.lang.Double(lst(25).toDouble)
                    )
                )
            )
        )
    }
).toArray

val mOracle = new ForeignMembershipOracle[DomType](new java.lang.String("127.0.0.1"), 3826)

print(mOracle.serialize_domain_object(data).toArray.mkString)

mOracle.start()

mOracle.query(data(0))

mOracle.stop()

//val eOracle = new ForeignEquivalenceOracle[PredType, DomType](algebra, data)

//val algLearner = new Learner

//val sfaLearner = new SFAAlgebraLearner[PredType, DomType](mOracle, )



