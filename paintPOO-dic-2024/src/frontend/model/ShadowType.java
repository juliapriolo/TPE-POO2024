package frontend.model;

//NO ESTOY SEGURA SI HACE FALTA O NO EL NONE PERO
// LO PUSE XQ ME PARECIO TIPO UN DEFAULT X SI
// NO TENIA SOMBRA

//TAMPOCO ESTOY SEGURA EXACTAMENTE DONDE PONER EL ENUM,
//PERO ASUMO QUE ES CON LOS DRAWABLES ASI LO LLAMO

import java.awt.*;

public enum ShadowType {
    NONE(0){
        @Override
        public double calculateShadowCoord(double coord, double axis){
            return 0;
        }

        @Override
        public double calculateAbs(double coord1, double coord2){
            return 0;
        }

        @Override
        public Color getColor(Color color){
            return Color.GRAY;
        }

        @Override
        public String toString(){
            return "Ninguna";
        }
    },
    SIMPLE(10.0){
        @Override
        public double calculateShadowCoord(double coord, double axis){
            return coord - axis/2 + offSet;
        }

        @Override
        public double calculateAbs(double coord1, double coord2){
            return Math.abs(coord1 - coord2);
        }

        @Override
        public Color getColor(Color color){
            return Color.GRAY;
        }

        @Override
        public String toString(){
            return "Simple";
        }
    },
    COLORED(10.0){
        @Override
        public double calculateShadowCoord(double coord, double axis){
            return coord - axis/2 + offSet;
        }

        @Override
        public double calculateAbs(double coord1, double coord2){
            return Math.abs(coord1 - coord2);
        }

        @Override
        public Color getColor(Color color){
            return color.darker();
        }

        @Override
        public String toString(){
            return "Coloreada";
        }
    },
    SIMPLE_INVERSED(-10.0){
        @Override
        public double calculateShadowCoord(double coord, double axis){
            return coord - axis/2 + offSet;
        }

        @Override
        public double calculateAbs(double coord1, double coord2){
            return Math.abs(coord1 - coord2);
        }

        @Override
        public Color getColor(Color color){
            return Color.GRAY;
        }

        @Override
        public String toString(){
            return "Simple Inversa";
        }
    },
    COLORED_INVERSED(-10.0){
        @Override
        public double calculateShadowCoord(double coord, double axis){
            return coord - axis/2 + offSet;
        }

        @Override
        public double calculateAbs(double coord1, double coord2){
            return Math.abs(coord1 - coord2);
        }

        @Override
        public Color getColor(Color color){
            return color.darker();
        }

        @Override
        public String toString(){
            return "Coloreada Inversa";
        }
    };

    final double offSet;

    ShadowType(double offSet){
        this.offSet = offSet;
    }

    public abstract double calculateShadowCoord(double coord, double axis);
    public abstract double calculateAbs(double coord1, double coord2);
    public abstract Color getColor(Color color);
}
