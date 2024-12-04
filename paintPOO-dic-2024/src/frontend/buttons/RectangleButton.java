package frontend.buttons;

import backend.interfaces.Figure;
import backend.model.Point;
import frontend.model.DrawableRectangle;
import javafx.scene.canvas.GraphicsContext;

public class RectangleButton extends FigureButton {

        public RectangleButton(String name) {
            super(name);
        }

        @Override
        public Figure create(Point startPoint, Point endPoint) {
            return new DrawableRectangle(startPoint, endPoint);
        }

}
