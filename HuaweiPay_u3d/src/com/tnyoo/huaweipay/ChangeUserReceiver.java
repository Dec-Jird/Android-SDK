/*
 * 文 件 名: ChangeUserReceiver.java
 * 版 权: Huawei Technologies Co., Ltd. Copyright YYYY-YYYY, All rights reserved
 * 描 述: <描述>
 * 修 改 人: c00206870
 * 修改时间: 2014-6-28
 * 跟踪单号: <跟踪单号>
 * 修改单号: <修改单号>
 * 修改内容: <修改内容>
 */
package com.tnyoo.huaweipay;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.huawei.gamebox.buoy.sdk.util.BuoyConstant;
import com.huawei.gamebox.buoy.sdk.util.DebugConfig;
import com.unity3d.player.UnityPlayer;

/**
 * 接收切换帐号的广播，这里提供的是静态注册的方式， 开发者可根据自己的实际需求是否能满足决定是否要把注册方式改为动态注册
 * 要求：要保证能接收到SDK发出的切换帐号的广播和跳转到游戏首页
 * 
 * @author c00206870
 * @version [版本号, 2014-6-28]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ChangeUserReceiver extends BroadcastReceiver
{
    private static final String TAG = "Huawei";
    
    @Override
    public void onReceive(Context context, Intent intent)
    {
        String action = intent.getAction();
        DebugConfig.d(TAG, "onReceive action=" + action);
        if (BuoyConstant.CHANGE_USER_LOGIN_ACTION.equals(action))
        {
            Bundle bundle = intent.getBundleExtra(BuoyConstant.GAMEBOX_EXTRA_DATA);
            int value = 0;
            if(null != bundle)
            {
                value = bundle.getInt(BuoyConstant.KEY_GAMEBOX_CHANGEUSERLOGIN);
            }
            DebugConfig.d(TAG, "onReceive value=" + value);
            if (BuoyConstant.VALUE_CHANGE_USER == value)
            {
            	//切换帐号成功，code=3为切换帐号
				UnityPlayer.UnitySendMessage(HuaweiPayActivity.gameObjName, HuaweiPayActivity.RESP_METHOD_Msg_Receiver,
						"{\"code\":\"3\",\"ret\":\"true\"}");
				
                // 跳转到首页逻辑，以及后续逻辑请开发者自行补充
                Toast.makeText(context, "即将跳转到首页", Toast.LENGTH_SHORT).show();
                Intent mainIntent = new Intent(context, HuaweiPayActivity.class);
                
                // 用于标注此请求来自切换账号
                mainIntent.putExtra("from", "changeuserinfo");
                mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(mainIntent);
            }
        }
    }
    
}
