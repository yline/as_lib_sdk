package com.yline.view.custom;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.yline.base.BaseAppCompatActivity;
import com.yline.inner.InnerConstant;
import com.yline.view.apply.SimpleHeadFootRecyclerAdapter;
import com.yline.view.custom.swiperefresh.SuperSwipeRefreshLayout;
import com.yline.view.demo.R;

import java.util.Random;

public class SuperSwipeSimpleActivity extends BaseAppCompatActivity
{
	private SuperSwipeRefreshLayout swipeRefreshLayout;
	
	private RecyclerView recyclerView;
	
	private SimpleHeadFootRecyclerAdapter recyclerAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_super_swipe);
		
		swipeRefreshLayout = (SuperSwipeRefreshLayout) findViewById(R.id.super_swipe_refresh);
		recyclerView = (RecyclerView) findViewById(R.id.recycler_super_swipe);
		recyclerAdapter = new SimpleHeadFootRecyclerAdapter();
		
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		recyclerView.setAdapter(recyclerAdapter);
		
		swipeRefreshLayout.setOnRefreshListener(new SuperSwipeRefreshLayout.OnSwipeListener()
		{
			@Override
			public void onAnimate()
			{
				new Handler().postDelayed(new Runnable()
				{
					
					@Override
					public void run()
					{
						swipeRefreshLayout.setRefreshing(false);
						// 更新recyclerView
						recyclerAdapter.add(0, "Refresh Item + " + new Random().nextInt(300));
						recyclerView.scrollToPosition(0);
					}
				}, 4000);
			}
		});
		swipeRefreshLayout.setOnLoadListener(new SuperSwipeRefreshLayout.OnSwipeListener()
		{
			@Override
			public void onAnimate()
			{
				new Handler().postDelayed(new Runnable()
				{
					@Override
					public void run()
					{
						swipeRefreshLayout.setLoadMore(false);
						// 更新recyclerView
						int itemNumber = recyclerAdapter.getItemCount();
						recyclerAdapter.add(itemNumber, "Loaded Item + " + new Random().nextInt(300));
						recyclerView.scrollToPosition(itemNumber);
					}
				}, 4000);
			}
		});
		
		recyclerAdapter.setDataList(InnerConstant.getMvList());
	}
	
	public static void actionStart(Context context)
	{
		context.startActivity(new Intent(context, SuperSwipeSimpleActivity.class));
	}
}