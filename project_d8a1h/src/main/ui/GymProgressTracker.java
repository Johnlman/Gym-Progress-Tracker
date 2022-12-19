package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// Gym Tracker Application
public class GymProgressTracker extends JPanel {
    protected static final String JSON_LOCATION = "./data/gymProgressTracker.json";
    protected User user;
    protected Scanner input;
    protected JsonWriter jsonWriter;
    protected JsonReader jsonReader;


    // EFFECTS: runs the GymTracker
    public GymProgressTracker() throws FileNotFoundException {
        jsonWriter = new JsonWriter(JSON_LOCATION);
        jsonReader = new JsonReader(JSON_LOCATION);
        //runGymTracker();
        //Uncomment This line to run the Console based application
    }

    //MODIFIES: this
    // EFFECTS: processes user input
    protected void runGymTracker() {
        boolean running = true;
        String command;
        user = new User("John");
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        while (running) {
            mainDisplayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                saveGymProgressTracker();
                running = false;
            } else if (command.equals("w")) {
                running = false;
            } else {
                mainProcessCommand(command);
            }
        }
    }

    //MODIFIES: this
    // EFFECTS: processes user input
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    protected void mainProcessCommand(String command) {
        switch (command) {
            case "g":
                recordGymSession();
                break;
            case "v":
                displayGymSessions();
                break;
            case "y":
                System.out.println("Please enter the Gym Session Number ( Can be found within Gym Session History)");
                int index = input.nextInt();
                printExercises(user.getGymSessions().get(index - 1));
                break;
            case "t":
                trackerDisplayMenu();
                String selection = input.next();
                selection = selection.toLowerCase();
                trackerProcessCommand(selection);
                break;
            case "l":
                loadUser();
                break;
            default:
                System.out.println("Selection not valid...");
                break;
        }
    }

    // EFFECTS: displays menu of options to user
    protected void mainDisplayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tG -> Record Gym Session");
        System.out.println("\tV -> View Gym Session History");
        System.out.println("\tY -> View a specific Gym Session");
        System.out.println("\tT -> Track Progress");
        System.out.println("\tL -> Load Data");
        System.out.println("\tW -> quit without saving");
        System.out.println("\tQ -> quit and save");
    }

    // EFFECTS: displays menu of options to user
    protected void trackerDisplayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tA -> Track your all time progress on an exercise");
        System.out.println("\tS -> Track your all time progress on an exercise over a specific range");
        System.out.println("\tq -> quit");
    }

    //MODIFIES: this
    // EFFECTS: processes user input
    protected void trackerProcessCommand(String command) {
        System.out.println("Please print out the exercise name");
        String exerciseName = input.next();
        exerciseName = exerciseName.toLowerCase();
        if (command.equals("a")) {
            trackTotalProgress(exerciseName);
        } else if (command.equals("s")) {
            System.out.println("If you do not know the Gym Session numbers of your range, select a, else, select s ");
            String selection = input.next();
            selection = selection.toLowerCase();
            if (selection.equals("a")) {
                displayGymSessions();
            } else if (selection.equals("s")) {
                System.out.println("Select your starting session");
                int start = input.nextInt();
                System.out.println("Select your endingsession");
                int end = input.nextInt();
                trackProgressOverTime(exerciseName, start, end);
            } else {
                System.out.println("Selection not valid...");
            }
        } else {
            System.out.println("Selection not valid...");
        }
    }

    //MODIFIES: this
    // EFFECTS: processes user input
    protected void gymSessionsProcessCommand(String command,GymSession gymSession) {
        if (command.equals("e")) {
            recordExercise(gymSession);
        } else if (command.equals("v")) {
            printExercises(gymSession);
        }   else {
            System.out.println("Selection not valid...");
        }
    }

    // EFFECTS: displays menu of options to user
    protected void gymSessionDisplayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tE -> Record Exercise");
        System.out.println("\tV -> View completed exercises");
        System.out.println("\tq -> End Gym Session");
    }

    //MODIFIES: this
    // EFFECTS: processes user input
    protected void recordGymSession() {
        System.out.println("Please enter the current date typed out as 'DD/MM/YYYY'");
        String[] date = input.next().split("/",3);
        GymSession gymSession = new GymSession(Integer.parseInt(date[0]),
                Integer.parseInt(date[1]), Integer.parseInt(date[2]));
        boolean running = true;
        String command;
        while (running) {
            gymSessionDisplayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                user.getGymSessions().add(gymSession);
                running = false;
            } else {
                gymSessionsProcessCommand(command, gymSession);
            }
        }
    }

    //MODIFIES: this
    // EFFECTS: processes user input
    protected void recordExercise(GymSession gymSession) {
        System.out.println("Please enter the exercise name");
        Exercise exercise = new Exercise(input.next().toLowerCase(),user);
        boolean running = true;
        String command;
        while (running) {
            exerciseDisplayMenu();
            command = input.next().toLowerCase();
            if (command.equals("q")) {
                running = false;
                gymSession.addExercise(exercise);
            } else {
                exerciseProcessCommand(command, exercise);
            }
        }
    }

    // EFFECTS: displays menu of options to user
    protected void exerciseDisplayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tE -> Record Set");
        System.out.println("\tq -> Go Back");
    }

    //MODIFIES: this
    // EFFECTS: processes user input
    protected void exerciseProcessCommand(String command, Exercise exercise) {
        if (command.equals("e")) {
            System.out.println("Please enter the weight");
            int weight = input.nextInt();
            System.out.println("Please enter the reps");
            int reps = input.nextInt();
            Set set = new Set(reps, weight,user,exercise);
            exercise.addSet(set);
        } else {
            System.out.println("Selection not valid...");
        }
    }

    //REQUIRES: exercise must have been completed
    // EFFECTS: displays Gym sessions, the max weight completed during that session
    public void trackTotalProgress(String exercise) {
        for (GymSession gymSession : user.getGymSessions()) {
            if (gymSession.getExercise(exercise) != null) {
                System.out.print(gymSession.getDate() + " ");
                System.out.println(gymSession.getExercise(exercise).getMaxWeight());
            }
        }
    }

    //REQUIRES: exercise must have been completed, start and end must be valid indexes
    // EFFECTS: displays Gym sessions, the average weight completed during that session
    public void trackProgressOverTime(String exercise, int start, int end) {
        for (int i = start - 1; i < end;i++) {
            if (user.getGymSessions().get(i).getExercise(exercise) != null) {
                System.out.print(user.getGymSessions().get(i).getDate() + " ");
                System.out.println("Average weight "
                        +
                        user.getGymSessions().get(i).getExercise(exercise).getMaxWeight());
            }
        }
    }

    // EFFECTS: displays menu of options to user
    public void displayGymSessions() {
        int index = 1;
        for (GymSession gymSession : user.getGymSessions()) {
            System.out.println("Gym Session " + index + " Date: " + gymSession.getDate());
            index++;
        }
    }

    //EFFECTS: Prints out all exercises completed, The amount of sets, and the average weight
    public void printExercises(GymSession gymSession) {
        for (Exercise exercise : gymSession.getExerciseList().values()) {
            System.out.println("Exercise: " + exercise.getName() + " Sets: " + exercise.getSets().size()
                    + " Average Weight:" + exercise.getMaxWeight());
        }
    }

    // EFFECTS: saves the users progress to file
    protected void saveGymProgressTracker() {
        try {
            jsonWriter.open();
            jsonWriter.write(user);
            jsonWriter.close();
            System.out.println("Saved " + user.getName() + " to " + JSON_LOCATION);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_LOCATION);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads users progress from file
    protected void loadUser() {
        try {
            user = jsonReader.read();
            System.out.println("Loaded " + user.getName() + " from " + JSON_LOCATION);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_LOCATION);
        }
    }

    public void printLog(EventLog el) {
        for (Event next : el) {
            System.out.print(next.toString() + "\n\n");
        }
    }
}
