package ma.smartwatering.model;


public class LoginResponse {
    private String access_token;

    public LoginResponse(String access_token) {
        this.access_token = access_token;
    }

    public String getAccess_token() {
        return access_token;
    }
}
