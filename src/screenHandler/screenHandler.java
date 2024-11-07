package screenHandler;

import screenControl.ScreenController;

import javafx.application.Application;
import testSuite.Proposition;
import testSuite.Propositions;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class screenHandler {

    private static ScreenController scr;
    private static Propositions propositions;

    public static void main(String[] args) {
        // Start the JavaFX application in a separate thread
        new Thread(() -> Application.launch(ScreenController.class)).start();

        // Wait for the JavaFX application to initialize the Controller instance
        while ((scr = ScreenController.getInstance()) == null) {
            // Busy-wait until the Controller instance is available
        }

        // instantiate the Propositions object
        // Start the command line input listener
        startCommandLineInput();
    }

    private static void startCommandLineInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("CLI ready. Type 'status' to print submitted votes, 'set_ballot' to set the ballot, or " +
                "'unlock' to unlock the controller");

        while (true) {
            System.out.print("> ");
            String command = scanner.nextLine().trim().toLowerCase();

            switch (command) {
                case "turn on":
                    scr.turnOn();
                    System.out.println("Screen turned on");
                    break;
                case "turn off":
                    scr.turnOff();
                    System.out.println("Screen turned off");
                    break;
                case "status":
                    List<Proposition> submitted_propositions = scr.getSubmittedVotes();
                    System.out.println("Current state of propositions:");
                    for (Proposition p : submitted_propositions) {
                        System.out.println(p.toString());  // Uses the existing toString() method for formatted output
                    }
                    break;
                case "unlock session":
                    scr.unlockVotingSession();
                    System.out.println("Voting session unlocked");
                    break;
                case "lock session":
                    scr.lockVotingSession();
                    System.out.println("Voting session locked");
                    break;
                case "unlock user":
                    scr.unlockForUser();
                    System.out.println("Controller unlocked!");
                    break;
                case "lock user":
                    scr.lockForUser();
                    System.out.println("Controller locked!");
                    break;
                case "clear ballot":
                    scr.setPropositions(new ArrayList<>());
                    System.out.println("Ballot cleared");
                    break;
                case "set ballot":
                    scr.setPropositions(propositions.getListOfPropositions());
                    System.out.println("Ballot set");
                    break;
                case "exit":
                    System.out.println("Exiting the CLI...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Unknown command. Please type 'status', 'set_ballot', 'clear_ballot', 'unlock, 'lock, or 'exit'.");
            }
        }
    }
}



