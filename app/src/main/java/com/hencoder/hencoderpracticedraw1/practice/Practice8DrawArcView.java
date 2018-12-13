package com.hencoder.hencoderpracticedraw1.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class Practice8DrawArcView extends View {
    private Paint paint = new Paint();
    private RectF rectF = new RectF();

    public Practice8DrawArcView(Context context) {
        super(context);
    }

    public Practice8DrawArcView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice8DrawArcView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        练习内容：使用 canvas.drawArc() 方法画弧形和扇形
        int width = getWidth();
        int height = getHeight();
        paint.setAntiAlias(true);

        rectF.set(width / 3, height / 3, width * 2 / 3, height * 2 / 3);
        canvas.drawArc(rectF, -120, 100, true, paint);

        paint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(rectF, -180, 50, false, paint);

        paint.setStyle(Paint.Style.FILL);
        canvas.drawArc(rectF, 20, 140, false, paint);
    }
}
