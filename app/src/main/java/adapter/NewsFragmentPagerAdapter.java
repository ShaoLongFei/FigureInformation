package adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import java.util.ArrayList;

public class NewsFragmentPagerAdapter extends FragmentPagerAdapter
{
	private ArrayList<Fragment> fragments;
	private FragmentManager fm;

	public NewsFragmentPagerAdapter(FragmentManager fm,
			ArrayList<Fragment> fragments) {
		super(fm);
		this.fm = fm;
		this.fragments = fragments;
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

	@Override
	public Fragment getItem(int position) {
		return fragments.get(position);
	}

	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}

	@Override
	public Object instantiateItem(ViewGroup container, final int position) {
		Log.e("TAG:", "position:"+position);
		Object obj = super.instantiateItem(container, position);
		return obj;
	}

	@Override
	public void finishUpdate(ViewGroup container) {
		try{
			super.finishUpdate(container);
		} catch (NullPointerException nullPointerException){
			Log.e("出错了，出错了","在这里");
		}
	}
}
