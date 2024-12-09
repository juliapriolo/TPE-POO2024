package backend.interfaces;

import backend.model.Point;

public interface Figure extends Movable{

    Point getStartPoint();

    Point getEndPoint();

    Point getCenterPoint();

    double getWidth();

    double getHeight();

    boolean contains(Point eventPoint);

    Figure duplicateWithOffset(double offsetX, double offsetY);

}

