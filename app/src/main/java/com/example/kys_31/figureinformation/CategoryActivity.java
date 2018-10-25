package com.example.kys_31.figureinformation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Created by 幽蓝丶流月 on 2017/9/17.
 */

public class CategoryActivity extends BaseActivity implements View.OnClickListener {

    private Button important_report;
    private Button intelligence_products;
    private Button compile_report;
    private Button hot_topics;
    private Button renewable_energy;
    private Button nanotechnology;
    private Button food_and_nutrition;
    private Button associated_gas;
    private Button pollution_of_waters;
    private Button air_pollution;
    private Button integrated_circuit;
    private Button numerical_control_machine;
    private Button transgene;
    private Button agricultural_pollution;
    private Button broadband;
    private Button new_drug;
    private Button infectious_diseases;
    private Button search_isbn;
    private Button recommend;

    private Intent intent;
    private List<String> list;

    @Override
    protected int getLayoutID() {
        return R.layout.category_test;
    }

    @Override
    protected void initControl() {
        important_report=(Button)findViewById(R.id.important_report);
        intelligence_products=(Button)findViewById(R.id.intelligence_products);
        compile_report=(Button)findViewById(R.id.compile_report);
        hot_topics=(Button)findViewById(R.id.hot_topics);
        renewable_energy=(Button)findViewById(R.id.renewable_energy);
        nanotechnology=(Button)findViewById(R.id.nanotechnology);
        food_and_nutrition=(Button)findViewById(R.id.food_and_nutrition);
        associated_gas=(Button)findViewById(R.id.associated_gas);
        pollution_of_waters=(Button)findViewById(R.id.pollution_of_waters);
        air_pollution=(Button)findViewById(R.id.air_pollution);
        integrated_circuit=(Button)findViewById(R.id.integrated_circuit);
        numerical_control_machine=(Button)findViewById(R.id.numerical_control_machine);
        transgene=(Button)findViewById(R.id.transgene);
        agricultural_pollution=(Button)findViewById(R.id.agricultural_pollution);
        broadband=(Button)findViewById(R.id.broadband);
        new_drug=(Button)findViewById(R.id.new_drug);
        infectious_diseases=(Button)findViewById(R.id.infectious_diseases);
        search_isbn=(Button)findViewById(R.id.search_isbn);
        recommend=(Button)findViewById(R.id.recommend);
    }

    @Override
    void setControlListener() {
        important_report.setOnClickListener(this);
        intelligence_products.setOnClickListener(this);
        compile_report.setOnClickListener(this);
        hot_topics.setOnClickListener(this);
        renewable_energy.setOnClickListener(this);
        nanotechnology.setOnClickListener(this);
        food_and_nutrition.setOnClickListener(this);
        associated_gas.setOnClickListener(this);
        pollution_of_waters.setOnClickListener(this);
        air_pollution.setOnClickListener(this);
        integrated_circuit.setOnClickListener(this);
        numerical_control_machine.setOnClickListener(this);
        transgene.setOnClickListener(this);
        agricultural_pollution.setOnClickListener(this);
        broadband.setOnClickListener(this);
        new_drug.setOnClickListener(this);
        infectious_diseases.setOnClickListener(this);
        search_isbn.setOnClickListener(this);
        recommend.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.important_report://重要报告
                intent=new Intent(CategoryActivity.this,InformatiinListActivity.class);
                intent.putExtra("url","http://portal.nstl.gov.cn/STMonitor/home/bg.htm?serverId=82");
                startActivity(intent);
                break;
            case R.id.intelligence_products://情报产品
                intent=new Intent(CategoryActivity.this,IntelligenceProductsListActivity.class);
                intent.putExtra("url","http://portal.nstl.gov.cn/STMonitor/home/allqbcplist.htm?parentPageId=1505703305248&serverId=82");
                startActivity(intent);
                break;
            case R.id.compile_report://编译报道
                intent=new Intent(CategoryActivity.this,InformatiinListActivity.class);
                intent.putExtra("url","http://portal.nstl.gov.cn/STMonitor/home/resource_more_recommendspecial.htm?parentPageId=1505653299236&serverId=82");
                startActivity(intent);
                break;
            case R.id.hot_topics://热点专题
                intent=new Intent(CategoryActivity.this,HotTopicsActivity.class);
                intent.putExtra("url","http://portal.nstl.gov.cn/STMonitor/home/resource_more_topic_recommend.htm?parentPageId=1505693164248&serverId=82");
                startActivity(intent);
                break;
            case R.id.renewable_energy:
                intent=new Intent(CategoryActivity.this,InformatiinListActivity.class);
                intent.putExtra("url","http://portal.nstl.gov.cn/STMonitor/home/resource_more_recommendadd.htm;jsessionid=C6E1686738E860D7CF9A735C35FEB6CA?parentPageId=1505810791964&directionId=4&controlType=openhome");
                startActivity(intent);
                break;
            case R.id.nanotechnology:
                intent=new Intent(CategoryActivity.this,InformatiinListActivity.class);
                intent.putExtra("url","http://portal.nstl.gov.cn/STMonitor/home/resource_more_recommendadd.htm?parentPageId=1505811585475&directionId=1&controlType=openhome");
                startActivity(intent);
                break;
            case R.id.food_and_nutrition:
                intent=new Intent(CategoryActivity.this,InformatiinListActivity.class);
                intent.putExtra("url","http://portal.nstl.gov.cn/STMonitor/home/resource_more_recommendadd.htm?parentPageId=1505811793424&directionId=5&controlType=openhome");
                startActivity(intent);
                break;
            case R.id.associated_gas:
                intent=new Intent(CategoryActivity.this,InformatiinListActivity.class);
                intent.putExtra("url","http://portal.nstl.gov.cn/STMonitor/home/resource_more_recommendadd.htm?parentPageId=1505813148957&directionId=19&controlType=openhome");
                startActivity(intent);
                break;
            case R.id.pollution_of_waters:
                intent=new Intent(CategoryActivity.this,InformatiinListActivity.class);
                intent.putExtra("url","http://portal.nstl.gov.cn/STMonitor/home/resource_more_recommendadd.htm?parentPageId=1505813167069&directionId=8&controlType=openhome");
                startActivity(intent);
                break;
            case R.id.air_pollution:
                intent=new Intent(CategoryActivity.this,InformatiinListActivity.class);
                intent.putExtra("url","http://portal.nstl.gov.cn/STMonitor/home/resource_more_recommendadd.htm?parentPageId=1505813183979&directionId=20&controlType=openhome");
                startActivity(intent);
                break;
            case R.id.integrated_circuit:
                intent=new Intent(CategoryActivity.this,InformatiinListActivity.class);
                intent.putExtra("url","http://portal.nstl.gov.cn/STMonitor/home/resource_more_recommendadd.htm?parentPageId=1505813203183&directionId=7&controlType=openhome");
                startActivity(intent);
                break;
            case R.id.numerical_control_machine:
                intent=new Intent(CategoryActivity.this,InformatiinListActivity.class);
                intent.putExtra("url","http://portal.nstl.gov.cn/STMonitor/home/resource_more_recommendadd.htm?parentPageId=1505813218549&directionId=9&controlType=openhome");
                startActivity(intent);
                break;
            case R.id.transgene:
                intent=new Intent(CategoryActivity.this,InformatiinListActivity.class);
                intent.putExtra("url","http://portal.nstl.gov.cn/STMonitor/home/resource_more_recommendadd.htm?parentPageId=1505813284131&directionId=12&controlType=openhome");
                startActivity(intent);
                break;
            case R.id.agricultural_pollution:
                intent=new Intent(CategoryActivity.this,InformatiinListActivity.class);
                intent.putExtra("url","http://portal.nstl.gov.cn/STMonitor/home/resource_more_recommendadd.htm?parentPageId=1505813301166&directionId=6&controlType=openhome");
                startActivity(intent);
                break;
            case R.id.broadband:
                intent=new Intent(CategoryActivity.this,InformatiinListActivity.class);
                intent.putExtra("url","http://portal.nstl.gov.cn/STMonitor/home/resource_more_recommendadd.htm?parentPageId=1505813316922&directionId=2&controlType=openhome");
                startActivity(intent);
                break;
            case R.id.new_drug:
                intent=new Intent(CategoryActivity.this,InformatiinListActivity.class);
                intent.putExtra("url","http://portal.nstl.gov.cn/STMonitor/home/resource_more_recommendadd.htm?parentPageId=1505813335018&directionId=10&controlType=openhome");
                startActivity(intent);
                break;
            case R.id.infectious_diseases:
                intent=new Intent(CategoryActivity.this,InformatiinListActivity.class);
                intent.putExtra("url","http://portal.nstl.gov.cn/STMonitor/home/resource_more_recommendadd.htm?parentPageId=1505813348341&directionId=3&controlType=openhome");
                startActivity(intent);
                break;
            case R.id.search_isbn:
                intent=new Intent(CategoryActivity.this,GetISBNActivity.class);
                startActivity(intent);
                break;

        }
    }

    @Override
    public void update(Observable observable, Object o) {
        mLoginState = (Boolean) o;
    }
}
