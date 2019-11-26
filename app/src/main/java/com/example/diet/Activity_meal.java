package com.example.diet;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.exit;

/**
 * Created by 黎活宝 on 2019/11/26.
 */

public class Activity_meal extends AppCompatActivity implements MealListAdapter.InnerItemOnclickListener,AdapterView.OnItemClickListener,View.OnClickListener {

    private TextView meal_title;
    private ImageButton exit_btn;
    private ListView meal_List;
    private List<String> DataList;
    private MealListAdapter mealListAdapter;
    private static final String[] item_names={"番茄","米饭","鸡蛋","酸奶","苹果"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS| WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION| View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            //window.setNavigationBarColor(Color.TRANSPARENT);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//修改状态栏中的图标文字为深色
        }


        setContentView(R.layout.edit_meal);

        Intent intent=getIntent();
        String meal_name=intent.getStringExtra("meal_name");
        meal_title=(TextView)findViewById(R.id.meal_name);
        exit_btn=(ImageButton)findViewById(R.id.exit_btn);
        exit_btn.setOnClickListener(this);
        meal_title.setText("添加"+meal_name);

        initView();
        DataList=new ArrayList<>();
        for(int i=0;i<item_names.length;i++){
            DataList.add(item_names[i]);
        }
        mealListAdapter=new MealListAdapter(DataList,this);
        mealListAdapter.setOnInnerItemOnClickListener(this);
        meal_List.setAdapter(mealListAdapter);
        meal_List.setOnItemClickListener(this);

    }
    private void initView() {
        meal_List= (ListView) findViewById(R.id.meal_item);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.exit_btn:
                finish();
                break;
        }
    }

    @Override
    public void itemClick(View v) {
        int position;
        position = (Integer) v.getTag();
        switch (v.getId()) {
            case R.id.add_meal_item_btn:
                Log.e("内部item--1-->", position + "");
                break;
            default:
                break;
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        Log.e("整体item----->", position + "");
    }
}
