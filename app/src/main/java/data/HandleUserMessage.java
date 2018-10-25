package data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Parcel;

import com.example.kys_31.figureinformation.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;

/**
 * Created by 张同心 on 2017/9/19.
 * @function 处理用户信息
 */

public class HandleUserMessage {

    public static final String osStrDir = Environment.getExternalStorageDirectory()+"/FigureInformation/userMessage";//目录

    /**
     * 保存数据
     */
    public static void saveData(UserMessage userMessage) {
        File dir = new File(osStrDir);
        File file = new File(dir, "user_"+userMessage.oStrPhoneNumber+".txt");
        if (!dir.exists()){ dir.mkdirs();}
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(userMessage);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * o判断用户是否存在
     * @param phoneNumber 手机号
     * @return 判断结果
     */
    public static boolean userExist(String phoneNumber){
        File dir = new File(osStrDir);
        File file = new File(dir, "user_"+phoneNumber+".txt");
        if (file.exists()){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 读取用户信息
     * @param phoneNumber 手机号
     * @return 用户信息
     */
    public static UserMessage readUserMessage(String phoneNumber){
        File dir = new File(osStrDir);
        File file = new File(dir, "user_"+phoneNumber+".txt");
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            UserMessage userMessage = (UserMessage) ois.readObject();
            return userMessage;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
