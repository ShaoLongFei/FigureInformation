package com.example.kys_31.figureinformation;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatDelegate;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.io.File;
import java.util.Observable;

import custom.DrawHookViewCustom;
import data.HandleUserMessage;
import data.HandleWebDataMessage;
import util.AutoLoginUtil;
import util.FileUtil;
import util.ViewUtil;
import variable.SystemSetVariable;

/**
 * Created by 张同心 on 2017/9/13.
 * @function 系统设置
 */

public class SystemSetActivity extends BaseActivity {

    private LinearLayout mLlBack;
    private LinearLayout mLlHandMoveMent;
    private TextView mTvDataSize;
    private Switch mSNightModel;
    private TextView mTvSwitch;
    private LinearLayout mLlCheckUpdate;
    private LinearLayout mLlDoGrade;
    private LinearLayout mLlAbout;
    private Button mBtExitLogin;

    @Override
    protected int getLayoutID() {
        return R.layout.systemset_activity;
    }

    @Override
    protected void initControl() {
        mLlBack = (LinearLayout)findViewById(R.id.titleBack_persionMessage_ll);
        mLlHandMoveMent =(LinearLayout)findViewById(R.id.handMoveMentClear_systemSet_ll);
        mTvDataSize = (TextView)findViewById(R.id.dataSize_systemSet_tv);
        mSNightModel = (Switch)findViewById(R.id.nightModel_systemSet_s);
        mTvSwitch = (TextView)findViewById(R.id.nightModel_systemSet_tv);
        mLlCheckUpdate = (LinearLayout) findViewById(R.id.checkUpdata_systemSet_ll);
        mLlDoGrade = (LinearLayout)findViewById(R.id.DoGrade_systemSet_ll);
        mLlAbout = (LinearLayout)findViewById(R.id.about_systemSet_ll);
        mBtExitLogin = (Button) findViewById(R.id.exitLogin_systemSet_bt);
    }

    @Override
    void setControlListener() {
        mLlBack.setOnClickListener(this);
        mLlHandMoveMent.setOnClickListener(this);
        mLlCheckUpdate.setOnClickListener(this);
        mLlDoGrade.setOnClickListener(this);
        mLlAbout.setOnClickListener(this);
        mBtExitLogin.setOnClickListener(this);
        mSNightModel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPreferences nightModelSp = getSharedPreferences("nightModel", 0);
                SharedPreferences.Editor nightEditor = nightModelSp.edit();
                if (b){
                    SystemSetVariable.osNightModel = true;
                    ViewUtil.setScreenBrightness(SystemSetActivity.this, 10);
                    mTvSwitch.setText("开");
                    nightEditor.putBoolean("nightModel", true);
                }else {
                    SystemSetVariable.osNightModel = false;
                    ViewUtil.setScreenBrightness(SystemSetActivity.this,SystemSetVariable.osScreenBrightValue);
                    mTvSwitch.setText("关");
                    nightEditor.putBoolean("nightModel", false);
                }
                nightEditor.commit();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.titleBack_persionMessage_ll:
                finish();
                break;
            case R.id.handMoveMentClear_systemSet_ll:
                handMoveMentClear();
                break;
            case R.id.checkUpdata_systemSet_ll:
                showToast("已是最新版本",true);
                break;
            case R.id.DoGrade_systemSet_ll:
                doGradeAboutApp();
                break;
            case R.id.about_systemSet_ll:
                startActivity(new Intent(this,AboutActivity.class));
                break;
            case R.id.exitLogin_systemSet_bt:
                AutoLoginUtil.cancleAutoLogin(this);
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;

        }
    }

    /**
     * 为App打分
     */
    private void doGradeAboutApp() {
        final Dialog dialogDoGrade = new AlertDialog.Builder(this).create();
        View viewDoGrade = LayoutInflater.from(this).inflate(R.layout.dograde_systemset_view,null);
        dialogDoGrade.show();
        dialogDoGrade.getWindow().setContentView(viewDoGrade);
        dialogDoGrade.setCanceledOnTouchOutside(false);
        ViewUtil.setDialogWindowAttr(dialogDoGrade,1000,800);
        ImageView ivClose = (ImageView)viewDoGrade.findViewById(R.id.close_systemSet_iv);
        ImageView ivSure = (ImageView)viewDoGrade.findViewById(R.id.sure_systemSet_iv);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogDoGrade.dismiss();
            }
        });
        ivSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast("感谢您的评价，愿你每天快乐！", true);
                dialogDoGrade.dismiss();
            }
        });
    }

    /**
     * 手动清理缓存
     */
    private void handMoveMentClear() {
        final Dialog dialogHandMoveMentClear = new AlertDialog.Builder(this).create();
        View viewHandMoveMentClear = LayoutInflater.from(this).inflate(R.layout.handmovement_systemset_view,null);
        dialogHandMoveMentClear.show();
        dialogHandMoveMentClear.getWindow().setContentView(viewHandMoveMentClear);
        dialogHandMoveMentClear.setCanceledOnTouchOutside(false);
        ViewUtil.setDialogWindowAttr(dialogHandMoveMentClear,700,550);
        TextView tvClearSuccess = (TextView)viewHandMoveMentClear.findViewById(R.id.clearSuccess_handMoveMent_tv);
        Button btSure = (Button)viewHandMoveMentClear.findViewById(R.id.sure_systemSet_bt);
        DrawHookViewCustom dhvcDrawHook = (DrawHookViewCustom)viewHandMoveMentClear.findViewById(R.id.drawHook_handMoveMent_dhvc);
        dhvcDrawHook.setTextView(tvClearSuccess);
        mTvDataSize.setText("0");
        btSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File cacheFile = new File(HandleWebDataMessage.osStrDir);
                FileUtil.deleteDirFile(cacheFile);
                dialogHandMoveMentClear.dismiss();
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        /*夜间模式*/
        if (SystemSetVariable.osNightModel){
            mSNightModel.setChecked(true);
        }else {
            mSNightModel.setChecked(false);
        }
        /*计算缓存大小*/
        mTvDataSize.setText(FileUtil.formatFileSize(FileUtil.getFileSizes(new File(HandleWebDataMessage.osStrDir))));
    }

    @Override
    public void update(Observable observable, Object o) {
        mLoginState = (Boolean)o;
    }
}
