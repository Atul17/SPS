package cdac.diot.sps;

public class UserDetails {
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

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
