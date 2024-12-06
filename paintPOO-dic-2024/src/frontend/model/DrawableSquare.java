package frontend.model;

import backend.interfaces.Figure;
import backend.model.Point;
import backend.model.Square;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

public class DrawableSquare extends DrawFigure{

    private final Square square;

    public DrawableSquare(Point startPoint, Point endPoint, FigureInfo info){
        super(info);
        double size = calculateSize(startPoint, endPoint);
        this.square = new Square(startPoint, size);
    }

    private double calculateSize(Point startPoint, Point endPoint){
        return Math.min(
                Math.abs(endPoint.getX() - startPoint.getX()),
                Math.abs(endPoint.getY() - startPoint.getY())
        );
    }

    @Override
    public void draw(GraphicsContext gc, Color firstFillColor, Color secondFillColor, Color strokeColor){
        double x = Math.min(square.getTopLeft().getX(), square.getBottomRight().getX());
        double y = Math.min(square.getTopLeft().getY(), square.getBottomRight().getY());

        double side = Math.abs(square.getTopLeft().getX() - square.getBottomRight().getX());

        setShadowRect(gc, square.getTopLeft(), square.getBottomRight());

        gc.setFill(getGradientColor(firstFillColor, secondFillColor));
        gc.setStroke(strokeColor);

        gc.fillRect(x, y, side, side);
        gc.strokeRect(x, y, side, side);
    }

    private LinearGradient getGradientColor(Color firstFillColor, Color secondFillColor){
        return new LinearGradient(0, 0, 1, 0, true,
                CycleMethod.NO_CYCLE,
                new Stop(0, firstFillColor),
                new Stop(1, secondFillColor));
    }

    @Override
    public Figure getFigure(){
        return square;
    }
}
