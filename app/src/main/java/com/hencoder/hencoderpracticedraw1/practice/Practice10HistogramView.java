package com.hencoder.hencoderpracticedraw1.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class Practice10HistogramView extends View {
    private static final int BAR_WIDTH = 85;
    private static List<BarData> mBarDataList = new ArrayList<>();

    private Paint mAxisPaint = new Paint();
    private Paint mBarPaint = new Paint();
    private Paint mTextPaint = new Paint();
    private Paint mTouchPaint = new Paint();
    private Point mBarPoint = new Point();
    private int mWidth;
    private int mHeight;

    private Point mTouchPoint;

    public Practice10HistogramView(Context context) {
        super(context);
    }

    public Practice10HistogramView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice10HistogramView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setLayerType(LAYER_TYPE_SOFTWARE, null);

//        综合练习
//        练习内容：使用各种 Canvas.drawXXX() 方法画直方图
        mBarPaint.setColor(Color.parseColor("#72b916"));
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setTextSize(24);
        int axisLength = mWidth * 2 / 3;
        int axisHeight = mHeight * 7 / 15;
        int barCount = mBarDataList.size();
        int i = 0;
        for (BarData data : mBarDataList) {
            mBarPoint.set((int) (axisLength / barCount * (i + 1f / 2)) + mWidth / 2 - mWidth / 3, mHeight * 2 / 3);
            float height = data.getPercent() * axisHeight;
            drawBar(canvas, mBarPaint, mBarPoint, height, mTextPaint, data.getText());
            i++;
        }

        mAxisPaint.setColor(Color.WHITE);
        mAxisPaint.setStrokeWidth(2);
        drawAxis(canvas, mAxisPaint);

        mTouchPaint.setColor(Color.WHITE);
        mTouchPaint.setStyle(Paint.Style.STROKE);
        mTouchPaint.setStrokeWidth(2);
        mTouchPaint.setPathEffect(new DashPathEffect(new float[]{4, 4}, 1));
        if (mTouchPoint != null) {
            drawTouchLine(canvas, mTouchPaint, mTouchPoint);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getWidth();
        mHeight = getHeight();

        mBarDataList.clear();
        mBarDataList.add(new BarData(0.005f, "Froyo"));
        mBarDataList.add(new BarData(0.05f, "GB"));
        mBarDataList.add(new BarData(0.04f, "ICS"));
        mBarDataList.add(new BarData(0.45f, "JB"));
        mBarDataList.add(new BarData(0.80f, "KitKat"));
        mBarDataList.add(new BarData(0.95f, "L"));
        mBarDataList.add(new BarData(0.4f, "M"));
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        // Remove touch line.
        if (MotionEvent.ACTION_UP == event.getAction()) {
            getParent().requestDisallowInterceptTouchEvent(false);
            if (mTouchPoint != null) {
                mTouchPoint = null;
                invalidate();
            }
        }
        if (event.getX() < (mWidth / 2 - mWidth / 3) || event.getX() > (mWidth / 2 + mWidth / 3)
                || event.getY() < mHeight / 5 || event.getY() > mHeight * 2 / 3) {
            return super.dispatchTouchEvent(event);
        } else {
            switch (event.getAction()) {
                // Show touch line.
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_MOVE:
                    getParent().requestDisallowInterceptTouchEvent(true);
                    mTouchPoint = new Point();
                    mTouchPoint.set((int) event.getX(), (int) event.getY());
                    invalidate();
                    break;
                default:
            }
            return true;
        }
    }

//    private int lastX = 0;
//    private int lastY = 0;
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        if (event.getX() < (mWidth / 2 - mWidth / 3) || event.getX() > (mWidth / 2 + mWidth / 3)
//                || event.getY() < mHeight / 5 || event.getY() > mHeight * 2 / 3) {
//            return false;
//        } else {
//            int x = (int) event.getX();
//            int y = (int) event.getY();
//            switch (event.getAction()) {
//                case MotionEvent.ACTION_DOWN:
//                    lastX = x;
//                    lastY = y;
//                    break;
//                case MotionEvent.ACTION_MOVE:
//                    mTouchPoint = null;
//                    if (Math.abs((y - lastY)) > 2 * Math.abs((x - lastX))) {
//                        mTouchPoint = new Point();
//                        mTouchPoint.set((int) event.getX(), (int) event.getY());
//                    }
//                    invalidate();
//                    break;
//                case MotionEvent.ACTION_UP:
//                    if (mTouchPoint != null) {
//                        mTouchPoint = null;
//                        invalidate();
//                    }
//                    performClick();
//                case MotionEvent.ACTION_CANCEL:
//                    if (mTouchPoint != null) {
//                        mTouchPoint = null;
//                        invalidate();
//                    }
//                    break;
//                default:
//            }
//            return true;
//        }
//    }

    private void drawAxis(Canvas canvas, Paint paint) {
        canvas.drawLine(mWidth / 2 - mWidth / 3, mHeight * 2 / 3,
                mWidth / 2 + mWidth / 3, mHeight * 2 / 3,
                paint);
        canvas.drawLine(mWidth / 2 - mWidth / 3, mHeight * 2 / 3,
                mWidth / 2 - mWidth / 3, mHeight / 5,
                paint);
    }

    private void drawBar(Canvas canvas, Paint barPaint, Point point, float height,
                         Paint textPaint, String text) {
        RectF rectF = new RectF();
        rectF.set(point.x - BAR_WIDTH / 2, point.y - height,
                point.x + BAR_WIDTH / 2, point.y);
        canvas.drawRect(rectF, barPaint);

        canvas.drawText(text, point.x, point.y + 20, textPaint);
    }

    private void drawTouchLine(Canvas canvas, Paint paint, Point point) {
        canvas.drawLine(mWidth / 2 - mWidth / 3, point.y, point.x, point.y, paint);
    }

    class BarData {
        private float percent;
        private String text;

        public BarData(float percent, String text) {
            this.percent = percent;
            this.text = text;
        }

        public float getPercent() {
            return percent;
        }

        public void setPercent(float percent) {
            this.percent = percent;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}
