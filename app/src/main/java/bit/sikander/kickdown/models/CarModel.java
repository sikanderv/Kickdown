package bit.sikander.kickdown.models;

/**
 * Created by Sikander on 2017-04-28.
 */

public class CarModel {

    private int make_id;
    private String make_name = "";
    private int model_id;
    private String model_name = "";

    public CarModel(){

    }

    public String getModel_name() {
        return model_name;
    }

    public void setModel_name(String model_name) {
        this.model_name = model_name;
    }

}
