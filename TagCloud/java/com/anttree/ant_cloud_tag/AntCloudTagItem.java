package com.anttree.ant_cloud_tag;

@SuppressWarnings("WeakerAccess, unused")
public class AntCloudTagItem {
    private boolean isChecked;
    private int columnIndex;
    private String text;

    public AntCloudTagItem(String text
            , int columnIndex
            , boolean isChecked) {

        this.text = text;
        this.columnIndex = columnIndex;
        this.isChecked = isChecked;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    public String toString() {
        return "Text : " + text + ", column Index : " + columnIndex + ", isChecked : " + isChecked;
    }
}