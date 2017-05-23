package com.yline.test;

import com.yline.view.common.CommonRecyclerAdapter;
import com.yline.view.common.RecyclerViewHolder;

/**
 * 使用默认布局，简单的Recycler布局
 *
 * @author yline 2017/5/23 -- 10:17
 * @version 1.0.0
 */
public class SimpleRecyclerAdapter extends CommonRecyclerAdapter<String>
{
	@Override
	public void onBindViewHolder(RecyclerViewHolder viewHolder, int position)
	{
		viewHolder.setText(android.R.id.text1, sList.get(position));
	}

	@Override
	public int getItemRes()
	{
		return android.R.layout.simple_list_item_1;
	}
}
