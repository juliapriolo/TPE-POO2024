package backend.interfaces;

import backend.model.Point;

public interface Figure extends Movable{ //ver despues si queda figure como interfaz o se cambia a clase abstracta

    void move(double deltaX, double deltaY);

    double getWidth();

    double getHeight();

    boolean contains(Point eventPoint);

    Point getStartPoint();

    Point getEndPoint();
}
