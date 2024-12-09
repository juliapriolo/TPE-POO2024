package frontend.model;

import backend.interfaces.Figure;
import backend.model.Point;
import backend.model.Rectangle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.*;


public class DrawableRectangle extends DrawFigure {

    private final Rectangle rectangle;

    public DrawableRectangle(FigureInfo info, Figure figure) {
        super(info, figure);
        this.rectangle = (Rectangle) figure;
    }

    @Override
    public void draw(GraphicsContext gc, FigureInfo info, Color strokeColor,Figure figure) {

        double x = Math.min(rectangle.getStartPoint().getX(), rectangle.getEndPoint().getX());
        double y = Math.min(rectangle.getStartPoint().getY(), rectangle.getEndPoint().getY());

        double width = rectangle.getWidth();
        double height = rectangle.getHeight();

        setShadowRect(gc, rectangle.getStartPoint(), rectangle.getEndPoint());

        gc.setFill(getGradientColor(info.getColor(), info.getSecondaryColor()));
        gc.setStroke(strokeColor);

        gc.fillRect(x, y, width, height);
        gc.strokeRect(x, y, width, height);

        if (info.getArcType()) {
            setRectangleArcType(gc);
        }
        gc.setLineWidth(1);
    }

    private LinearGradient getGradientColor(Color firstFillColor, Color secondFillColor) {
        return new LinearGradient(0, 0, 1, 0, true,
                CycleMethod.NO_CYCLE,
                new Stop(0, firstFillColor),
                new Stop(1, secondFillColor));
    }

    private void setRectangleArcType(GraphicsContext gc) {
        double xStart = rectangle.getStartPoint().getX();
        double yStart = rectangle.getStartPoint().getY();

        double xEnd = rectangle.getEndPoint().getX();
        double yEnd = rectangle.getEndPoint().getY();

        double width = rectangle.getWidth();
        double height = rectangle.getHeight();

        gc.setLineWidth(10);
        gc.setStroke(Color.LIGHTGRAY);
        gc.strokeLine(xStart, yStart, xStart + width, yStart);
        gc.strokeLine(xStart, yStart, xStart, yStart + height);
        gc.setStroke(Color.BLACK);
        gc.strokeLine(xEnd, yEnd, xEnd, yEnd - height);
        gc.strokeLine(xEnd - width, yEnd, xEnd, yEnd);
    }

    @Override
    public void rotateRight(FigureInfo info) {
        if (info.getRotate()) {
            double halfNewWidth = rectangle.getHeight() / 2;
            double halfNewHeight = rectangle.getWidth() / 2;

            Point newTopLeft = new Point(rectangle.getCenterPoint().getX() - halfNewWidth, rectangle.getCenterPoint().getY() - halfNewHeight);
            Point newBottomRight = new Point(rectangle.getCenterPoint().getX() + halfNewWidth, rectangle.getCenterPoint().getY() + halfNewHeight);

            updateInfo(newTopLeft, newBottomRight);

            info.setRotate(false);
        }
    }

    public void flipVertically(FigureInfo info) {
        double height = rectangle.getHeight();

        customFlipRect(0, height, info, true);
    }

    public void flipHorizontally(FigureInfo info) {
        double width = rectangle.getWidth();

        customFlipRect(width, 0, info, false);
    }

    private void customFlipRect(double deltaX, double deltaY, FigureInfo info, boolean isVertical){
        Point newTopLeft = new Point(rectangle.getStartPoint().getX() + deltaX, rectangle.getStartPoint().getY() + deltaY);
        Point newBottomRight = new Point(rectangle.getEndPoint().getX() + deltaX, rectangle.getEndPoint().getY() + deltaY);

        updateInfo(newTopLeft, newBottomRight);

        if(isVertical){
            info.setFlipV(false);
        }else{
            info.setFlipH(false);
        }
    }

    private void updateInfo(Point newStartPoint, Point newEndPoint) {
        rectangle.setStartPoint(newStartPoint);
        rectangle.setEndPoint(newEndPoint);
    }
}
