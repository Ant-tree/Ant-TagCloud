package com.anttree.ant_cloud_tag;

import static com.anttree.ant_cloud_tag.AntCloudTagBuilder.EMPTY_COLOR;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Created by Hyunseok on 2018-11-03.
 * <p>
 * TalkerGridAdapter
 */
public class AntCloudTagAdapter extends RecyclerView.Adapter<AntCloudTagAdapter.ViewHolder> {

    private static final String TAG = "AASListAdapter";

    private Context mContext;
    private int itemLayout;

    private ArrayList<AntCloudTagListItem> dataList;
    private onItemClickListener mListener = null;

    private int colorBackground;
    private int colorBackgroundSelected;
    private int colorText;
    private int colorTextSelected;

    private boolean isAnimated;

    private int selectableNumber = -1;

    public interface onItemClickListener {
        void onItemClicked(AntCloudTagListItem item);

        void onListChanged(ArrayList<AntCloudTagListItem> dataList);
    }

    /**
     * Listener 를 부착합니다.
     */
    void setOnItemClickListener(onItemClickListener iListener) {
        mListener = iListener;
    }

    AntCloudTagAdapter(Context context
            , ArrayList<AntCloudTagListItem> dataList
            , int colorBackground
            , int colorBackgroundSelected
            , int colorText
            , int colorTextSelected
            , int selectableNumber
            , boolean isAnimated
            , int itemLayout) {

        this.colorBackground = colorBackground;
        this.colorBackgroundSelected = colorBackgroundSelected;
        this.colorText = colorText;
        this.colorTextSelected = colorTextSelected;
        this.selectableNumber = selectableNumber;
        this.isAnimated = isAnimated;

        mContext = context;
        this.dataList = dataList;
        this.itemLayout = itemLayout;
        setOnItemClickListener(mListener);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.mItemBase.setTag(position);

        AntCloudTagListItem listItem = dataList.get(position);
        ArrayList<AntCloudTagItem> antItems = listItem.getItems();
        int itemSize = antItems.size();

        TextView[] textViews = new TextView[]{holder.mTxt1
                , holder.mTxt2
                , holder.mTxt3
                , holder.mTxt4
                , holder.mTxt5
                , holder.mTxt6
                , holder.mTxt7
                , holder.mTxt8
                , holder.mTxt9
                , holder.mTxt10};

        initializeTextViews(textViews, antItems, itemSize);
        setListener(textViews, position, itemSize);
    }

    private void initializeTextViews(TextView[] textViews, ArrayList<AntCloudTagItem> antItems, int itemSize){
        for(int index = 0; index < itemSize; index++){
            textViews[index].setVisibility(View.VISIBLE);
            textViews[index].setText(antItems.get(index).getText());
            setSelected(textViews[index], textViews[index].isSelected());
        }
    }

    private void setListener(TextView[] textViews, int position, int itemSize){
        for(int index = 0; index < itemSize; index++){
            TextView textView = textViews[index];
            int itemPosition = index;
            textView.setOnClickListener(view -> onItemSelected(textView, position, itemPosition));
        }
    }

    private void onItemSelected(TextView view, int position, int itemPosition) {
        if (!checkSelectable() && !view.isSelected()) {
            return;
        }
        setSelected(view, !view.isSelected());

        AntCloudTagListItem listItem = dataList.get(position);
        ArrayList<AntCloudTagItem> antItems = listItem.getItems();

        antItems.get(itemPosition).setChecked(view.isSelected());
        listItem.setItems(antItems);
        mListener.onItemClicked(listItem);
        mListener.onListChanged(dataList);
    }

    private int getSelectedNumber() {
        int selected = 0;
        for (AntCloudTagListItem listItem : dataList) {
            for (AntCloudTagItem item : listItem.getItems()) {
                if (item.isChecked()) {
                    selected++;
                }
            }
        }
        return selected;
    }

    private boolean checkSelectable() {
        return selectableNumber == -1 || getSelectedNumber() < selectableNumber;
    }

    private int getIntTag(View view) {
        return Integer.parseInt(view.getTag().toString());
    }

    @Override
    public void onViewAttachedToWindow(@NonNull ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (isAnimated) {
            new Handler().postDelayed(() -> {
                holder.mItemBase.setVisibility(View.VISIBLE);
                holder.mItemBase.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.alpha));
            }, (getIntTag(holder.mItemBase)) * 100L + 400);
        } else {
            holder.mItemBase.setVisibility(View.VISIBLE);
        }
    }

    private void setSelected(TextView view, boolean isSelected) {
        view.setSelected(isSelected);
        if (colorBackground == EMPTY_COLOR
                || colorBackgroundSelected == EMPTY_COLOR
                || colorText == EMPTY_COLOR
                || colorTextSelected == EMPTY_COLOR) {
            return;
        }

        if (isSelected) {
            view.getBackground().setColorFilter(colorBackgroundSelected, PorterDuff.Mode.SRC_ATOP);
            view.setTextColor(colorTextSelected);
        } else {
            view.getBackground().setColorFilter(colorBackground, PorterDuff.Mode.SRC_ATOP);
            view.setTextColor(colorText);
        }

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final LinearLayout mItemBase;
        private final TextView mTxt1;
        private final TextView mTxt2;
        private final TextView mTxt3;
        private final TextView mTxt4;
        private final TextView mTxt5;
        private final TextView mTxt6;
        private final TextView mTxt7;
        private final TextView mTxt8;
        private final TextView mTxt9;
        private final TextView mTxt10;

        ViewHolder(View itemView) {
            super(itemView);
            mItemBase = itemView.findViewById(R.id.baseItem);
            mTxt1 = itemView.findViewById(R.id.itemTxt1);
            mTxt2 = itemView.findViewById(R.id.itemTxt2);
            mTxt3 = itemView.findViewById(R.id.itemTxt3);
            mTxt4 = itemView.findViewById(R.id.itemTxt4);
            mTxt5 = itemView.findViewById(R.id.itemTxt5);
            mTxt6 = itemView.findViewById(R.id.itemTxt6);
            mTxt7 = itemView.findViewById(R.id.itemTxt7);
            mTxt8 = itemView.findViewById(R.id.itemTxt8);
            mTxt9 = itemView.findViewById(R.id.itemTxt9);
            mTxt10 = itemView.findViewById(R.id.itemTxt10);
        }
    }
}
