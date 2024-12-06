package frontend.model;

import backend.interfaces.Figure;
import backend.model.Ellipse;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.*;

public class DrawableEllipse extends DrawFigure {

    private final Ellipse ellipse;

    public DrawableEllipse(Point centerPoint, double sMinorAxis, double sMajorAxis) {
        this.ellipse = new Ellipse(centerPoint, sMajorAxis, sMinorAxis);
    }

    @Override
    public void draw(GraphicsContext gc, Color firstFillColor, Color secondFirstColor, Color strokeColor) {
        // Cálculo de los valores de posición y dimensiones de la elipse
        double x = ellipse.getCenterPoint().getX() - ellipse.getsMayorAxis() / 2;
        double y = ellipse.getCenterPoint().getY() - ellipse.getsMinorAxis() / 2;
        double width = ellipse.getsMayorAxis();
        double height = ellipse.getsMinorAxis();

        // Configuración de colores de relleno y borde
        gc.setFill(getGradientColor(firstFillColor, secondFirstColor));
        gc.setStroke(strokeColor);

        // Dibuja la elipse (relleno y borde)
        gc.fillOval(x, y, width, height);
        gc.strokeOval(x, y, width, height);
    }

    private RadialGradient getGradientColor(Color firstFillColor, Color secondFillColor){
        return  new RadialGradient(0, 0, 0.5, 0.5, 0.5, true,
                CycleMethod.NO_CYCLE,
                new Stop(0, firstFillColor),
                new Stop(1, secondFillColor));
    }

    @Override
    public Figure getFigure() {
        return ellipse;
    }
}
