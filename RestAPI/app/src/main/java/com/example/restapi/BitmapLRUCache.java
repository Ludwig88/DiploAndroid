package com.example.restapi;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

public class BitmapLRUCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache {

    public static final BitmapLRUCache instance = new BitmapLRUCache();

    public static BitmapLRUCache getInstance() { return instance; };

    public BitmapLRUCache(int maxSize) {
        super(maxSize);
    }

    public BitmapLRUCache() {
        super(getDefaultSize());
        //super(maxSize);
    }

    public static  int getDefaultSize(){
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory()/1024);
        final int cacheSize = maxMemory / 8;
        return cacheSize;
    }

    @Override
    public Bitmap getBitmap(String url) {
        return get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        put(url, bitmap);
    }
}
