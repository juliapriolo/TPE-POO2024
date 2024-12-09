package frontend.model;

import javafx.scene.paint.Color;

public enum ShadowType {
    NONE{
        @Override
        public double getOffSet(){
            return 0;
        }

        @Override
        public String toString(){
            return "Ninguna";
        }
    },
    SIMPLE{
        @Override
        public double getOffSet(){
            return offSet;
        }

        @Override
        public String toString(){
            return "Simple";
        }
    },
    COLORED{
        @Override
        public double getOffSet(){
            return offSet;
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
    SIMPLE_INVERSED{

        @Override
        public double getOffSet(){
            return -offSet;
        }

        @Override
        public String toString(){
            return "Simple Inversa";
        }
    },
    COLORED_INVERSED{

        @Override
        public double getOffSet(){
            return -offSet;
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

    final static double offSet = 10;
    final static Color defaultColor = Color.GRAY;

    public Color getColor(Color color){
        return defaultColor;
    }
    public abstract double getOffSet();
}
