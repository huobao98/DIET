package com.example.diet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class EX_nutritionFragment extends Fragment implements AdapterView.OnItemClickListener{


    private ListView listView;
    private FrameLayout frameLayout;
    private List<User> mList=new ArrayList<>();
    private List<Fragment> fragmentList=new ArrayList<>();
    private FragmentManager fragmentManager;
    private MyListAdapter myListAdapter;


    private SearchView searchView;
    private Fragment_recommend fragment_recommend;
    private Fragment_fruits fragment_fruits;
    private Fragment_grain fragment_grain;
    private Fragment_meat fragment_meat;
    private Fragment_milk fragment_milk;
    private Fragment_snack fragment_snack;
    private Fragment_wine fragment_wine;
    private Fragment_seafood fragment_seafood;
    private Fragment fragments2[];
    private int lastfragment;//用于记录上个选择的Fragment

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.explore_nutrition, null);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //搜索框
        searchView=(SearchView)getActivity().findViewById(R.id.searchView);
        searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(true);

        fragmentManager=this.getChildFragmentManager();

        initView();
        initData();
    }

    private void initView() {
        listView=(ListView)getActivity().findViewById(R.id.listview);
        frameLayout=(FrameLayout)getActivity().findViewById(R.id.fragment_container);
    }

    private void initData(){
        //左边listView集合添加数据，适配器适配
        listViewData();
        //添加fragment，复用fragment
        multiplexFragment();
        //默认选中ListView第一条item
        replese(0);
        //ListView第一条item的Select为true
        mList.get(0).setSelect(true);
        //listView点击事件
        listView.setOnItemClickListener(this);
    }

    /*
    * listViewData() 左边listView集合添加数据*/
    private void listViewData(){
        String name[]={"常用推荐","蔬菜水果","肉禽蛋类","海鲜水产","乳品烘焙","休闲零食","粮油坚果","酒水饮料"};
        if(mList.size()==0) {
            for (int i = 0; i < 8; i++)
                mList.add(new User(name[i]));
        }
        //适配器适配
        myListAdapter=new MyListAdapter(mList,getActivity());
        listView.setAdapter(myListAdapter);
    }

    /*
    * addFragment() 添加fragment 复用fragment*/
    private void multiplexFragment(){
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        MultiplexingFragment multiplexingfragment=new MultiplexingFragment();
        for(int i=0;i<8;i++){
            Fragment multiplex=multiplexingfragment.getMultiplexing(mList.get(i).getName(),"");
            if (!multiplex.isAdded()) {
                fragmentList.add(multiplex);
            }
        }
        for(int i=0;i<fragmentList.size();i++){
            if(!fragmentList.get(i).isAdded()){
                transaction.add(R.id.fragment_container,fragmentList.get(i));
            }
        }
        transaction.commit();
    }

    /*
    * replese(int position) 根据点击事件的下标切换fragment页面*/
    private void replese(int position){
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        for(int i=0;i<fragmentList.size();i++){
            Fragment fragment=fragmentList.get(i);
            transaction.hide(fragment);
        }
        transaction.show(fragmentList.get(position)).commit();
    }

    /*
    * onItemClick(AdapterView<?> parent,View view,int position,longid)
    * 实现ListView的item点击事件*/

    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        //切换fragment
        replese(position);
        //在bean类里写的一个标记 boolean类型的isSelect是关键，默认无状态并设置
        // get set方法
        //集合里所有数据的Select设置为flase,position下标所对应的item的Select为true，
        // 刷新适配器。
        for(int i=0;i<mList.size();i++){
            mList.get(i).setSelect(false);
        }
        mList.get(position).setSelect(true);
        myListAdapter.notifyDataSetChanged();
    }
}
