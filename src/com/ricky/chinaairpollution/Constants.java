package com.ricky.chinaairpollution;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;

public class Constants {
	/* Service Stuff */
	public static final int MAINACTIVITY_NOTIFICATION_ID = 1;
	public static final int SERVICE_NOTIFICATION_ID = 3;

	public static final String PREF_NAME = "preferenceconnector";
	
	public static final String SOUND_DEFAULT = "jingle.mp3";
	
	public static final String ACTION_MAINACTIVITY_TO_SERVICE = "ACTION_MAINACTIVITY_TO_SERVICE";
	public static final String ACTION_SETTINGSACTIVITY_TO_SERVICE = "ACTION_SETTINGSACTIVITY_TO_SERVICE";
	public static final String ACTION_SETTINGSACTIVITY_TO_MAINACTIVITY = "ACTION_SETTINGSACTIVITY_TO_MAINACTIVITY";
	public static final String ACTION_SERVICE_TO_MAINACTIVITY = "ACTION_SERVICE_TO_MAINACTIVITY";
	public static final String ACTION_EXITACTIVITY_TO_MAINACTIVITY = "ACTION_EXITACTIVITY_TO_MAINACTIVITY";
	public static final String ACTION_SERVICE_TO_SERVICE = "ACTION_SERVICE_TO_SERVICE";

	public static final String AQIURL = "http://aqicn.org/city/beijing/m";
	public static final String AQIURL_BEIJING = "http://aqicn.org/city/beijing/m";
	public static final String AQIURL_CHANGZHOU = "http://aqicn.org/city/changzhou/m";
	public static final String AQIURL_CHENGDU = "http://aqicn.org/city/chengdu/m";
	public static final String AQIURL_FOSHAN = "http://aqicn.org/city/foshan/m";
	public static final String AQIURL_GUANGZHOU = "http://aqicn.org/city/guangzhou/m";
	public static final String AQIURL_HANGZHOU = "http://aqicn.org/city/hangzhou/m";
	public static final String AQIURL_HEFEI = "http://aqicn.org/city/hefei/m";
	public static final String AQIURL_HONGKONG = "http://aqicn.org/city/hongkong/m";
	public static final String AQIURL_HUAIAN = "http://aqicn.org/city/huaian/m";
	public static final String AQIURL_HUZHOU = "http://aqicn.org/city/huzhou/m";
	public static final String AQIURL_JIAXING = "http://aqicn.org/city/jiaxing/m";
	public static final String AQIURL_LIANYUNGANG = "http://aqicn.org/city/lianyungang/m";
	public static final String AQIURL_LINYI = "http://aqicn.org/city/linyi/m";
	public static final String AQIURL_MAANSHAN = "http://aqicn.org/city/maanshan/m";
	public static final String AQIURL_NANJING = "http://aqicn.org/city/nanjing/m";
	public static final String AQIURL_NANTONG = "http://aqicn.org/city/nantong/m";
	public static final String AQIURL_NINGBO = "http://aqicn.org/city/ningbo/m";
	public static final String AQIURL_QINGDAO = "http://aqicn.org/city/qingdao/m";
	public static final String AQIURL_RIZHAO = "http://aqicn.org/city/rizhao/m";
	public static final String AQIURL_SHANGHAI = "http://aqicn.org/city/shanghai/m";
	public static final String AQIURL_SHAOXING = "http://aqicn.org/city/shaoxing/m";
	public static final String AQIURL_SHENZHEN = "http://aqicn.org/city/shenzhen/m";
	public static final String AQIURL_SUQIAN = "http://aqicn.org/city/suqian/m";
	public static final String AQIURL_TAIZHOUSHI = "http://aqicn.org/city/taizhoushi/m";
	public static final String AQIURL_WEIFANG = "http://aqicn.org/city/weifang/m";
	public static final String AQIURL_WUHU = "http://aqicn.org/city/wuhu/m";
	public static final String AQIURL_WUXI = "http://aqicn.org/city/wuxi/m";
	public static final String AQIURL_XUZHOU = "http://aqicn.org/city/xuzhou/m";
	public static final String AQIURL_YANCHENG = "http://aqicn.org/city/yancheng/m";
	public static final String AQIURL_YANGZHOU = "http://aqicn.org/city/yangzhou/m";
	public static final String AQIURL_ZAOZHUANG = "http://aqicn.org/city/zaozhuang/m";
	public static final String AQIURL_ZHENJIANG = "http://aqicn.org/city/zhenjiang/m";
	public static final String AQIURL_ZHOUSHAN = "http://aqicn.org/city/zhoushan/m";
	
	public static final String AQICITY = "BEIJING";
	public static final String AQICITY_BEIJING = "BEIJING";
	public static final String AQICITY_CHANGZHOU = "CHANGZHOU";
	public static final String AQICITY_CHENGDU = "CHENGDU";
	public static final String AQICITY_FOSHAN = "FOSHAN";
	public static final String AQICITY_GUANGZHOU = "GUANGZHOU";
	public static final String AQICITY_HANGZHOU = "HANGZHOU";
	public static final String AQICITY_HEFEI = "HEFEI";
	public static final String AQICITY_HONGKONG = "HONGKONG";
	public static final String AQICITY_HUAIAN = "HUAI\'AN";
	public static final String AQICITY_HUZHOU = "HUZHOU";
	public static final String AQICITY_JIAXING = "JIAXING";
	public static final String AQICITY_LIANYUNGANG = "LIANYUNGANG";
	public static final String AQICITY_LINYI = "LINYI";
	public static final String AQICITY_MAANSHAN = "MAANSHAN";
	public static final String AQICITY_NANJING = "NANJING";
	public static final String AQICITY_NANTONG = "NANTONG";
	public static final String AQICITY_NINGBO = "NINGBO";
	public static final String AQICITY_QINGDAO = "QINGDAO";
	public static final String AQICITY_RIZHAO = "RIZHAO";
	public static final String AQICITY_SHANGHAI = "SHANGHAI";
	public static final String AQICITY_SHAOXING = "SHAOXING";
	public static final String AQICITY_SHENZHEN = "SHENZHEN";
	public static final String AQICITY_SUQIAN = "SUQIAN";
	public static final String AQICITY_TAIZHOUSHI = "TAIZHOUSHI";
	public static final String AQICITY_WEIFANG = "WEIFANG";
	public static final String AQICITY_WUHU = "WUHU";
	public static final String AQICITY_WUXI = "WUXI";
	public static final String AQICITY_XUZHOU = "XUZHOU";
	public static final String AQICITY_YANCHENG = "YANCHENG";
	public static final String AQICITY_YANGZHOU = "YANGZHOU";
	public static final String AQICITY_ZAOZHUANG = "ZAOZHUANG";
	public static final String AQICITY_ZHENJIANG = "ZHENJIANG";
	public static final String AQICITY_ZHOUSHAN = "ZHOUSHAN";
	
	public static final long INTERVAL_ONE_SECOND = 1000;
	public static final long INTERVAL_ONE_MINUTE = 60000; //1000 * 60
	public static final long INTERVAL_FIVE_MINUTE = 300000; //1000 * 60 * 5
	public static final long INTERVAL_FIFTEEN_MINUTE = 900000; //1000 * 60 * 15
	public static final long INTERVAL_THIRTY_MINUTE = 1800000; //1000 * 60 * 30
	public static final long INTERVAL_ONE_HOUR = 3600000; //1000 * 60 * 60
	public static final long INTERVAL_THREE_HOUR = 10800000; //1000 * 60 * 60 * 3
	public static final long INTERVAL_ONE_DAY = 86400000; //1000 * 60 * 60 * 24
	
	public static final int aqiLevel1 = 0;
	public static final int aqiLevel2 = 51;
	public static final int aqiLevel3 = 101;
	public static final int aqiLevel4 = 151;
	public static final int aqiLevel5 = 201;
	public static final int aqiLevel6 = 301;
	
	public static final int COLOR_WHITE = 0xffd0d0d0;
	public static final int COLOR_GREEN = 0xff008040;
	public static final int COLOR_YELLOW = 0xfffffe00;
	public static final int COLOR_BLACK_LIGHT = 0xff141414;
	public static final int COLOR_GREY_DARK = 0xff222222;
	public static final int COLOR_ORANGE = 0xffff8b19;
	public static final int COLOR_PURPLE_RED_LIGHT = 0xffff0065;
	public static final int COLOR_PURPLE_RED_DARK = 0xff940034;
	public static final int COLOR_PURPLE_BLUE_DARK = 0xff7e047a;
	
	public static final String oriUrl = "http://www.airnow.gov/index.cfm?action=aqibasics.aqi";
}
