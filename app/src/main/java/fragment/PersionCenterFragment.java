package fragment;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kys_31.figureinformation.CollectionActivity;
import com.example.kys_31.figureinformation.FancySelectActivity;
import com.example.kys_31.figureinformation.GetISBNActivity;
import com.example.kys_31.figureinformation.LoginActivity;
import com.example.kys_31.figureinformation.PersionMessageActivity;
import com.example.kys_31.figureinformation.R;
import com.example.kys_31.figureinformation.SystemSetActivity;

import java.util.Observable;

import custom.RoundImageCustom;
import util.BitmapUtil;
import variable.LoginStateVariable;
import variable.PersionCenterVariable;
import variable.UserMessageVariable;

/**
 * Created by 张同心 on 2017/9/8.
 * @function 我的
 */

public class PersionCenterFragment extends BaseFragment {

    private TextView mTvName;
    private TextView mTvUpLookTime;
    private TextView mTvTableWord;
    private LinearLayout mLlPersionMessage;
    private LinearLayout mLlCustomize;
    private LinearLayout mLlCollection;
    private LinearLayout mLlSubCription;
    private LinearLayout mLlSystemSet;
    private RoundImageCustom mRivHead;
    private LinearLayout ll_information;

    @Override
    protected int getLayoutID() {
        return R.layout.persioncenter_fragment;
    }

    public PersionCenterFragment(){
    }

    @Override
    protected void initControl() {
        mTvName = (TextView)view.findViewById(R.id.name_persionCenter_tv);
        mTvUpLookTime = (TextView)view.findViewById(R.id.upLookTime_persionCenter_tv);
        mTvTableWord = (TextView)view.findViewById(R.id.tableWord_persionCenter_tv);
        mLlPersionMessage = (LinearLayout)view.findViewById(R.id.persionMessage_persionCenter_ll);
        mLlCustomize = (LinearLayout)view.findViewById(R.id.customize_persionCenter_ll);
        mLlCollection = (LinearLayout)view.findViewById(R.id.collection_persioncenter_ll);
        mLlSubCription = (LinearLayout)view.findViewById(R.id.subscription_persioncenter_ll);
        mLlSystemSet = (LinearLayout)view.findViewById(R.id.systemSet_persionCenter_ll);
        mRivHead = (RoundImageCustom) view.findViewById(R.id.circlehead_persionCenter_riv);
        ll_information=(LinearLayout)view.findViewById(R.id.ll_information);
        mTvUpLookTime.setText(PersionCenterVariable.osUpLookTime);
    }

    @Override
    protected void setListener() {
        mLlPersionMessage.setOnClickListener(this);
        mLlCustomize.setOnClickListener(this);
        mLlCollection.setOnClickListener(this);
        mLlSubCription.setOnClickListener(this);
        mLlSystemSet.setOnClickListener(this);
        ll_information.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.persionMessage_persionCenter_ll:
                loginCanShow(PersionMessageActivity.class);
                break;
            case R.id.customize_persionCenter_ll:
                loginCanShow(FancySelectActivity.class);
                break;
            case R.id.collection_persioncenter_ll:
                loginCanShow(CollectionActivity.class);
                break;
            case R.id.subscription_persioncenter_ll:
                loginCanShow(GetISBNActivity.class);
                break;
            case R.id.systemSet_persionCenter_ll:
                startActivity(new Intent(getActivity(), SystemSetActivity.class));
                break;
            case R.id.ll_information:
                login();
                break;
        }
    }

    /**
     * 登录才可显示
     * @param clas 字节码
     */
    private void loginCanShow(Class clas){
        if (LoginStateVariable.osLoginState){
            startActivity(new Intent(getActivity(), clas));
        }else {
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }
    }

    private void login(){
        if (!LoginStateVariable.osLoginState){
            startActivity(new Intent(getActivity(),  LoginActivity.class));
        }
    }

    @Override
    protected void showByLoginState() {
        if (LoginStateVariable.osLoginState){
            mTvName.setText(UserMessageVariable.osUserMessage.oStrName);//用户登录时的名字
            mRivHead.setImageBitmap(BitmapUtil.stringToBitmap(UserMessageVariable.osUserMessage.oStrHead));//用户登录时的头像
        }else {
            mTvName.setText("游  客");//用户未登录时的名字
            mRivHead.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.logo));//用户未登录时的头像
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        LoginStateVariable.osLoginState = (Boolean)o;
    }
}
