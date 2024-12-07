package frontend.model;

import backend.interfaces.Figure;
import backend.model.Point;
import backend.model.Rectangle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.*;


public class DrawableRectangle extends DrawFigure {

    private final Rectangle rectangle;

    public DrawableRectangle(Point topLeft, Point bottomRight, FigureInfo info) {
        super(info);
        this.rectangle = new Rectangle(topLeft, bottomRight);
    }

    @Override
    public void draw(GraphicsContext gc, FigureInfo info, Color strokeColor) {

        double x = Math.min(rectangle.getTopLeft().getX(), rectangle.getBottomRight().getX());
        double y = Math.min(rectangle.getTopLeft().getY(), rectangle.getBottomRight().getY());

        double width = Math.abs(rectangle.getTopLeft().getX() - rectangle.getBottomRight().getX());
        double height = Math.abs(rectangle.getTopLeft().getY() - rectangle.getBottomRight().getY());

        setShadowRect(gc, rectangle.getTopLeft(), rectangle.getBottomRight());

        gc.setFill(getGradientColor(info.getColor(), info.getSecondaryColor()));
        gc.setStroke(strokeColor);

        gc.fillRect(x, y, width, height);
        gc.strokeRect(x, y, width, height);

        if(info.getArcType()){
            setRectangleArcType(gc);
        }
        gc.setLineWidth(1);
    }

    private LinearGradient getGradientColor(Color firstFillColor, Color secondFillColor){
        return new LinearGradient(0, 0, 1, 0, true,
                CycleMethod.NO_CYCLE,
                new Stop(0, firstFillColor),
                new Stop(1, secondFillColor));
    }

    private void setRectangleArcType(GraphicsContext gc){
        double x = rectangle.getTopLeft().getX();
        double y = rectangle.getTopLeft().getY();

        double width = Math.abs(x - rectangle.getBottomRight().getX());
        double height = Math.abs(y - rectangle.getBottomRight().getY());

        gc.setLineWidth(10);
        gc.setStroke(Color.LIGHTGRAY);
        gc.strokeLine(x, y, x + width, y);
        gc.strokeLine(x, y, x, y + height);
        gc.setStroke(Color.BLACK);
        gc.strokeLine(x + width, y, x + width, y + height);
        gc.strokeLine(x, y + height, x + width, y + height);
    }

    @Override
    public void rotateRight(FigureInfo info) {
        if (info.getRotate()) {
            // Realiza la lógica específica de rotación de esta figura.
            double centerX = (rectangle.getTopLeft().getX() + rectangle.getBottomRight().getX()) / 2;
            double centerY = (rectangle.getTopLeft().getY() + rectangle.getBottomRight().getY()) / 2;

            double width = Math.abs(rectangle.getBottomRight().getX() - rectangle.getTopLeft().getX());
            double height = Math.abs(rectangle.getBottomRight().getY() - rectangle.getTopLeft().getY());

            double halfNewWidth = height / 2;
            double halfNewHeight = width / 2;

            Point newTopLeft = new Point(centerX - halfNewWidth, centerY - halfNewHeight);
            Point newBottomRight = new Point(centerX + halfNewWidth, centerY + halfNewHeight);

            rectangle.setTopLeft(newTopLeft);
            rectangle.setBottomRight(newBottomRight);

            info.setStartPoint(newTopLeft);
            info.setEndPoint(newBottomRight);

            // Una vez rotada, resetea el flag para evitar rotaciones repetidas.
            info.setRotate(false);
        }
    }

    public void voltearV(FigureInfo info){
        double height = Math.abs(rectangle.getBottomRight().getX() - rectangle.getTopLeft().getX()) / 2;

        Point newTopLeft = new Point(rectangle.getTopLeft().getX(), rectangle.getTopLeft().getY() + height);
        Point newBottomRight = new Point(rectangle.getBottomRight().getX(), rectangle.getBottomRight().getY() + height);

        rectangle.setTopLeft(newTopLeft);
        rectangle.setBottomRight(newBottomRight);

        info.setStartPoint(newTopLeft);
        info.setEndPoint(newBottomRight);

        info.setVoltearV(false);
    }

    public void voltearH(FigureInfo info){
        double width = Math.abs(rectangle.getBottomRight().getX() - rectangle.getTopLeft().getX());

        Point newTopLeft = new Point(rectangle.getTopLeft().getX() + width, rectangle.getTopLeft().getY());
        Point newBottomRight = new Point(rectangle.getBottomRight().getX() + width, rectangle.getBottomRight().getY());

        info.setStartPoint(newTopLeft);
        info.setEndPoint(newBottomRight);

        rectangle.setTopLeft(newTopLeft);
        rectangle.setBottomRight(newBottomRight);

        info.setVoltearH(false);
    }


    @Override
    public Figure getFigure() {
        return rectangle;
    }

}
