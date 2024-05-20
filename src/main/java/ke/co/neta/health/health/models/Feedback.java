package ke.co.neta.health.health.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Feedback {
    List<Map<String, String>> errors = new ArrayList<>();
    boolean success;

    public void setErrors(List<Map<String, String>> errors){
        this.errors = errors;
    }

    public void setSuccess(boolean success){
        this.success = success;
    }

    public List<Map<String, String>> getErrors(){
        return this.errors;
    }
}
