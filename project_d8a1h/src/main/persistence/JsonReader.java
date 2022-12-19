package persistence;

import model.Exercise;
import model.GymSession;
import model.Set;
import model.User;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads User from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads User from file and returns it;
    // throws IOException if an error occurs reading data from file
    public User read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseUserData(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses Userdata from JSON object and returns it
    private User parseUserData(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        User user = new User(name);
        addGymSessions(user, jsonObject);
        return user;
    }

    // MODIFIES: Userdata
    // EFFECTS: parses GymSessions from JSON object and adds them to User
    private void addGymSessions(User user, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("GymSessions");
        for (Object json : jsonArray) {
            JSONObject nextGymSession = (JSONObject) json;
            addGymSession(user, nextGymSession);
        }
    }

    // MODIFIES: userdata
    // EFFECTS: parses GymSession from JSON object and adds it to User
    private void addGymSession(User user, JSONObject jsonObject) {
        String[] date = jsonObject.getString("Date").split("/", 3);
        JSONArray jsonArray = jsonObject.getJSONArray("Exercises List");
        GymSession nextGymSession
                = new GymSession(Integer.parseInt(date[2]),Integer.parseInt(date[1]),Integer.parseInt(date[0]));
        for (Object json : jsonArray) {
            JSONObject nextExercise = (JSONObject) json;
            addExercise(nextGymSession, nextExercise, user);
        }
        user.getGymSessions().add(nextGymSession);
    }

    // MODIFIES: GymSession
    // EFFECTS: parses Exercise from JSON object and adds it to GymSession
    private void addExercise(GymSession gymSession, JSONObject jsonObject,User user) {
        String name = jsonObject.getString("Exercise Name");
        JSONArray jsonArray = jsonObject.getJSONArray("Sets");
        Exercise exercise = new Exercise(name,user);
        for (Object json : jsonArray) {
            JSONObject nextSet = (JSONObject) json;
            addSets(exercise, nextSet, user);
        }
        gymSession.addExercise(exercise);
    }

    // MODIFIES: Exercise
    // EFFECTS: parses Sets from JSON object and adds it to Exercise
    private void addSets(Exercise exercise, JSONObject jsonObject, User user) {
        int reps = jsonObject.getInt("reps");
        int weight = jsonObject.getInt("Weight");;
        Set set = new Set(reps, weight,user,exercise);
        exercise.addSet(set);
    }
}
