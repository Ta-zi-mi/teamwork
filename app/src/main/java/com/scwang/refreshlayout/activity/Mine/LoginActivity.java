package com.scwang.refreshlayout.activity.Mine;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.scwang.refreshlayout.R;
import com.scwang.refreshlayout.fragment.index.ItemsView;

public class LoginActivity extends Activity {
  private EditText mUsernameView;
  private EditText mPasswordView;
  private View mProgressView;
  private View mLoginFormView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login2);

    if (AVUser.getCurrentUser() != null) {
      //startActivity(new Intent(LoginActivity.this, MainActivity.class));
      LoginActivity.this.finish();
    }

//    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//    getSupportActionBar().setTitle("登录");
    mUsernameView = (EditText) findViewById(R.id.username);

    mPasswordView = (EditText) findViewById(R.id.password);
    mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
      @Override
      public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
        if (id == R.id.login || id == EditorInfo.IME_NULL) {
          attemptLogin();
          return true;
        }
        return false;
      }
    });

    Button mUsernameLoginButton = (Button) findViewById(R.id.username_login_button);
    mUsernameLoginButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        attemptLogin();
      }
    });

    Button mUsernameRegisterButton = (Button) findViewById(R.id.username_register_button);
    mUsernameRegisterButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        LoginActivity.this.finish();
      }
    });

    mLoginFormView = findViewById(R.id.login_form);
    mProgressView = findViewById(R.id.login_progress);
  }

  private void attemptLogin() {
    mUsernameView.setError(null);
    mPasswordView.setError(null);

    final String username = mUsernameView.getText().toString();
    final String password = mPasswordView.getText().toString();

    boolean cancel = false;
    View focusView = null;

    if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
      mPasswordView.setError("密码不少于5位");
      focusView = mPasswordView;
      cancel = true;
    }
    if (!isUsernameValid(username))
    {
      mUsernameView.setError("用户名只能包含字母、数字、下划线，不能以数字开头");
      focusView = mUsernameView;
      cancel = true;
    }

    if (TextUtils.isEmpty(username)) {
      mUsernameView.setError("这个是必填项");
      focusView = mUsernameView;
      cancel = true;
    }

    if (cancel) {
      focusView.requestFocus();
    } else {
      showProgress(true);

      AVUser.logInInBackground(username, password, new LogInCallback<AVUser>() {
        @Override
        public void done(AVUser avUser, AVException e) {
          if (e == null) {
            LoginActivity.this.finish();
            //startActivity(new Intent(LoginActivity.this,.class));
          } else {
            showProgress(false);
            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
          }
        }
      });
    }
  }


  private boolean isPasswordValid(String password) {
    //TODO: Replace this with your own logic
    return password.length() > 4;
  }
  public static boolean isUsernameValid(String str){
    return str.matches("^[a-zA-Z][a-zA-Z0-9_]*$");
  }

  /**
   * Shows the progress UI and hides the login form.
   */
  @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
  private void showProgress(final boolean show) {
    // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
    // for very easy animations. If available, use these APIs to fade-in
    // the progress spinner.
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
      int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

      mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
      mLoginFormView.animate().setDuration(shortAnimTime).alpha(
              show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
          mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
      });

      mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
      mProgressView.animate().setDuration(shortAnimTime).alpha(
              show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
          mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        }
      });
    } else {
      // The ViewPropertyAnimator APIs are not available, so simply show
      // and hide the relevant UI components.
      mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
      mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
    }
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      onBackPressed();
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  protected void onPause() {
    super.onPause();
    AVAnalytics.onPause(this);
  }

  @Override
  protected void onResume() {
    super.onResume();
    AVAnalytics.onResume(this);
  }
}

