package com.hyphenate.easeui.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.domain.EaseEmojicon;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.model.EaseNotifier;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

public final class EaseUI {
    private static final String TAG = EaseUI.class.getSimpleName();

    /**
     * the global EaseUI instance
     */
    private static EaseUI instance = null;
    
    /**
     * 鐢ㄦ埛灞炴�鎻愪緵鑰�     */
    private EaseUserProfileProvider userProvider;
    
    private EaseSettingsProvider settingsProvider;
    
    /**
     * application context
     */
    private Context appContext = null;
    
    /**
     * init flag: test if the sdk has been inited before, we don't need to init again
     */
    private boolean sdkInited = false;
    
    /**
     * the notifier
     */
    private EaseNotifier notifier = null;
    
    /**
     * 鐢ㄦ潵璁板綍娉ㄥ唽浜唀ventlistener鐨刦oreground Activity
     */
    private List<Activity> activityList = new ArrayList<Activity>();
    
    public void pushActivity(Activity activity){
        if(!activityList.contains(activity)){
            activityList.add(0,activity); 
        }
    }
    
    public void popActivity(Activity activity){
        activityList.remove(activity);
    }
    
    
    private EaseUI(){}
    
    /**
     * 鑾峰彇EaseUI鍗曞疄渚嬪璞�     * @return
     */
    public synchronized static EaseUI getInstance(){
        if(instance == null){
            instance = new EaseUI();
        }
        return instance;
    }
    
    /**
     *this function will initialize the HuanXin SDK
     * 
     * @return boolean true if caller can continue to call HuanXin related APIs after calling onInit, otherwise false.
     * 
     * 鍒濆鍖栫幆淇dk鍙奺aseui搴�     * 杩斿洖true濡傛灉姝ｇ‘鍒濆鍖栵紝鍚﹀垯false锛屽鏋滆繑鍥炰负false锛岃鍦ㄥ悗缁殑璋冪敤涓笉瑕佽皟鐢ㄤ换浣曞拰鐜俊鐩稿叧鐨勪唬鐮�     * @param context
     * @param options 鑱婂ぉ鐩稿叧鐨勮缃紝浼爊ull鍒欎娇鐢ㄩ粯璁ょ殑
     * @return
     */
    public synchronized boolean init(Context context, EMOptions options){
        if(sdkInited){
            return true;
        }
        appContext = context;
        
        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
        
        Log.d(TAG, "process app name : " + processAppName);
        
        if (processAppName == null || !processAppName.equalsIgnoreCase(appContext.getPackageName())) {
            Log.e(TAG, "enter the service process!");
            
            // 鍒欐application::onCreate 鏄service 璋冪敤鐨勶紝鐩存帴杩斿洖
            return false;
        }
        if(options == null){
            EMClient.getInstance().init(context, initChatOptions());
        }else{
            EMClient.getInstance().init(context, options);
        }
        
        initNotifier();
        
        if(settingsProvider == null){
            settingsProvider = new DefaultSettingsProvider();
        }
        
        sdkInited = true;
        return true;
    }
    
    protected EMOptions initChatOptions(){
        Log.d(TAG, "init HuanXin Options");
        
        // 鑾峰彇鍒癊MChatOptions瀵硅薄
        EMOptions options = new EMOptions();
        // 榛樿娣诲姞濂藉弸鏃讹紝鏄笉闇�楠岃瘉鐨勶紝鏀规垚闇�楠岃瘉
        options.setAcceptInvitationAlways(false);
        // 璁剧疆鏄惁闇�宸茶鍥炴墽
        options.setRequireAck(true);
        // 设置是否需要已送达回执
        options.setRequireDeliveryAck(false);
        
        return options;
//        notifier.setNotificationInfoProvider(getNotificationListener());
    }
    
    void initNotifier(){
        notifier = createNotifier();
        notifier.init(appContext);
    }
    
    protected EaseNotifier createNotifier(){
        return new EaseNotifier();
    }
    
    public EaseNotifier getNotifier(){
        return notifier;
    }
    
    public boolean hasForegroundActivies(){
        return activityList.size() != 0;
    }
    
    /**
     * 璁剧疆鐢ㄦ埛灞炴�鎻愪緵鑰�     * @param provider
     */
    public void setUserProfileProvider(EaseUserProfileProvider userProvider){
        this.userProvider = userProvider;
    }
    
    /**
     * 鑾峰彇鐢ㄦ埛灞炴�鎻愪緵鑰�     * @return
     */
    public EaseUserProfileProvider getUserProfileProvider(){
        return userProvider;
    }
    
    public void setSettingsProvider(EaseSettingsProvider settingsProvider){
        this.settingsProvider = settingsProvider;
    }
    
    public EaseSettingsProvider getSettingsProvider(){
        return settingsProvider;
    }
    
    
    /**
     * check the application process name if process name is not qualified, then we think it is a service process and we will not init SDK
     * @param pID
     * @return
     */
    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) appContext.getSystemService(Context.ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = appContext.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    CharSequence c = pm.getApplicationLabel(pm.getApplicationInfo(info.processName, PackageManager.GET_META_DATA));
                    // Log.d("Process", "Id: "+ info.pid +" ProcessName: "+
                    // info.processName +"  Label: "+c.toString());
                    // processName = c.toString();
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }
    
    /**
     * 鐢ㄦ埛灞炴�鎻愪緵鑰�     * @author wei
     *
     */
    public interface EaseUserProfileProvider {
        /**
         * 杩斿洖姝sername瀵瑰簲鐨剈ser
         * @param username 鐜俊id
         * @return
         */
        EaseUser getUser(String username);
    }
    
    /**
     * 琛ㄦ儏淇℃伅鎻愪緵鑰�     *
     */
    public interface EaseEmojiconInfoProvider {
        /**
         * 鏍规嵁鍞竴璇嗗埆鍙疯繑鍥炴琛ㄦ儏鍐呭
         * @param emojiconIdentityCode
         * @return
         */
        EaseEmojicon getEmojiconInfo(String emojiconIdentityCode);
        
        /**
         * 鑾峰彇鏂囧瓧琛ㄦ儏鐨勬槧灏凪ap,map鐨刱ey涓鸿〃鎯呯殑emoji鏂囨湰鍐呭锛寁alue涓哄搴旂殑鍥剧墖璧勬簮id鎴栬�鏈湴璺緞(涓嶈兘涓虹綉缁滃湴鍧�
         * @return
         */
        Map<String, Object> getTextEmojiconMapping();
    }
    
    private EaseEmojiconInfoProvider emojiconInfoProvider;
    
    /**
     * 鑾峰彇琛ㄦ儏鎻愪緵鑰�     * @return
     */
    public EaseEmojiconInfoProvider getEmojiconInfoProvider(){
        return emojiconInfoProvider;
    }
    
    /**
     * 璁剧疆琛ㄦ儏淇℃伅鎻愪緵鑰�     * @param emojiconInfoProvider
     */
    public void setEmojiconInfoProvider(EaseEmojiconInfoProvider emojiconInfoProvider){
        this.emojiconInfoProvider = emojiconInfoProvider;
    }
    
    /**
     * 鏂版秷鎭彁绀鸿缃殑鎻愪緵鑰�     *
     */
    public interface EaseSettingsProvider {
        boolean isMsgNotifyAllowed(EMMessage message);
        boolean isMsgSoundAllowed(EMMessage message);
        boolean isMsgVibrateAllowed(EMMessage message);
        boolean isSpeakerOpened();
    }
    
    /**
     * default settings provider
     *
     */
    protected class DefaultSettingsProvider implements EaseSettingsProvider{

        @Override
        public boolean isMsgNotifyAllowed(EMMessage message) {
            // TODO Auto-generated method stub
            return true;
        }

        @Override
        public boolean isMsgSoundAllowed(EMMessage message) {
            return true;
        }

        @Override
        public boolean isMsgVibrateAllowed(EMMessage message) {
            return true;
        }

        @Override
        public boolean isSpeakerOpened() {
            return true;
        }

        
    }
}
