package com.example.pictureperfect;

import android.graphics.Bitmap;
import android.os.Build;
import androidx.annotation.RequiresApi;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Callable;

public class ColourFinderCallable implements Callable {
    private Bitmap Image;

    ColourFinderCallable(Bitmap image){
        Image = image;
    }
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public Map<Integer, Integer> call() throws Exception {
        Map<Integer, Integer> colours = new TreeMap<>();
        int height = Image.getHeight();
        int width = Image.getWidth();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++){
                int colour = Image.getColor(x,y).toArgb();
                if(colours.containsKey(colour)){
                    colours.put(colour, colours.get(colour) + 1);
                }
                else{
                    colours.put(colour,1);
                }
            }
        }
        return colours;
    }
}
