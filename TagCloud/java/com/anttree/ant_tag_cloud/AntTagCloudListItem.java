package com.anttree.ant_tag_cloud;

import java.util.ArrayList;

@SuppressWarnings("WeakerAccess, unused")
public class AntTagCloudListItem {
    private int columnIndex;
    private ArrayList<AntTagCloudItem> items;

    public AntTagCloudListItem(ArrayList<AntTagCloudItem> items
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

    public ArrayList<AntTagCloudItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<AntTagCloudItem> items) {
        this.items = items;
    }

    public void addItem(AntTagCloudItem item) {
        ArrayList<AntTagCloudItem> antTagCloudItems = getItems();
        antTagCloudItems.add(item);
    }

    public String toString() {
        StringBuilder itemToString = new StringBuilder();
        for (AntTagCloudItem item : items) {
            itemToString.append(" ").append(item);
        }
        return "items : " + itemToString.toString() + ", column Index : " + columnIndex;
    }
}