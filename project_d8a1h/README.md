# My Personal Project

## Gym Progress Tracker
*The application is intended for people who go to the gym. Its purpose is to aid
gym-goers in the recording and tracking of their progress. By entering in what specific
exercises are accomplished in a **session**, the **reps** completed, and the **weight** achieved,
users are able to track their progress over numerous sessions through statistics. This project is of interest
to me because I go to the gym.*

*User Stories:*
- As a user, I want to be able to add today's gym session to my list of gym sessions
- As a user, I want to be able to add today's exercises to today's gym session.
- As a user, I want to be able to add reps and a weight count to today's exercises.
- As a user, I want to be able to view today's exercises.
- As a user, I want to be able to select yesterday's gym session and view its exercises.
- As a user, when I select the quit option from the application menu, I want to be reminded to save my Progress
- As a user, when I start the application, I want my the option to load my progress

Citations
- Borrowed ui features from the Teller app
- Took and modified code from the persistence package of the User demo
- Borrowed functionality from the textDemo component sample
- https://www.youtube.com/playlist?list=PLWfJeLqH0KUUicazPYhTdnZydkC10h6wB 
- I watched all the videos in this playlist and based my GUI code off of the knowledge i obtained from it
# Instructions for Grader

- You can generate the first required event related to adding Xs to a Y by 
  - going to record GymSession, entering a date, and recording exercises
- You can generate the second required event related to adding Xs to a Y by...
  - Selecting an Exercise from the GymSession, and adding Sets to it
- You can also Remove an exercise/set from a GymSession/Exercise.
- You can locate my visual component by...
  - The login screen
- You can save the state of my application by...
  - Pressing save
- You can reload the state of my application by...
  - Pressing Load user data

# EventLog Sample
Thu Dec 01 01:15:49 PST 2022
Added Gym Session (1/1/1) to  John's Records

Thu Dec 01 01:15:52 PST 2022
Added Bench to 1/1/1 Gym Session

Thu Dec 01 01:15:54 PST 2022
Added Squat to 1/1/1 Gym Session

Thu Dec 01 01:15:56 PST 2022
Removed Squat from 1/1/1 Gym Session (1/1/1)

Thu Dec 01 01:16:02 PST 2022
Added set (Weight: 1000 Reps: 1) to Bench

Thu Dec 01 01:16:04 PST 2022
Added set (Weight: 1 Reps: 1) to Bench

Thu Dec 01 01:16:05 PST 2022
Removed set (Weight: 1 Reps: 1) from Bench


Process finished with exit code 0

# Phase 4:Task 3
- The image is located in the data folder
- Though it is not apparent in the diagram, I had a lot of dependencies within the project, for example 
- I had to pass a user through the gymSession, Set, and Exercise.
- Knowing what I know now, I would use the singleton pattern instead. Other than that, I am satisfied with the current model.
