package com.futureproteam.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by ding-syi on 2017/12/4.
 */

public class IndexBar extends View {

    private char[] indexs = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
    'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    private float height;
    private float itemHeight;
    private int lastIndex = -1;
    private Context context;
    private Paint paint;
    private OnIndexClickedListener listener;

    public IndexBar(Context context) {
        super(context);
        init(context);
    }

    public IndexBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public IndexBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context context){
        this.context = context;
        paint = createPaint();
    }

    private Paint createPaint(){
        //画笔
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(2);
        paint.setTextSize(dpToPx(14));
        paint.setColor(Color.BLACK);
        return paint;
    }

    private float dpToPx(int dp){
        float density = context.getResources().getDisplayMetrics().density;
        return density*dp;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        itemHeight = height/indexs.length;
        Log.i("高度", height + "");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int index = 0; index < indexs.length; index++){
            Log.e("高度", itemHeight*index + "");
            canvas.drawText(indexs, index, 1, getPaddingLeft() + dpToPx(3) , getPaddingTop() + itemHeight*index, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (listener == null){
            return super.onTouchEvent(event);
        }
        int index = (int) Math.floor(event.getY()/itemHeight);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                isConsumeClickEvent(index);
                return true;
            case MotionEvent.ACTION_MOVE:
                return isConsumeClickEvent(index);
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onTouchEvent(event);
    }

    private boolean isConsumeClickEvent(int index){
        if (index >= 0 && index < indexs.length && index != lastIndex){
            Log.i("lastIndex", lastIndex + "");
            Log.i("index", index + "");
            lastIndex = index;
            listener.onClicked(index, indexs[index] + "");
            return true;
        }
        return false;
    }

    public void setOnIndexClickedListener(OnIndexClickedListener listener){
        this.listener = listener;
    }

    public interface OnIndexClickedListener {
        void onClicked(int position, String c);
    }
}
