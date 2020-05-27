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
        List<Bitmap> splits = SplitImageIntoPieces(image, threadcount / 2, threadcount / 2);
        FutureTask[] findPixelColourtask = new FutureTask[threadcount];
        for(int i = 0; i < threadcount; i++){
            Callable callable = new ColourFinderCallable(splits.get(i));
            findPixelColourtask[i] = new FutureTask(callable);
            Thread t = new Thread(findPixelColourtask[i]);
            t.start();
        }
        TreeMap<Integer, Integer>[] arr = new TreeMap[threadcount];
        for(int i = 0; i < threadcount; i++) {
            try {
                arr[i] = (TreeMap) findPixelColourtask[i].get();
            }
            catch (Exception e){
                //do nothing
            }
        }
        Map<Integer, Integer> joined =  JoinMap(arr);
        List<Map.Entry<Integer, Integer>> joinedList = new ArrayList<>(joined.entrySet());
        joinedList.sort(Collections.reverseOrder(Map.Entry.comparingByValue()));
        List<Integer> result = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            result.add(joinedList.get(i).getValue());
        }
        return result;
    }

    private List<Bitmap> SplitImageIntoPieces(Bitmap image, int horizontalCount, int verticalCount) {
        List<Bitmap> splits = new ArrayList<>();
        int width = image.getWidth();
        int height = image.getHeight();
        for (int w = 0; w < horizontalCount; w++) {
            for (int v = 0; v < verticalCount; v++) {
                splits.add(Bitmap.createBitmap(image, w * width, v * height, width, height));
            }
        }
        return splits;
    }

    private Map<Integer, Integer> JoinMap(TreeMap<Integer, Integer>[] arr) {
        Map<Integer, Integer> result = new TreeMap<>();
        for (Map<Integer, Integer> m : arr) {
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
