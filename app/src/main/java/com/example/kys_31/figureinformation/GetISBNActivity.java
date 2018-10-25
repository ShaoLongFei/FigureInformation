package com.example.kys_31.figureinformation;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.zxing.client.android.CaptureActivity;

import java.util.Observable;

/**
 * Created by 幽蓝丶流月 on 2017/9/20.
 */

public class GetISBNActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_isbn;
    private Button bt_search;
    private Button one_dimensional_code;
    private Button two_dimensional_code;
    private String ISBN;
    private Intent intent;
    private LinearLayout ll_back;
    private TextView title;

    @Override
    protected int getLayoutID() {
        return R.layout.get_isbn;
    }

    @Override
    protected void initControl() {
        et_isbn=(EditText)findViewById(R.id.et_isbn);
        bt_search=(Button)findViewById(R.id.bt_search);
        one_dimensional_code=(Button)findViewById(R.id.one_dimensional_code);
        two_dimensional_code=(Button)findViewById(R.id.two_dimensional_code);
        title=(TextView)findViewById(R.id.title);
        ll_back=(LinearLayout)findViewById(R.id.titleBack_persionMessage_ll);
        title.setText("个人中心");
    }

    @Override
    void setControlListener() {
        bt_search.setOnClickListener(this);
        one_dimensional_code.setOnClickListener(this);
        two_dimensional_code.setOnClickListener(this);
        ll_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_search:
                ISBN=et_isbn.getText().toString().trim();
                intent=new Intent(GetISBNActivity.this,SearchResultActivity.class);
                intent.putExtra("ISBN",ISBN);
                startActivity(intent);
                break;
            case R.id.one_dimensional_code:
                intent = new Intent(GetISBNActivity.this, CaptureActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.two_dimensional_code:
                intent = new Intent(GetISBNActivity.this, CaptureActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.titleBack_persionMessage_ll:
                finish();
                break;
        }

    }

    @Override
    public void update(Observable observable, Object o) {
        mLoginState = (Boolean)o;
    }
}
