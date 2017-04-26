package com.lib.http;

import com.lib.http.helper.IHttpResponse;
import com.lib.http.util.LogUtil;

/**
 * 配置每次的请求参数
 *
 * @param <Result>
 */
public abstract class XHttpAdapter<Result> implements IHttpResponse<Result>
{
	public abstract void onSuccess(Result result);

	@Override
	public void onFailureCode(int code)
	{
		if (isDebug())
		{
			LogUtil.e("onFailureCode code = " + code);
		}
	}

	@Override
	public void onFailure(Exception ex)
	{
		if (isDebug())
		{
			LogUtil.e("onFailure net exception happened", ex);
		}
	}

	public boolean isDebug()
	{
		return XHttpConstant.isDefaultDebug();
	}
}