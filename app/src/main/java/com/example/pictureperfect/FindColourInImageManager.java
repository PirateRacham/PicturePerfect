package com.example.pictureperfect;

import android.graphics.Bitmap;
import android.graphics.Color;

import java.util.List;

public class FindColourInImageManager {

    private IFindColour findColour;
    public FindColourInImageManager(int threadcount){
        //setting to 4, should be set in options
        findColour = new FindColourThreading(threadcount);
    }

    Integer[] FindDominantColours(Bitmap image){
        return GetFiveMostDominant(findColour.FindColours(image));
    }
    private Integer[] GetFiveMostDominant(List<Integer> colours){
        Integer[] result = new  Integer[5];
        byte numberOfColours = 0;
        int previousColour = 0;
        for (int color : colours) {
            if (previousColour != 0 && numberOfColours != 5){
                if (IsColourColour(color, previousColour)){
                    result[numberOfColours] = color;
                    numberOfColours++;
                }
            }
            previousColour = color;
        }
        return result;
    }

    private boolean IsColourColour(int colour, int secondColour){
        double distance = ColourDistance(colour, secondColour);
        if(distance < 60){
            return true;
        }
        return false;
    }

    private double ColourDistance(int colour, int secondColour){
        Color _colour = Color.valueOf(colour);
        Color _secondColour = Color.valueOf(secondColour);
        final double red = Math.pow(_secondColour.red() - _colour.red(), 2);
        final double green = Math.pow(_secondColour.green() - _colour.green(), 2);
        final double blue = Math.pow(_secondColour.blue() - _colour.blue(), 2);
        return Math.sqrt(red + green + blue);
    }

}

