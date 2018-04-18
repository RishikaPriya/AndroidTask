package task.avantari.com.optimizedatabase;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import task.avantari.com.optimizedatabase.model.Dictionary;
import task.avantari.com.optimizedatabase.volley.AppCommunication;
import task.avantari.com.optimizedatabase.volley.ResponseListener;


public class MainActivity extends AppCompatActivity {

    private AppCommunication communication;
    int noOfRequest = 26;
    private Context context;
    ProgressDialog progressDialog;
    private long[] exec_time = new long[26];
    private long total_exec_time = 0;
    TextView min_time_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        communication = AppCommunication.getInstance(this);
        context = this;
        progressDialog = new ProgressDialog(MainActivity.this);
        min_time_view = findViewById(R.id.time_view);
    }

    public void updateView(View view) {
        ApplicationDatabase.getAppDB(context).dictionaryDao().deleteTable();
        showProgressDialog();
        for(char c='a'; c<='z';c++){
            communication.makeStringRequest("http://unreal3112.16mb.com/wb1913_" + c + ".html",
                    new ResponseListener() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("response", response);
                            long currentTime = System.currentTimeMillis();
                            Document document = Jsoup.parse(response);
                            Elements paragraph = document.select("P");
                            Dictionary[] dictionaries = new Dictionary[paragraph.size()];
                            for(int i = 0; i<paragraph.size(); i++){
                                Element p = paragraph.get(i);
                                Dictionary dictionary = new Dictionary();
                                dictionary.setLine(p.text());
                                dictionaries[i] = dictionary;
                            }
                            if(dictionaries.length>0){
                                ApplicationDatabase.getAppDB(context).dictionaryDao().insertAll(dictionaries);
                            }
                            exec_time[noOfRequest-1] = System.currentTimeMillis()-currentTime;
                            total_exec_time += exec_time[noOfRequest-1];
                            Log.d("exec_time: ", String.valueOf(exec_time[noOfRequest-1]));
                            onDone();
                        }

                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }

                        @Override
                        public void onDone() {
                            progressDialog.setProgress((27 - noOfRequest)*4);
                            noOfRequest--;
                            if(noOfRequest==0){
                                callOnDone();
                            }
                        }
                    });
        }
    }

    private void callOnDone() {
        progressDialog.dismiss();
        Arrays.sort(exec_time);
        min_time_view.setText(getString(R.string.exec_time) + exec_time[0] + " ms");
        Log.d("Total Time:",String.valueOf(total_exec_time));
    }

    private void showProgressDialog() {
        if(!progressDialog.isShowing())
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setProgressNumberFormat(null);
        progressDialog.setProgressPercentFormat(null);
        progressDialog.setMessage("Download In Progress");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void displayData(View v) {
        List<Dictionary> retrievedData = ApplicationDatabase.getAppDB(context).dictionaryDao().getAll();
        List<String> stringList = new ArrayList<>();

        for(Dictionary dictionary : retrievedData){
            stringList.add(dictionary.getLine());
        }

        ListView listView = findViewById(R.id.list_view);

        ArrayAdapter adapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, stringList);
        listView.setAdapter(adapter);
    }

}
