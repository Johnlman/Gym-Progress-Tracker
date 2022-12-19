package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.HashMap;

//Represents a user and their Gym records
public class User implements Writable {
    private String userid;
    private ArrayList<GymSession> gymSessions;
    private HashMap<String,Integer> personalRecords;

    //EFFECTS: Creates a user with the name and initializes an empty array of Gym Sessions and Personal Records
    public User(String name) {
        userid = name;
        gymSessions = new ArrayList<>();
        personalRecords = new HashMap<>();
    }

    public ArrayList<GymSession> getGymSessions() {
        return gymSessions;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", userid);
        json.put("GymSessions", gymSessionsToJson());
        json.put("PersonalRecords",personalRecordsToJson());
        return json;
    }

    // EFFECTS: returns all Gym Sessions as a JSON array
    private JSONArray gymSessionsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (GymSession gymSession : gymSessions) {
            jsonArray.put(gymSession.toJson());
        }

        return jsonArray;
    }

    //MODIFIES
    public void addGymSession(GymSession gymSession) {
        EventLog.getInstance().logEvent(new Event("Added Gym Session (" + gymSession.getDate() + ") to  "
                + userid + "'s Records"));
        gymSessions.add(gymSession);
    }

    public HashMap<String, Integer> getPersonalRecords() {
        return personalRecords;
    }

    public String getName() {
        return userid;
    }

    public int getNumberGymSessions() {
        return gymSessions.size();
    }

    // EFFECTS: Displays Personal Records
    public void displayPersonalRecords() {
        System.out.println("Personal Records");
        for (String exercise:personalRecords.keySet()) {
            System.out.println(exercise + ": " + personalRecords.get(exercise));
        }
    }

    public JSONArray personalRecordsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (String exercise: personalRecords.keySet()) {
            JSONObject json = new JSONObject();
            json.put(exercise, personalRecords.get(exercise));
            jsonArray.put(json);
        }
        return jsonArray;
    }
}
