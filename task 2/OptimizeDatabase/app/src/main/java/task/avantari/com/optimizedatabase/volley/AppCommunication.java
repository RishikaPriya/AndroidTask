package task.avantari.com.optimizedatabase.volley;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class AppCommunication {

    private static AppCommunication instance = null;
    private Context context;
    private static RequestQueue requestQueue;

    private AppCommunication(Context context){
        this.context = context;
    }

    public static AppCommunication getInstance(Context context){
        if(instance == null){
            instance = new AppCommunication(context);
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return instance;
    }

    public void makeStringRequest(String url,ResponseListener listener){
        StringRequest request = new StringRequest(Request.Method.GET,url,listener,listener);
        requestQueue.add(request);
    }

}
