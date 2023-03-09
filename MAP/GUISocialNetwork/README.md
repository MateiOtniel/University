# Social Network - Graphical User Interface App
## A fully functional JavaFX project that allows users to simulate a simple social media platform.

### This project comes with the following functionalities:
 * Login user (the input being validated)  
 > #### After logging in, the user can:
 * Add, Delete users from friend list
 * Search by name existing users
 * See the friends list
 * Accept, deny or unsend friend requests
 * View conversations with his friends
 * Send messages

 > *Every crud action updates the databases and therefore, the app.*

### Technologies and apps used:
 * JavaFX and Gradle for the structure
 * PostgreSQL for creating the databases and storing the information
 * SceneBuilder for creating the GUI
 * CSS for styling

### Concepts used:
 * OOP principles:
  > For example, all four principles where respected when created the repositories for the app:  
  >
  > *interface Repository -> partially implemented by AbstractRepository -> extended by FileRepository and DatabaseRepository (depending on the scope of the app)*
 * SOLID principles
 * Design patterns:
 > Singleton - the Service class (which manages to model the data received from repositories) have just one instance
 > Observer - the Main Controller class updates all the items in the app when a database modification appears
