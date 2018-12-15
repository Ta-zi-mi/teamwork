package com.scwang.refreshlayout.activity.Task;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.avos.avoscloud.AVUser;
import com.scwang.refreshlayout.R;
import com.scwang.refreshlayout.activity.Mine.LoginActivity;
import com.scwang.refreshlayout.activity.Mine.RegisterActivity;
import com.scwang.refreshlayout.util.StatusBarUtil;

/**
 * 个人中心
 */
public class ProfilePracticeActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_profile);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //状态栏透明和间距处理
        StatusBarUtil.immersive(this);
        StatusBarUtil.setPaddingSmart(this, toolbar);
        StatusBarUtil.setPaddingSmart(this, findViewById(R.id.profile));
        StatusBarUtil.setPaddingSmart(this, findViewById(R.id.blurView));

        Button mLogOutButton = (Button) findViewById(R.id.logout);
        mLogOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AVUser.logOut();
                startActivity(new Intent(ProfilePracticeActivity.this, LoginActivity.class));
                ProfilePracticeActivity.this.finish();
            }
        });
    }

}
