package observer;

import com.example.kys_31.figureinformation.LoginActivity;

import java.util.Observable;

/**
 * Created by 张同心 on 2017/9/20.
 * @function 登录状态被观察者
 */

public class LoginStateObservable extends Observable {

    private static class InstanceLoginState{
        static final LoginStateObservable LOGIN_STATE_OBSERVABLE = new LoginStateObservable();
    }

    public static LoginStateObservable getInstatnce(){
        return InstanceLoginState.LOGIN_STATE_OBSERVABLE;
    }

    public void notificationOberver(boolean loginState){
        setChanged();
        notifyObservers(loginState);
    }


}
