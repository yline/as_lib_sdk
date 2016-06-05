package com.yline.application.timer;

import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import android.util.Log;

import com.yline.log.LogFileUtil;

public final class TimerManager
{
    public static final String TAG_TIMER = "timer";
    
    // 储存数据
    private HashMap<String, TimerValueHolder> mTimeMap;
    
    private Lock lock = new ReentrantLock();
    
    private ITimerListener mTimerListener;
    
    private TimerManager()
    {
        mTimeMap = new HashMap<String, TimerValueHolder>();
    }
    
    public static TimerManager getInstance()
    {
        return TimeManagerHolder.instance;
    }
    
    private static class TimeManagerHolder
    {
        private static TimerManager instance = new TimerManager();
    }
    
    /**
     * 注册或更新设置,并重新开始计时,永不停止
     * @param tag   标签
     * @param time  每次通知的时间
     * @param listener
     */
    public void register(String tag, float time, ITimerListener listener)
    {
        lock.lock();
        try
        {
            if (!mTimeMap.containsKey(tag)) // 添加tag标签
            {
                LogFileUtil.v(TAG_TIMER, "register -> tag注册");
                mTimeMap.put(tag, new TimerValueHolder().setHolder(Math.abs(time)).setListener(listener));
            }
            else
            {
                LogFileUtil.v(TAG_TIMER, "register -> 该tag已经注册,更新数据");
                mTimeMap.put(tag, mTimeMap.get(tag).setHolder(Math.abs(time)));
            }
        }
        finally
        {
            lock.unlock();
        }
    }
    
    /**
     * 注册或更新设置,并重新开始计时
     * @param tag   标签
     * @param time  每次通知的时间
     * @param number    次数,(小于0,永远)(大于等于0,次数)
     * @param listener
     */
    public void register(String tag, float time, int number, ITimerListener listener)
    {
        lock.lock();
        try
        {
            if (!mTimeMap.containsKey(tag)) // 添加tag标签
            {
                LogFileUtil.v(TAG_TIMER, "register number -> tag注册");
                mTimeMap.put(tag, new TimerValueHolder().setHolder(Math.abs(time), number).setListener(listener));
            }
            else
            {
                LogFileUtil.v(TAG_TIMER, "register number -> 该tag已经注册,更新数据");
                mTimeMap.put(tag, mTimeMap.get(tag).setHolder(Math.abs(time), number));
            }
        }
        finally
        {
            lock.unlock();
        }
    }
    
    /**
     * 移除设置
     * @param tag   标签
     */
    public void remove(String tag)
    {
        if (!mTimeMap.containsKey(tag))
        {
            Log.e(TAG_TIMER, "update -> 该tag还未注册,移除失败");
        }
        else
        {
            mTimeMap.remove(tag);
        }
    }
    
    /**
     * 移除所有
     */
    public void removeAll()
    {
        mTimeMap.clear();
    }
    
    public Set<String> getKeySet()
    {
        return mTimeMap.keySet();
    }
    
    public TimerValueHolder getTimeValueHolder(String tag)
    {
        return mTimeMap.get(tag);
    }
    
    public void putTimeValueHolder(String key, TimerValueHolder value)
    {
        mTimeMap.put(key, value);
    }
    
    /**
     * 调用监听器,执行
     * @param tag 标签
     */
    public void setTimerListener(String tag)
    {
        if (null != mTimerListener)
        {
            mTimerListener.onResult(tag);
        }
    }
    
    /**
     * 设置监听器,开始监听
     * @param timerListener
     */
    public void setOnTimerListener(ITimerListener timerListener)
    {
        this.mTimerListener = timerListener;
    }
    
    public interface ITimerListener
    {
        /**
         * 仅通知添加对象中相对应的listener,其它对象中的通知,不会通知过来.
         * 该函数,在Handler中执行,不要使用耗时操作
         * @param tag 标志tag
         */
        void onResult(String tag);
    }
}