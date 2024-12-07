package frontend.model;

import backend.model.Point;
import javafx.scene.paint.Color;


public class FigureInfo {
    private Color color;
    private Color secondaryColor;
    private Point startPoint;
    private Point endPoint;
    private double axis1;
    private double axis2;
    private ShadowType shadowType;
    private boolean arcType;
    private boolean rotateRight;

    public FigureInfo(Color color, Color secondaryColor, Point startPoint, Point endPoint, ShadowType shadowType, boolean arcType, boolean rotateRight) {
        this.color = color;
        this.secondaryColor = secondaryColor;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.shadowType = shadowType;
        this.arcType = arcType;
    }

    public boolean getArcType(){
        return arcType;
    }

    public void transferArcType(boolean arcType){
        this.arcType = arcType;
    }

    public void setArcType(){
        arcType = !arcType;
    }

    public boolean getRotate(){
        return rotateRight;
    }

    public void setRotate(boolean rotateRight) {
        this.rotateRight = rotateRight;
    }

    public void setRotate() {
        this.rotateRight = true; // Activa la rotación
    }

    public ShadowType getShadowType() {
        return shadowType;
    }

    public void setShadowType(ShadowType shadowType) {
        this.shadowType = shadowType;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getSecondaryColor() {
        return secondaryColor;
    }

    public void setSecondaryColor(Color secondaryColor) {
        this.secondaryColor = secondaryColor;
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Point endPoint) {
        this.endPoint = endPoint;
    }

    public double getAxis1() {
        return axis1;
    }

    public void setAxis1(double axis1) {
        this.axis1 = axis1;
    }

    public double getAxis2() {
        return axis2;
    }

    public void setAxis2(double axis2) {
        this.axis2 = axis2;
    }
}
