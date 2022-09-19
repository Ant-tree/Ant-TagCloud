package com.anttree.ant_cloud_tag;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Hyunseok on 2018-04-06.
 * <p>
 * List 가 확장된 상태로 유지시킵니다.
 * 스크롤 뷰 내에서 사용할 수 있으며, 이중 스크롤이 아닌 스크롤이 불가능한 확장된 뷰로 고정됩니다.
 */
public class AntCloudTagView extends RecyclerView {
    boolean expanded = true;

    public AntCloudTagView(Context context) {
        super(context);
        this.setFadingEdgeLength(0);
        this.setOverScrollMode(OVER_SCROLL_NEVER);
    }

    public AntCloudTagView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setFadingEdgeLength(0);
        this.setOverScrollMode(OVER_SCROLL_NEVER);
    }

    public AntCloudTagView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.setFadingEdgeLength(0);
        this.setOverScrollMode(OVER_SCROLL_NEVER);
    }

    public boolean isExpanded() {
        return expanded;
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (isExpanded()) {
            // Calculate entire height by providing a very large height hint.
            // View.MEASURED_SIZE_MASK represents the largest height possible.
            int expandSpec = View.MeasureSpec.makeMeasureSpec(MEASURED_SIZE_MASK, View.MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, expandSpec);
            ViewGroup.LayoutParams params = getLayoutParams();
            params.height = getMeasuredHeight();
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

}
