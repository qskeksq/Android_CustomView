package com.example.administrator.customview3;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launchpad);
//
//        final RainBowProgress rainBowProgress = (RainBowProgress) findViewById(R.id.rainbow);
//        final RainbowDivider divider = (RainbowDivider) findViewById(R.id.divider);
//
//        Button button = (Button) findViewById(R.id.button6);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                handler.sendEmptyMessage(0);
//                handler.sendEmptyMessage(1);
//            }
//        });
//
//        handler = new Handler(){
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//                switch (msg.what){
//                    case 0:
//                        int curPos = rainBowProgress.getCurPos();
//                        if(curPos < rainBowProgress.getMAX()){
//                            rainBowProgress.setCurPos(curPos+1);
//                            handler.sendEmptyMessageDelayed(0, 10);
//                        } else {
//                            rainBowProgress.setCurPos(0);
//                        }
//                        break;
//                    case 1:
//                        int curPos2 = divider.getCurPos();
//                        if(curPos2 < divider.getMAX()){
//                            divider.setCurPos(curPos2+1);
//                            handler.sendEmptyMessageDelayed(0, 10);
//
//                        }
//                        break;
//                }
//            }
//        };

    }
}
