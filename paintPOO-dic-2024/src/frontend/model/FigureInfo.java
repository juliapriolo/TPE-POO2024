package frontend.model;

import backend.model.Point;
import javafx.scene.paint.Color;


public class FigureInfo {
    private Color color;
    private Color secondaryColor;
    private ShadowType shadowType;
    private boolean arcType;
    private boolean rotateRight;
    private boolean flipH;
    private boolean flipV;

    public FigureInfo(Color color, Color secondaryColor, ShadowType shadowType, boolean arcType, boolean rotateRight, boolean flipH, boolean flipV) {
        this.color = color;
        this.secondaryColor = secondaryColor;
        this.shadowType = shadowType;
        this.arcType = arcType;
        this.rotateRight = rotateRight;
        this.flipH = flipH;
        this.flipV = flipV;
    }
    public void setColor(Color color) {
        this.color = color;
    }
    public Color getColor() {
        return color;
    }

    public void setSecondaryColor(Color secondaryColor) {
        this.secondaryColor = secondaryColor;
    }
    public Color getSecondaryColor() {
        return secondaryColor;
    }

    public void setShadowType(ShadowType shadowType) {
        this.shadowType = shadowType;
    }
    public ShadowType getShadowType() {
        return shadowType;
    }

    public void transferArcType(boolean arcType){
        this.arcType = arcType;
    }
    public void setArcType(){
        arcType = !arcType;
    }
    public boolean getArcType(){
        return arcType;
    }

    public void setRotate(boolean rotateRight) {
        this.rotateRight = rotateRight;
    }
    public void setRotate() {
        this.rotateRight = true;
    }
    public boolean getRotate(){
        return rotateRight;
    }

    public void setFlipH(boolean flipH){
        this.flipH = flipH;
    }
    public void setFlipH(){
        this.flipH = true;
    }
    public boolean getFlipH(){
        return flipH;
    }

    public void setFlipV(boolean flipV){
        this.flipV = flipV;
    }
    public void setFlipV(){
        flipV = true;
    }
    public boolean getFlipV(){
        return flipV;
    }

}
