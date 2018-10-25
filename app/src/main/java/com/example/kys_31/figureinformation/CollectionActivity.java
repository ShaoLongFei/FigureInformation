package com.example.kys_31.figureinformation;

import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;

import adapter.CollectionAdapter;
import database.DBUtil;
import variable.UserMessageVariable;

import static adapter.CollectionAdapter.isShow;
import static adapter.CollectionAdapter.listBoolean;

/**
 * Created by 张同心 on 2017/9/26.
 * @function 收藏栏
 */

public class CollectionActivity extends BaseActivity {

    private LinearLayout mLlBack;
    private TextView mTvTitleName;
    private TextView mTvDeleteSelected;
    private CheckBox mRbSelectAll;
    private ListView mLvCollectionMessage;
    private List<HashMap<String,String>> list=new ArrayList<>();
    private boolean isDelete=true;
    private CollectionAdapter collectionAdapter;

    @Override
    protected int getLayoutID() {
        return R.layout.collectionorsubscription_activity;
    }

    @Override
    protected void initControl() {
        mLlBack = (LinearLayout)findViewById(R.id.titleBack_persionMessage_ll);
        mTvTitleName = (TextView)findViewById(R.id.titleName_tv);
        mTvDeleteSelected = (TextView)findViewById(R.id.selectDelete_collection_tv);
        mRbSelectAll=(CheckBox)findViewById(R.id.selectAll_collection_rb);
        mLvCollectionMessage = (ListView)findViewById(R.id.collectionMessage_collection_lv);
        mTvDeleteSelected.setVisibility(View.VISIBLE);
        mTvTitleName.setText("收藏栏");
        initListView();
    }


    private void initListView() {
        mLvCollectionMessage.setAdapter(null);
        list= DBUtil.getInstance(CollectionActivity.this).selectCollection("phoneNumber",UserMessageVariable.osUserMessage.oStrPhoneNumber);
        Log.e("收藏的条数",list.size()+"");
        collectionAdapter= CollectionAdapter.getInitCollectionAdapter(CollectionActivity.this,list);
        mLvCollectionMessage.setAdapter(collectionAdapter);
    }

    @Override
    void setControlListener() {
        mLlBack.setOnClickListener(this);
        mTvDeleteSelected.setOnClickListener(this);
        mRbSelectAll.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    for (int i=0;i<list.size();i++){
                        listBoolean.set(i,true);
                        collectionAdapter.notifyDataSetChanged();
                    }
                }else {
                    for (int i=0;i<list.size();i++){
                        listBoolean.set(i,false);
                        collectionAdapter.notifyDataSetChanged();
                    }
                    Log.e("全不选","");
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.titleBack_persionMessage_ll:
                finish();
                break;
            case R.id.selectDelete_collection_tv:
                Log.e("列表情况",listBoolean+"");
                if (isDelete){
                    Log.e("点击的是删除","");
                    mRbSelectAll.setVisibility(View.VISIBLE);
                    mTvDeleteSelected.setText("提交");
                    isShow=true;
                    isDelete=false;
                    collectionAdapter.notifyDataSetChanged();
                }else {
                    Log.e("点击的是提交","");

                    for (int i=0;i<listBoolean.size();i++){
                        Log.e(""+i,""+listBoolean);
                        if (listBoolean.get(i)){
                            DBUtil.getInstance(CollectionActivity.this).deleteCollection(list.get(i).get("phoneNumber"),list.get(i).get("title"));
                            listBoolean.remove(i);
                            list.remove(i);
                            i--;
                            Log.e(""+i,""+listBoolean);
                        }
                    }
                    initListView();
                    mTvDeleteSelected.setText("删除");
                    isShow=false;
                    isDelete=true;
                    mRbSelectAll.setVisibility(View.GONE);
                   // collectionAdapter.notifyDataSetChanged();
                }

                break;
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        mLoginState = (Boolean) o;
    }

}
