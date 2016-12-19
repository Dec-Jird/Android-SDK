
package com.tnyoo.XiYou;
 
import com.unity3d.player.UnityPlayerActivity;
 
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
 
public class IapppayActivity extends UnityPlayerActivity {
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    /**Iapppay爱贝支付接口*/
    public void StartActivity(String tranid, String AppID)
    {
    	Context mContext = this;
    	Intent intent = new Intent(mContext, StartIapppay.class);
    	intent.putExtra("TranID", tranid);//发送数据
    	intent.putExtra("AppID", AppID);
    	this.startActivity(intent);//打开StartIapppay
    }
} 