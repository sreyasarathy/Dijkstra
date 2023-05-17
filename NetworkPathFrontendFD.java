import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class NetworkPathFrontendFD implements FrontendInterface {

    private Scanner userInput;
    private BackendInterface backend;
    private List<String> disabledNodes;

    /**
     * Initialize NetworkPathFrontend to make use of a provided Scanner and Backend.
     *
     * @param userInput can be used to read input from use, or to read from files
     *                  for testing
     * @param backend   placeholder by me, working implementation by Backend
     *                  Developer
     */
    public NetworkPathFrontendFD(Scanner userInput, BackendInterface backend) {
        this.userInput = userInput;
        this.backend = backend;

    }

    /**
     * Helper method to display a 79 column wide row of dashes: a horizontal rule.
     */
    private void hr() {
        System.out.println("-------------------------------------------------------------------------------");
    }

    /**
     * This loop repeated prompts the user for commands and displays appropriate
     * feedback for each. This continues until the user enters 'q' to quit.
     */
    @Override
    public void runCommandLoop() {
        hr();
        // display welcome message
        System.out.println("Welcome to the Network Path Finder App!");
        hr();

        List<String> locations;
        char command = '\0';

        while (command != 'Q') {
            //main loop will continue till user chooses Quit

            command = this.mainMenuPrompt();

            switch(command) {
                case 'D': // System.out.println(" Load [D]ata from file");
                    loadDataCommand();
                    break;

                case 'L': // System.out.println(" [L]ist all locations");
                    listLocations();
                    break;

                case 'P': // System.out.println(" Find Shortest [P]ath between Two Locations:);
                    findShortestPath();
                    break;

                case 'C': // System.out.println(" [C]hoose Locations to Disable");
                    // initialized diabledNodes by running the locationsPrompt
                    // gets list of nodes to disable from user
                    if (this.disabledNodes == null) {
                        this.disabledNodes = locationsPrompt();
                    } else {
                        this.disabledNodes.addAll(locationsPrompt());
                    }

                    // prints to the user which nodes have been disabled
                    if (!disabledNodes.isEmpty()) {
                        System.out.println("The following locations have been disabled: " + disabledNodes);
                    } else {
                        System.out.println("No locations are currently disabled.");
                    }
                    break;

                case 'E': // System.out.println(" [E]nable Locations:");
                    // gets list of locations to enable from user
                    locations = locationsPrompt();
                    for (String city : locations) {
                        // checks that the node has been disabled before enabling
                        if (this.disabledNodes.contains(city)) {
                            this.disabledNodes.remove(city);
                            System.out.println("The location " + city + " is now enabled");
                        } else {
                            System.out.println("The location " + city + " is already enabled");
                        }
                    }
                    break;

                case 'Q': // System.out.println(" [Q]uit");
                    // do nothing, containing loop condition will fail
                    break;
                default:
                    System.out.println(
                            "Didn't recognize that command.  Please type one of the letters presented within []s to identify the command you would like to choose.");
                    break;

            }
        }

        hr(); // thank user before ending this application
        System.out.println("Thank you for using the Network Path Finder App.");
        hr();

    }



    /**
     * Prints the command options to System.out and reads user's choice through
     * userInput scanner.
     */
    @Override
    public char mainMenuPrompt() {
        // display menu of choices
        System.out.println("Choose a command from the list below:");
        System.out.println("    Load [D]ata from file");
        System.out.println("    [L]ist all locations");
        System.out.println("    Find Shortest [P]ath between two locations");
        System.out.println("    [C]hoose locations to disable");
        System.out.println("    [E]nable locations");
        System.out.println("    [Q]uit");

        // read in user's choice, and trim leading or trailing whitespace
        System.out.print("Choose command: ");
        String input = userInput.nextLine().trim();
        if (input.length() == 0) // if user's choice is blank, return null character
            return '\0';
        // otherwise, return an uppercase version of the first character in input
        return Character.toUpperCase(input.charAt(0));
    }

    /**
     * Prompt user to enter filename, and display error message when loading fails.
     */
    @Override
    public void loadDataCommand() {

        System.out.print("Enter the name of the file to load: ");
        String filename = userInput.nextLine().trim();
        try {
            backend.loadData(filename);
        } catch (FileNotFoundException e) {
            System.out.println("Error: Could not find or load file: " + filename);
        }

    }

    /**
     * This method gives user the ability to interactively add or remove individual
     * cities from their list, before they disable the specified locations
     */
    public List<String> locationsPrompt() {

        List<String> locations = new ArrayList<>();
        while (true) { // this loop ends when the user types done
            System.out.println("List of locations: " + locations.toString());
            System.out.print("Location(s) to add-to/remove-from query, or type done to disable/enable: ");
            String input = userInput.nextLine()+","; //.replaceAll(",", "").trim();
            if (input.contains("done")) {// typing "done" ends this loop and method call
                return locations;
            } else {
                // otherwise the specified location's presence in the list is toggled:
                for (String city : input.split(",")) {
                    if (locations.contains(city)) {
                        // remove any cities that were already in the list
                        locations.remove(city);
                    } else if(!backend.getAllNodes().contains(city)) {
                        // check that the city is a valid location to add (exists in graph)
                        locations.remove(city);
                        System.out.println("could not add " + city + " because it is not a valid location");
                    } else {
                        // add any cities that were previously missing
                        locations.add(city);
                    }
                }
            }
        }
    }


    /**
     * Prints all the locations contained in the loaded graph
     */
    @Override
    public void listLocations() {

        // call the getAllNodes() method from backend
        List<String> list = backend.getAllNodes();
        if (list == null) {
            System.out.println("The graph is empty.");
        } else {
            System.out.println("Printing all locations...");

            for (String city : list) {
                System.out.println(city);
            }
        }

    }


    /**
     * Prints the shortest path between two locations along with its cost
     */
    @Override
    public void findShortestPath() {
        // will pass start and end locations to backend
        String startingLocation = "";
        String endingLocation = "";

        boolean isValidStartingLocation = false;
        boolean isValidEndingLocation = false;

        // checks that the user enters a starting location included in graph
        while (!isValidStartingLocation) {
            System.out.print("Input starting location: ");

            startingLocation = userInput.nextLine();
            if (!backend.getAllNodes().contains(startingLocation)) {
                System.out.println("Please enter a valid location.");
            } else {
                isValidStartingLocation = true;
            }
        }

        // checks that the user enters a ending location included in graph
        // also checks that starting location and ending location are different
        while (!isValidEndingLocation) {
            System.out.print("Input ending location: ");

            endingLocation = userInput.nextLine();
            if (!backend.getAllNodes().contains(endingLocation) || endingLocation.equals(startingLocation)) {
                System.out.println("Please enter a valid location.");
            } else {
                isValidEndingLocation = true;
            }
        }

        // gets the shortest path by calling backend method
        List<String> path = backend.getShortestPath(startingLocation, endingLocation, disabledNodes);

        // displays the shortest path
        String display = "";
        int i;
        for (i = 0; i < path.size() - 1; i++) {
            display += path.get(i) + " -> ";
        }
        display += path.get(i);

        System.out.println(
                "The shortest path from " + startingLocation + " to " + endingLocation + " is: \n	" + display);

        // displays the shortest path cost
        double cost = backend.getShortestPathCost(startingLocation, endingLocation, disabledNodes);
        System.out.println("	The cost of this path is: " + cost);
    }



}
