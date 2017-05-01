package bit.sikander.kickdown.models;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Sikander on 2017-04-30.
 */

@IgnoreExtraProperties
public class User {

    private String id;
    private String username = "";
    private String email;

    // Default constructor required for calls to DataSnapshot.getValue(User.class)
    public User(){

    }

    public User(String id, String username, String email){
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public String getId(){
        return this.id;
    }

    public String getUserName() {
        return this.username;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
