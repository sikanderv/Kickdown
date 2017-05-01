package bit.sikander.kickdown.models;

/**
 * Created by Sikander on 2017-04-30.
 */

public class UserCar {
    private String make_name = "";
    private String model_name = "";

    // Default constructor required for calls to DataSnapshot.getValue(User.class)
    public UserCar(){

    }

    public UserCar(String make, String model){
        this.make_name = make;
        this.model_name = model;
    }


    public String getMake_name() {
        return this.make_name;
    }

    public void setMake_name(String make) {
        this.make_name = make;
    }

    public String getModel_name() {
        return this.model_name;
    }

    public void setModel_name(String model) {
        this.model_name = model;
    }
}
