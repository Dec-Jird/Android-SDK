
package com.tnyoo.iappay.jr;
 
import com.unity3d.player.UnityPlayerActivity;
 
import android.os.Bundle;
import android.util.Log;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
 
public class TransActivity extends UnityPlayerActivity {
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    /**Iapppay爱贝支付接口*/
    @SuppressLint("NewApi") 
    public void StartActivity(String tranid, String AppID)
    {
    	Context mContext = this;
    	Intent intent = new Intent(mContext, Iapppay.class);
    	intent.putExtra("TranID", tranid);//发送数据
    	intent.putExtra("AppID", AppID);
    	Log.i("PAY", "[TransActivity] 准备支付：{" + tranid + ", " + AppID + "}");
    	this.startActivity(intent);//打开StartIapppay
    }
} 