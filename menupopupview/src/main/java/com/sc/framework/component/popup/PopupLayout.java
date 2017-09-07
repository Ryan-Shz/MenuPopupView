package com.sc.framework.component.popup;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * 弹出的气泡菜单
 *
 * @author ShamsChu
 * @Date 17/2/24 下午5:57
 */
public class PopupLayout extends LinearLayout implements View.OnLayoutChangeListener, View.OnClickListener, IPopupLayout {

    private static final String TAG = PopupLayout.class.getName();
    private int mOffset = 0;
    private int mRadiusSize = DEFAULT_RADIUS;
    private int mBulgeSize = DEFAULT_BULGE_SIZE;
    private Path mPopMaskPath;
    private Path mBulgePath;
    private Path mDestBulgePath;
    private Matrix mCornuteMatrix;
    private PopupLocation mPopupLocation = PopupLocation.Bottom;
    private static final int DEFAULT_RADIUS = 16;
    private static final int DEFAULT_BULGE_SIZE = 16;
    private static final Xfermode MODE = new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP);
    private ImageView mLeftIndicator;
    private ImageView mRightIndicator;
    private PopupHorizontalScrollView mScrollView;
    private PopupItemLayout mPopupItemContainer;
    private int mScrollMaxWidth = 0;
    private OnPopupItemClickListener mOnPopupItemClickListener;
    private int mDriverColorResId = R.color.menu_popup_driver_default;
    private Paint mPaint;
    private MenuPopupAdapter mAdapter;
    private DataSetObserver mDataSetObserver;

    public PopupLayout(Context context) {
        this(context, null, 0);
    }

    public PopupLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PopupLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
        initBubble();
        initListeners();
    }

    private void initViews() {
        inflate(getContext(), R.layout.menu_popup_layout, this);
        mLeftIndicator = (ImageView) findViewById(R.id.left_arrow_iv);
        mRightIndicator = (ImageView) findViewById(R.id.right_arrow_iv);
        mScrollView = (PopupHorizontalScrollView) findViewById(R.id.scrollView);
        mPopupItemContainer = new PopupItemLayout(getContext());
        mPopupItemContainer.setOrientation(LinearLayout.HORIZONTAL);
        mScrollView.addView(mPopupItemContainer);
        mLeftIndicator.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        mScrollView.setWindowSpacing(mLeftIndicator.getMeasuredWidth());
    }

    private void resetPaint() {
        mPaint.setXfermode(MODE);
        mPaint.setColor(getContext().getResources().getColor(R.color.menu_popup_transparent_25));
    }

    private void initBubble() {
        if (getBackground() == null) {
            // 需要设置背景，可能是因为没有背景Layout就不会去执行绘制操作
            setBackgroundColor(Color.TRANSPARENT);
        }
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        resetPaint();
        mBulgePath = new Path();
        mPopMaskPath = new Path();
        mDestBulgePath = new Path();
        mCornuteMatrix = new Matrix();
        resetBulge();
        resetMask();
    }

    private void initListeners() {
        mLeftIndicator.setOnClickListener(this);
        mRightIndicator.setOnClickListener(this);
        addOnLayoutChangeListener(this);
    }

    private void resetBulge() {
        mBulgePath.reset();
        mBulgePath.lineTo(mBulgeSize << 1, 0);
        mBulgePath.lineTo(mBulgeSize, mBulgeSize);
        mBulgePath.close();
    }

    private void resetMask() {
        mPopMaskPath.reset();
        int width = getMeasuredWidth(), height = getMeasuredHeight();
        if (width <= mRadiusSize || height <= mRadiusSize) return;
        int offset = reviseOffset(mOffset);
        mPopMaskPath.addRect(new RectF(0, 0, width, height), Path.Direction.CW);
        mPopMaskPath.addRoundRect(new RectF(mBulgeSize, mBulgeSize, width - mBulgeSize, height - mBulgeSize), mRadiusSize, mRadiusSize, Path.Direction.CCW);
        mPopMaskPath.setFillType(Path.FillType.INVERSE_EVEN_ODD);

        switch (mPopupLocation) {
            case Bottom:
                mCornuteMatrix.setRotate(180, mBulgeSize, 0);
                mCornuteMatrix.postTranslate(0, mBulgeSize);
                mBulgePath.transform(mCornuteMatrix, mDestBulgePath);
                mPopMaskPath.addPath(mDestBulgePath, offset - mBulgeSize, 0);
                break;
            case TOP:
                mCornuteMatrix.setTranslate(-mBulgeSize, 0);
                mBulgePath.transform(mCornuteMatrix, mDestBulgePath);
                mPopMaskPath.addPath(mDestBulgePath, offset, height - mBulgeSize);
                break;
        }
    }

    private int reviseOffset(int offset) {
        int size = 0, bulgeWidth = mBulgeSize << 1;
        switch (mPopupLocation) {
            case TOP:
            case Bottom:
                size = getWidth();
                break;
        }
        offset = Math.max(offset, mRadiusSize + bulgeWidth);
        if (size > 0) {
            offset = Math.min(offset, size - mRadiusSize - bulgeWidth);
            if (mRadiusSize + bulgeWidth > offset) {
                offset = size >> 1;
            }
        }
        return offset;
    }

    public void setOffset(int offset) {
        if (mOffset != offset) {
            mOffset = offset;
            resetMask();
            postInvalidate();
        }
    }

    public void setPopupLocation(PopupLocation popupLocation) {
        if (mPopupLocation != popupLocation) {
            mPopupLocation = popupLocation;
            resetMask();
            postInvalidate();
        }
    }

    public int getOffset() {
        return mOffset;
    }

    @Override
    public void draw(Canvas canvas) {
        int layer = canvas.saveLayer(0, 0, canvas.getWidth(),
                canvas.getHeight(), null, Canvas.ALL_SAVE_FLAG);
        super.draw(canvas);
        canvas.drawPath(mPopMaskPath, mPaint);
        canvas.restoreToCount(layer);
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom,
                               int oldLeft, int oldTop, int oldRight, int oldBottom) {
        resetMask();
        postInvalidate();
    }


    public void setContentView(View view) {
        mScrollView.addView(view);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == mLeftIndicator.getId()) {
            showPrevious();
        } else if (v.getId() == mRightIndicator.getId()) {
            showNext();
        }
    }

    private void showNext() {
        int scrollOffset = mScrollView.getScrollX() + mScrollView.getMeasuredWidth();
        if (scrollOffset < mScrollMaxWidth) {
            mScrollView.scrollTo(scrollOffset, 0);
            mLeftIndicator.setVisibility(VISIBLE);
        }
        resetIndicator();
    }

    private void resetIndicator() {
        int scrollX = mScrollView.getScrollX();
        mLeftIndicator.setVisibility(scrollX > 0 ? VISIBLE : GONE);
        mRightIndicator.setVisibility(scrollX + mScrollView.getMeasuredWidth() >= mScrollMaxWidth ? GONE : VISIBLE);
    }

    private void showPrevious() {
        if (mScrollView.getScrollX() > 0) {
            int scrollOffset = mScrollView.getScrollX() - mScrollView.getMeasuredWidth();
            mScrollView.scrollTo(scrollOffset, 0);
        }
        resetIndicator();
    }

    @Override
    public void setOnPopupItemClickListener(OnPopupItemClickListener onPopupItemClickListener) {
        mOnPopupItemClickListener = onPopupItemClickListener;
    }

    @Override
    public void notifyDataSetChanged() {
        if (mPopupItemContainer != null) {
            if (mAdapter == null) {
                throw new NullPointerException("MenuPopupAdapter can not be null!");
            }
            int itemCount = mAdapter.getItemCount();
            if (itemCount <= 0) {
                Log.w(TAG, "item count is 0, don't do anything.");
                return;
            }
            resetMenus();
            for (int i = 0; i < itemCount; i++) {
                View popupItemView = mAdapter.getView(this, i);
                if (popupItemView != null) {
                    final int finalI = i;
                    popupItemView.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mOnPopupItemClickListener != null) {
                                mOnPopupItemClickListener.onItemClick(v, finalI);
                            }
                        }
                    });
                    mPopupItemContainer.addView(popupItemView);
                }
            }
        }
        measureScrollMaxOffset();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        resetIndicator();
    }

    @Override
    public void setIndicatorDriverColorResId(int colorResId) {
        mDriverColorResId = colorResId;
    }

    @Override
    public void setDriverColorResId(int driverColorResId) {
        mDriverColorResId = driverColorResId;
        if (mPopupItemContainer != null) {
            mPopupItemContainer.setDriverColorResId(mDriverColorResId);
        }
    }

    @Override
    public void setAdapter(MenuPopupAdapter adapter) {
        if (mAdapter != null && mDataSetObserver != null) {
            mAdapter.unregisterDataSetObserver(mDataSetObserver);
        }
        mDataSetObserver = new AdapterDataSetObserver();
        adapter.registerDataSetObserver(mDataSetObserver);
        mAdapter = adapter;
        notifyDataSetChanged();
    }

    @Override
    public MenuPopupAdapter getAdapter() {
        return mAdapter;
    }

    private void resetMenus() {
        mPopupItemContainer.removeAllViews();
    }

    private void measureScrollMaxOffset() {
        mPopupItemContainer.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        mScrollMaxWidth = mPopupItemContainer.getMeasuredWidth();
    }

    public interface OnPopupItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setRadiusSize(int radius) {
        if (mRadiusSize != radius) {
            mRadiusSize = radius;
            resetMask();
            postInvalidate();
        }
    }

    public void setBulgeSize(int size) {
        if (mBulgeSize != size) {
            mBulgeSize = size;
            resetBulge();
            resetMask();
            postInvalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(getResources().getColor(mDriverColorResId));
        mPaint.setStrokeWidth(3);
        int itemLeft = mScrollView.getLeft();
        int itemRight = mScrollView.getRight();
        canvas.drawLine(itemLeft, 0, itemLeft, getMeasuredHeight(), mPaint);
        canvas.drawLine(itemRight, 0, itemRight, getMeasuredHeight(), mPaint);
        resetPaint();
    }

    public enum PopupLocation {
        TOP,
        Bottom
    }

    private class AdapterDataSetObserver extends DataSetObserver {
        @Override
        public void onChanged() {
            super.onChanged();
            notifyDataSetChanged();
        }

        @Override
        public void onInvalidated() {
            super.onInvalidated();
        }
    }
}
