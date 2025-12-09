⛳ GolfControl - Golf Management System


GolfControl is a desktop application built with Java and JavaFX, designed for the comprehensive management of golf players, tournaments, and user preferences.
The project implements a clean MVC (Model-View-Controller) architecture and utilizes the DAO design pattern for efficient data persistence.

✨ Key Features
🏌️ Golfers Management (CRUD)

    Comprehensive Listing: Detailed view of all registered players.

    Computed Properties:

        Category: Automatically calculated based on age (Junior, Senior, Veteran).

        Full Name: Real-time concatenation of first and last names.

    Advanced Search: Implementation of a Debounce (500ms) mechanism to filter by name, surname, or country without overloading the database.

    Editing: Seamless interface to add, modify, and delete golfers.

🏆 Tournament Management

    Input Validation: The "Year" field is strictly restricted to 4-digit numbers using a custom TextFormatter.

    Dynamic Status: A computed column that automatically determines if the tournament is "Finished", "Current Season", or "Upcoming" by comparing the tournament year with the system date.

    Filtering: Search functionality by tournament name, year, or country.

❤️ Favorites System (Many-to-Many Relation)

    Personalized list where logged-in users can save their favorite players.

    Visualization: Exclusive view to manage the user's favorite golfers.

    Management: Logic to add favorites from the main list and remove them from the favorites view, handling the N:M relationship in the database.

🔐 User & Session Management

    Authentication: Secure login system against the database.

    Navigation: Custom ViewSwitcher utility to handle scene transitions smoothly while maintaining the user session.

🛠️ Tech Stack

    Language: Java 17+

    GUI Framework: JavaFX (FXML)

    Database: MySQL

    Connectivity: JDBC

    Design Patterns:

        MVC: Strict separation between UI, data logic, and control logic.

        DAO: Data Access Object pattern (GolfistasDAO, TorneoDAO, FavoritosDAO).

        Singleton: Used for the SessionManager to handle the active user state.

🗄️ Database Structure

The project relies on a relational database named golfdi. The core tables are:

    usuarios: Stores user credentials and profile data.

    golfistas: Stores player information (name, age, club type, country...).

    torneo: Stores tournament details.

    favoritos: Intermediate table (N:M) linking usuarios and golfistas.

🚀 Installation & Setup
Prerequisites

    JDK 17 or higher.

    Maven.

    MySQL Server installed and running.

    Steps

    Clone the repository:
    Bash

    git clone https://github.com/your-username/GolfControl.git

    Database Setup:

        Create a database named golfdi.

        Import the SQL script provided in the /sql folder or execute the table creation queries.

    Configure Connection:

        Navigate to src/main/java/.../db/DataBaseConnection.java.

        Update the static constants (URL, USER, PASSWORD) to match your local MySQL configuration.

    Run:

        Open the project in IntelliJ IDEA or Eclipse.

        Run the HelloApplication.java class.

✒️ Author

    [Your Name] - Full Stack Developer - YourGitHubProfile

📄 License

This project is for educational purposes.