package frontend.model;

import backend.interfaces.Figure;
import backend.model.Ellipse;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.*;
import javafx.scene.shape.ArcType;

public class DrawableEllipse extends DrawFigure {

    private final Ellipse ellipse;

    public DrawableEllipse(FigureInfo info, Figure figure) {
        super(info, figure);
        this.ellipse = (Ellipse) figure;
    }

    @Override
    public void draw(GraphicsContext gc, FigureInfo info, Color strokeColor, Figure figure) {
        Point topLeft = getTopLeftCorner();
        double width = ellipse.getWidth();
        double height = ellipse.getHeight();

        setShadowOval(gc, ellipse.getCenterPoint(), height, width);

        gc.setFill(getGradientColor(info.getColor(), info.getSecondaryColor()));
        gc.setStroke(strokeColor);

        gc.fillOval(topLeft.getX(), topLeft.getY(), width, height);
        gc.strokeOval(topLeft.getX(), topLeft.getY(), width, height);

        if (info.getArcType()) {
            setEllipseArcType(gc);
        }

        gc.setLineWidth(1);
    }

    private Point getTopLeftCorner() {
        return new Point(
                ellipse.getCenterPoint().getX() - ellipse.getWidth() / 2,
                ellipse.getCenterPoint().getY() - ellipse.getHeight() / 2
        );
    }

    private RadialGradient getGradientColor(Color firstFillColor, Color secondFillColor) {
        return new RadialGradient(0, 0, 0.5, 0.5, 0.5, true,
                CycleMethod.NO_CYCLE,
                new Stop(0, firstFillColor),
                new Stop(1, secondFillColor));
    }

    private void setEllipseArcType(GraphicsContext gc) {
        Point topLeft = getTopLeftCorner();
        double width = ellipse.getWidth();
        double height = ellipse.getHeight();

        gc.setLineWidth(10);
        gc.setStroke(Color.LIGHTGRAY);
        gc.strokeArc(topLeft.getX(), topLeft.getY(), width, height, 45, 180, ArcType.OPEN);
        gc.setStroke(Color.BLACK);
        gc.strokeArc(topLeft.getX(), topLeft.getY(), width, height, 225, 180, ArcType.OPEN);
    }

    @Override
    public void rotateRight(FigureInfo info) {
        if (info.getRotate()) {
            double tempAxis = ellipse.getWidth();
            ellipse.setMajorAxis(ellipse.getHeight());
            ellipse.setMinorAxis(tempAxis);
            info.setRotate(false);
        }
    }

    public void flipVertically(FigureInfo info) {
        double height = ellipse.getHeight();
        customFlipEllipse(0, height, info, true);
    }

    public void flipHorizontally(FigureInfo info) {
        double width = ellipse.getWidth();
        customFlipEllipse(width, 0, info, false);
    }

    private void customFlipEllipse(double deltaX, double deltaY, FigureInfo info, boolean isVertical) {
        Point newCenter = new Point(ellipse.getCenterPoint().getX() + deltaX, ellipse.getCenterPoint().getY() + deltaY);
        ellipse.setCenterPoint(newCenter);

        if (isVertical) {
            info.setFlipV(false);
        } else {
            info.setFlipH(false);
        }
    }
}
