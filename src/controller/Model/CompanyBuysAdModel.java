package controller.Model;
import java.sql.Timestamp;

public class CompanyBuysAdModel {

    private final int adID;

    private final Timestamp startDate;

    private final Timestamp endDate;


    private final Timestamp adLength;

    private final String userName;

    public CompanyBuysAdModel(int adID, String userName, Timestamp startDate, Timestamp endDate, Timestamp adLength) {
        this.adID = adID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.adLength = adLength;
        this.userName = userName;
    }

    public int getAdID() {
        return adID;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public Timestamp getAdLength() {
        return adLength;
    }

    public String getUserName() {
        return userName;
    }
}
