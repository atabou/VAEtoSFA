package VAELearning;

import algebralearning.oracles.EquivalenceOracle;
import org.sat4j.specs.TimeoutException;
import theory.BooleanAlgebra;

import java.util.List;

public class ForeignEquivalenceOracle<PredType, DomType> extends EquivalenceOracle<PredType, DomType> {

    private final BooleanAlgebra<PredType, DomType> ba;
    private final List<DomType> data;

    public ForeignEquivalenceOracle(BooleanAlgebra<PredType, DomType> ba, List<DomType> data) {

        this.ba = ba;
        this.data = data;

    }

    @Override
    public DomType getCounterexample(PredType p) throws TimeoutException {

        for(int i=0; i<data.size(); i++) {

            if(ba.HasModel(p, this.data.get(i))) {

                return this.data.get(i);

            }

        }

        return null;

    }

}
