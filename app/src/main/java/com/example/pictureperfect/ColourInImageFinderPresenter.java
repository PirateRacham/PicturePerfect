package com.example.pictureperfect;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ColourInImageFinderPresenter implements IColourInImageFinderPresenter {

    private IMainActivityView view;
    private FindColourInImageManager manager;

    public ColourInImageFinderPresenter(IMainActivityView view) {
        this.view = view;
        manager = new FindColourInImageManager(8);
    }

    @Override
    public Integer[] FindColour(Bitmap imageBitmap) {
        return manager.FindDominantColours(imageBitmap);
    }

//    public void ImageAnalyzed(ArrayList<Integer> coloursInInt) {
//        view.UpdateColours(coloursInInt);
//    }
}
