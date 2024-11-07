package screenControl;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import testSuite.Proposition;
import javafx.scene.layout.Region;

public class VoteScreen {

  private Proposition proposition;
  private ScreenController controller;
  private Scene scene;

  // Main function to create a screen
  public int createScreen(Proposition prop) {

    String title = prop.getName();
    String desc = prop.getDescription();
    String[] options = prop.getOptions();
    int maxNumSelections = prop.getMaxNumSelections();

    /*
     * We need to save return value and handle it later, since we can't 
     * direclty return from inside a setOnAction block being a void method. 
    */
    int RET = -1;

    // Title Label
    Label titleLabel = new Label(title);
    titleLabel.setFont(Font.font("Georgia", FontWeight.EXTRA_BOLD, 28));
    titleLabel.setStyle("-fx-text-fill: black;");

    // Description Label
    Label descriptionLabel = new Label("Select " + maxNumSelections + " Option" + (maxNumSelections > 1 ? "s" : ""));
    descriptionLabel.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 20));
    descriptionLabel.setStyle("-fx-text-fill: black;");

    // Options Box
    FlowPane optionsBox = new FlowPane(15, 15);
    optionsBox.setAlignment(Pos.TOP_CENTER);

    // ToggleGroup for options
    ToggleGroup toggleGroup = new ToggleGroup();

    for (int i = 0; i < options.length; i++) {
      String optionText = options[i];
      ToggleButton optionButton = new ToggleButton(optionText);
      optionButton.setMinWidth(250);
      optionButton.setFont(Font.font("Times New Roman", FontWeight.BOLD, 18));
      optionButton.setStyle(
          "-fx-background-color: lightgray; " +
              "-fx-text-fill: black; " +
              "-fx-border-color: darkgray; " +
              "-fx-border-radius: 10; " +
              "-fx-background-radius: 10; " +
              "-fx-padding: 10px;");

      final int index = i;

      if (optionText.isEmpty()) {
        optionButton.setDisable(true);
        optionButton.setStyle(
            "-fx-background-color: #e0e0e0; " +
                "-fx-text-fill: gray; " +
                "-fx-border-color: darkgray; " +
                "-fx-border-radius: 10; " +
                "-fx-background-radius: 10; " +
                "-fx-padding: 10px;");
      } else {
        optionButton.setOnAction(e -> {
          // Set ret val to 0 for no expected resposne 
          RET = 0;
        });
      }

      optionsBox.getChildren().add(optionButton);
    }

    // Back Button
    Button backButton = new Button("Back");
    styleNavigationButton(backButton);
    backButton.setOnAction(e -> {
      // Implement return logic for back action (-2)
    });

    // Next Button
    Button nextButton = new Button("Next");
    styleNavigationButton(nextButton);
    nextButton.setOnAction(e -> {
      // Implement return logic for next action (-1)
    });

    // Navigation Box
    HBox navigationBox = new HBox(30, backButton, nextButton);
    navigationBox.setAlignment(Pos.CENTER);

    // Spacer
    Region spacer = new Region();
    spacer.setPrefHeight(30);

    // Main Layout
    VBox mainLayout = new VBox(20, spacer, titleLabel, descriptionLabel, optionsBox, navigationBox);
    mainLayout.setPadding(new Insets(20));
    mainLayout.setAlignment(Pos.CENTER);
    mainLayout
        .setStyle("-fx-background-color: linear-gradient(to bottom, #e6ecf2, #cfd8e4); -fx-background-radius: 20;");

    // Save the created scene to this instance of VoteScene
    setScene(new Scene(mainLayout, 400, 600));

    // if(RET > 0){
    //   return RET;
    // }
    return RET;
  }

  private void styleNavigationButton(Button button) {
    button.setFont(Font.font("Arial", FontWeight.BOLD, 16));
    button.setStyle(
        "-fx-background-color: #4CAF50; " +
            "-fx-text-fill: white; " +
            "-fx-background-radius: 10; " +
            "-fx-padding: 10px 20px;");
  }

  public void setScene(Scene scene) {this.scene = scene; }
  public Scene getScene(){ return this.scene; }
}