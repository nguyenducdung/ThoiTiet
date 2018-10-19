package t3h.nguyenducdung.com.thoitiet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etSearch;
    private Button btnSearch;
    private TextView tvName;
    private TextView tvCountry;
    private TextView tvTemp;
    private TextView tvStatus;
    private TextView tvHumidity;
    private TextView tvCloud;
    private TextView tvWind;
    private TextView tvDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        btnSearch.setOnClickListener(this);
    }

    private void init() {
        etSearch = findViewById(R.id.et_search_city);
        btnSearch = findViewById(R.id.btn_search);
        tvName = findViewById(R.id.tv_name);
        tvCountry = findViewById(R.id.tv_country);
        tvTemp = findViewById(R.id.tv_temp);
        tvStatus = findViewById(R.id.tv_status);
        tvHumidity = findViewById(R.id.tv_humidity);
        tvCloud = findViewById(R.id.tv_cloud);
        tvWind = findViewById(R.id.tv_wind);
        tvDay = findViewById(R.id.tv_day);

    }

    public void GetCurrentWeatherData(String data){
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        String url = "https://api.openweathermap.org/data/2.5/weather?q="+data+"&units=metric&appid=c07e9ea81ad96884319ba60984d19393";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String day = jsonObject.getString("dt");
                    String name = jsonObject.getString("name");
                    tvName.setText("Ten Thanh Pho"+ name);

                    long l = Long.valueOf(day);
                    Date date = new Date(l*1000L);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd");
                    String Day = simpleDateFormat.format(date);

                    tvDay.setText(Day);
                    JSONArray jsonArrayWeather = jsonObject.getJSONArray("weather");
                    JSONObject jsonObject1Weather = jsonArrayWeather.getJSONObject(0);
                    String status = jsonObject1Weather.getString("main");
                    tvStatus.setText(status);

                    JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
                    String nhietdo = jsonObjectMain.getString("temp");
                    String doam = jsonObjectMain.getString("humidity");

                    Double a = Double.valueOf(nhietdo);
                    String nhietDo = String.valueOf(a.intValue());

                    tvTemp.setText(nhietDo+" C");
                    tvHumidity.setText(doam+"%");

                    JSONObject jsonObjectWind = jsonObject.getJSONObject("wind");
                    String gio = jsonObjectWind.getString("speed");
                    tvWind.setText(gio+" m/s");

                    JSONObject jsonObjectCloud = jsonObject.getJSONObject("clouds");
                    String may = jsonObjectCloud.getString("all");
                    tvCloud.setText(may+"%");

                    JSONObject jsonObjectSys = jsonObject.getJSONObject("sys");
                    String country = jsonObjectSys.getString("country");
                    tvCountry.setText("Quoc Gia: "+ country);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        String city = etSearch.getText().toString();
        GetCurrentWeatherData(city);
    }
}
