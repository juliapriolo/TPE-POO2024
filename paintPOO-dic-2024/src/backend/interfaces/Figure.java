package backend.interfaces;

import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public interface Figure extends Movable{ //ver despues si queda figure como interfaz o se cambia a clase abstracta

    void move(double deltaX, double deltaY);


    boolean contains(Point eventPoint);
}
