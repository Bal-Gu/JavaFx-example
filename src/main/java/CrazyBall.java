import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class CrazyBall extends Application {


    private double getRandomArbitrary(double min, double max) {
        return Math.random() * (max - min) + min;
    }

    @Override
    public void start(Stage stage) {
        //creates a group for the shapes to bee in + the background
        Group canvas = new Group();
        //creating the background
        Image image = new Image("billardpool.png");
        ImageView img = new ImageView();
        img.setImage(image);
        img.setPreserveRatio(true);
        img.setFitHeight(900);
        canvas.getChildren().add(img);

        //method to create the borders that will be rectangle to fit the table
        rectangleMaker rm = new rectangleMaker();
        rm.initRectangles();
        ArrayList<Rectangle> rectangles = rm.getRectangles();
        //add every rectangle to the group
        for (Rectangle rectangle : rectangles) {
            canvas.getChildren().add(rectangle);
        }

        Scene scene = new Scene(canvas, 1625, 900, Color.BURLYWOOD);
        //make the 8 ball
        Circle ball = new Circle();
        //ball will be placed in the middle
        ball.setCenterX(scene.getWidth() / 2);
        ball.setCenterY(scene.getHeight() / 2);
        ball.setRadius(30.0);
        //add an image in the ball
        Image image2 = new Image("8 ball pool.png");
        //create small shadow such that the ball would float a bit
        ball.setEffect(new DropShadow(BlurType.GAUSSIAN, Color.BLACK, ball.getRadius() - 10, 0.1, 5.0, 5.0));
        ball.setFill(new ImagePattern(image2));
        canvas.getChildren().add(ball);

        //imports the image of the impact for later one saving an import collision
        Image image3 = new Image("dust.png");


        //name of the window
        stage.setTitle("Crazy ball is rolling");
        //since the background shouldn't move the window size should stay fixed
        stage.setResizable(false);
        //adding each group to appear on the stage
        stage.setScene(scene);
        //make them all visible
        stage.show();

        HashMap<ImageView, Integer> impacts = new HashMap<>();

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(18),
                new EventHandler<>() {
                    int flash = 0;
                    double dx = 12;
                    double dy = 12;
                    final double maxd = 30;
                    final double mind = 10;
                    int SpeedToReachY = 12;
                    int SpeedToReachX = 12;
                    final double minRadius = ball.getRadius() - 10;
                    final double maxRadius = ball.getRadius() + 24;
                    double radiusToAchieve = ball.getRadius();
                    int EffectPause;
                    boolean rotate = false;
                    boolean Impactspeed = false;
                    int rotatebackwards = 1;
                    int IndexOflastHit;
                    double opacity = 0.0;
                    boolean reset = false;
                    final double originalX = ball.getLayoutX();
                    final double originalY = ball.getLayoutY();


                    @Override
                    public void handle(ActionEvent t) {
                        //if the ball gets stuck in the wall it should reset to avoid getting to crazy
                        if (reset) {
                            if (opacity == 90.0) {
                                reset = false;
                            }
                            //set the ball to the middle of the screen an do a  little fad in
                            ball.setLayoutX(originalX);
                            ball.setLayoutY(originalY);
                            opacity += 5.0;
                            ball.setOpacity(opacity);


                        } else {
                            //if not paused continue moving
                            ball.setLayoutX(ball.getLayoutX() + dx);
                            ball.setLayoutY(ball.getLayoutY() + dy);
                        }


                        Bounds b = canvas.getBoundsInParent();

                        //This part should never happen!!!
                        //After testing  my ball for 1h it has a 1/10.000.000 chance to slip through both walls
                        //If this should happen the ball will get reset to the middle of the screen
                        if(!(ball.getBoundsInParent().intersects (b ))){

                            //Game will have to be reset because the clipping went wrong
                            reset = true;
                            //0  for the fade effect
                            opacity = 0.0;

                        }



                        //pause the effect for 1 frame which is enough  for the ball to  move without colliding twice with the wall
                        if (EffectPause == 0) {
                            //effect that will brighten the ball when it get's hit for 100 frames
                            if (flash != 0) {
                                ColorAdjust ca = new ColorAdjust();
                                ca.setBrightness(flash * 0.04);
                                ball.setEffect(ca);


                                flash--;
                            }

                            //has a 1/200 chance to get a rotate otherwise it will be to much spinning
                            if (5 == (int) getRandomArbitrary(0.0, 200.0)) {
                                rotate = true;
                            }
                            //if it hited the chance it will do a complete rotation
                            if (rotate) {
                                ball.setRotate(ball.getRotate() + ((rotatebackwards == 0) ? -1.0 : 1.0));
                                if (((int) ball.getRotate()) % 360 == 0) {
                                    //1/2 chance to rotate either left or right
                                    rotatebackwards = (int) getRandomArbitrary(0.0, 1.9);
                                    rotate = false;
                                }
                            }
                            //the speed after impact is set to 2 to avoid glitching out of the corners
                            if (Impactspeed) {
                                Impactspeed = false;
                                dx = dx >= 0 ? 2 : -2;
                                dy = dy >= 0 ? 2 : -2;
                            }

                            //the speed that the ball should  reach.  X and Y will gain the same speed.
                            if (SpeedToReachY == dy) {
                                int tmp = (int) getRandomArbitrary(mind, maxd);
                                //after hitting the speedTo
                                SpeedToReachY = SpeedToReachY >= 0 ? tmp : -tmp;
                                SpeedToReachX = SpeedToReachX >= 0 ? tmp : -tmp;

                                //if the speed is  higher increase the speed
                            } else if (SpeedToReachY > dy) {
                                dy++;
                                dx = SpeedToReachX > dx ? dx + 1 : dx - 1;

                                //if the speed is  lower increase the speed
                            } else if (SpeedToReachY < dy) {
                                dy--;
                                dx = SpeedToReachX < dx ? dx - 1 : dx + 1;

                            }

                            //the radius to achieve such that the ball gets bigger
                            if (radiusToAchieve == ball.getRadius()) {
                                radiusToAchieve = getRandomArbitrary(minRadius, maxRadius);
                                int tmp = (int) (radiusToAchieve * 10.0);
                                radiusToAchieve = tmp / 10.0;

                                //increments the radius by 0.1
                            } else if (radiusToAchieve > ball.getRadius()) {
                                int tmp = (int) ((ball.getRadius() + 0.1) * 10.0);
                                ball.setRadius(tmp / 10.0);

                                //decrement the radius by 0.1
                            } else {
                                int tmp = (int) ((ball.getRadius() - 0.1) * 10.0);
                                ball.setRadius(tmp / 10.0);
                            }

                            //checks if the ball touches the borders if yes gives it -y speed or -x speed
                            for (Rectangle r : rectangles) {
                                //intersection between ball and rectangles(borders)
                                if (ball.getBoundsInParent().intersects(r.getBoundsInParent())) {
                                    //check for weird bugs that scares the ball. The ball  usually then goes into
                                    // repeating the last negative speed
                                    if (rectangles.indexOf(r) == IndexOflastHit) {
                                        //Game will have to be reset because the clipping went wrong
                                        reset = true;
                                        //0  for the fade effect
                                        opacity = 0.0;

                                        break;
                                    }
                                    //left or right wall  has been hit
                                    if (rectangles.indexOf(r) == 3 || rectangles.indexOf(r) == 4) {

                                        //speed is inverted
                                        dx = -dx;
                                        SpeedToReachX = -SpeedToReachX;
                                        //reset the speed to avoid glitching out of the map
                                        Impactspeed = true;
                                        //pauses all effect expect of the movement by 1 frame
                                        //so the ball doesn't glitch out
                                        EffectPause = 1;
                                        //set the flash up
                                        flash = 20;
                                        //set the index so that we don't hit the wall twice.
                                        //This shouldn't happen
                                        // but the intersection method has decided something else.
                                        IndexOflastHit = rectangles.indexOf(r);


                                        if (rectangles.indexOf(r) == 3) {
                                            //creates a new image of the impact
                                            ImageView img3 = new ImageView(image3);
                                            //set the rotation corresponding to the impact hit.
                                            img3.setRotate(90);
                                            //set the impact to the center of the ball
                                            //has 100 pixel difference
                                            img3.setLayoutX(ball.getBoundsInParent().getMaxX() - 100);
                                            img3.setLayoutY(ball.getBoundsInParent().getMaxY() - 100);
                                            //keeps the same proportion of the image
                                            img3.setPreserveRatio(true);
                                            img3.setFitHeight(100);
                                            //makes the image visible for on the screen
                                            img3.setVisible(true);
                                            canvas.getChildren().add(img3);
                                            //adds the impact to a HashMap
                                            //since there are  no 2 image with the same opacity(fadeout)
                                            impacts.put(img3, 100);
                                        } else {
                                            //same as above with some tweaks
                                            ImageView img3 = new ImageView(image3);
                                            img3.setRotate(270);
                                            img3.setLayoutX(ball.getBoundsInParent().getMaxX() - 100);
                                            img3.setLayoutY(ball.getBoundsInParent().getMaxY() - 100);
                                            img3.setPreserveRatio(true);
                                            img3.setFitHeight(100);
                                            img3.setVisible(true);
                                            canvas.getChildren().add(img3);
                                            impacts.put(img3, 100);


                                        }

                                        break;
                                        //repats the pattern of above for the top and the bottom
                                    } else if (rectangles.indexOf(r) == 1 || rectangles.indexOf(r) == 6) {

                                        dy = -dy;

                                        SpeedToReachY = -SpeedToReachY;
                                        Impactspeed = true;
                                        flash = 20;

                                        EffectPause = 1;
                                        IndexOflastHit = rectangles.indexOf(r);

                                        if (rectangles.indexOf(r) == 1) {
                                            ImageView img3 = new ImageView(image3);
                                            img3.setRotate(180);
                                            img3.setLayoutX(ball.getBoundsInParent().getMaxX() - 100);
                                            img3.setLayoutY(ball.getBoundsInParent().getMaxY() - 100);
                                            img3.setPreserveRatio(true);
                                            img3.setFitHeight(100);
                                            img3.setVisible(true);

                                            canvas.getChildren().add(img3);
                                            impacts.put(img3, 100);
                                        } else {


                                            ImageView img3 = new ImageView(image3);
                                            img3.setRotate(0);
                                            img3.setLayoutX(ball.getBoundsInParent().getMaxX() - 100);
                                            img3.setLayoutY(ball.getBoundsInParent().getMaxY() - 100);
                                            img3.setPreserveRatio(true);
                                            img3.setFitHeight(100);
                                            img3.setVisible(true);

                                            canvas.getChildren().add(img3);
                                            impacts.put(img3, 100);

                                        }
                                        break;
                                    }


                                }
                            }
                        } else {
                            EffectPause--;

                        }
                        //impact is fading out
                        try {
                            for (Map.Entry<ImageView, Integer> entry : impacts.entrySet()) {
                                //if the opacity  is 100 gets a random fade value between 100-50 to make
                                // it an "unique" fade.
                                if (entry.getValue().equals(100)) {
                                    entry.getKey().setOpacity(entry.getValue() / 100.0);
                                    entry.setValue(entry.getValue() - (int) getRandomArbitrary(0, 50));
                                }
                                //if it's 0 get's removed from Hashmap and delete out of the screen
                                else if (entry.getValue().equals(0)) {
                                    canvas.getChildren().remove(entry.getKey());
                                    impacts.remove(entry.getKey());
                                } else {
                                    //fades by 1 each frame (diving by 100 because opacity goes from 1-0
                                    entry.setValue(entry.getValue() - 1);
                                    entry.getKey().setOpacity(entry.getValue() / 100.0);
                                }
                            }

                        } catch (Exception ignored) {
                            //Hashmap is itterating over empty place while using the iterator.
                            //nothing realy to worry about.
                        }
                    }
                }));
        //playes up to  an undertermin time
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();


    }


    public static void main(String[] args) {
        launch();
    }
}