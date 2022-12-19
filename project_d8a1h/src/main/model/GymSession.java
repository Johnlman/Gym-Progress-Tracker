package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.Date;
import java.util.HashMap;

// Represents a gymSession, with a list of exercises completed during the session and a date
public class GymSession implements Writable {
    private  HashMap<String,Exercise> exerciseList;
    String date;


    //EFFECTS: creates a gymSession with a date and an exercise list
    public GymSession(int year, int month, int day) {
        date = Integer.toString(day) + "/" + Integer.toString(month) + "/" + Integer.toString(year);
        exerciseList = new HashMap<>();
    }

    //MODIFIES: This
    //EFFECTS: adds exercise to exerciseList, if an object of that name is already there, its place
    // in the hashmap will be overwritten
    public void addExercise(Exercise exercise) {
        exerciseList.put(exercise.getName(), exercise);
    }

    //REQUIRES: Key to exist in hashmap
    //MODIFIES: This
    //EFFECTS: removes exercise from exerciseList and adds to the EventLog
    public void removeExercise(String exercise) {
        EventLog.getInstance().logEvent(new Event("Removed " + exercise + " from "
                + date + " Gym Session (" + date + ")"));
        exerciseList.remove(exercise);
    }

    //MODIFIES: This
    //EFFECTS: adds exercise to exerciseList, if an object of that name is already there, its place
    // in the hashmap will be overwritten and adds to EventLog
    public void addExerciseAndToEventLog(Exercise exercise) {
        EventLog.getInstance().logEvent(new Event("Added " + exercise.getName() + " to "
                + date + " Gym Session"));
        exerciseList.put(exercise.getName(), exercise);
    }

    public Exercise getExercise(String name) {
        return exerciseList.get(name);
    }

    public String getDate() {
        return date;
    }



    public HashMap<String,Exercise> getExerciseList() {
        return exerciseList;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Date", date);
        json.put("Exercises List", exercisesToJson());
        return json;
    }

    // EFFECTS: returns the Exercises performed during the session as a JSON array
    private JSONArray exercisesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Exercise exercise : exerciseList.values()) {
            jsonArray.put(exercise.toJson());
        }

        return jsonArray;
    }
}
