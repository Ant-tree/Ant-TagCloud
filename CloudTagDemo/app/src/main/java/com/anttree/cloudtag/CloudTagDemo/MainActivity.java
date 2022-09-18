package com.anttree.cloudtag.CloudTagDemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.anttree.ant_cloud_tag.AntCloudTagBuilder;
import com.anttree.ant_cloud_tag.AntCloudTagListItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<AntCloudTagListItem> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new AntCloudTagBuilder(MainActivity.this, false)
                .setDefaultMaxLength(15)
                .setMaxSeletableNumber(3)
                .setMaxColumn(10)
                .expanded(true)
                .sizingPolicy(AntCloudTagBuilder.SIZING_WITH_FONT_CONFIGURATION)
                .setAnimatedChange(true)
                .initialize(getList())
                .with(findViewById(R.id.antListView))
                .setOnItemCheckChangeListener(this::onDataChanged);
    }

    private void onDataChanged(ArrayList<AntCloudTagListItem> dataList) {
        this.dataList = dataList;
    }

    private ArrayList<String> getList() {
        ArrayList<String> bodyList = new ArrayList<>();
        bodyList.add("WELCOME!");
        bodyList.add("To");
        bodyList.add("Cloud");
        bodyList.add("Tag");
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
        bodyList.add("Not");
        bodyList.add("Licensed");
        bodyList.add("So");
        bodyList.add("Do");
        bodyList.add("WTF");
        bodyList.add("You");
        bodyList.add("Want");

        return bodyList;
    }
}