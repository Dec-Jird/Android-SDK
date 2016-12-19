package com.tnyoo.baidugame_u3d.utils;

import com.baidu.gamesdk.BDGameApplication;

/*
 * 如果您的游戏有需要继承 Application 的类，可让该类直接继承 “BDGameApplication”，
 * 然后再 配置到 AndroidManifest.xml 里。如下：DemoApplication
 */
public class DemoApplication extends BDGameApplication{

	@Override
	public void onCreate() { 
		super.onCreate();
		
		//TODO 实现您自己的逻辑
		
	}
	
}
