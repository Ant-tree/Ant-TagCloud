package com.anttree.ant_cloud_tag;

import java.util.ArrayList;

@SuppressWarnings("WeakerAccess, unused")
public class AntCloudTagListItem {
    private int columnIndex;
    private ArrayList<AntCloudTagItem> items;

    public AntCloudTagListItem(ArrayList<AntCloudTagItem> items
            , int columnIndex) {

        this.items = items;
        this.columnIndex = columnIndex;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    public ArrayList<AntCloudTagItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<AntCloudTagItem> items) {
        this.items = items;
    }

    public void addItem(AntCloudTagItem item) {
        ArrayList<AntCloudTagItem> antCloudTagItems = getItems();
        antCloudTagItems.add(item);
    }

    public String toString() {
        StringBuilder itemToString = new StringBuilder();
        for (AntCloudTagItem item : items) {
            itemToString.append(" ").append(item);
        }
        return "items : " + itemToString.toString() + ", column Index : " + columnIndex;
    }
}