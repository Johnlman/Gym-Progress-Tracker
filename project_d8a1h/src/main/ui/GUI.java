
package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class GUI extends GymProgressTracker implements ListSelectionListener {
    protected JFrame frame;
    protected JButton buttonRecordGymSession;
    protected JButton buttonViewGymSessions;
    protected JButton buttonTrackProgress;
    protected JButton buttonLoadData;
    protected JButton buttonSave;
    protected JButton buttonSubmit;
    protected JButton buttonAddExercise;
    protected JButton buttonGoBack;
    protected JButton buttonAddSet;
    protected JButton buttonView;
    protected JButton buttonRemoveExercise;
    protected JButton buttonRemoveSet;
    protected JTextField textField;
    protected JTextField addExerciseTextField;
    protected JTextField addWeightTextField;
    protected JTextField addRepsTextField;
    protected JTextField buttonUsername;
    protected ImageIcon gymImage;
    protected JLabel label;
    protected JLabel label1;
    protected JLabel setLabel;
    protected JLabel weightLabel;
    protected JLabel repsLabel;
    protected JLabel buttonUserLabel;
    protected JList list;
    private DefaultListModel listModel;
    protected JPanel panel;
    protected Container cp;

    //EFFECTS: Runs the GUI Program
    public GUI() throws FileNotFoundException {
        login();
        jsonWriter = new JsonWriter(JSON_LOCATION);
        jsonReader = new JsonReader(JSON_LOCATION);
    }

    //EFFECTS: Creates the JFrame for the main program
    public void setUpGUI() {
        frame = new JFrame("Gym Progress Tracker");
        setUpCloseWindowListener();
        setUpMainMenuButtons();
    }

    //EFFECTS: Sets up the functionality of the close button
    public void setUpCloseWindowListener() {
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                printLog(EventLog.getInstance());
                System.exit(0);
            }
        });
    }

    //EFFECTS: Creates a JFrame with a login screen and background
    public void login() {
        frame = new JFrame("Gym Progress Tracker");
        frame.setSize(628, 446);;
        setUpCloseWindowListener();
        label = new JLabel("");
        gymImage = new ImageIcon("./data/Gym.jpg");
        label.setIcon(gymImage); // using image as background
        frame.add(label);
        label.setLayout(new GridBagLayout());
        label.setIcon(gymImage);
        panel = new JPanel();
        label.add(panel, new GridBagConstraints());
        panel.setLayout(new GridLayout(2, 2));
        buttonUsername = new JTextField();
        buttonUserLabel = new JLabel("Enter username: ");
        buttonLoadData = new JButton("Load Data");
        buttonSubmit = new JButton("Submit");
        panel.add(buttonUserLabel);
        panel.add(buttonUsername);
        panel.add(buttonLoadData);
        panel.add(buttonSubmit);
        frame.setVisible(true);
        setUpLoginButtonListeners();
    }

    //EFFECTS: Button Listeners for the login screen
    public void setUpLoginButtonListeners() {
        ActionListener buttonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == buttonSubmit) {
                    user = new User(buttonUsername.getText());
                } else {
                    loadUser();
                }
                frame.setVisible(false);
                setUpGUI();
                label = new JLabel("Successfully",SwingConstants.RIGHT);
                cp.add(label);
                label1 = new JLabel(" loaded Data");
                cp.add(label1);
            }
        };
        buttonSubmit.addActionListener(buttonListener);
        buttonLoadData.addActionListener(buttonListener);
    }

    //EFFECTS: Creates the main menu Buttons
    public void setUpMainMenuButtons() {
        cp = frame.getContentPane();
        GridLayout grid = new GridLayout(3,2);
        cp.setLayout(grid);
        buttonRecordGymSession = new JButton("Record Gym Session");
        buttonViewGymSessions = new JButton("View Gym Sessions");
        buttonTrackProgress = new JButton("Track Progress");
        buttonSave = new JButton("Save");
        cp.add(buttonRecordGymSession);
        cp.add(buttonViewGymSessions);
        cp.add(buttonTrackProgress);
        cp.add(buttonSave);
        frame.setSize(500,500);
        frame.setVisible(true);
        setUpMainMenuButtonListeners();
    }

    //EFFECTS: Removes the main menu buttons
    public void removeMainMenuButtons() {
        cp.remove(buttonRecordGymSession);
        cp.remove(buttonViewGymSessions);
        cp.remove(buttonTrackProgress);
        cp.remove(buttonLoadData);
        cp.remove(buttonSave);
        if (cp.getComponentCount() == 2) {
            cp.remove(label);
            cp.remove(label1);
        }
    }

    //EFFECTS: Creates a screen with fields to get Date from user
    public void getDateFromUser() {
        GridLayout grid = new GridLayout(3,1);
        cp.setLayout(grid);
        label = new JLabel("Please enter the current date typed out as 'DD/MM/YY'");
        textField = new JTextField();
        buttonSubmit = new JButton("Submit");
        cp.add(label);
        cp.add(textField);
        cp.add(buttonSubmit);
        frame.setSize(350,300);
        frame.setVisible(true);
        setUpGetDateButtonListeners();
    }

    //EFFECTS: Removes get Date buttons
    public void removeGetDateButtons() {
        cp.remove(label);
        cp.remove(textField);
        cp.remove(buttonSubmit);
    }

    //EFFECTS: Sets up button listeners for Get Date Buttons
    public void setUpGetDateButtonListeners() {
        ActionListener buttonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == buttonSubmit) {
                    String text = textField.getText();
                    if (text.matches(".*[a-zA-Z]+.*")) {
                        label.setText("Invalid argument, please use only integers and /, please try again");
                    } else {
                        String[] date = text.split("/",3);
                        GymSession gymSession = new GymSession(Integer.parseInt(date[0]),
                                Integer.parseInt(date[1]), Integer.parseInt(date[2]));
                        user.addGymSession(gymSession);
                        removeGetDateButtons();
                        setUpGymSessionGUI(gymSession, 1);
                    }
                }
            }
        };
        buttonSubmit.addActionListener(buttonListener);
    }

    //EFFECTS: Sets up the buttons for the Gym Session
    public void setUpGymSessionGUI(GymSession gymSession, int version) {
        frame.setSize(450,350);
        GridBagLayout grid = new GridBagLayout();
        cp.setLayout(grid);
        buttonGoBack = new JButton("Go Back");
        setUpGymSessionGuiHelper(version);
        addButtonWithConstraints(label,0,1,4,0);
        frame.setVisible(true);
        for (Exercise exercise1:gymSession.getExerciseList().values()) {
            listModel.addElement(exercise1.getName());
        }
        frame.pack();
        setUpGymSessionButtonListeners(gymSession,version);
    }

    //EFFECTS: Sets up the buttons for Gym Session
    public void setUpGymSessionGuiHelper(int version) {
        if (version == 1) {
            listSetUp(1);
            addButtonWithConstraints(list,0,0,4,200);
            addExerciseTextField = new JTextField();
            buttonAddExercise = new JButton("Add Exercise");
            buttonAddSet = new JButton("Add Set");
            buttonRemoveExercise = new JButton("Remove Exercise");
            addButtonWithConstraints(addExerciseTextField,0,2,4,0);
            addButtonWithConstraints(buttonAddExercise,0,3,1,0);
            addButtonWithConstraints(buttonRemoveExercise,1,3,1,0);
            addButtonWithConstraints(buttonAddSet,2,3,1,0);
            addButtonWithConstraints(buttonGoBack,3,3,1,0);
            buttonRemoveExercise.setEnabled(false);
            buttonAddSet.setEnabled(false);
        } else {
            listSetUp(0);
            addButtonWithConstraints(list,0,0,4,200);
            buttonView = new JButton("View");
            buttonView.setEnabled(false);
            addButtonWithConstraints(buttonView,0,3,2,0);
            addButtonWithConstraints(buttonGoBack,2,3,2,0);
        }
    }

    //EFFECTS: Helper method for creating button with constraints
    public void addButtonWithConstraints(Component comp,int x, int y, int width, int height) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = x;
        constraints.gridy = y;
        if (width > 0) {
            constraints.gridwidth = width;
        }
        if (height > 0) {
            constraints.ipady = height;
        }
        cp.add(comp,constraints);
    }

    //EFFECTS: Creates a list
    public void listSetUp(int version) {
        listModel = new DefaultListModel();
        list = new JList(listModel);
        if (version == 1) {
            label = new JLabel("Please enter the exercise name below and press the 'Add Exercise' Button");
        } else {
            label = new JLabel("Please select the desired exercise that you want to view");
        }
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(5);
    }

    //EFFECTS: Removes the Gym Session GUI
    public void removeGymSessionGUI(int version) {
        if (version == 1) {
            cp.remove(buttonAddExercise);
            cp.remove(buttonAddSet);
            cp.remove(addExerciseTextField);
            cp.remove(buttonRemoveExercise);
        } else {
            cp.remove(buttonView);
        }
        cp.remove(buttonGoBack);
        cp.remove(list);
        cp.remove(label);
    }

    //EFFECTS: Sets up the Gym Session Button's Listeners
    public void setUpGymSessionButtonListeners(GymSession gymSession,int version) {
        if (version == 1) {
            setUpGymSessionButtonListenersHelper1(gymSession);
        } else {
            setUpGymSessionButtonListenersHelper2(gymSession);
        }
    }

    //EFFECTS:Helper method for setUpGymSessionButtonListeners
    public void setUpGymSessionButtonListenersHelper1(GymSession gymSession) {
        ActionListener buttonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == buttonAddExercise) {
                    addExerciseHelper(gymSession);
                } else if (e.getSource() == buttonRemoveExercise) {
                    gymSession.removeExercise(list.getSelectedValue().toString());
                    listModel.remove(list.getSelectedIndex());
                    frame.pack();
                } else if (e.getSource() == buttonAddSet) {
                    Exercise exercise = gymSession.getExercise(list.getSelectedValue().toString());
                    removeGymSessionGUI(1);
                    setUpAddSetGUI(gymSession, exercise,1);
                } else {
                    removeGymSessionGUI(1);
                    setUpMainMenuButtons();
                }
            }
        };
        buttonRemoveExercise.addActionListener(buttonListener);
        buttonAddExercise.addActionListener(buttonListener);
        buttonGoBack.addActionListener(buttonListener);
        buttonAddSet.addActionListener(buttonListener);
    }

    //EFFECTS: Helper to reduce code length
    public void addExerciseHelper(GymSession gymSession) {
        Exercise exercise = new Exercise(addExerciseTextField.getText(),user);
        if (!listModel.contains(addExerciseTextField.getText())) {
            listModel.addElement(addExerciseTextField.getText());
            gymSession.addExerciseAndToEventLog(exercise);
            addExerciseTextField.setText("");
            frame.pack();
        }
    }

    //EFFECTS:Helper method for setUpGymSessionButtonListeners
    public void setUpGymSessionButtonListenersHelper2(GymSession gymSession) {
        ActionListener buttonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == buttonView) {
                    Exercise exercise = gymSession.getExercise(list.getSelectedValue().toString());
                    removeGymSessionGUI(0);
                    setUpAddSetGUI(gymSession,exercise,0);
                }  else {
                    removeGymSessionGUI(0);
                    setUpViewGymSessionsGUI();
                }
            }
        };
        buttonView.addActionListener(buttonListener);
        buttonGoBack.addActionListener(buttonListener);
    }

    //EFFECTS: Sets up the Main menu Button's Listeners
    public void setUpMainMenuButtonListeners() {
        ActionListener buttonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == buttonRecordGymSession) {
                    removeMainMenuButtons();
                    getDateFromUser();
                } else if (e.getSource() == buttonViewGymSessions) {
                    removeMainMenuButtons();
                    setUpViewGymSessionsGUI();
                } else if (e.getSource() == buttonTrackProgress) {
                    removeMainMenuButtons();
                    setUpTrackProgressButtons();
                } else {
                        saveAndLoadLabels();
                        saveGymProgressTracker();
                    }
                }
        };
        buttonRecordGymSession.addActionListener(buttonListener);
        buttonViewGymSessions.addActionListener(buttonListener);
        buttonTrackProgress.addActionListener(buttonListener);
        buttonSave.addActionListener(buttonListener);
    }

    //EFFECTS: Sets up the GUI to view gym sessions
    public void setUpViewGymSessionsGUI() {
        GridBagLayout grid = new GridBagLayout();
        frame.setSize(350,400);
        cp.setLayout(grid);
        buttonView = new JButton("View");
        buttonView.setEnabled(false);
        buttonGoBack = new JButton("Go Back");
        setLabel = new JLabel("Please select a session and press view");
        listSetUp(0);
        addButtonWithConstraints(list,0,0,4,200);
        addButtonWithConstraints(setLabel,0,1,4,0);
        addButtonWithConstraints(buttonView,0,2,2,0);
        addButtonWithConstraints(buttonGoBack,2,2,2,0);

        frame.setVisible(true);
        int index = 1;
        for (GymSession gymSession:user.getGymSessions()) {
            listModel.addElement("Gym Session " + index++ + " Date: " + gymSession.getDate());
        }
        setUpViewGymSessionsButtonListeners();
    }

    //EFFECTS: Removes the View Gym Session GUI
    public void removeViewGymSessionGUI() {
        cp.remove(buttonGoBack);
        cp.remove(buttonView);
        cp.remove(setLabel);
        cp.remove(list);

    }

    //EFFECTS: Sets up the Button Listeners for Viewing Gym Sessions
    public void setUpViewGymSessionsButtonListeners() {
        ActionListener buttonListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == buttonView) {
                    GymSession gymSession = user.getGymSessions().get(list.getSelectedIndex());
                    removeViewGymSessionGUI();
                    setUpGymSessionGUI(gymSession,0);
                }  else {
                    removeViewGymSessionGUI();
                    setUpMainMenuButtons();
                }
            }
        };
        buttonView.addActionListener(buttonListener);
        buttonGoBack.addActionListener(buttonListener);
    }

    //EFFECTS: Creates Save and load labels
    public void saveAndLoadLabels() {
        if (cp.getComponentCount() == 6) {
            cp.remove(label);
            cp.remove(label1);
        }
        label = new JLabel("Successfully",SwingConstants.RIGHT);
        cp.add(label);
        label1 = new JLabel(" Saved");
        cp.add(label1);
        frame.setVisible(true);
    }

    //EFFECTS: Creates the GUI for adding sets to an exercise
    public void setUpAddSetGUI(GymSession gymSession, Exercise exercise, int version) {
        GridBagLayout grid = new GridBagLayout();
        cp.setLayout(grid);
        listSetUp(version);
        addButtonWithConstraints(list,0,0,4,200);
        for (Set set:exercise.getSets()) {
            listModel.addElement("Weight: " + set.getWeight() + " Reps: " + set.getReps());
        }
        if (version == 1) {
            buttonGoBack = new JButton("Go Back");
            setUpAddGuiSetHelper();
            frame.pack();
        } else {
            buttonGoBack = new JButton("              Go Back              ");
            addButtonWithConstraints(buttonGoBack,0,1,4,0);
            frame.setSize(250,300);
        }
        frame.setVisible(true);

        setUpExerciseButtonListeners(gymSession, exercise, version);
    }

//EFFECTS: Sets up the GUI to view sets
    public void setUpAddGuiSetHelper() {
        buttonSubmit = new JButton("Submit");
        addWeightTextField = new JTextField();
        addRepsTextField = new JTextField();
        weightLabel = new JLabel("Weight:");
        repsLabel = new JLabel("Reps:");
        setLabel = new JLabel("Please enter 'Submit' only after you have entered both Weight and Reps");
        buttonRemoveSet = new JButton("Remove Set");
        addButtonWithConstraints(setLabel,0,1,4,0);
        addButtonWithConstraints(weightLabel,0,2,1,0);
        addButtonWithConstraints(addWeightTextField,1,2,1,0);
        addButtonWithConstraints(repsLabel,2,2,1,0);
        addButtonWithConstraints(addRepsTextField,3,2,1,0);
        addButtonWithConstraints(buttonSubmit,0,3,1,0);
        addButtonWithConstraints(buttonRemoveSet,1,3,1,0);
        addButtonWithConstraints(buttonGoBack,2,3,2,0);
        buttonRemoveSet.setEnabled(false);
    }

    //EFFECTS: Removes the GUI for adding a set to an exercise
    public void removeExerciseGUI(int version) {
        cp.remove(list);
        if (version == 1) {
            cp.remove(setLabel);
            cp.remove(weightLabel);
            cp.remove(addWeightTextField);
            cp.remove(repsLabel);
            cp.remove(addRepsTextField);
            cp.remove(buttonSubmit);
            cp.remove(buttonRemoveSet);
        }
        cp.remove(buttonGoBack);
    }

    //EFFECTS: Sets up the Button Listeners for adding Sets to an exercise
    public void setUpExerciseButtonListeners(GymSession gymSession,Exercise exercise, int version) {
        if (version == 1) {
            setUpExerciseButtonListenersHelper1(gymSession,exercise);
        } else {
            setUpExerciseButtonListenersHelper2(gymSession,exercise);
        }
    }

    //EFFECTS:Helper method for setUpGymSessionButtonListeners
    public void setUpExerciseButtonListenersHelper1(GymSession gymSession,Exercise exercise) {
        ActionListener buttonListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == buttonSubmit) {
                    Set set = new Set(Integer.parseInt(addRepsTextField.getText()),
                            Integer.parseInt(addWeightTextField.getText()),user,exercise);
                    exercise.addSetAndToEventLog(set);
                    listModel.addElement("Weight: " + set.getWeight() + " Reps: " + set.getReps());
                    addWeightTextField.setText("");
                    addRepsTextField.setText("");
                    frame.pack();
                }  else if (e.getSource() == buttonRemoveSet) {
                    exercise.removeSet(list.getSelectedIndex());
                    listModel.remove(list.getSelectedIndex());
                    frame.pack();
                } else {
                    removeExerciseGUI(1);
                    setUpGymSessionGUI(gymSession, 1);
                }
            }
        };
        buttonRemoveSet.addActionListener(buttonListener);
        buttonSubmit.addActionListener(buttonListener);
        buttonGoBack.addActionListener(buttonListener);
    }

    //EFFECTS:Helper method for setUpGymSessionButtonListeners
    public void setUpExerciseButtonListenersHelper2(GymSession gymSession,Exercise exercise) {
        ActionListener buttonListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == buttonGoBack) {
                    removeExerciseGUI(2);
                    setUpGymSessionGUI(gymSession, 0);
                }
            }
        };
        buttonGoBack.addActionListener(buttonListener);
    }


    //EFFECTS: Sets up the GUI to view progress
    public void setUpTrackProgressButtons() {
        GridBagLayout grid = new GridBagLayout();
        cp.setLayout(grid);
        buttonView = new JButton("View Progress");
        buttonView.setEnabled(false);
        listSetUp(0);
        buttonGoBack = new JButton("Go Back");
        if (user.getPersonalRecords().size() == 0) {
            label = new JLabel("There are no exercises to view");
        }
        addButtonWithConstraints(list,0,0,4,200);
        addButtonWithConstraints(label,0,1,4,0);
        addButtonWithConstraints(buttonGoBack,2,2,2,0);
        addButtonWithConstraints(buttonView,0,2,2,0);
        frame.setVisible(true);
        for (String exercise:user.getPersonalRecords().keySet()) {
            listModel.addElement(exercise + "   Personal Record: " + user.getPersonalRecords().get(exercise));
        }
        frame.pack();
        setUpTrackProgressButtonsListeners();
    }

    //EFFECTS: Removes the progress Buttons
    public void removeTrackProgressButtons() {
        cp.remove(list);
        cp.remove(buttonView);
        cp.remove(buttonGoBack);
        cp.remove(label);

    }

    //EFFECTS: Sets up the Progress Button Listeners over time
    public void setUpTrackProgressOverTimeButtonsListeners() {
        ActionListener buttonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == buttonGoBack) {
                    removeTrackProgressOverTimeButtons();
                    setUpTrackProgressButtons();
                }
            }
        };
        buttonGoBack.addActionListener(buttonListener);
    }

    //EFFECTS: Sets up the GUI to view progress over time
    public void setUpTrackProgressOverTimeButtons(String exercise) {
        GridBagLayout grid = new GridBagLayout();
        cp.setLayout(grid);
        listSetUp(0);
        buttonGoBack = new JButton("Go Back");
        addButtonWithConstraints(list,0,0,4,200);
        addButtonWithConstraints(buttonGoBack,0,1,4,0);
        frame.setVisible(true);
        for (int i = 0; i < user.getNumberGymSessions(); i++) {
            if (user.getGymSessions().get(i).getExercise(exercise) != null) {
                listModel.addElement(user.getGymSessions().get(i).getDate() + " " + "Weight Achieved "
                        + user.getGymSessions().get(i).getExercise(exercise).getMaxWeight());
            }
        }
        frame.pack();
        setUpTrackProgressOverTimeButtonsListeners();
    }

    //EFFECTS: Sets up the GUI to view progress over time
    public void removeTrackProgressOverTimeButtons() {
        cp.remove(list);
        cp.remove(buttonView);
        cp.remove(buttonGoBack);
        cp.remove(label);

    }

    //EFFECTS: Sets up the GUI to view progress
    public void setUpTrackProgressButtonsListeners() {
        ActionListener buttonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == buttonView) {
                    removeTrackProgressButtons();
                    String exercise = list.getSelectedValue().toString().split("   Personal",2)[0];
                    setUpTrackProgressOverTimeButtons(exercise);
                } else {
                    removeTrackProgressButtons();
                    setUpMainMenuButtons();
                }
            }
        };
        buttonGoBack.addActionListener(buttonListener);
        buttonView.addActionListener(buttonListener);
    }

    //EFFECTS: Used for various buttons, it will disable the button if no list item is selected
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            if (Arrays.stream(cp.getComponents()).anyMatch(x -> x == buttonAddSet)) {
                buttonAddSet.setEnabled(list.getSelectedIndex() != -1);
                buttonRemoveExercise.setEnabled(list.getSelectedIndex() != -1);
            } else if (Arrays.stream(cp.getComponents()).anyMatch(x -> x == buttonRemoveSet)) {
                buttonRemoveSet.setEnabled(list.getSelectedIndex() != -1);
            } else if (Arrays.stream(cp.getComponents()).anyMatch(x -> x == buttonView)) {
                buttonView.setEnabled(list.getSelectedIndex() != -1);
            }
        }
    }


    public static void main(String[] args) {
        try {
            GUI gui = new GUI();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

}
