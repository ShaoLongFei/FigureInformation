package com.example.kys_31.figureinformation;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Observable;

/**
 * Created by 张同心 on 2017/9/26.
 * @function 订阅栏
 */

public class SubscriptionActivity extends BaseActivity {

    private LinearLayout mLlBack;
    private TextView mTvTitleName;
    private TextView mTvDeleteSelected;
    private ListView mLvCollectionMessage;

    @Override
    protected int getLayoutID() {
        return R.layout.collectionorsubscription_activity;
    }

    @Override
    protected void initControl() {
        mLlBack = (LinearLayout)findViewById(R.id.titleBack_persionMessage_ll);
        mTvTitleName = (TextView)findViewById(R.id.titleName_tv);
        mTvDeleteSelected = (TextView)findViewById(R.id.selectDelete_collection_tv);
        mLvCollectionMessage = (ListView)findViewById(R.id.collectionMessage_collection_lv);
        mTvTitleName.setText("订阅栏");
    }

    @Override
    void setControlListener() {
        mLlBack.setOnClickListener(this);
        mTvDeleteSelected.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.titleBack_persionMessage_ll:
                finish();
                break;
            case R.id.selectDelete_collection_tv:

                break;
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        mLoginState = (Boolean)o;
    }
}
