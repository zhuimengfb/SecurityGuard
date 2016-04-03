package com.fbi.securityguard.view.widget.sinkview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Region;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.fbi.securityguard.R;

/**
 * Created by fubo on 2016/4/3 0003.
 * email:bofu1993@163.com
 */
public class SinkView extends FrameLayout {
  private static final int DEFAULT_TEXTCOLOT = 0xFFFFFFFF;

  private static final int DEFAULT_TEXTSIZE = 60;

  private float mPercent = 0.3f;

  private Paint mPaint = new Paint();

  private Bitmap mBitmap;

  private Bitmap mScaledBitmap;

  private float mLeft;

  private int mSpeed = 15;

  private int mRepeatCount = 0;

  private Status mFlag = Status.NONE;

  private int mTextColor = DEFAULT_TEXTCOLOT;

  private int mTextSize = DEFAULT_TEXTSIZE;

  public static final int TYPE_USED = 0;
  public static final int TYPE_UNUSED = 1;
  private String text = "";
  private String preffixText = "";
  private int type = TYPE_USED;
  public enum Status {
    RUNNING, NONE
  }

  public SinkView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public void setText(String text, int type) {
    this.type = type;
    if (type == TYPE_USED) {
      preffixText = getResources().getString(R.string.usedTrffix);
    } else if (type == TYPE_UNUSED) {
      preffixText = getResources().getString(R.string.unusedTraffic);
    }
    mFlag = Status.RUNNING;
    this.text = text;
    postInvalidate();
  }

  public void setTextColor(int color) {
    mTextColor = color;
  }

  public void setTextSize(int size) {
    mTextSize = size;
  }

  public void setPercent(float percent) {
    mFlag = Status.RUNNING;
    mPercent = percent;
    text = (int) (mPercent * 100) + "%";
    postInvalidate();
  }

  public void setStatus(Status status) {
    mFlag = status;
  }

  public void clear() {
    mFlag = Status.NONE;
    if (mScaledBitmap != null) {
      mScaledBitmap.recycle();
      mScaledBitmap = null;
    }

    if (mBitmap != null) {
      mBitmap.recycle();
      mBitmap = null;
    }
  }

  @Override
  protected void dispatchDraw(Canvas canvas) {
    super.dispatchDraw(canvas);
    int width = getWidth();
    int height = getHeight();

    //裁剪成圆区域
    Path path = new Path();
    canvas.save();
    path.reset();
    canvas.clipPath(path);
    path.addCircle(width / 2, height / 2, width / 2, Path.Direction.CCW);
    canvas.clipPath(path, Region.Op.REPLACE);

    if (mFlag == Status.RUNNING) {
      if (mScaledBitmap == null) {
        mBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.wave2);
        mScaledBitmap = Bitmap.createScaledBitmap(mBitmap, mBitmap.getWidth(), getHeight(), false);
        mBitmap.recycle();
        mBitmap = null;
        mRepeatCount = (int) Math.ceil(getWidth() / mScaledBitmap.getWidth() + 0.5) + 1;
      }
      for (int idx = 0; idx < mRepeatCount; idx++) {
        canvas.drawBitmap(mScaledBitmap, mLeft + (idx - 1) * mScaledBitmap.getWidth(), (1-mPercent) * getHeight(), null);
      }
      mPaint.setColor(mTextColor);
      mPaint.setTextSize(mTextSize);
      mPaint.setStyle(Style.FILL);
      canvas.drawText(preffixText, (getWidth() - mPaint.measureText(preffixText)) / 2,
          getHeight() / 2 - mTextSize / 3, mPaint);
      canvas.drawText(text, (getWidth() - mPaint.measureText(text)) / 2, getHeight() / 2
          + mTextSize / 3 * 2, mPaint);
      mLeft += mSpeed;
      if (mLeft >= mScaledBitmap.getWidth())
        mLeft = 0;
      // 绘制外圆环
      mPaint.setStyle(Paint.Style.STROKE);
      mPaint.setStrokeWidth(4);
      mPaint.setAntiAlias(true);
      mPaint.setColor(Color.rgb(33, 211, 39));
      canvas.drawCircle(width / 2, height / 2, width / 2 - 2, mPaint);

      postInvalidateDelayed(20);
    }
    canvas.restore();

  }



}
