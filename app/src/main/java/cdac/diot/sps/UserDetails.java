package cdac.diot.sps;

public class UserDetails {
    public String username, email, mobile, passwd;

    public UserDetails() {

    }

    public UserDetails(String username, String email, String mobile, String passwd) {
        this.username = username;
        this.email = email;
        this.mobile = mobile;
        this.passwd = passwd;
    }
}
