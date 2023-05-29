package com.example.supersub.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.supersub.R;
import com.example.supersub.models.Formation;

public class FormationView extends View {
    private Formation formation;
    private Paint linePaint;

    public FormationView(Context context) {
        super(context);

        init(null);
    }

    public FormationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(attrs);
    }

    public FormationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(attrs);
    }

    public FormationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init(attrs);
    }

    private void init(@Nullable AttributeSet attrs) {
        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(getResources().getColor(R.color.white, null));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        //Drawable d = getResources().getDrawable(R.drawable.field, null);
        //d.draw(canvas);
        //int midpoint = d.getIntrinsicWidth()/2;

        //canvas.drawLine(midpoint, 0f, midpoint, d.getIntrinsicHeight(), paint);
        canvas.drawLine(0f, 0f, 0f, 100f, linePaint);
    }
}
