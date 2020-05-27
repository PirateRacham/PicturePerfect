package com.example.pictureperfect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements IMainActivityView {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView imageView;
    List<TextView> texts;
    List<TextView> colours;
    IColourInImageFinderPresenter colourInImageFinderPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        colourInImageFinderPresenter = new ColourInImageFinderPresenter(this);
        //texts
        texts = new ArrayList<>();
        texts.add((TextView) findViewById(R.id.txt_1));
        texts.add((TextView) findViewById(R.id.txt_2));
        texts.add((TextView) findViewById(R.id.txt_3));
        texts.add((TextView) findViewById(R.id.txt_4));
        texts.add((TextView) findViewById(R.id.txt_5));
        //colours
        colours = new ArrayList<>();
        colours.add((TextView) findViewById(R.id.color_1));
        colours.add((TextView) findViewById(R.id.color_2));
        colours.add((TextView) findViewById(R.id.color_3));
        colours.add((TextView) findViewById(R.id.color_4));
        colours.add((TextView) findViewById(R.id.color_5));

        imageView = findViewById(R.id.imageView);
        dispatchTakePictureIntent();
    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
            colourInImageFinderPresenter.FindColour(imageBitmap);
        }
    }
    @Override
    public void UpdateColours(ArrayList<Integer> ColourAsInt) {
        for (int i=0; i<5; i++){
            texts.get(i).setText(ColourAsInt.get(i));
            colours.get(i).setBackgroundColor(ColourAsInt.get(i));
        }
    }
}
