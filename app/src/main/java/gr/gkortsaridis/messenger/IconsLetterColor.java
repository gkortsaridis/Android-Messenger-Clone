package gr.gkortsaridis.messenger;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

import java.util.ArrayList;

/**
 * Created by yoko on 23/07/16.
 */
public class IconsLetterColor {

    ArrayList<String> letters;
    ArrayList<String> colors;
    ArrayList<String> colorsDark;
    String defaultColor = "#212121";

    public IconsLetterColor(){
        letters = new ArrayList<>();
        colors = new ArrayList<>();
        colorsDark = new ArrayList<>();

        letters.add("A");letters.add("B");letters.add("C");letters.add("D");letters.add("E");letters.add("F");letters.add("G");letters.add("H");
        letters.add("I");letters.add("J");letters.add("K");letters.add("L");letters.add("M");letters.add("N");letters.add("O");letters.add("P");
        letters.add("Q");letters.add("R");letters.add("S");letters.add("T");letters.add("U");letters.add("V");letters.add("W");letters.add("X");
        letters.add("Y");letters.add("Z");

        letters.add("Α");letters.add("Β");letters.add("Γ");letters.add("Δ");letters.add("Ε");letters.add("Ζ");letters.add("Η");letters.add("Θ");
        letters.add("Ι");letters.add("Κ");letters.add("Λ");letters.add("Μ");letters.add("Ν");letters.add("Ξ");letters.add("Ο");letters.add("Π");
        letters.add("Ρ");letters.add("Σ");letters.add("Τ");letters.add("Υ");letters.add("Φ");letters.add("Χ");letters.add("Ψ");letters.add("Ω");

        //A
        colors.add("#F44336");
        colorsDark.add("#B71C1C");
        //B
        colors.add("#E91E63");
        colorsDark.add("#880E4F");
        //C
        colors.add("#9C27B0");
        colorsDark.add("#4A148C");
        //D
        colors.add("#673AB7");
        colorsDark.add("#311B92");
        //E
        colors.add("#3F51B5");
        colorsDark.add("#1A237E");
        //F
        colors.add("#2196F3");
        colorsDark.add("#0D47A1");
        //G
        colors.add("#03A9F4");
        colorsDark.add("#1976D2");
        //H
        colors.add("#00BCD4");
        colorsDark.add("#006064");
        //I
        colors.add("#009688");
        colorsDark.add("#004D40");
        //J
        colors.add("#4CAF50");
        colorsDark.add("#1B5E20");
        //K
        colors.add("#8BC34A");
        colorsDark.add("#33691E");
        //L
        colors.add("#CDDC39");
        colorsDark.add("#827717");
        //M
        colors.add("#FFEB3B");
        colorsDark.add("#F57F17");
        //N
        colors.add("#FFC107");
        colorsDark.add("#FF6F00");
        //O
        colors.add("#FF9800");
        colorsDark.add("#E65100");
        //P
        colors.add("#FF5722");
        colorsDark.add("#BF360C");
        //Q
        colors.add("#795548");
        colorsDark.add("#3E2723");
        //R
        colors.add("#607D8B");
        colorsDark.add("#263238");
        //S
        colors.add("#F44336");
        colorsDark.add("#B71C1C");
        //T
        colors.add("#E91E63");
        colorsDark.add("#880E4F");
        //U
        colors.add("#9C27B0");
        colorsDark.add("#4A148C");
        //V
        colors.add("#673AB7");
        colorsDark.add("#311B92");
        //W
        colors.add("#3F51B5");
        colorsDark.add("#1A237E");
        //X
        colors.add("#2196F3");
        colorsDark.add("#0D47A1");
        //Y
        colors.add("#03A9F4");
        colorsDark.add("#01579B");
        //Z
        colors.add("#00BCD4");
        colorsDark.add("#006064");


        //Α
        colors.add("#F44336");
        colorsDark.add("#B71C1C");
        //Β
        colors.add("#E91E63");
        colorsDark.add("#880E4F");
        //Γ
        colors.add("#9C27B0");
        colorsDark.add("#1976D2");
        //Δ
        colors.add("#673AB7");
        colorsDark.add("#311B92");
        //Ε
        colors.add("#3F51B5");
        colorsDark.add("#1A237E");
        //Ζ
        colors.add("#2196F3");
        colorsDark.add("#0D47A1");
        //Η
        colors.add("#03A9F4");
        colorsDark.add("#01579B");
        //Θ
        colors.add("#00BCD4");
        colorsDark.add("#006064");
        //Ι
        colors.add("#009688");
        colorsDark.add("#004D40");
        //Κ
        colors.add("#4CAF50");
        colorsDark.add("#1B5E20");
        //Λ
        colors.add("#8BC34A");
        colorsDark.add("#33691E");
        //Μ
        colors.add("#CDDC39");
        colorsDark.add("#827717");
        //Ν
        colors.add("#FFEB3B");
        colorsDark.add("#F57F17");
        //Ξ
        colors.add("#FFC107");
        colorsDark.add("#FF6F00");
        //Ο
        colors.add("#FF9800");
        colorsDark.add("#E65100");
        //Π
        colors.add("#FF5722");
        colorsDark.add("#BF360C");
        //Ρ
        colors.add("#795548");
        colorsDark.add("#3E2723");
        //Σ
        colors.add("#607D8B");
        colorsDark.add("#263238");
        //Τ
        colors.add("#F44336");
        colorsDark.add("#B71C1C");
        //Υ
        colors.add("#E91E63");
        colorsDark.add("#880E4F");
        //Φ
        colors.add("#9C27B0");
        colorsDark.add("#4A148C");
        //Χ
        colors.add("#673AB7");
        colorsDark.add("#311B92");
        //Ψ
        colors.add("#3F51B5");
        colorsDark.add("#1A237E");
        //Ω
        colors.add("#2196F3");
        colorsDark.add("#0D47A1");

    }

    public int getColorFromLetter(String letter){
        for(int i=0; i<letters.size(); i++){
            if(letter.toUpperCase().equals(letters.get(i))){
                return Color.parseColor(colors.get(i));
            }

        }
        return Color.parseColor(defaultColor);
    }

    public int getColorDarkFromLetter(String letter){
        for(int i=0; i<letters.size(); i++){
            if(letter.toUpperCase().equals(letters.get(i))){
                return Color.parseColor(colorsDark.get(i));
            }

        }
        return Color.parseColor(defaultColor);
    }
}
