package com.example.kys_31.figureinformation;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.Observable;

import variable.FancySelectVariable;
import view.FunSwitch;

/**
 * Created by 幽蓝丶流月 on 2017/9/26.
 */

public class FancySelectActivity extends BaseActivity{

private FunSwitch funSwitch1,funSwitch2,funSwitch3,funSwitch4,funSwitch5,
        funSwitch6,funSwitch7,funSwitch8,funSwitch9,funSwitch10,funSwitch11,
        funSwitch12,funSwitch13,funSwitch14,funSwitch15;
    private Button bt_save;
    private LinearLayout ll_back;
    private StringBuffer fancy=new StringBuffer();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fancy_select;
    }

    @Override
    protected void initControl() {
        funSwitch1=(FunSwitch)findViewById(R.id.switchButton1);
        funSwitch2=(FunSwitch)findViewById(R.id.switchButton2);
        funSwitch3=(FunSwitch)findViewById(R.id.switchButton3);
        funSwitch4=(FunSwitch)findViewById(R.id.switchButton4);
        funSwitch5=(FunSwitch)findViewById(R.id.switchButton5);
        funSwitch6=(FunSwitch)findViewById(R.id.switchButton6);
        funSwitch7=(FunSwitch)findViewById(R.id.switchButton7);
        funSwitch8=(FunSwitch)findViewById(R.id.switchButton8);
        funSwitch9=(FunSwitch)findViewById(R.id.switchButton9);
        funSwitch10=(FunSwitch)findViewById(R.id.switchButton10);
        funSwitch11=(FunSwitch)findViewById(R.id.switchButton11);
        funSwitch12=(FunSwitch)findViewById(R.id.switchButton12);
        funSwitch13=(FunSwitch)findViewById(R.id.switchButton13);
        funSwitch14=(FunSwitch)findViewById(R.id.switchButton14);
        funSwitch15=(FunSwitch)findViewById(R.id.switchButton15);
        ll_back=(LinearLayout)findViewById(R.id.ll_back);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        bt_save=(Button)findViewById(R.id.bt_save);
        changeView();
    }

    @Override
    void setControlListener() {
        bt_save.setOnClickListener(this);
    }

    private void changeView() {
        if (FancySelectVariable.osFancySelected.contains("可再生能源")){
            funSwitch1.setState(true);
        }
        if (FancySelectVariable.osFancySelected.contains("纳米科技")){
            funSwitch2.setState(true);
        }
        if (FancySelectVariable.osFancySelected.contains("食物营养")){
            funSwitch3.setState(true);
        }
        if (FancySelectVariable.osFancySelected.contains("油气类")){
            funSwitch4.setState(true);
        }
        if (FancySelectVariable.osFancySelected.contains("水体污染治理")){
            funSwitch5.setState(true);
        }
        if (FancySelectVariable.osFancySelected.contains("大气污染防治")){
            funSwitch6.setState(true);
        }
        if (FancySelectVariable.osFancySelected.contains("集成电路装备")){
            funSwitch7.setState(true);
        }
        if (FancySelectVariable.osFancySelected.contains("数控机床")){
            funSwitch8.setState(true);
        }
        if (FancySelectVariable.osFancySelected.contains("转基因生物")){
            funSwitch9.setState(true);
        }
        if (FancySelectVariable.osFancySelected.contains("农业污染防治")){
            funSwitch10.setState(true);
        }
        if (FancySelectVariable.osFancySelected.contains("宽带移动通信")){
            funSwitch11.setState(true);
        }
        if (FancySelectVariable.osFancySelected.contains("新药创制")){
            funSwitch12.setState(true);
        }
        if (FancySelectVariable.osFancySelected.contains("重大传染防治")){
            funSwitch13.setState(true);
        }
        if (FancySelectVariable.osFancySelected.contains("重要报告")){
            funSwitch14.setState(true);
        }
        if (FancySelectVariable.osFancySelected.contains("编译报道")){
            funSwitch15.setState(true);
        }
    }


    @Override
    public void onClick(View view) {
        if (funSwitch1.getState()) {
            fancy.append("可再生能源");
        }
        if (funSwitch2.getState()) {
            fancy.append("纳米科技");
        }
        if (funSwitch3.getState()) {
            fancy.append("食物营养");
        }
        if (funSwitch4.getState()) {
            fancy.append("油气类");
        }
        if (funSwitch5.getState()) {
            fancy.append("水体污染治理");
        }
        if (funSwitch6.getState()) {
            fancy.append("大气污染防治");
        }
        if (funSwitch7.getState()) {
            fancy.append("集成电路装备");
        }
        if (funSwitch8.getState()) {
            fancy.append("数控机床");
        }
        if (funSwitch9.getState()) {
            fancy.append("转基因生物");
        }
        if (funSwitch10.getState()) {
            fancy.append("农业污染防治");
        }
        if (funSwitch11.getState()) {
            fancy.append("宽带移动通信");
        }
        if (funSwitch12.getState()) {
            fancy.append("新药创制");
        }
        if (funSwitch13.getState()) {
            fancy.append("重大传染防治");
        }
        if (funSwitch14.getState()) {
            fancy.append("重要报告");
        }
        if (funSwitch15.getState()) {
            fancy.append("编译报道");
        }
        if (fancy.length() == 0){
            FancySelectVariable.osFancySelected = "重要报告";
            funSwitch14.setState(true);
        }else {
            FancySelectVariable.osFancySelected =fancy.toString();
        }
        SharedPreferences sp = getBaseContext().getSharedPreferences("like",Context.MODE_APPEND);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("like", FancySelectVariable.osFancySelected).commit();
        showToast("定制成功",false);
        finish();
    }

    @Override
    public void update(Observable observable, Object o) {
        mLoginState = (Boolean)o;
    }
}
