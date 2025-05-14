package drinkselector.drinks.Etcs.Recommend;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.nd4j.linalg.api.ndarray.INDArray;

@Data
@AllArgsConstructor
public class IndArraySplit {

    private INDArray drink_id_arr;

    private INDArray drink_feature_arr;


}
