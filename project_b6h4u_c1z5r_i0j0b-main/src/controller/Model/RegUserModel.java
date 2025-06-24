package controller.Model;

public class RegUserModel {
    private final String userName;

    private final String email;

    private final int age;

    private final String address;

    private final String phoneNumber;

    private final String postalCode;

    public RegUserModel(String userName, String email, int age, String address, String postalCode, String phoneNumber) {
        this.userName = userName;
        this.email = email;
        this.age = age;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.postalCode = postalCode;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getAddress() {return address; }
}
