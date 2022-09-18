package com.anttree.ant_cloud_tag;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import java.util.ArrayList;

/**
 * AntAutoSizingListBuilder
 * <p>
 * new AntAutoSizingListBuilder()
 * -> sizingPolicy() -> initialize()
 * -> with()
 * -> setOnItemCheckChangeListener()
 */
@SuppressWarnings("all")
public class AntCloudTagBuilder {

    private static final String TAG = "AASListBuilder";

    private Context context;
    private boolean isFullSize;
    private boolean isExpanded;
    private ArrayList<String> bodyList = new ArrayList<>();
    private ArrayList<Integer> bodySizeList = new ArrayList<>();
    private ArrayList<AntCloudTagItem> itemList = new ArrayList<>();

    /**
     * Sizing Policy
     */
    private int sizingPolicy = SIZING_WITH_FONT_CONFIGURATION;
    public static final int SIZING_WITH_TEXT_LENGTH = 1;
    public static final int SIZING_WITH_FONT_CONFIGURATION = 2;

    /**
     * Font Scale From Configuration
     */
    private static final int FONT_SCALE_SMALL = 0;
    private static final int FONT_SCALE_NORMAL = 1;
    private static final int FONT_SCALE_BIG = 2;

    private int MAX_ITEM_LENGTH_DEFAULT = 10;
    private int MAX_COLUMN = 10 - 1;
    private int SELECTABLE_NUMBER = -1;
    private boolean isAnimated = true;

    /**
     * Colors
     */
    public static final int EMPTY_COLOR = 99999999;
    private int COLOR_BACKGROUND = EMPTY_COLOR;
    private int COLOR_BACKGROUND_SELECTED = EMPTY_COLOR;
    private int COLOR_TEXT = EMPTY_COLOR;
    private int COLOR_TEXT_SELECTED = EMPTY_COLOR;

    private onItemCheckChangeListener mListener = null;

    public interface onItemCheckChangeListener {
        //void onItemState(AntListItem item);
        void onListChanged(ArrayList<AntCloudTagListItem> dataList);
    }

    public void setOnItemCheckChangeListener(onItemCheckChangeListener iListener) {
        mListener = iListener;
    }

    /**
     * AntAutoSizingListBuilder
     * 가장 먼저 호출, Context 및 Full Size 여부 전달 받은 이후 진행
     *
     * @param context    context
     * @param isFullSize 각 Column 에서 Row 마다의 사이즈 통일
     *                   및 전체 화면으로 채우기 여부
     */
    public AntCloudTagBuilder(Context context
            , boolean isFullSize) {
        this.context = context;
        this.isFullSize = isFullSize;
    }

    private int getMaxItemLength() {
        int width = getScreenWidth();
        int height = getScreenHeight();
        float dpi = getDensity(context);

        Log.d(TAG, "width : " + width);
        Log.d(TAG, "height : " + height);
        Log.d(TAG, "dpi : " + dpi);
        Log.d(TAG, "width / dpi : " + width / dpi);

        int MAX_ITEM_LENGTH = (int) (MAX_ITEM_LENGTH_DEFAULT * (width / dpi) / 360);
        Log.d(TAG, "MAX_ITEM_LENGTH : " + MAX_ITEM_LENGTH);
        return MAX_ITEM_LENGTH;
    }

    private static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    private static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    /**
     * 현재 디바이스의 DPI 를 받아옵니다.
     * 예외시 DPI = XHDPI
     */
    @SuppressWarnings({"ConstantConditions", "UnnecessaryLocalVariable"})
    private float getDensity(Context context) {
        try {
            DisplayMetrics metrics = new DisplayMetrics();
            ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(metrics);

            float dpi = metrics.density;
            //int density = metrics.densityDpi;

            return dpi;
        } catch (Exception e) {
            e.printStackTrace();
            return 2.0f;
        }
    }

    public AntCloudTagBuilder setMaxSeletableNumber(int number){
        this.SELECTABLE_NUMBER = number;
        return this;
    }

    public AntCloudTagBuilder setMaxColumn(int column) {
        this.MAX_COLUMN = column - 1;
        Log.d(TAG, "MAX_COLUMN : " + MAX_COLUMN);
        return this;
    }

    public AntCloudTagBuilder setDefaultMaxLength(int maxLength) {
        this.MAX_ITEM_LENGTH_DEFAULT = maxLength;
        return this;
    }

    public AntCloudTagBuilder setAnimatedChange(boolean isAnimated) {
        this.isAnimated = isAnimated;
        return this;
    }

    /**
     * 텍스트 뷰 크기에 따른 Sizing 여부
     * <p>
     * {@link #SIZING_WITH_FONT_CONFIGURATION}
     * {@link #SIZING_WITH_TEXT_LENGTH}
     * 두가지 Policy 에 따라서 적용됨.
     *
     * @param sizingPolicy AutoSizing Policy
     *                     Default : SIZING_WITH_FONT_CONFIGURATION
     * @return AntAutoSizingListBuilder
     */
    public AntCloudTagBuilder sizingPolicy(int sizingPolicy) {
        this.sizingPolicy = sizingPolicy;
        return this;
    }

    /**
     * 데이터 및 sizing Policy 에 따른 initializing
     * dataList 및 itemList 추출
     *
     * @param bodyList String List
     */
    public AntCloudTagBuilder initialize(ArrayList<String> bodyList) {
        this.bodyList = bodyList;
        if (sizingPolicy == SIZING_WITH_TEXT_LENGTH)
            itemList = calculateSize(bodyList);
        else if (sizingPolicy == SIZING_WITH_FONT_CONFIGURATION)
            itemList = calculateSizeWithConfiguration(bodyList);
        return this;
    }

    /**
     * @param isExpanded RecyclerView isExpanded
     */
    public AntCloudTagBuilder expanded(boolean isExpanded) {
        this.isExpanded = isExpanded;
        return this;
    }

    public AntCloudTagBuilder setColorBackGround(int color) {
        this.COLOR_BACKGROUND = getColorRes(color);
        return this;
    }

    public AntCloudTagBuilder setColorBackGroundSelected(int color) {
        this.COLOR_BACKGROUND_SELECTED = getColorRes(color);
        return this;
    }

    public AntCloudTagBuilder setColorText(int color) {
        this.COLOR_TEXT = getColorRes(color);
        return this;
    }

    public AntCloudTagBuilder setColorTextSelected(int color) {
        this.COLOR_TEXT_SELECTED = getColorRes(color);
        return this;
    }

    /**
     * 안드로이드 버전에 따라 Resource 에서 Color 를 받아옵니다.
     *
     * @param colorId R.color 이후의 color ID
     */
    @SuppressWarnings("deprecation")
    private int getColorRes(int colorId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.getResources().getColor(colorId, null);
        } else {
            return context.getResources().getColor(colorId);
        }
    }

    /**
     * Set Adapter to ListView
     */
    public AntCloudTagBuilder with(AntCloudTagView listView) {
        ArrayList<AntCloudTagListItem> dataList = getDataList();

        AntCloudTagAdapter adapter
                = new AntCloudTagAdapter(context
                , dataList
                , COLOR_BACKGROUND
                , COLOR_BACKGROUND_SELECTED
                , COLOR_TEXT
                , COLOR_TEXT_SELECTED
                , SELECTABLE_NUMBER
                , isAnimated
                , isFullSize
                ? R.layout.item_list_auto_sizing_full
                : R.layout.item_list_auto_sizing);
        adapter.setOnItemClickListener(
                new AntCloudTagAdapter
                        .onItemClickListener() {
                    @Override
                    public void onItemClicked(AntCloudTagListItem item) {
                        for (AntCloudTagItem antCloudTagItem : item.getItems()) {
                            Log.d(TAG, "List Item Selected : " + antCloudTagItem.getText()
                                    + ", " + antCloudTagItem.isChecked());
                        }
                    }

                    @Override
                    public void onListChanged(ArrayList<AntCloudTagListItem> dataList) {
                        mListener.onListChanged(dataList);
                    }
                });


        LinearLayoutManager vertical
                = new LinearLayoutManager(context
                , LinearLayoutManager.VERTICAL
                , false);
        listView.setExpanded(isExpanded);
        listView.setLayoutManager(vertical);
        listView.setAdapter(adapter);
        return this;
    }

    /**
     * Calculate Size
     * BodyList 를 {@link AntCloudTagItem} List 로 변환
     *
     * @param bodyList String List
     * @return ArrayList<AntItem>
     */
    private ArrayList<AntCloudTagItem> calculateSize(ArrayList<String> bodyList) {
        bodySizeList = new ArrayList<>();
        for (String body : bodyList) {
            bodySizeList.add(body.length());
        }
        return getItemList(getMaxItemLength(), bodyList);
    }

    /**
     * Calculate Size Depends On Font Size
     * BodyList 를 {@link AntCloudTagItem} List 로 변환
     *
     * @param bodyList String List
     * @return ArrayList<AntItem>
     */
    private ArrayList<AntCloudTagItem> calculateSizeWithConfiguration(ArrayList<String> bodyList) {
        bodySizeList = new ArrayList<>();

        int fontScale = getFontScale();
        int MAX_LENGTH = getMaxLength(fontScale);
        Log.d(TAG, "Font Scale : " + fontScale);

        for (String body : bodyList) {
            bodySizeList.add(body.length());
        }

        return getItemList(MAX_LENGTH, bodyList);
    }

    /**
     * getDataList
     * BodyList 를 {@link AntCloudTagListItem} List 로 변환
     *
     * @return ArrayList<AntListItem>
     */
    private ArrayList<AntCloudTagListItem> getDataList() {
        int columnIndex = 0;
        ArrayList<AntCloudTagListItem> dataList = new ArrayList<>();
        dataList.add(new AntCloudTagListItem(new ArrayList<>(), columnIndex));
        for (AntCloudTagItem item : itemList) {
            if (item.getColumnIndex() != columnIndex)
                dataList.add(new AntCloudTagListItem(new ArrayList<>(), item.getColumnIndex()));

            columnIndex = item.getColumnIndex();
        }
        /*for (AntItem item : itemList) {
            dataList.add(item.getColumnIndex(),
                    new AntListItem(new ArrayList<>()
                            , item.getColumnIndex()));
        }*/
        for (AntCloudTagItem item : itemList) {
            AntCloudTagListItem listItem = dataList.get(item.getColumnIndex());
            listItem.addItem(item);
        }
        return dataList;
    }


    /**
     * getDataList
     * BodyList 를 {@link AntCloudTagItem} List 로 변환
     *
     * @param maxLength 하나의 row 당 텍스트 총 길이 합 제한
     * @param bodyList  String List
     * @return ArrayList<AntItem>
     */
    private ArrayList<AntCloudTagItem> getItemList(int maxLength, ArrayList<String> bodyList) {
        ArrayList<AntCloudTagItem> itemList = new ArrayList<>();
        int totalColumnCount = 0;
        int totalTextSize = 0;
        int column = 0;

        for (int index = 0; index < bodySizeList.size(); index++) {
            int currentSize = bodySizeList.get(index);
            totalTextSize = totalTextSize + currentSize;

            if (totalTextSize > maxLength || totalColumnCount > MAX_COLUMN) {
                column = column + 1;
                totalColumnCount = 0;
                totalTextSize = currentSize;
            }

            totalColumnCount = totalColumnCount + 1;
            itemList.add(new AntCloudTagItem(bodyList.get(index)
                    , column
                    , false));
        }
        return itemList;
    }

    /**
     * 시스템 폰트 크기 반환
     * <p>
     * {@link #FONT_SCALE_SMALL}
     * {@link #FONT_SCALE_NORMAL}
     * {@link #FONT_SCALE_BIG}
     */
    private int getFontScale() {
        Configuration configuration = context.getResources()
                .getConfiguration();
        if (configuration.fontScale > 1.15) {
            return FONT_SCALE_BIG;
        } else if (configuration.fontScale > 1.0) {
            return FONT_SCALE_NORMAL;
        } else {
            return FONT_SCALE_SMALL;
        }
    }

    /**
     * 시스템 크기에 따른 총 텍스트 길이 제한 반환
     */
    private int getMaxLength(int fontScale) {
        int MAX_LENGTH = getMaxItemLength();;
        if (fontScale == FONT_SCALE_SMALL) {
            return MAX_LENGTH + 2;
        }
        if (fontScale == FONT_SCALE_BIG) {
            return MAX_LENGTH - 5;
        }
        return MAX_LENGTH;
    }
}
