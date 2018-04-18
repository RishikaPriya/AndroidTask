package task.avantari.com.optimizedatabase.volley;

import com.android.volley.Response;
import com.android.volley.VolleyError;

public interface ResponseListener extends Response.Listener<String>,Response.ErrorListener {

    @Override
    void onResponse(String response);

    @Override
    void onErrorResponse(VolleyError error);

    void onDone();
}
