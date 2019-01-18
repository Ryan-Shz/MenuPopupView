package com.sc.framework.component.popup;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.WindowManager.LayoutParams;
import android.widget.PopupWindow;

/*
 * create by 17/2/18 下午9:12
 *
 * @author Ryan
 */
abstract class AbsPopupView extends PopupWindow {

    private PopupLayout.PopupLocation mPopupLocation = PopupLayout.PopupLocation.Bottom;

    AbsPopupView(Context context) {
        super(context);
        setWidth(LayoutParams.WRAP_CONTENT);
        setHeight(LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        setOutsideTouchable(true);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    void measureContentView() {
        if (getContentView() != null) {
            getContentView().measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        }
    }

    public void setPopupLocation(PopupLayout.PopupLocation popupLocation) {
        this.mPopupLocation = popupLocation;
    }

    /**
     * 获取载体控件在窗口中的坐标, 并计算出载体控件的中心点和窗口显示区域矩形
     *
     * @param anchor 载体控件
     * @param frame  载体控件所在窗口的矩形区域
     * @param origin 记录载体控件一半长宽
     * @return 载体控件的坐标
     */
    public int[] reviseFrameAndOrigin(View anchor, Rect frame, Point origin) {
        int[] location = new int[2];
        anchor.getLocationInWindow(location);
        if (origin.x < 0 || origin.y < 0) {
            origin.set(anchor.getWidth() >> 1, anchor.getHeight() >> 1);
        }
        if (frame.isEmpty() || !frame.contains(origin.x + location[0], origin.y + location[1])) {
            anchor.getWindowVisibleDisplayFrame(frame);
        }
        return location;
    }

    public void show(View anchor) {
        // 记录载体的一半长宽
        Point anchorPoint = new Point(-1, -1);
        // 窗口矩形
        Rect windowFrame = new Rect();
        // 载体控件坐标
        int[] location = reviseFrameAndOrigin(anchor, windowFrame, anchorPoint);
        // 载体控件x, y
        int x = location[0], y = location[1];
        // 载体控件宽高
        int height = anchor.getHeight();
        // 菜单内容的宽高
        int contentWidth = getContentView().getMeasuredWidth();
        int contentHeight = getContentView().getMeasuredHeight();
        // 计算popup内容区域需要偏移的x, y值
        Point offset = getOffset(windowFrame, new Rect(x, y + height, contentWidth + x,
                contentHeight + y + height), x + anchorPoint.x, y + anchorPoint.y);
        if (mPopupLocation == PopupLayout.PopupLocation.TOP) {
            if (y - contentHeight > windowFrame.top) {
                showAtTop(anchor, anchorPoint, offset.x, -height - contentHeight);
            }
        } else {
            if (y + height + contentHeight < windowFrame.bottom) {
                showAtBottom(anchor, anchorPoint, offset.x, 0);
            }
        }
    }

    /**
     * @param frame 窗口矩形
     * @param rect  菜单控件矩形区域
     * @param x     载体控件的中心点x值
     * @param y     载体控件的中心点y值
     * @return 需要偏移的x、y值
     */
    private Point getOffset(Rect frame, Rect rect, int x, int y) {
        Rect rt = new Rect(rect);
        rt.offset(x - rt.centerX(), y - rt.centerY());
        if (!frame.contains(rt)) {
            int offsetX = 0, offsetY = 0;
            if (rt.bottom > frame.bottom) {
                offsetY = frame.bottom - rt.bottom;
            }

            if (rt.top < frame.top) {
                offsetY = frame.top - rt.top;
            }

            if (rt.right > frame.right) {
                offsetX = frame.right - rt.right;
            }

            if (rt.left < frame.left) {
                offsetX = frame.left - rt.left;
            }

            rt.offset(offsetX, offsetY);
        }
        return new Point(rt.left - rect.left, rt.top - rect.top);
    }

    public void showAtTop(View anchor, Point origin, int xOff, int yOff) {
        showAsDropDown(anchor, xOff, yOff);
    }

    public void showAtBottom(View anchor, Point origin, int xOff, int yOff) {
        showAsDropDown(anchor, xOff, yOff);
    }

}
