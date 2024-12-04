package frontend.buttons;


import backend.interfaces.Figure;
import backend.model.Point;

@FunctionalInterface
public interface FigureCreator {
    abstract Figure create(Point startPoint, Point endPoint);
}
