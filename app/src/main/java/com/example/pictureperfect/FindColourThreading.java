package com.example.pictureperfect;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class FindColourThreading implements IFindColour {
    private int threadcount;

    FindColourThreading(int threadCount) {
        this.threadcount = threadCount;
    }

    @Override
    public List<Integer> FindColours(Bitmap image) {
        List<Bitmap> splits = SplitImageIntoPieces(image, threadcount);
        List<FutureTask<TreeMap<Integer,Integer>>> findPixelColourtask = new ArrayList<>();
        for(int i = 0; i < splits.size(); i++){
            ColourFinderCallable callable = new ColourFinderCallable(splits.get(i));
            findPixelColourtask.add(i, new FutureTask<>(callable));
            Thread t = new Thread(findPixelColourtask.get(i));
            t.start();
        }
        List<TreeMap<Integer, Integer>> list = new ArrayList<>();
        for(int i = 0; i < threadcount; i++) {
            try {
                list.add(findPixelColourtask.get(i).get());
            }
            catch (Exception e){
                //do nothing
            }
        }
        Map<Integer, Integer> joined =  JoinMap(list);
        List<Map.Entry<Integer, Integer>> joinedList = new ArrayList<>(joined.entrySet());
        joinedList.sort(Collections.reverseOrder(Map.Entry.comparingByValue()));
        List<Integer> result = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            result.add(joinedList.get(i).getValue());
        }
        return result;
    }

    private List<Bitmap> SplitImageIntoPieces(Bitmap image, int threadcount) {
        List<Bitmap> splits = new ArrayList<>();
        int width = image.getWidth();
        int height = image.getHeight();
        int partImageWidth = width / threadcount;
        for (int w = 1; w < threadcount; w++) {
            if (!(((width / threadcount) * w) > width))
                splits.add(Bitmap.createBitmap(image, w * partImageWidth, 0, partImageWidth, height));
            }
        return splits;
    }

    private Map<Integer, Integer> JoinMap(List<TreeMap<Integer, Integer>> arr) {
        Map<Integer, Integer> result = new TreeMap<>();
        for (TreeMap<Integer, Integer> m : arr) {
            for (Map.Entry<Integer, Integer> entry : m.entrySet()) {
                if (result.containsKey(entry.getKey())) {
                    result.put(entry.getKey(), entry.getValue() + 1);
                } else {
                    result.put(entry.getKey(), entry.getValue());
                }
            }
        }
        return result;
    }
}
