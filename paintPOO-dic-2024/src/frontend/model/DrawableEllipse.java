package frontend.model;

import backend.interfaces.Figure;
import backend.model.Ellipse;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.*;
import javafx.scene.shape.ArcType;

public class DrawableEllipse extends DrawFigure {

    private Ellipse ellipse;

    public DrawableEllipse(FigureInfo info,Figure figure, GraphicsContext gc) {
        super(info,figure,gc);
        this.ellipse = (Ellipse) getFigure();
    }

    @Override
    public void draw(GraphicsContext gc, FigureInfo info, Color strokeColor,Figure figure) {
        double x = ellipse.getCenterPoint().getX() - ellipse.getsMayorAxis() / 2;
        double y = ellipse.getCenterPoint().getY() - ellipse.getsMinorAxis() / 2;
        double width = ellipse.getsMayorAxis();
        double height = ellipse.getsMinorAxis();

        setShadowOval(gc, ellipse.getCenterPoint(), ellipse.getsMinorAxis(), ellipse.getsMayorAxis());

        // Configuración de colores de relleno y borde
        gc.setFill(getGradientColor(info.getColor(), info.getSecondaryColor()));
        gc.setStroke(strokeColor);

        // Dibuja la elipse
        gc.fillOval(x, y, width, height);
        gc.strokeOval(x, y, width, height);
        if(info.getArcType()){
            setEllipseArcType(gc);
        }
        gc.setLineWidth(1);
    }

    private RadialGradient getGradientColor(Color firstFillColor, Color secondFillColor){
        return  new RadialGradient(0, 0, 0.5, 0.5, 0.5, true,
                CycleMethod.NO_CYCLE,
                new Stop(0, firstFillColor),
                new Stop(1, secondFillColor));
    }

    private void setEllipseArcType(GraphicsContext gc){
        double ellipseX = ellipse.getCenterPoint().getX() - ellipse.getWidth() / 2;
        double ellipseY = ellipse.getCenterPoint().getY() - ellipse.getHeight() / 2;
        double width = ellipse.getWidth();
        double height = ellipse.getHeight();

        gc.setLineWidth(10);
        gc.setStroke(Color.LIGHTGRAY);
        gc.strokeArc(ellipseX, ellipseY, width, height, 45, 180, ArcType.OPEN);
        gc.setStroke(Color.BLACK);
        gc.strokeArc(ellipseX, ellipseY, width, height, 225, 180, ArcType.OPEN);
    }

    @Override
    public void rotateRight(FigureInfo info){
        if (info.getRotate()) {
            double tempAxis = getFigure().getWidth();
            ellipse.setsMayorAxis(ellipse.getsMinorAxis());
            ellipse.setsMinorAxis(tempAxis);
            info.setRotate(false);
        }
    }


    private void customFlipEllipse(double deltaX, double deltaY, FigureInfo info, boolean isVertical) {
        ellipse.setCenterPoint(new Point(ellipse.getCenterPoint().getX() + deltaX, ellipse.getCenterPoint().getY() + deltaY));

        if (isVertical) {
            info.setVoltearV(false);
        } else {
            info.setVoltearH(false);
        }
    }

    public void flipVertically(FigureInfo info) {
        double height = ellipse.getsMinorAxis();
        customFlipEllipse(0, height, info, true);
    }

    public void flipHorizontally(FigureInfo info) {
        double width = getFigure().getWidth();
        customFlipEllipse(width, 0, info, false);
    }

}
