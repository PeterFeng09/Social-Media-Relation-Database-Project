package controller.Model;
import java.sql.Timestamp;
public class AdCostLengthModel {
    private final Timestamp adLength;

    private final float cost;

    public AdCostLengthModel(Timestamp adLength, float cost) {
        this.adLength = adLength;
        this.cost = cost;
    }

    public Timestamp getAdLength() {
        return adLength;
    }

    public float getCost() {
        return cost;
    }
}
