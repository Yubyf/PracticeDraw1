package com.hencoder.hencoderpracticedraw1.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class Practice9DrawPathView extends View {
    private static final float CIRCLE_BEZIER = 0.552284749831F;
    private static final int CIRCLE_RADIUS = 200;
    private static final int HEART_RATE = 40;
    private Point mCircleCenterPoint = new Point();
    private Point mHeartCenterPoint = new Point();
    private List<PointF> mCircleBezierPoints = new ArrayList<>();
    private List<PointF> mHeartBezierPoints = new ArrayList<>();

    private Paint paint = new Paint();
    private Path mCirclePath = new Path();
    private Path mHeartPath = new Path();

    public Practice9DrawPathView(Context context) {
        super(context);
    }

    public Practice9DrawPathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice9DrawPathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        练习内容：使用 canvas.drawPath() 方法画心形
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(6);

        mCirclePath.moveTo(mCircleBezierPoints.get(0).x, mCircleBezierPoints.get(0).y);
        for (int i = 1; i < mCircleBezierPoints.size() - 1; i++) {
            mCirclePath.cubicTo(mCircleBezierPoints.get(i).x, mCircleBezierPoints.get(i++).y,
                    mCircleBezierPoints.get(i).x, mCircleBezierPoints.get(i++).y,
                    mCircleBezierPoints.get(i >= mCircleBezierPoints.size() ? 0 : i).x,
                    mCircleBezierPoints.get(i >= mCircleBezierPoints.size() ? 0 : i).y);
        }

        mHeartPath.moveTo(mHeartBezierPoints.get(0).x, mHeartBezierPoints.get(0).y);
        for (int i = 1; i < mHeartBezierPoints.size() - 1; i++) {
            mHeartPath.cubicTo(mHeartBezierPoints.get(i).x, mHeartBezierPoints.get(i++).y,
                    mHeartBezierPoints.get(i).x, mHeartBezierPoints.get(i++).y,
                    mHeartBezierPoints.get(i >= mCircleBezierPoints.size() ? 0 : i).x,
                    mHeartBezierPoints.get(i >= mCircleBezierPoints.size() ? 0 : i).y);
        }

        canvas.drawPath(mCirclePath, paint);
        canvas.drawPath(mHeartPath, paint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mCircleCenterPoint.set(getWidth() / 4, getHeight() / 2);
        mHeartCenterPoint.set(getWidth() * 3 / 4, getHeight() / 2 - 50);

        mCircleBezierPoints.add(new PointF(mCircleCenterPoint.x, mCircleCenterPoint.y - CIRCLE_RADIUS));
        mCircleBezierPoints.add(new PointF(mCircleCenterPoint.x - CIRCLE_RADIUS * CIRCLE_BEZIER,
                mCircleCenterPoint.y - CIRCLE_RADIUS));
        mCircleBezierPoints.add(new PointF(mCircleCenterPoint.x - CIRCLE_RADIUS,
                mCircleCenterPoint.y - CIRCLE_RADIUS * CIRCLE_BEZIER));
        mCircleBezierPoints.add(new PointF(mCircleCenterPoint.x - CIRCLE_RADIUS, mCircleCenterPoint.y));
        mCircleBezierPoints.add(new PointF(mCircleCenterPoint.x - CIRCLE_RADIUS,
                mCircleCenterPoint.y + CIRCLE_RADIUS * CIRCLE_BEZIER));
        mCircleBezierPoints.add(new PointF(mCircleCenterPoint.x - CIRCLE_RADIUS * CIRCLE_BEZIER,
                mCircleCenterPoint.y + CIRCLE_RADIUS));
        mCircleBezierPoints.add(new PointF(mCircleCenterPoint.x, mCircleCenterPoint.y + CIRCLE_RADIUS));
        mCircleBezierPoints.add(new PointF(mCircleCenterPoint.x + CIRCLE_RADIUS * CIRCLE_BEZIER,
                mCircleCenterPoint.y + CIRCLE_RADIUS));
        mCircleBezierPoints.add(new PointF(mCircleCenterPoint.x + CIRCLE_RADIUS,
                mCircleCenterPoint.y + CIRCLE_RADIUS * CIRCLE_BEZIER));
        mCircleBezierPoints.add(new PointF(mCircleCenterPoint.x + CIRCLE_RADIUS, mCircleCenterPoint.y));
        mCircleBezierPoints.add(new PointF(mCircleCenterPoint.x + CIRCLE_RADIUS,
                mCircleCenterPoint.y - CIRCLE_RADIUS * CIRCLE_BEZIER));
        mCircleBezierPoints.add(new PointF(mCircleCenterPoint.x + CIRCLE_RADIUS * CIRCLE_BEZIER,
                mCircleCenterPoint.y - CIRCLE_RADIUS));

        mHeartBezierPoints.add(new PointF(mHeartCenterPoint.x, mHeartCenterPoint.y));
        mHeartBezierPoints.add(new PointF(mHeartCenterPoint.x, mHeartCenterPoint.y - 3 * HEART_RATE));
        mHeartBezierPoints.add(new PointF(mHeartCenterPoint.x - 5 * HEART_RATE, mHeartCenterPoint.y - 3 * HEART_RATE));
        mHeartBezierPoints.add(new PointF(mHeartCenterPoint.x - 5 * HEART_RATE, mHeartCenterPoint.y));
        mHeartBezierPoints.add(new PointF(mHeartCenterPoint.x - 5 * HEART_RATE, mHeartCenterPoint.y + 3 * HEART_RATE));
        mHeartBezierPoints.add(new PointF(mHeartCenterPoint.x, mHeartCenterPoint.y + 3.5f * HEART_RATE));
        mHeartBezierPoints.add(new PointF(mHeartCenterPoint.x, mHeartCenterPoint.y + 6 * HEART_RATE));
        mHeartBezierPoints.add(new PointF(mHeartCenterPoint.x, mHeartCenterPoint.y + 3.5f * HEART_RATE));
        mHeartBezierPoints.add(new PointF(mHeartCenterPoint.x + 5 * HEART_RATE, mHeartCenterPoint.y + 3 * HEART_RATE));
        mHeartBezierPoints.add(new PointF(mHeartCenterPoint.x + 5 * HEART_RATE, mHeartCenterPoint.y));
        mHeartBezierPoints.add(new PointF(mHeartCenterPoint.x + 5 * HEART_RATE, mHeartCenterPoint.y - 3 * HEART_RATE));
        mHeartBezierPoints.add(new PointF(mHeartCenterPoint.x, mHeartCenterPoint.y - 3 * HEART_RATE));
    }
}
