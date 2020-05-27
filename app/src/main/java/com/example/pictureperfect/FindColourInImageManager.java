package com.example.pictureperfect;

import android.graphics.Bitmap;
import android.graphics.Color;

import java.util.List;

public class FindColourInImageManager {

    private IFindColour findColour;
    public FindColourInImageManager(){
        //setting to 4, should be set in options
        findColour = new FindColourThreading(4);
    }

    String[] FindDominantColours(Bitmap image){
        return GetFiveMostDominant(findColour.FindColours(image));
    }
    private String[] GetFiveMostDominant(List<Integer> colours){
        for (int color : colours) {

        }
    }
    private boolean IsColourColour(int colour, int secondColour){
        int distance = (int)Math.sqrt(Math.pow(rgbA[0] - rgbB[0], 2) + (Math.pow(rgbA[1] - rgbB[1], 2) + (Math.pow(rgbA[2] - rgbB[2], 2))));

    }

}

