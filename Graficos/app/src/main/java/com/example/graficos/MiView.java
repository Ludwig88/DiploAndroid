package com.example.graficos;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.view.View;

import java.util.jar.Attributes;

public class MiView extends View {
    private final Context context;
    private ShapeDrawable miforma;

    public MiView(Context context, AttributeSet atributesSets) {
        super(context, atributesSets );
        this.context = context;

        //puedo definir assets en el constructor y luego dibujarlos cuando yo quiera
        miforma = new ShapeDrawable(new OvalShape());
        miforma.getPaint().setColor(Color.GREEN);
    }

    @Override
    protected void onDraw(Canvas canvas ) {
        super.onDraw(canvas);
        miforma.draw(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        miforma.setBounds(0,0,w, h);
    }
}
