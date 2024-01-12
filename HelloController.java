package com.example.stickhero;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.animation.RotateTransition;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;
import javafx.animation.SequentialTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


import java.io.IOException;


public class HelloController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    public void switchToGameScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("game-scene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToHome(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToGameOverScene(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("gameOver-scene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);

        Label scoreFinalLabel = (Label) scene.lookup("#scoreFinal");
        if (scoreFinalLabel != null) {
            scoreFinalLabel.setText(String.valueOf(score));
        }

        stage.show();
    }

    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    private Rectangle rectangle;
    @FXML
    private Rectangle base1;
    @FXML
    private Rectangle base2;
    @FXML
    private Rectangle player;
    @FXML
    private Label scoreLabel;
    @FXML
    private Label cherryScore;
    @FXML
    private Label cherryScoreFinal;
    @FXML
    private Label scoreFinal;
    private Timeline increaseHeightTimeline;
    private Timeline fallDownTimeline;
    private RotateTransition rotateTransition;
    private TranslateTransition translateTransition;

    public void initialize(){

        increaseHeightTimeline = new Timeline(new KeyFrame(Duration.millis(10), event -> increaseHeight()));
        increaseHeightTimeline.setCycleCount(Timeline.INDEFINITE);

//        if ((rectangle.getHeight() < ((base2.getLayoutX() + (base2.getWidth())/2) - (base1.getWidth()))) && rectangle.getHeight() > (base2.getLayoutX() - (base2.getWidth()/2) - base1.getWidth())){
//            //startMoveAnimation(base2.getLayoutX() - (base1.getLayoutX() + (base1.getWidth()/2)));
//            System.out.println();
//        }

        if (rectangle != null) {
            System.out.println(rectangle.getHeight());
        }

    }


    @FXML
    private void handleMousePressed(MouseEvent event) {
        System.out.println("Mouse Pressed");
        if (event.isPrimaryButtonDown()) {
            // Start increasing height when the left mouse button is pressed
            increaseHeightTimeline.play();
        }
    }

    private int score = 0;
    private double prevLength;

    public void setScore(int score) {
        scoreFinal.setText(String.valueOf(score));
    }

    @FXML
    private Pane gamePane;  // Assuming you have a Pane in your game-scene.fxml to hold the cherries

    private void generateCherry() {
        // Load cherry image
        Image cherryImage = new Image("C:/Users/nisha/Downloads/StickHero/src/main/resources/com/example/stickhero/cherries1.png");  // Replace with the actual path

        // Create ImageView for the cherry
        ImageView cherryView = new ImageView(cherryImage);
        cherryView.setFitWidth(30);  // Set the width of the cherry image
        cherryView.setFitHeight(30); // Set the height of the cherry image

        // Generate random X-coordinate within the desired range (150 to 300)
        double randomX = Math.random() * (300 - 150) + 150;

        // Set fixed Y-coordinate (adjust as needed)
        double fixedY = 532;

        // Set the position of the cherry
        cherryView.setLayoutX(randomX);
        cherryView.setLayoutY(fixedY);

        // Add the cherry to the gamePane
        gamePane.getChildren().add(cherryView);

        // Set up an event handler for the cherry to handle collection logic
        cherryView.setOnMouseClicked(event -> {
            // Add logic for cherry collection (e.g., increase score)
            gamePane.getChildren().remove(cherryView); // Remove the collected cherry
            score++;  // Assuming score is a class member variable
            cherryScore.setText(String.valueOf(score));  // Update the score label
        });
    }

    @FXML
    private void handleMouseReleased(MouseEvent event) {
        System.out.println("Mouse Released");
        if (!event.isPrimaryButtonDown()) {
            // Stop increasing height when the left mouse button is released
            increaseHeightTimeline.stop();

            // Get the height of the rectangle after the mouse is released
            double finalRectangleHeight = rectangle.getHeight();

            // Print or use the finalRectangleHeight as needed
            System.out.println("Final Rectangle Height: " + finalRectangleHeight);

            startRotationAnimation();

            if (score == 1){
                startMoveAnimationFastRectY(0.975*prevLength);
            }

            if (score == 2){
                startMoveAnimationFastRectY(2*1.03*prevLength);
            }

            if (score == 3){
                startMoveAnimationFastRectY(3*0.94*prevLength);
            }

            // Start movement animation after rotation
            rotateTransition.setOnFinished(rotationFinishedEvent -> {
                if (finalRectangleHeight >= 260 && finalRectangleHeight <= 322) {

                    generateCherry();

                    // The rotation has finished, start the movement animation
                    startMoveAnimation(314); // Replace 'desiredX' with your actual value
                    translateTransition.setOnFinished(playerMoveFinishedEvent -> {

                        translateTransition = new TranslateTransition(Duration.seconds(1), player);
                        translateTransition.setByX(-314);
                        startMoveAnimationFastX(-314);

                        translateTransition.setOnFinished(playerMoveFastFinishedEvent -> {
                            rectangle.setHeight(7);
                            rectangle.setLayoutX(106);
                            rectangle.setLayoutY(551);
                            startMoveAnimationFastRectX(-finalRectangleHeight/2);
                            startMoveAnimationFastRectY(finalRectangleHeight/2);
                            startRotationAnimation();
                            startRotationAnimation();
                            startRotationAnimation();

                            score++;
                            prevLength = finalRectangleHeight;
                            System.out.println("Score: " + score);
                            scoreLabel.setText(String.valueOf(score));

                        });

                        player.setLayoutX(82);
                        player.setLayoutY(532);
                    });

                    translateTransition.play();
                } else if (finalRectangleHeight < 260) {

                    generateCherry();
                    // The rotation has finished, start the movement animation first
                    startMoveAnimation(finalRectangleHeight + 5); // Replace 'desiredX' with your actual value

                    // Set up the onFinished event for the movement animation
                    translateTransition.setOnFinished(playerMoveFinishedEvent -> {
                        // The player has moved, start the fall-down animation
                        startFallDownAnimation();
                        this.scoreFinal = new Label(String.valueOf(score));
                        scoreFinal.setText(String.valueOf(score));

                        // Set up the onFinished event for the fall-down animation
                        translateTransition.setOnFinished(fallDownFinishedEvent -> {
                            try {
                                switchToGameOverScene(event);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                    });
                }
                else if (finalRectangleHeight > 322) {

                    generateCherry();
                    // The rotation has finished, start the movement animation first
                    startMoveAnimation(finalRectangleHeight + 8); // Replace 'desiredX' with your actual value

                    // Set up the onFinished event for the movement animation
                    translateTransition.setOnFinished(playerMoveFinishedEvent -> {
                        // The player has moved, start the fall-down animation
                        startFallDownAnimation();
                        this.scoreFinal = new Label(String.valueOf(score));
                        scoreFinal.setText(String.valueOf(score));

                        // Set up the onFinished event for the fall-down animation
                        translateTransition.setOnFinished(fallDownFinishedEvent -> {
                            try {
                                switchToGameOverScene(event);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                    });
                }
            });
        }
    }


    private TranslateTransition createMoveAnimation(double distance) {
        // Create a TranslateTransition for x-axis movement
        TranslateTransition moveTransition = new TranslateTransition(Duration.seconds(1), player);
        moveTransition.setByX(distance);
        return moveTransition;
    }

    private TranslateTransition createFallDownAnimation() {
        // Create a TranslateTransition for y-axis movement (fall down)
        TranslateTransition fallDownTransition = new TranslateTransition(Duration.seconds(1), player);
        fallDownTransition.setByY(500); // Replace 'desiredY' with your actual value
        return fallDownTransition;
    }


    private void startMoveAnimation(double distance) {
        // Create a TranslateTransition for x-axis movement
        translateTransition = new TranslateTransition(Duration.seconds(1), player);
        translateTransition.setByX(distance);
        translateTransition.play();
    }

    private void startMoveAnimationFastX(double distance) {
        // Create a TranslateTransition for x-axis movement
        translateTransition = new TranslateTransition(Duration.seconds(0.0001), player);
        translateTransition.setByX(distance);
        translateTransition.play();
    }

    private void startMoveAnimationFastRectX(double distance) {
        // Create a TranslateTransition for x-axis movement
        translateTransition = new TranslateTransition(Duration.seconds(0.0001), rectangle);
        translateTransition.setByX(distance);
        translateTransition.play();
    }

    private void startMoveAnimationFastRectY(double distance) {
        // Create a TranslateTransition for y-axis movement
        translateTransition = new TranslateTransition(Duration.seconds(0.0001), rectangle);
        translateTransition.setByY(distance);
        translateTransition.play();
    }

    private void startFallDownAnimation() {
        // Create a TranslateTransition for y-axis movement (fall down)
        translateTransition = new TranslateTransition(Duration.seconds(1), player);
        translateTransition.setByY(500); // Replace 'desiredY' with your actual value
        translateTransition.play();
    }

    private void startRotationAnimation() {
        // Set the pivot point for rotation (lower right corner of the rectangle)
        rectangle.setTranslateX((rectangle.getHeight())/2);
        rectangle.setTranslateY((rectangle.getHeight())/2);
        rectangle.setTranslateZ((rectangle.getHeight())/2);

        // Create a RotateTransition for 90 degrees rotation
        rotateTransition = new RotateTransition(Duration.seconds(1), rectangle);
        rotateTransition.setAxis(Rotate.Z_AXIS);
        rotateTransition.setByAngle(90);
        rotateTransition.play();
    }

    private void startFallDownTimeline(){

        fallDownTimeline = new Timeline(
                new KeyFrame(Duration.millis(10), event -> fallDown()));
        fallDownTimeline.setCycleCount(Timeline.INDEFINITE);
        fallDownTimeline.play();

    }

    private void fallDown() {
        // Adjust the horizontal movement value as needed
        double horizontalMovement = 2.0;

        // Calculate the new x position
        double newX = rectangle.getX() + horizontalMovement;

        // Set the new x position
        rectangle.setX(newX);
    }

    private void increaseHeight() {
        // Adjust the height increase value as needed
        double heightIncrease = 1.0;
        double newY = rectangle.getY() - heightIncrease;

        // Increase the height of the rectangle
        rectangle.setHeight(rectangle.getHeight() + heightIncrease);
        rectangle.setY(newY);
    }
}