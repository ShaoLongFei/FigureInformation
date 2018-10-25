package data;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 张同心 on 2017/9/28.
 * 网页数据存数
 */

public class WebDataMessage implements Serializable{

    public String[] oURL ;//地址
    public String[] oTitleName;//标题名字
    public String[] oContent;//文字内容
    public String[] oTime;//发表时间
    public String[] oLookCount;//观看次数
    public String[] oPictureURL;//图片
    public int oArticleCount;//文章数量
    public String oStrCategory;//种类
    public String[] oStrFrom;//来自
    public String[] oStrAuthor; //作者

    public WebDataMessage(String oStrCategory,String[] oURL, String[] oTitleName, String[] oContent, String[] oTime, String[] oLookCount, String[] oPictureURL, String[] oStrFrom,String[] oStrAuthor ,int oArticleCount) {
        this.oStrCategory = oStrCategory;
        this.oURL = oURL;
        this.oTitleName = oTitleName;
        this.oContent = oContent;
        this.oTime = oTime;
        this.oLookCount = oLookCount;
        this.oPictureURL = oPictureURL;
        this.oArticleCount = oArticleCount;
        this.oStrFrom = oStrFrom;
        this.oStrAuthor = oStrAuthor;
    }
}
