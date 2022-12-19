package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

//Represents an exercise, with a name, an array of sets, the average weight completed.
//and the duration completed if the exercise is cardio
public class Exercise implements Writable {
    private String name;
    private ArrayList<Set> sets;

    //REQUIRES: a valid string
    //EFFECTS: sets the name to the input
    public Exercise(String name,User user) {
        this.name = name;
        sets = new ArrayList<>();
        if (!user.getPersonalRecords().containsKey(this)) {
            user.getPersonalRecords().put(name,0);
        }
    }

    public ArrayList<Set> getSets() {
        return sets;
    }

    //REQUIRES: sets not be empty
    //MODIFIES: This
    //EFFECTS: Removes the set from the array of sets and adds to the event log
    public void removeSet(int index) {
        Set set = sets.get(index);
        EventLog.getInstance().logEvent(new Event("Removed set (Weight: " + set.getWeight() + " Reps: "
                + set.getReps() + ") from " + getName()));
        sets.remove(index);
    }

    public String getName() {
        return name;
    }

    //MODIFIES: This
    //EFFECTS: Adds the set to the array of sets
    public void addSet(Set set) {
        sets.add(set);
    }

    //MODIFIES: This
    //EFFECTS: Adds the set to the array of sets and adds to the event log
    public void addSetAndToEventLog(Set set) {
        EventLog.getInstance().logEvent(new Event("Added set (Weight: " + set.getWeight() + " Reps: "
                + set.getReps() + ") to " + getName()));
        sets.add(set);
    }

    //EFFECTS: Returns the total reps from a set of sets
    public int getTotalReps() {
        int totalReps = 0;
        for (Set set: sets) {
            totalReps += set.getReps();
        }
        return totalReps;
    }

    //EFFECTS: Returns the average weight of the sets
    public float getMaxWeight() {
        int maxWeight = 0;
        for (Set set: sets) {
            if (maxWeight < set.getWeight()) {
                maxWeight = set.getWeight();
            }
        }
        return maxWeight;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Exercise Name", name);
        json.put("Sets", setsToJson());
        return json;
    }

    // EFFECTS: returns the Sets of the exercise as a JSON array
    private JSONArray setsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Set set : sets) {
            jsonArray.put(set.toJson());
        }

        return jsonArray;
    }
}
