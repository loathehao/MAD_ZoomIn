package mad.android.com.mad;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import mad.android.com.mad.adapter.MovieAdapter;

public class MyfavoriteActivity extends Activity {

    private RecyclerView recyclerView;
    private MovieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myfavorite_activity);

        initViews();

    }

    private void initViews() {
        ImageButton titleImv = (ImageButton) findViewById(R.id.title_imv);
        titleImv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyfavoriteActivity.this.finish();
            }
        });
        TextView titleCenterTv = (TextView) findViewById(R.id.title_center_text_tv);
        titleCenterTv.setText("喜爱电影");
        recyclerView = findViewById(R.id.myfavorite_movie);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MovieAdapter(this);
        recyclerView.setAdapter(adapter);

    }

}
