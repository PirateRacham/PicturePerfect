package com.example.pictureperfect;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ColourInImageFinderPresenter implements IColourInImageFinderPresenter {

    private IMainActivityView view;
    private FindColourInImageManager manager;
    public ColourInImageFinderPresenter(IMainActivityView view){
        this.view = view;
        manager = new FindColourInImageManager(8);
    }
    @Override
    public void FindColour(Bitmap imageBitmap) {
        try {
            manager.FindColours(imageBitmap);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void ImageAnalyzed(ArrayList<Integer> coloursInInt){
        view.UpdateColours(coloursInInt);
    }
}
