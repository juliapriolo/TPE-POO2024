package backend.interfaces;

import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public interface Figure extends Movable{ //ver despues si queda figure como interfaz o se cambia a clase abstracta

    void draw(GraphicsContext gc, Color fillColor, Color strokeColor);

    boolean contains(Point eventPoint);
}
