package video.player.mp4player.videoplayer.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Locale;

import video.player.mp4player.videoplayer.R;

import static com.android.volley.VolleyLog.TAG;

public class BlankFragment extends Fragment {
    public static Context mContext;
    public static ArrayAdapter<String> adapter;
    private static String JSON_URL = "";
    private static String search_string;
    private static String[] values;
    ListView listView;

    public static void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());

        search_string = charText;
        if (search_string.length() != 0) {
            loadTutorialList();
        }
        adapter.notifyDataSetChanged();
    }

    private static String method(String str) {
        if (str != null && str.length() > 0 && str.charAt(str.length() - 1) == ')') {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    private static void loadTutorialList() {
        JSON_URL = "http://suggestqueries.google.com/complete/search?client=youtube&ds=yt&q=" + search_string + "&type=json";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response_fack) {
                        String[] separated = response_fack.split("\\(");
                        String response_face2 = separated[1];
                        String response = method(response_face2);

                        try {

                            JSONArray arr = new JSONArray(response);
                            JSONArray tutorialsArray = arr.getJSONArray(0);
                            values = new String[tutorialsArray.length()];

                            for (int i = 0; i < tutorialsArray.length(); i++) {
                                JSONArray tutorialsObject = tutorialsArray.getJSONArray(0);
                                String string = String.valueOf(tutorialsObject);
                                values[i] = string;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof NetworkError) {
                } else if (error instanceof ServerError) {
                } else if (error instanceof AuthFailureError) {
                } else if (error instanceof ParseError) {
                } else if (error instanceof NoConnectionError) {
                } else if (error instanceof TimeoutError) {
                }
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        stringRequest.setTag(TAG);
        requestQueue.add(stringRequest);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        listView = (ListView) view.findViewById(R.id.list);
        mContext = getContext();


        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, values);


        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }

        });
        return view;
    }

}
