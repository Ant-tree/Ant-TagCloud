package com.anttree.cloudtag.CloudTagDemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.anttree.ant_tag_cloud.AntTagCloudBuilder;
import com.anttree.ant_tag_cloud.AntTagCloudListItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<AntTagCloudListItem> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new AntTagCloudBuilder(MainActivity.this, false)
                .setDefaultMaxLength(16)
                .setMaxSeletableNumber(3)
                .setMaxColumn(10)
                .expanded(true)
                .sizingPolicy(AntTagCloudBuilder.SIZING_WITH_FONT_CONFIGURATION)
                .setAnimatedChange(true)
                .initialize(getList())
                .with(findViewById(R.id.antListView))
                .setOnItemCheckChangeListener(this::onDataChanged);
    }

    private void onDataChanged(ArrayList<AntTagCloudListItem> dataList) {
        this.dataList = dataList;
    }

    private ArrayList<String> getList() {
        ArrayList<String> bodyList = new ArrayList<>();
        bodyList.add("WELCOME!");
        bodyList.add("To");
        bodyList.add("The");
        bodyList.add("Tag");
        bodyList.add("Cloud");
        bodyList.add("Demo");
        bodyList.add("You");
        bodyList.add("May");
        bodyList.add("Clone");
        bodyList.add("The");
        bodyList.add("Full");
        bodyList.add("Source");
        bodyList.add("To");
        bodyList.add("Modify");
        bodyList.add("The");
        bodyList.add("Attributes");
        bodyList.add("Defined");
        bodyList.add("These");
        bodyList.add("Codes");
        bodyList.add("Are");
        bodyList.add("Under");
        bodyList.add("WTFPL");
        bodyList.add("License");
        bodyList.add("So");
        bodyList.add("Do");
        bodyList.add("WTF");
        bodyList.add("You");
        bodyList.add("Want");

        return bodyList;
    }
}