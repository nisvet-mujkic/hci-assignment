package ba.fit.bookdiary.data;

import java.io.Serializable;

public class UserViewModel implements Serializable {
    private int id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;

    public UserViewModel(int id, String firstName, String lastName, String username, String password) {
        this.firstName = firstName;
        this.id = id;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString(){
        return getFirstName() + " " + getLastName();
    }
}
