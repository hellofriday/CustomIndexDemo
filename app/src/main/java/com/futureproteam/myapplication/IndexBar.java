package com.futureproteam.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
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
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        itemHeight = getHeight()/indexs.length;
        for (int index = 0; index < indexs.length; index++){
            Log.e("高度", itemHeight*index + "");
            Rect bound = new Rect();
            paint.getTextBounds(indexs, index, 1, bound);
//            float textWidth = paint.measureText(indexs[index] + "");
            float textWidth = bound.width();
            float textHeight = bound.height();
            Log.e("textHeight", textHeight + "");
            Log.e("itemHeight", itemHeight + "");
            Paint.FontMetrics fontMetrics = paint.getFontMetrics();
            Log.e("top", fontMetrics.top + "");
            Log.e("ascent", fontMetrics.ascent + "");
            Log.e("descent", fontMetrics.descent + "");
            Log.e("bottom", fontMetrics.bottom + "");

            float dx = (getRight() - getLeft() - getPaddingLeft() - textWidth)/2.0f;
            Log.e("dx", dx + "");
//            float dy = getPaddingTop() + itemHeight*index;

            float dy = getPaddingTop() + Math.abs(fontMetrics.top) + itemHeight*index;

            canvas.drawText(indexs, index, 1, dx, dy, paint);
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
