package frontend.model;

import backend.model.Point;
import backend.model.Rectangle;

public class DrawableRectangle extends Rectangle {

    public DrawableRectangle(Point topLeft, Point bottomRight) {
        super(topLeft, bottomRight);
    }
}
