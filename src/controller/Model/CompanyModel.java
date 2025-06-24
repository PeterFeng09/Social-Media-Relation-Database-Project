package controller.Model;

public class CompanyModel extends RegUserModel{
    private final String companyID;

    public CompanyModel(String userName, String email, int age, String address, String phoneNumber, String postalCode, String companyID) {
        super(userName, email, age, address, phoneNumber, postalCode);
        this.companyID = companyID;
    }

    public String getCompanyID() {
        return companyID;
    }
}
