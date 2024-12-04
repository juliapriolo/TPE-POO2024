package frontend.model;

import backend.model.Ellipse;
import backend.model.Point;

public class DrawableEllipse extends Ellipse {

    public DrawableEllipse(Point centerPoint, double sMayorAxis, double sMinorAxis) {
        super(centerPoint, sMayorAxis, sMinorAxis);
    }

}
