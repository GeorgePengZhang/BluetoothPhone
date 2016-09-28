package com.aura.bluetoothphone.view.connect;

import static android.support.v4.widget.ViewDragHelper.INVALID_POINTER;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;


/**
 * @ClassName: SideBar
 * @Description: TODO
 * @author: steven zhang
 * @date: Sep 27, 2016 2:32:55 PM
 */
public class SideBar extends View {

    private static final String TAG = SideBar.class.getSimpleName();


    public interface OnTouchingLetterChangedListener {
        void onTouchingLetterChanged(String s);
    }

    private OnTouchingLetterChangedListener mOnTouchingLetterChangedListener;

    private String[] mLetters = { "A", "B", "C", "D", "E", "F", "G", "H", "I",
    		"J", "K", "L", "M", "N", "O", "P", "Q", "R",
			"S", "T", "U", "V", "W", "X", "Y", "Z", "#" };
    private Paint mPaint;
    private int mTextColor;

    private int mChoose = -1;

    private final float mDensity;
    private float mY;
    private float mHalfWidth, mHalfHeight;
    private float mLetterHeight;
    private float mAnimStep;

    private int mTouchSlop;
    private float mInitialDownY;
    private boolean mIsBeingDragged, mStartEndAnim;
    private int mActivePointerId = INVALID_POINTER;

    private RectF mIsDownRect = new RectF();

    public SideBar(Context context) {
        this(context, null);
    }

    public SideBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SideBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mPaint = new Paint();
        this.mTextColor = 0xff33B5E5;//Color.GRAY;
        this.mPaint.setAntiAlias(true);
        this.mPaint.setTextAlign(Paint.Align.CENTER);
        this.mPaint.setColor(this.mTextColor);

        //最小的滑动距离，超过这个值表示开始滑动(android 4.4其值为8)
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mDensity = getContext().getResources().getDisplayMetrics().density;

//        float dimension = getResources().getDimension(R.dimen.x20);
        setPadding(0, dip2px(20), 0, dip2px(20));
    }

    public void setOnTouchingLetterChangedListener(OnTouchingLetterChangedListener listener) {
        this.mOnTouchingLetterChangedListener = listener;
    }

    /**
     * 设置自定义索引
     * @param letters
     */
    public void setLetters(String[] letters) {
        mLetters = letters;
//        postInvalidate();
        requestLayout();
    }

    private int getLettersSize() {
        return mLetters.length;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        final int action = MotionEventCompat.getActionMasked(ev);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
                mIsBeingDragged = false;
                final float initialDownY = getMotionEventY(ev, mActivePointerId);
                if (initialDownY == -1) {
                    return false;
                }
                if (!mIsDownRect.contains(ev.getX(), ev.getY())) {
                    return false;
                }
                mInitialDownY = initialDownY;
                break;
            case MotionEvent.ACTION_MOVE:
                if (mActivePointerId == INVALID_POINTER) {
                    return false;
                }

                final float y = getMotionEventY(ev, mActivePointerId);
                if (y == -1) {
                    return false;
                }
                final float yDiff = Math.abs(y - mInitialDownY);
                if (yDiff > mTouchSlop && !mIsBeingDragged) {
                    mIsBeingDragged = true;
                }
                if (mIsBeingDragged) {
                    mY = y;
                    final float moveY = y - getPaddingTop() - mLetterHeight / 1.64f;
                    final int characterIndex = (int) (moveY / mHalfHeight * mLetters.length);
                    if (mChoose != characterIndex) {
                        if (characterIndex >= 0 && characterIndex < mLetters.length) {
                            mChoose = characterIndex;
                            Log.d(TAG, "mChoose " + mChoose + " mLetterHeight " + mLetterHeight);
                            mOnTouchingLetterChangedListener.onTouchingLetterChanged(mLetters[characterIndex]);
                        }
                    }
                    invalidate();
                }
                break;
            case MotionEventCompat.ACTION_POINTER_UP:
                onSecondaryPointerUp(ev);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (mOnTouchingLetterChangedListener != null) {
                    if (mIsBeingDragged) {
                        mOnTouchingLetterChangedListener.onTouchingLetterChanged(mLetters[mChoose]);
                    } else {
                        float downY = ev.getY() - getPaddingTop();
                        final int characterIndex = (int) (downY / mHalfHeight * mLetters.length);
                        if (characterIndex >= 0 && characterIndex < mLetters.length) {
                            mOnTouchingLetterChangedListener.onTouchingLetterChanged(mLetters[characterIndex]);
                        }
                    }
                }
                mStartEndAnim = mIsBeingDragged;
                mIsBeingDragged = false;
                mActivePointerId = INVALID_POINTER;

                mChoose = -1;
                mAnimStep = 0f;
                invalidate();
                return false;
        }
        return true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHalfWidth = w - dip2px(16);
        mHalfHeight = h - getPaddingTop() - getPaddingBottom();

        float lettersLen = getLettersSize();

        mLetterHeight = mHalfHeight / lettersLen;
        int textSize = (int) (mHalfHeight * 0.7f / lettersLen);
        this.mPaint.setTextSize(textSize);

        //设置可触摸滑动区域
        mIsDownRect.set(w - dip2px(16 * 2), 0, w, h);
        
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        
        for (int i = 0; i < getLettersSize(); i++) {
        	float measureTextHeight = this.mPaint.measureText(mLetters[i]);
            float letterPosY = mLetterHeight * (i+1) + getPaddingTop()-(mLetterHeight-measureTextHeight)/2;
            float diff, diffY, diffX;
            if (mChoose == i && i != 0 && i != getLettersSize() - 1) {
                diffX = 0f;
                diffY = 0f;
                diff = 2.16f;
            } else {
                float maxPos = Math.abs((mY - letterPosY) / mHalfHeight * 7f);
                diff = Math.max(1f, 2.2f - maxPos);
                if (mStartEndAnim && diff != 1f) {
                    diff -= mAnimStep;
                    if (diff <= 1f) {
                        diff = 1f;
                    }
                } else if (!mIsBeingDragged) {
                    diff = 1f;
                }
                diffY = maxPos * 50f * (letterPosY >= mY ? -1 : 1);
                diffX = maxPos * 100f;
            }
            canvas.save();
            canvas.scale(diff, diff, mHalfWidth * 1.20f + diffX, letterPosY + diffY);
            if (diff == 1f) {
                this.mPaint.setAlpha(255);
                this.mPaint.setTypeface(Typeface.DEFAULT);
            } else {
                int alpha = (int) (255 * (1 - Math.min(0.9, diff - 1)));
                if (mChoose == i)
                    alpha = 255;
                this.mPaint.setAlpha(alpha);
                this.mPaint.setTypeface(Typeface.DEFAULT_BOLD);
            }
            canvas.drawText(mLetters[i], mHalfWidth, letterPosY, this.mPaint);
            canvas.restore();
        }
        
        if (mChoose == -1 && mStartEndAnim && mAnimStep <= 0.6f) {
            mAnimStep += 0.6f;
            postInvalidateDelayed(25);
        } else {
            mAnimStep = 0f;
            mStartEndAnim = false;
        }
    }

    private int dip2px(int dipValue) {
        return (int) (dipValue * mDensity + 0.5f);
    }

    private float getMotionEventY(MotionEvent ev, int activePointerId) {
        final int index = MotionEventCompat.findPointerIndex(ev, activePointerId);
        if (index < 0) {
            return -1;
        }
        return MotionEventCompat.getY(ev, index);
    }

    private void onSecondaryPointerUp(MotionEvent ev) {
        final int pointerIndex = MotionEventCompat.getActionIndex(ev);
        final int pointerId = MotionEventCompat.getPointerId(ev, pointerIndex);
        if (pointerId == mActivePointerId) {
            final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
            mActivePointerId = MotionEventCompat.getPointerId(ev, newPointerIndex);
        }
    }
}
