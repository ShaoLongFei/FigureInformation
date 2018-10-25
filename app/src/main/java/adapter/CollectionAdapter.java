package adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.example.kys_31.figureinformation.CollectionInformationActivity;
import com.example.kys_31.figureinformation.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by kys_31 on 2017/9/27.
 */

public class CollectionAdapter extends BaseAdapter {
    private static List<HashMap<String,String>> mList;
    private static Context mContext;
    private Intent intent;
    public static List<Boolean> listBoolean=new ArrayList<>();
    public static boolean isShow=false;
    private ImageLoader imageLoader;
    private RequestQueue mQueue;

    private static class InitCollectionDateAdapter{
        static final CollectionAdapter collectionAdapter = new CollectionAdapter();
    }

    public static CollectionAdapter getInitCollectionAdapter(Context context, List<HashMap<String,String>> list){
        mContext = context;
        mList = list;
        for (int j=0;j<list.size();j++){
            listBoolean.add(j,false);
        }
        return InitCollectionDateAdapter.collectionAdapter;
    }

    private void pictureSetting() {
        mQueue = Volley.newRequestQueue(mContext);
        imageLoader = new ImageLoader(mQueue, new ImageLoader.ImageCache() {
            @Override
            public void putBitmap(String url, Bitmap bitmap) {

            }

            @Override
            public Bitmap getBitmap(String url) {
                return null;
            }
        });
    }

    @Override
    public int getCount() {
        pictureSetting();
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final viewHolder viewHolder;

        if (view == null){
            viewHolder = new viewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.collectionmessage_collection_item,null);
            view.setTag(viewHolder);
            viewHolder.imageView = (NetworkImageView) view.findViewById(R.id.pictureMessage_collectionMessage_iv);
            viewHolder.title = (TextView)view.findViewById(R.id.nameMessage_collectionMessage_iv);
            viewHolder.time=(TextView)view.findViewById(R.id.timeMessage_collectionMessage_tv) ;
            viewHolder.collectionNumber = (TextView)view.findViewById(R.id.sumMessage_collection_tv);
            viewHolder.checkBox = (CheckBox)view.findViewById(R.id.deleteCollection_collectionMessage_cb);
            viewHolder.ll_collection=(LinearLayout)view.findViewById(R.id.ll_collection);
        }else {
            viewHolder = (viewHolder) view.getTag();
        }

        if (isShow){
            viewHolder.checkBox.setVisibility(View.VISIBLE);
        }else {
            viewHolder.checkBox.setVisibility(View.INVISIBLE);
        }

        viewHolder.imageView.setDefaultImageResId(R.drawable.picture_load);
        viewHolder.imageView.setErrorImageResId(R.drawable.picture_error);
        viewHolder.imageView.setImageUrl(mList.get(i).get("pictureURL"), imageLoader);

        if (listBoolean.get(i)){
            viewHolder.checkBox.setChecked(true);
        }else
        viewHolder.title.setText(mList.get(i).get("title"));
        viewHolder.collectionNumber.setText(mList.get(i).get("clickNumber"));
        viewHolder.time.setText(mList.get(i).get("time"));
     //   viewHolder.imageView.setImageResource(picture[i%11]);
        viewHolder.ll_collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent=new Intent(mContext, CollectionInformationActivity.class);
                intent.putExtra("title",mList.get(i).get("title"));
                intent.putExtra("content",mList.get(i).get("content"));
                intent.putExtra("pictureURL",mList.get(i).get("pictureURL"));
                intent.putExtra("clickNumber",mList.get(i).get("clickNumber"));
                intent.putExtra("time",mList.get(i).get("time"));
                intent.putExtra("author",mList.get(i).get("author"));
                startActivity(mContext,intent,null);
            }
        });
        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    listBoolean.set(i,true);
                }else {
                    listBoolean.set(i,false);
                }
            }
        });

        return view;
    }

    static class viewHolder{
        public NetworkImageView imageView;
        public TextView title;
        public TextView time;
        public TextView collectionNumber;
        public CheckBox checkBox;
        private LinearLayout ll_collection;
    }
}
