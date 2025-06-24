package controller.Model;
public class AddressTimezonePostalCodeModel {
    private final String address;

    private final String timezone;

    private final String postalCode;

    public AddressTimezonePostalCodeModel(String address, String timezone, String postalCode) {
        this.address = address;
        this.timezone = timezone;
        this.postalCode = postalCode;
    }

    public String getAddress() {
        return address;
    }

    public String getTimezone() {
        return timezone;
    }

    public String getPostalCode() {
        return postalCode;
    }
}
