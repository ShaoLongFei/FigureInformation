package fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Observer;

import observer.LoginStateObservable;
import util.AutoLoginUtil;
import util.ViewUtil;
import variable.SystemSetVariable;

/**
 * Created by 张同心 on 2017/9/12.
 * @function Fragment 基类
 */

public abstract class BaseFragment extends Fragment implements View.OnClickListener,Observer{

    public View view;
    public boolean mLoginState = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle saveInstanceState){
        view = inflater.inflate(getLayoutID(),null);
        initControl();
        setListener();
        LoginStateObservable.getInstatnce().addObserver(this);//注册观察者
        return view;
    }

    protected abstract int getLayoutID();

    protected abstract void initControl();

    protected abstract void setListener();

    protected abstract void showByLoginState();

    public void showToast(String content, boolean show){
        Toast.makeText(getActivity(),content,show?Toast.LENGTH_LONG:Toast.LENGTH_SHORT);
    }

    @Override
    public void onResume(){
        super.onResume();
        /*屏幕亮度*/
        SystemSetVariable.osScreenBrightValue = ViewUtil.getScreenBrightness(getActivity());
        if (SystemSetVariable.osNightModel){
            ViewUtil.setScreenBrightness(getActivity(), 10);
        }else {
            ViewUtil.setScreenBrightness(getActivity(), SystemSetVariable.osScreenBrightValue);
        }
        /*自动登录*/
        AutoLoginUtil.startAutoLogin(getActivity());
        /*根据登录状态显示*/
        showByLoginState();
    }

}
