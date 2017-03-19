package com.yline.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.yline.application.BaseApplication;

/**
 * simple introduction
 *
 * @author YLine 2016-5-25 - 上午7:32:43
 */
public class BaseFragment extends Fragment
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		BaseApplication.addFragmentForRecord(this);
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		BaseApplication.removeFragmentForRecord(this);
	}
}
