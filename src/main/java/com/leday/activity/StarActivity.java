package com.leday.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.leday.R;
import com.leday.Util.MySingleton;

import org.json.JSONException;
import org.json.JSONObject;

public class StarActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mImg;
    private TextView mTxt;
    private Button mBtnToday, mBtnWeek, mBtnTomorrow, mBtnNextweek;

    private String localstar;
    private String localtime = "today";
    private static final String URL = "http://web.juhe.cn:8080/constellation/getAll?key=c86828899c7c2b9cd39281ee48f90105&consName=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star);

        initView();
        getJson();
    }

    //    初始化控件
    private void initView() {
        Intent intent = getIntent();
        localstar = intent.getStringExtra("star");

        mImg = (ImageView) findViewById(R.id.img_star_title);
        mTxt = (TextView) findViewById(R.id.txt_star_content);
        mBtnToday = (Button) findViewById(R.id.btn_star_today);
        mBtnWeek = (Button) findViewById(R.id.btn_star_week);
        mBtnTomorrow = (Button) findViewById(R.id.btn_star_tomorrow);
        mBtnNextweek = (Button) findViewById(R.id.btn_star_nextweek);

        mBtnToday.setOnClickListener(this);
        mBtnWeek.setOnClickListener(this);
        mBtnTomorrow.setOnClickListener(this);
        mBtnNextweek.setOnClickListener(this);
    }

    private void getJson() {
        StringRequest starRequest = new StringRequest(Request.Method.GET, URL + localstar + "&type=" + localtime, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject obj;
                try {
                    obj = new JSONObject(response);
//                    今日或明日的数据处理
                    if (localtime.equals("today") || localtime.equals("tomorrow")) {
                        String j_datetime = obj.getString("datetime");
                        String j_color = obj.getString("color");
                        String j_number = obj.getString("number");
                        String j_QFriend = obj.getString("QFriend");
                        String j_love = obj.getString("love");
                        String j_money = obj.getString("money");
                        String j_summary = obj.getString("summary");
                        String all = j_datetime + "\n幸运色: " + j_color + "\n幸运数: " + j_number + "\n幸运星座: " + j_QFriend +
                                "\n爱情指数: " + j_love + "\r\r\r\r\r\r\r\r财运指数: " + j_money + "\n\n总星势: " + j_summary;
                        mTxt.setText(all);
                    }
//                    本周或下周的数据处理
                    else if (localtime.equals("week") || localtime.equals("nextweek")) {
                        String j_date = obj.getString("date");
                        String j_health = obj.getString("health");
                        String j_job = obj.getString("job");
                        String j_love = obj.getString("love");
                        String j_money = obj.getString("money");
                        String j_work = obj.getString("work");
                        String all = j_date + "\n" + j_health + "\n" + j_job + "\n" +
                                "\n" + j_love + "\n" + j_money + "\n" + j_work;
                        mTxt.setText(all);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(StarActivity.this, volleyError + "", Toast.LENGTH_SHORT).show();
            }
        });
        starRequest.setTag("GET");
        MySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(starRequest);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_star_today:
                localtime = "today";
                getJson();
                break;
            case R.id.btn_star_week:
                localtime = "week";
                getJson();
                break;
            case R.id.btn_star_tomorrow:
                localtime = "tomorrow";
                getJson();
                break;
            case R.id.btn_star_nextweek:
                localtime = "nextweek";
                getJson();
                break;
        }
    }
}