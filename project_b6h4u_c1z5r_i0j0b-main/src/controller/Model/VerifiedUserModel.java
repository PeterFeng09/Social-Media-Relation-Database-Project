package controller.Model;

public class VerifiedUserModel extends RegUserModel{
    private final float monthlyFee;

    public VerifiedUserModel(String userName, String email, int age, String address, String phoneNumber, String postalCode, float monthlyFee) {
        super(userName, email, age, address, phoneNumber, postalCode);
        this.monthlyFee = monthlyFee;
    }
    public float getMonthlyFee() {
        return monthlyFee;
    }
}
