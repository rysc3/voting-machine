package screenControl;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import testSuite.Proposition;

import java.util.ArrayList;
import java.util.Locale;


/**
 * TODO:
 * Clean out ALL admin logic - completed
 *
 * Modify function that builds the "scene" to handle different combos of inputs -- completed
 *
 * Add functionality for multiple options to be picked  -- completed
 *
 */
public class





VoteScreen {

    private Proposition proposition;
    private ScreenController controller;

    public VoteScreen(Proposition proposition, ScreenController controller) {
        this.proposition = proposition;
        this.controller = controller;
    }

    public Scene draw() {
        if ("Welcome".equals(proposition.getName())) {
            return createWelcomeScreen();
        } else if ("Admin".equals((proposition.getName()))) {
            return null;
        } else {
            return createVotingScreen();
        }
    }

    public Scene drawOffScreen() {
        VBox layout = new VBox();
        layout.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        return new Scene(layout, 400, 600);
    }

    public Scene drawOnScreen() {
        VBox layout = new VBox();
        layout.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        return new Scene(layout, 400, 600);
    }


    /**
     * Possibly good to reskin for a text only page
     */
    private Scene createWelcomeScreen() {
        Label welcomeToLabel = new Label("Welcome to");
        welcomeToLabel.setFont(Font.font("Georgia", FontWeight.EXTRA_BOLD, 36));
        welcomeToLabel.setStyle("-fx-text-fill: #2c3e50;");
        welcomeToLabel.setTextAlignment(TextAlignment.CENTER);

        Label yearLabel = new Label("2024");
        yearLabel.setFont(Font.font("Georgia", FontWeight.EXTRA_BOLD, 48));
        yearLabel.setStyle("-fx-text-fill: #2c3e50;");
        yearLabel.setTextAlignment(TextAlignment.CENTER);

        Label electionLabel = new Label("Election!");
        electionLabel.setFont(Font.font("Georgia", FontWeight.EXTRA_BOLD, 36));
        electionLabel.setStyle("-fx-text-fill: #2c3e50;");
        electionLabel.setTextAlignment(TextAlignment.CENTER);


        Button startButton = new Button("BEGIN ➔");
        startButton.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        startButton.setMinWidth(200);
        startButton.setStyle(
                "-fx-background-color: #4a90e2; " +
                        "-fx-text-fill: white; " +
                        "-fx-border-radius: 15; " +
                        "-fx-background-radius: 15; " +
                        "-fx-padding: 10px;"
        );
        startButton.setOnMouseEntered(e -> startButton.setStyle(
                "-fx-background-color: #357ABD; " +
                        "-fx-text-fill: white; " +
                        "-fx-border-radius: 15; " +
                        "-fx-background-radius: 15; " +
                        "-fx-padding: 10px;"
        ));
        startButton.setOnMouseExited(e -> startButton.setStyle(
                "-fx-background-color: #4a90e2; " +
                        "-fx-text-fill: white; " +
                        "-fx-border-radius: 15; " +
                        "-fx-background-radius: 15; " +
                        "-fx-padding: 10px;"
        ));
 //       startButton.setOnAction(e -> controller.navigateBegin());

        Stop[] stops = new Stop[] { new Stop(0, Color.web("#DCE2F2")), new Stop(1, Color.web("#F4D3D3")) };
        LinearGradient gradient = new LinearGradient(0, 0, 0, 1, true, null, stops);

        Region spacer = new Region();
        spacer.setPrefHeight(80);
        VBox layout = new VBox(20, spacer, welcomeToLabel, yearLabel, electionLabel, startButton);
        layout.setPadding(new Insets(0, 0, 140, 0));
        layout.setAlignment(Pos.CENTER);
        layout.setBackground(new Background(new BackgroundFill(gradient, CornerRadii.EMPTY, Insets.EMPTY)));

        return new Scene(layout, 400, 600);
    }

    /**
     * Possibly good to reskin for all 'ballot' questions
     */
    private Scene createVotingScreen() {

        Label titleLabel = new Label(proposition.getName());
        titleLabel.setFont(Font.font("Georgia", FontWeight.EXTRA_BOLD, 28));
        titleLabel.setStyle("-fx-text-fill: black;");


        Label descriptionLabel = new Label(proposition.getDescription());
        descriptionLabel.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 20));
        descriptionLabel.setStyle("-fx-text-fill: black;");

        String numOptionString = "";
        if (proposition.getOptions() != null){
            numOptionString = "Select " + proposition.getMaxNumSelections() + " Option" + (proposition.getMaxNumSelections() > 1 ? "s" : "");
        }
        Label numOptionsLabel = new Label(numOptionString);
        numOptionsLabel.setFont(Font.font("Times New Roman", FontWeight.MEDIUM, 14));
        numOptionsLabel.setStyle("-fx-text-fill: black;");

        FlowPane optionsBox = new FlowPane(5, 5);
        optionsBox.setAlignment(Pos.CENTER);


        //Fill Flowpane with proposition options
        if (proposition.getOptions() != null){
            for (int i = 0; i < proposition.getOptions().length; i++) {
                String optionText = proposition.getOptions()[i];
                ToggleButton optionButton = new ToggleButton(optionText);
                optionButton.setMinWidth(100);
                styleOptionButton(optionButton);

                final int index = i;

                /**
                 * TODO:
                 * Modify the button action to do what exists below, BUT only if it is entering or exiting the queue!!
                 * theres a poop ass mspaint photo in discord showing what i mean
                 */
                optionButton.setOnAction(e -> {
                    boolean isSelected = proposition.getSelections()[index];
                    if (isSelected || proposition.getNumCurrentSelections() < proposition.getMaxNumSelections()){
                        proposition.setSelection(index, !isSelected);
                        updateButtonStyle(optionButton, !isSelected);
                    }

                    System.out.println("isSelected: " + isSelected);
                    System.out.println("numCurrSelections: " + proposition.getNumCurrentSelections());
                    System.out.println("maxSelections: " + proposition.getMaxNumSelections());

                });

                optionsBox.getChildren().add(optionButton);

            }
        }




        HBox navigationBox = new HBox(30);
        navigationBox.setAlignment(Pos.CENTER);

        if ( proposition.getNavOptions() != null){
            for (int j = 0; j < proposition.getNavOptions().length; j++) {
                String navText = proposition.getNavOptions()[j];
                Button navButton = new Button(navText.toUpperCase(Locale.ROOT));
                navButton.setMinWidth(250);
                styleNavigationButton(navButton);
                navButton.setId(Integer.toString(j));
                navButton.setOnAction(e -> controller.buttonHandler(e));
                navigationBox.getChildren().add(navButton);
            }
        }

        Region spacer = new Region();
        spacer.setPrefHeight(30);

        VBox mainLayout = new VBox(20, spacer, titleLabel, descriptionLabel, numOptionsLabel, optionsBox, navigationBox);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setStyle("-fx-background-color: linear-gradient(to bottom, #e6ecf2, #cfd8e4); -fx-background-radius: 20;");

        return new Scene(mainLayout, 400, 600);
    }

    private void styleOptionButton(ToggleButton button) {
        button.setFont(Font.font("Times New Roman", FontWeight.BOLD, 18));
        button.setStyle(
                "-fx-background-color: lightgray; " +
                        "-fx-text-fill: black; " +
                        "-fx-border-color: darkgray; " +
                        "-fx-border-radius: 10; " +
                        "-fx-background-radius: 10; " +
                        "-fx-padding: 10px;"
        );
    }

    private void updateButtonStyle(ToggleButton button, boolean isSelected) {
        button.setStyle(
                isSelected
                        ? "-fx-background-color: darkblue; -fx-text-fill: white; -fx-border-radius: 10; -fx-background-radius: 10; -fx-padding: 10px; -fx-font-size: 18px; -fx-font-family: 'Times New Roman'; -fx-font-weight: bold; -fx-border-color: darkgray;"
                        : "-fx-background-color: lightgray; -fx-text-fill: black; -fx-border-radius: 10; -fx-background-radius: 10; -fx-padding: 10px; -fx-font-size: 18px; -fx-font-family: 'Times New Roman'; -fx-font-weight: bold; -fx-border-color: darkgray;"
        );
    }


    /**
     * QOL function to style all nav buttons. Saves redundant code
     */
    private void styleNavigationButton(Button button) {
        button.setFont(Font.font("Georgia", FontWeight.BOLD, 16));
        button.setMinWidth(120);
        button.setStyle(
                "-fx-background-color: darkblue; " +
                        "-fx-text-fill: white; " +
                        "-fx-border-radius: 10; " +
                        "-fx-background-radius: 10; " +
                        "-fx-padding: 10px;"
        );
        button.setOnMouseEntered(e -> button.setStyle(
                "-fx-background-color: navy; " +
                        "-fx-text-fill: white; " +
                        "-fx-border-radius: 10; " +
                        "-fx-background-radius: 10; " +
                        "-fx-padding: 10px;"
        ));
        button.setOnMouseExited(e -> button.setStyle(
                "-fx-background-color: darkblue; " +
                        "-fx-text-fill: white; " +
                        "-fx-border-radius: 10; " +
                        "-fx-background-radius: 10; " +
                        "-fx-padding: 10px;"
        ));
    }

}