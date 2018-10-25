package data;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by 张同心 on 2017/9/28.
 * @function
 */

public class HandleWebDataMessage {

    public static final String osStrDir = Environment.getExternalStorageDirectory()+"/FigureInformation/WebDataMessage";//目录

    /**
     * 保存数据
     */
    public static void saveWebDataMessageData(WebDataMessage webDataMessage) {
        File dir = new File(osStrDir);
        File file = new File(dir, "WebDataMessage_"+webDataMessage.oStrCategory+".txt");
        if (!dir.exists()){ Log.e("TAG", ""+dir.mkdirs());}
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(webDataMessage);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * o判断用户是否存在
     * @param category 手机号
     * @return 判断结果
     */
    public static boolean webDataMessageExist(String category){
        File dir = new File(osStrDir);
        File file = new File(dir, "WebDataMessage_"+category+".txt");
        if (file.exists()){
            return true;
        }else {
            return false;
        }
    }


    public static WebDataMessage readWebDataMessageMessage(String category){
        File dir = new File(osStrDir);
        File file = new File(dir, "WebDataMessage_"+category+".txt");
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            WebDataMessage webDataMessage = (WebDataMessage) ois.readObject();
            return webDataMessage;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
