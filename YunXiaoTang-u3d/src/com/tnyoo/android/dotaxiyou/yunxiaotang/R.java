package com.tnyoo.android.dotaxiyou.yunxiaotang;

import android.content.Context;

public class R
{
  private static int getResourcesId(String resName, String resType)
  {
    Context context = MainActivity.context;
    return context.getResources().getIdentifier(resName, resType, context.getPackageName());
  }

  public static class anim {
    public static int yyjia_sdk_ani_in = R.getResourcesId("yyjia_sdk_ani_in", "anim");
    public static int yyjia_sdk_ani_out = R.getResourcesId("yyjia_sdk_ani_out", "anim");
    public static int yyjia_sdk_anim = R.getResourcesId("yyjia_sdk_anim", "anim");
    public static int yyjia_sdk_anim_in = R.getResourcesId("yyjia_sdk_anim_in", "anim");
    public static int yyjia_sdk_anim_out = R.getResourcesId("yyjia_sdk_anim_out", "anim");
  }

  public static class attr {
  }

  public static class color {
    public static int yyjia_sdk_bg_pay_img = R.getResourcesId("yyjia_sdk_bg_pay_img", "color");
    public static int yyjia_sdk_bg_tv = R.getResourcesId("yyjia_sdk_bg_tv", "color");
    public static int yyjia_sdk_black = R.getResourcesId("yyjia_sdk_black", "color");
    public static int yyjia_sdk_translate = R.getResourcesId("yyjia_sdk_translate", "color");
    public static int yyjia_sdk_tv = R.getResourcesId("yyjia_sdk_tv", "color");
    public static int yyjia_sdk_wt = R.getResourcesId("yyjia_sdk_wt", "color");
  }

  public static class dimen
  {
    public static int activity_horizontal_margin = R.getResourcesId("activity_horizontal_margin", "dimen");
    public static int activity_vertical_margin = R.getResourcesId("activity_vertical_margin", "dimen");
  }

  public static class drawable {
    public static int ic_launcher = R.getResourcesId("ic_launcher", "drawable");
    public static int yxt = R.getResourcesId("yxt", "drawable");
    public static int yyjia_sdk_accountlist = R.getResourcesId("yyjia_sdk_accountlist", "drawable");
    public static int yyjia_sdk_accountmanage = R.getResourcesId("yyjia_sdk_accountmanage", "drawable");
    public static int yyjia_sdk_accountmanage_click = R.getResourcesId("yyjia_sdk_accountmanage_click", "drawable");
    public static int yyjia_sdk_back = R.getResourcesId("yyjia_sdk_back", "drawable");
    public static int yyjia_sdk_back_click = R.getResourcesId("yyjia_sdk_back_click", "drawable");
    public static int yyjia_sdk_background = R.getResourcesId("yyjia_sdk_background", "drawable");
    public static int yyjia_sdk_background_bottom = R.getResourcesId("yyjia_sdk_background_bottom", "drawable");
    public static int yyjia_sdk_background_toolbar = R.getResourcesId("yyjia_sdk_background_toolbar", "drawable");
    public static int yyjia_sdk_bbs = R.getResourcesId("yyjia_sdk_bbs", "drawable");
    public static int yyjia_sdk_bg = R.getResourcesId("yyjia_sdk_bg", "drawable");
    public static int yyjia_sdk_bg_back = R.getResourcesId("yyjia_sdk_bg_back", "drawable");
    public static int yyjia_sdk_bg_btnmenu = R.getResourcesId("yyjia_sdk_bg_btnmenu", "drawable");
    public static int yyjia_sdk_bg_logo = R.getResourcesId("yyjia_sdk_bg_logo", "drawable");
    public static int yyjia_sdk_bg_menu = R.getResourcesId("yyjia_sdk_bg_menu", "drawable");
    public static int yyjia_sdk_bg_pay = R.getResourcesId("yyjia_sdk_bg_pay", "drawable");
    public static int yyjia_sdk_bg_tab = R.getResourcesId("yyjia_sdk_bg_tab", "drawable");
    public static int yyjia_sdk_bluebutton = R.getResourcesId("yyjia_sdk_bluebutton", "drawable");
    public static int yyjia_sdk_bluebutton_click = R.getResourcesId("yyjia_sdk_bluebutton_click", "drawable");
    public static int yyjia_sdk_bt_back = R.getResourcesId("yyjia_sdk_bt_back", "drawable");
    public static int yyjia_sdk_button = R.getResourcesId("yyjia_sdk_button", "drawable");
    public static int yyjia_sdk_button_click = R.getResourcesId("yyjia_sdk_button_click", "drawable");
    public static int yyjia_sdk_checkbox = R.getResourcesId("yyjia_sdk_checkbox", "drawable");
    public static int yyjia_sdk_checkbox_pay = R.getResourcesId("yyjia_sdk_checkbox_pay", "drawable");
    public static int yyjia_sdk_checked = R.getResourcesId("yyjia_sdk_checked", "drawable");
    public static int yyjia_sdk_close = R.getResourcesId("yyjia_sdk_close", "drawable");
    public static int yyjia_sdk_close_click = R.getResourcesId("yyjia_sdk_close_click", "drawable");
    public static int yyjia_sdk_community = R.getResourcesId("yyjia_sdk_community", "drawable");
    public static int yyjia_sdk_community_click = R.getResourcesId("yyjia_sdk_community_click", "drawable");
    public static int yyjia_sdk_dlgbackup = R.getResourcesId("yyjia_sdk_dlgbackup", "drawable");
    public static int yyjia_sdk_downarrow = R.getResourcesId("yyjia_sdk_downarrow", "drawable");
    public static int yyjia_sdk_downwindow = R.getResourcesId("yyjia_sdk_downwindow", "drawable");
    public static int yyjia_sdk_downwindow_line = R.getResourcesId("yyjia_sdk_downwindow_line", "drawable");
    public static int yyjia_sdk_drawable = R.getResourcesId("yyjia_sdk_drawable", "drawable");
    public static int yyjia_sdk_dropdown = R.getResourcesId("yyjia_sdk_dropdown", "drawable");
    public static int yyjia_sdk_dropdown_click = R.getResourcesId("yyjia_sdk_dropdown_click", "drawable");
    public static int yyjia_sdk_facebook = R.getResourcesId("yyjia_sdk_facebook", "drawable");
    public static int yyjia_sdk_four = R.getResourcesId("yyjia_sdk_four", "drawable");
    public static int yyjia_sdk_google = R.getResourcesId("yyjia_sdk_google", "drawable");
    public static int yyjia_sdk_hdyx = R.getResourcesId("yyjia_sdk_hdyx", "drawable");
    public static int yyjia_sdk_ic_launcher = R.getResourcesId("yyjia_sdk_ic_launcher", "drawable");
    public static int yyjia_sdk_icon0 = R.getResourcesId("yyjia_sdk_icon0", "drawable");
    public static int yyjia_sdk_img = R.getResourcesId("yyjia_sdk_img", "drawable");
    public static int yyjia_sdk_img2 = R.getResourcesId("yyjia_sdk_img2", "drawable");
    public static int yyjia_sdk_inputbox = R.getResourcesId("yyjia_sdk_inputbox", "drawable");
    public static int yyjia_sdk_inputbox2 = R.getResourcesId("yyjia_sdk_inputbox2", "drawable");
    public static int yyjia_sdk_inputbox2_click = R.getResourcesId("yyjia_sdk_inputbox2_click", "drawable");
    public static int yyjia_sdk_line = R.getResourcesId("yyjia_sdk_line", "drawable");
    public static int yyjia_sdk_login = R.getResourcesId("yyjia_sdk_login", "drawable");
    public static int yyjia_sdk_longbluebutton = R.getResourcesId("yyjia_sdk_longbluebutton", "drawable");
    public static int yyjia_sdk_longbluebutton_click = R.getResourcesId("yyjia_sdk_longbluebutton_click", "drawable");
    public static int yyjia_sdk_longbluebutton_disable = R.getResourcesId("yyjia_sdk_longbluebutton_disable", "drawable");
    public static int yyjia_sdk_midline = R.getResourcesId("yyjia_sdk_midline", "drawable");
    public static int yyjia_sdk_more = R.getResourcesId("yyjia_sdk_more", "drawable");
    public static int yyjia_sdk_mcore_click = R.getResourcesId("yyjia_sdk_mcore_click", "drawable");
    public static int yyjia_sdk_moreapp = R.getResourcesId("yyjia_sdk_moreapp", "drawable");
    public static int yyjia_sdk_one = R.getResourcesId("yyjia_sdk_one", "drawable");
    public static int yyjia_sdk_paychecked = R.getResourcesId("yyjia_sdk_paychecked", "drawable");
    public static int yyjia_sdk_payorder = R.getResourcesId("yyjia_sdk_payorder", "drawable");
    public static int yyjia_sdk_paysearch = R.getResourcesId("yyjia_sdk_paysearch", "drawable");
    public static int yyjia_sdk_paysearch_click = R.getResourcesId("yyjia_sdk_paysearch_click", "drawable");
    public static int yyjia_sdk_qq = R.getResourcesId("yyjia_sdk_qq", "drawable");
    public static int yyjia_sdk_rightarrow = R.getResourcesId("yyjia_sdk_rightarrow", "drawable");
    public static int yyjia_sdk_showicon = R.getResourcesId("yyjia_sdk_showicon", "drawable");
    public static int yyjia_sdk_sina = R.getResourcesId("yyjia_sdk_sina", "drawable");
    public static int yyjia_sdk_three = R.getResourcesId("yyjia_sdk_three", "drawable");
    public static int yyjia_sdk_toastbackground = R.getResourcesId("yyjia_sdk_toastbackground", "drawable");
    public static int yyjia_sdk_toolbaricon = R.getResourcesId("yyjia_sdk_toolbaricon", "drawable");
    public static int yyjia_sdk_toolbaricon_bbs = R.getResourcesId("yyjia_sdk_toolbaricon_bbs", "drawable");
    public static int yyjia_sdk_toolbaricon_bg = R.getResourcesId("yyjia_sdk_toolbaricon_bg", "drawable");
    public static int yyjia_sdk_toolbaricon_bg_left = R.getResourcesId("yyjia_sdk_toolbaricon_bg_left", "drawable");
    public static int yyjia_sdk_toolbaricon_bg_right = R.getResourcesId("yyjia_sdk_toolbaricon_bg_right", "drawable");
    public static int yyjia_sdk_toolbaricon_click = R.getResourcesId("yyjia_sdk_toolbaricon_click", "drawable");
    public static int yyjia_sdk_toolbaricon_end = R.getResourcesId("yyjia_sdk_toolbaricon_end", "drawable");
    public static int yyjia_sdk_toolbaricon_iconleft = R.getResourcesId("yyjia_sdk_toolbaricon_iconleft", "drawable");
    public static int yyjia_sdk_toolbaricon_iconright = R.getResourcesId("yyjia_sdk_toolbaricon_iconright", "drawable");
    public static int yyjia_sdk_toolbaricon_left = R.getResourcesId("yyjia_sdk_toolbaricon_left", "drawable");
    public static int yyjia_sdk_toolbaricon_logo = R.getResourcesId("yyjia_sdk_toolbaricon_logo", "drawable");
    public static int yyjia_sdk_toolbaricon_manage = R.getResourcesId("yyjia_sdk_toolbaricon_manage", "drawable");
    public static int yyjia_sdk_toolbaricon_moreapp = R.getResourcesId("yyjia_sdk_toolbaricon_moreapp", "drawable");
    public static int yyjia_sdk_toolbaricon_paylog = R.getResourcesId("yyjia_sdk_toolbaricon_paylog", "drawable");
    public static int yyjia_sdk_toolbaricon_right = R.getResourcesId("yyjia_sdk_toolbaricon_right", "drawable");
    public static int yyjia_sdk_toolbaricon_rightend = R.getResourcesId("yyjia_sdk_toolbaricon_rightend", "drawable");
    public static int yyjia_sdk_two = R.getResourcesId("yyjia_sdk_two", "drawable");
    public static int yyjia_sdk_unchecked = R.getResourcesId("yyjia_sdk_unchecked", "drawable");
    public static int yyjia_sdk_unpaychecked = R.getResourcesId("yyjia_sdk_unpaychecked", "drawable");
    public static int yyjia_sdk_uparrow = R.getResourcesId("yyjia_sdk_uparrow", "drawable");
    public static int yyjia_sdk_user = R.getResourcesId("yyjia_sdk_user", "drawable");
  }

  public static class id {
    public static int Init = R.getResourcesId("Init", "id");
    public static int Login = R.getResourcesId("Login", "id");
    public static int Logout = R.getResourcesId("Logout", "id");
    public static int Pay = R.getResourcesId("Pay", "id");
    public static int imageView = R.getResourcesId("imageView", "id");
    public static int yyjia_sdk_btn_back = R.getResourcesId("yyjia_sdk_btn_back", "id");
    public static int yyjia_sdk_btn_pay = R.getResourcesId("yyjia_sdk_btn_pay", "id");
    public static int yyjia_sdk_imageView1 = R.getResourcesId("yyjia_sdk_imageView1", "id");
    public static int yyjia_sdk_progress_info = R.getResourcesId("yyjia_sdk_progress_info", "id");
    public static int yyjia_sdk_relativeLayout1 = R.getResourcesId("yyjia_sdk_relativeLayout1", "id");
    public static int yyjia_sdk_rlimg = R.getResourcesId("yyjia_sdk_rlimg", "id");
    public static int yyjia_sdk_tv_goodsName = R.getResourcesId("yyjia_sdk_tv_goodsName", "id");
    public static int yyjia_sdk_tv_goodsName1 = R.getResourcesId("yyjia_sdk_tv_goodsName1", "id");
    public static int yyjia_sdk_tv_goodsNames = R.getResourcesId("yyjia_sdk_tv_goodsNames", "id");
    public static int yyjia_sdk_tv_hx = R.getResourcesId("yyjia_sdk_tv_hx", "id");
    public static int yyjia_sdk_tv_paymoney = R.getResourcesId("yyjia_sdk_tv_paymoney", "id");
    public static int yyjia_sdk_tv_paymoney1 = R.getResourcesId("yyjia_sdk_tv_paymoney1", "id");
    public static int yyjia_sdk_tv_paymoneys = R.getResourcesId("yyjia_sdk_tv_paymoneys", "id");
    public static int yyjia_sdk_tv_rmb = R.getResourcesId("yyjia_sdk_tv_rmb", "id");
    public static int yyjia_sdk_tv_rmb1 = R.getResourcesId("yyjia_sdk_tv_rmb1", "id");
    public static int yyjia_sdk_tv_rmbs = R.getResourcesId("yyjia_sdk_tv_rmbs", "id");
    public static int yyjia_sdk_tv_spm = R.getResourcesId("yyjia_sdk_tv_spm", "id");
    public static int yyjia_sdk_tv_user = R.getResourcesId("yyjia_sdk_tv_user", "id");
    public static int yyjia_sdk_tv_user1 = R.getResourcesId("yyjia_sdk_tv_user1", "id");
    public static int yyjia_sdk_tv_users = R.getResourcesId("yyjia_sdk_tv_users", "id");
    public static int yyjia_sdk_tv_useyue = R.getResourcesId("yyjia_sdk_tv_useyue", "id");
    public static int yyjia_sdk_tv_yue = R.getResourcesId("yyjia_sdk_tv_yue", "id");
    public static int yyjia_sdk_tv_yue1 = R.getResourcesId("yyjia_sdk_tv_yue1", "id");
    public static int yyjia_sdk_tv_yues = R.getResourcesId("yyjia_sdk_tv_yues", "id");
    public static int yyjia_sdk_update_progress = R.getResourcesId("yyjia_sdk_update_progress", "id");
  }

  public static class layout {
    public static int activity_yyjia = R.getResourcesId("activity_yyjia", "layout");
    public static int yyjia_sdk_activity_main2 = R.getResourcesId("yyjia_sdk_activity_main2", "layout");
    public static int yyjia_sdk_activity_main2_hp = R.getResourcesId("yyjia_sdk_activity_main2_hp", "layout");
    public static int yyjia_sdk_softupdate_progress = R.getResourcesId("yyjia_sdk_softupdate_progress", "layout");
  }

  public static class string {
    public static int action_settings = R.getResourcesId("action_settings", "layout");
    public static int app_name = R.getResourcesId("app_name", "layout");
    public static int hello_world = R.getResourcesId("hello_world", "layout");
    public static int title_activity_yyjia = R.getResourcesId("title_activity_yyjia", "layout");
  }

  public static class style
  {
    public static int AppBaseTheme = R.getResourcesId("AppBaseTheme", "style");

    public static int AppTheme = R.getResourcesId("AppTheme", "style");

    public static int yyjia_sdk_AppBaseTheme = R.getResourcesId("yyjia_sdk_AppBaseTheme", "style");

    public static int yyjia_sdk_AppTheme = R.getResourcesId("yyjia_sdk_AppTheme", "style");
    public static int yyjia_sdk_MyAnim = R.getResourcesId("yyjia_sdk_MyAnim", "style");
    public static int yyjia_sdk_PopupAnimation = R.getResourcesId("yyjia_sdk_PopupAnimation", "style");
    public static int yyjia_sdk_baiwenStyle = R.getResourcesId("yyjia_sdk_baiwenStyle", "style");

    public static int yyjia_sdk_processDialog = R.getResourcesId("yyjia_sdk_processDialog", "style");
  }
}