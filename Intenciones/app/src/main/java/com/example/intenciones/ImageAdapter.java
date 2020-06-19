package com.example.intenciones;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

class ImageAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> itemList;

    ImageAdapter(Context context) {
        this.context = context;
        this.itemList = new ArrayList<String>();
    }

    public void Add(String path) {
        itemList.add(path);
    }

    public void Clear() {
        itemList.clear();
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int i) {
        return itemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        // Obtener el thumbnail de las imagenes de la grilla...
        ImageView imageView;
        int height = 200, width = 200;
        if (view == null) {
            imageView = new ImageView(this.context);

            imageView.setLayoutParams( new GridView.LayoutParams(width, height));
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setPadding(8,8,8,8);
            //imageView.setImageResource(R.drawable.ic_launcher_foreground);
        } else {
            imageView = (ImageView)view;
        }

        String bitmapPath = itemList.get(i);
        //Generar codigo para tratar a la imagen de forma mas eficiente en memoria...
        Bitmap bitmap = decodeSampleBitmap(bitmapPath, width, height);
        imageView.setImageBitmap(bitmap);
        return imageView;
    }

    private Bitmap decodeSampleBitmap(String bitmapPath, int width, int height) {
        Bitmap bmp = null;
        //Crear opciones para usar con el decodefile...
        BitmapFactory.Options options = new BitmapFactory.Options();

        //Leer la cabecera de la imagen y que me devuelva el ancho y alto de la misma...
        options.inJustDecodeBounds = true;
        //En options me quedará guardado el tamaño de la imagen..
        BitmapFactory.decodeFile(bitmapPath, options);

        //Establecer el inSampleSize (tamaño de muestra) de la imagen...
        options.inSampleSize = calculateSampleSize(options, width, height);

        //Apago el flag de lectura de la cabecera de la imagen...
        options.inJustDecodeBounds = false;

        //Leo la imagen con la opcion de inSampleSize modificada...
        bmp = BitmapFactory.decodeFile(bitmapPath, options);

        //Retorno el bitmap...
        return bmp;
    }

    private int calculateSampleSize(BitmapFactory.Options options, int width, int height) {
        int inSampleSize = 1;
        int optionsWidth = options.outWidth;
        int optionsHeight = options.outHeight;
        //Verifico que la imagen tenga que ser reducida....
        if (optionsHeight > height || optionsWidth > width) {
            //Selecciono la menor dimension para reducir...
            if (optionsWidth > optionsHeight) {
                //Relacion entre el alto de la imagen y el alto de sampleo...
                inSampleSize = Math.round((float)optionsHeight / (float) height);
            } else {
                //Relacion entre el ancho de la imagen y el ancho de sampleo...
                inSampleSize = Math.round((float)optionsWidth / (float) width);
            }
        }
        return inSampleSize;
    }
}

