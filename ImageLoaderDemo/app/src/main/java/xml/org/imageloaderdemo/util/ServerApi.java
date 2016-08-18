package xml.org.imageloaderdemo.util;

import android.content.Context;

import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.OnResponseListener;
import com.yolanda.nohttp.Request;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.RequestQueue;

import org.json.JSONObject;

/**
 * Created by Administrator on 2016/8/18.
 */
public class ServerApi {
    public static String URL = "http://192.168.1.210:8080/Today/";
    /**
     * 获取消息
     */
    public static String SELECTMESSAGE_URL = URL + "message_select.php";
    public static RequestQueue requestQueue;

    public static void init() {
        requestQueue = NoHttp.newRequestQueue();
    }
    /**
     * 获取消息通知
     *
     * @param key
     * @param context
     * @param onResponseListener
     */
    public static void getSelectMessageResult(int key, Context context, OnResponseListener onResponseListener) {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(SELECTMESSAGE_URL, RequestMethod.POST);
        request.add("user_id", "1");
        request.addHeader("Author", "nohttp_sample");
        ServerApi.requestQueue.add(key, request, onResponseListener);
    }
}
