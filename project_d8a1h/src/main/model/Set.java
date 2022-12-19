package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

//Represents a Set of reps
public class Set implements Writable {
    private int reps;
    private int weight;

    //REQUIRES: positive integers
    //EFFECTS: sets reps and weight to the parameters
    public Set(int reps, int weight,User user,Exercise exercise) {
        if (user.getPersonalRecords().get(exercise.getName()) < weight) {
            user.getPersonalRecords().put(exercise.getName(), weight);
        }
        this.reps = reps;
        this.weight = weight;
    }

    public int getReps() {
        return reps;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("reps", reps);
        json.put("Weight", weight);
        return json;
    }
}
