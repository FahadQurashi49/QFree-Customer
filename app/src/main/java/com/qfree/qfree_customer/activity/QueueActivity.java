package com.qfree.qfree_customer.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.qfree.qfree_customer.R;
import com.qfree.qfree_customer.model.Queue;

import java.io.Serializable;

public class QueueActivity extends AppCompatActivity {

    private TextView queueNameTextView;

    private Queue queue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queue);

        queue = (Queue) getIntent().getSerializableExtra("selected_queue");

        queueNameTextView = (TextView) findViewById(R.id.tv_queue_name);

        if (queue.getRunning()) {
            queueNameTextView.setText(queue.getName());
        } else {
            Toast.makeText(getApplicationContext(), "Queue not running", Toast.LENGTH_SHORT).show();
        }
    }
}
