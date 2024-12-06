package frontend.model;

import backend.interfaces.Figure;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class DrawFigure {

    public abstract void draw(GraphicsContext gc, Color firstFillColor, Color secondFillColor, Color colorStroke);

    public abstract Figure getFigure();


}