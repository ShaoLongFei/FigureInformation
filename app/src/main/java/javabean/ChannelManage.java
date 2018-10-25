package javabean;

import android.database.SQLException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dao.ChannelDao;
import database.SQLHelper;

/**
 * 杨铭 Created by kys_35 on 2017/9/24.
 * <p>Email:771365380@qq.com</p>
 * <p>Mobile phone:15133350726</p>
 */

public class ChannelManage
{
    public static ChannelManage channelManage;
    /**
     * 默认的用户选择频道列表
     * */
    public static List<ChannelItem> defaultUserChannels;
    /**
     * 默认的其他频道列表
     * */
    public static List<ChannelItem> defaultOtherChannels;
    private ChannelDao channelDao;
    /** 判断数据库中是否存在用户数据 */
    private boolean userExist = false;
    static {
        defaultUserChannels = new ArrayList<>();
        defaultOtherChannels = new ArrayList<>();
        defaultUserChannels.add(new ChannelItem(1, "可再生能源", 1, 1));
        defaultUserChannels.add(new ChannelItem(2, "纳米科技", 2, 1));
        defaultUserChannels.add(new ChannelItem(3, "食物与营养", 3, 1));
        defaultUserChannels.add(new ChannelItem(4, "油气开发与应用", 4, 1));
        defaultUserChannels.add(new ChannelItem(5, "水体污染治理", 5, 1));
        defaultUserChannels.add(new ChannelItem(6, "大气污染防治", 6, 1));
        defaultUserChannels.add(new ChannelItem(7, "集成电路装备", 7, 1));
        defaultOtherChannels.add(new ChannelItem(8, "数控机床", 1, 0));
        defaultOtherChannels.add(new ChannelItem(9, "转基因生物新品种培育", 2, 0));
        defaultOtherChannels.add(new ChannelItem(10, "农业立体污染防治", 3, 0));
        defaultOtherChannels.add(new ChannelItem(11, "宽带移动通信", 4, 0));
        defaultOtherChannels.add(new ChannelItem(12, "新药创制", 5, 0));
        defaultOtherChannels.add(new ChannelItem(13, "重大传染病防治", 6, 0));
        defaultOtherChannels.add(new ChannelItem(14, "重要报告", 7, 0));
        defaultOtherChannels.add(new ChannelItem(15, "编译报道", 8, 0));
        defaultOtherChannels.add(new ChannelItem(16, "情报产品", 9, 0));
        defaultOtherChannels.add(new ChannelItem(17, "热点专题", 10, 0));
    }

    private ChannelManage(SQLHelper paramDBHelper) throws SQLException
    {
        if (channelDao == null)
            channelDao = new ChannelDao(paramDBHelper.getContext());
        // NavigateItemDao(paramDBHelper.getDao(NavigateItem.class));
        return;
    }

    /**
     * 初始化频道管理类
     * @param dbHelper
     * @throws SQLException
     */
    public static ChannelManage getManage(SQLHelper dbHelper)throws SQLException {
        if (channelManage == null)
            channelManage = new ChannelManage(dbHelper);
        return channelManage;
    }

    /**
     * 清除所有的频道
     */
    public void deleteAllChannel() {
        channelDao.clearFeedTable();
    }
    /**
     * 获取其他的频道
     * @return 数据库存在用户配置 ? 数据库内的用户选择频道 : 默认用户选择频道 ;
     */
    public List<ChannelItem> getUserChannel() {
        Object cacheList = channelDao.listCache(SQLHelper.SELECTED + "= ?",new String[] { "1" });
        if (cacheList != null && !((List) cacheList).isEmpty()) {
            userExist = true;
            List<Map<String, String>> maplist = (List) cacheList;
            int count = maplist.size();
            List<ChannelItem> list = new ArrayList<ChannelItem>();
            for (int i = 0; i < count; i++) {
                ChannelItem navigate = new ChannelItem();
                navigate.setId(Integer.valueOf(maplist.get(i).get(SQLHelper.ID)));
                navigate.setName(maplist.get(i).get(SQLHelper.NAME));
                navigate.setOrderId(Integer.valueOf(maplist.get(i).get(SQLHelper.ORDERID)));
                navigate.setSelected(Integer.valueOf(maplist.get(i).get(SQLHelper.SELECTED)));
                list.add(navigate);
            }
            return list;
        }
        initDefaultChannel();
        return defaultUserChannels;
    }

    /**
     * 获取其他的频道
     * @return 数据库存在用户配置 ? 数据库内的其它频道 : 默认其它频道 ;
     */
    public List<ChannelItem> getOtherChannel() {
        Object cacheList = channelDao.listCache(SQLHelper.SELECTED + "= ?" ,new String[] { "0" });
        List<ChannelItem> list = new ArrayList<ChannelItem>();
        if (cacheList != null && !((List) cacheList).isEmpty()){
            List<Map<String, String>> maplist = (List) cacheList;
            int count = maplist.size();
            for (int i = 0; i < count; i++) {
                ChannelItem navigate= new ChannelItem();
                navigate.setId(Integer.valueOf(maplist.get(i).get(SQLHelper.ID)));
                navigate.setName(maplist.get(i).get(SQLHelper.NAME));
                navigate.setOrderId(Integer.valueOf(maplist.get(i).get(SQLHelper.ORDERID)));
                navigate.setSelected(Integer.valueOf(maplist.get(i).get(SQLHelper.SELECTED)));
                list.add(navigate);
            }
            return list;
        }
        if(userExist){
            return list;
        }
        cacheList = defaultOtherChannels;
        return (List<ChannelItem>) cacheList;
    }

    /**
     * 保存用户频道到数据库
     * @param userList
     */
    public void saveUserChannel(List<ChannelItem> userList) {
        for (int i = 0; i < userList.size(); i++) {
            ChannelItem channelItem = (ChannelItem) userList.get(i);
            channelItem.setOrderId(i);
            channelItem.setSelected(Integer.valueOf(1));
            channelDao.addCache(channelItem);
        }
    }

    /**
     * 保存其他频道到数据库
     * @param otherList
     */
    public void saveOtherChannel(List<ChannelItem> otherList) {
        for (int i = 0; i < otherList.size(); i++) {
            ChannelItem channelItem = (ChannelItem) otherList.get(i);
            channelItem.setOrderId(i);
            channelItem.setSelected(Integer.valueOf(0));
            channelDao.addCache(channelItem);
        }
    }

    /**
     * 初始化数据库内的频道数据
     */
    private void initDefaultChannel(){
        Log.d("deleteAll", "deleteAll");
        deleteAllChannel();
        saveUserChannel(defaultUserChannels);
        saveOtherChannel(defaultOtherChannels);
    }
}
