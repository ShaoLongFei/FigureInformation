package data;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by 张同心 on 2017/9/19.
 * @function 用户信息
 */

public class UserMessage implements Serializable {

    public String oStrHead;
    public String oStrName;
    public String oStrBirsday;
    public String oStrEmail;
    public String oStrPhoneNumber;
    public String oStrPassword;
    public String oStrUpLookTime;
    public int oIntSex;
    public int oIntLookCount;

    public UserMessage(String oStrPhoneNumber, String oStrPassword ,String oStrHead, String oStrName, String oStrBirsday, String oStrEmail ,int oIntSex, int oIntLookCount, String oStrUpLookTime){
        this.oStrPhoneNumber = oStrPhoneNumber;
        this.oStrPassword = oStrPassword;
        this.oStrHead = oStrHead;
        this.oStrName = oStrName;
        this.oStrBirsday = oStrBirsday;
        this.oStrEmail = oStrEmail;
        this.oIntSex = oIntSex;
        this.oIntLookCount = oIntLookCount;
        this.oStrUpLookTime = oStrUpLookTime;
    }

}
