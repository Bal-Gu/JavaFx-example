
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class rectangleMaker {
    public ArrayList<Rectangle> getRectangles() {
        return rectangles;
    }

    private ArrayList<Rectangle> rectangles;

    public void initRectangles() {
        this.rectangles =  new ArrayList<>();
        //setting the rectangle from top  left to right bottom. Moving from left to right
        topRightCorner();
        topMiddleBar();
        topLeftCorner();
        MiddleLeftBar();
        MiddleRightBar();
        bottomRightCorner();
        bottomMiddleBar();
        bottomLeftCorner();


    }
        //methode name should tell you which rectangle is initialised
    private void bottomLeftCorner() {
        //Creats a new rectangle
        Rectangle r = new Rectangle();
        //coordinates matches the picutres border
        r.setRotate(45.0);
        r.setY(804);
        r.setX(20);
        r.setWidth(100);
        r.setHeight(23);
        //shouldn't be visible  to the screen
        r.setVisible(false);

        this.rectangles.add(r);
    }

    private void bottomRightCorner() {
        Rectangle r = new Rectangle();
        r.setRotate(135.0);
        r.setY(804);
        r.setX(1502);
        r.setWidth(100);
        r.setHeight(23);
        r.setVisible(false);

        this.rectangles.add(r);


    }

    private void MiddleRightBar() {
        Rectangle r = new Rectangle();
        r.setRotate(90.0);
        r.setY(445);
        r.setX(1200);
        r.setWidth(770);
        r.setHeight(23);
        r.setVisible(false);

        this.rectangles.add(r);

    }

    private void MiddleLeftBar() {
        Rectangle r = new Rectangle();
        r.setRotate(90.0);
        r.setY(445);
        r.setX(-338);
        r.setWidth(770);
        r.setHeight(23);
        r.setVisible(false);

        this.rectangles.add(r);
    }

    private void topLeftCorner() {
        Rectangle r = new Rectangle();
        r.setRotate(135.0);
        r.setY(85);
        r.setX(20);
        r.setWidth(100);
        r.setHeight(23);
        r.setVisible(false);

        this.rectangles.add(r);

    }

    private void topRightCorner() {
        Rectangle r = new Rectangle();
        r.setRotate(45.0);
        r.setY(80);
        r.setX(1500);
        r.setWidth(100);
        r.setHeight(23);
        r.setVisible(false);

        this.rectangles.add(r);

    }

    private void bottomMiddleBar() {
        Rectangle r = new Rectangle();
        r.setY(835);
        r.setX(50);
        r.setWidth(1550);
        r.setHeight(23);
        r.setVisible(false);

        this.rectangles.add(r);
    }

    private void topMiddleBar() {
        Rectangle r = new Rectangle();
        r.setY(70);
        r.setX(50);
        r.setWidth(1600);
        r.setHeight(23);
        r.setVisible(false);
        this.rectangles.add(r);
    }
}
