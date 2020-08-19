package com.obtain25.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import com.google.gson.Gson;
import com.obtain25.R;
import com.obtain25.api.RetrofitHelper;
import com.obtain25.model.login.LoginModel;
import com.obtain25.utils.PrefUtils;
import com.obtain25.utils.ViewDialog;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    protected ViewDialog viewDialog;
    EditText editText_Email, editText_Password;
    AppCompatButton btn_login;
    AppCompatTextView label_donthaveaccount;
    LoginModel loginModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginModel = PrefUtils.getUser(LoginActivity.this);
        viewDialog = new ViewDialog(LoginActivity.this);
        viewDialog.setCancelable(false);
        try {
            loginModel = PrefUtils.getUser(LoginActivity.this);
            if (loginModel.getSessionData() != null) {
                Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(i);
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        editText_Email = findViewById(R.id.editText_Email);
        editText_Password = findViewById(R.id.editText_Password);
        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        label_donthaveaccount = findViewById(R.id.label_donthaveaccount);
        label_donthaveaccount.setOnClickListener(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    }

    public void LoginCall() {

        final String email_id = editText_Email.getText().toString().trim();
        final String pass_word = editText_Password.getText().toString().trim();


        if (!email_id.matches(("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+"))) {
            editText_Email.setError("Invalid Email");
            editText_Email.requestFocus();
            return;
        } else if (pass_word.isEmpty()) {
            editText_Password.setError("Password Required");
            editText_Password.requestFocus();
            return;
        } else {
            HashMap<String, String> hashMap = new HashMap<>();

            hashMap.put("email", email_id + "");
            hashMap.put("password", pass_word + "");

            showProgressDialog();
            Call<LoginModel> loginModelCall = RetrofitHelper.createService(RetrofitHelper.Service.class).LoginModel(hashMap);
            loginModelCall.enqueue(new Callback<LoginModel>() {

                @Override
                public void onResponse(@NonNull Call<LoginModel> call, @NonNull Response<LoginModel> response) {
                    LoginModel object = response.body();
                    hideProgressDialog();

                    if (object != null && object.getError() == false) {
                        Log.e("TAG", "Login_Response : " + new Gson().toJson(response.body()));

                        PrefUtils.setUser(object, LoginActivity.this);

                        Toast.makeText(LoginActivity.this, object.getMessage(), Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(i);
                        finish();


                    } else if (object != null && object.getError() == true) {
                        Toast.makeText(LoginActivity.this, object.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<LoginModel> call, @NonNull Throwable t) {
                    hideProgressDialog();
                    t.printStackTrace();
                    Log.e("Login_Response", t.getMessage() + "");
                }
            });
        }
    }

    protected void hideProgressDialog() {
        viewDialog.dismiss();
    }

    protected void showProgressDialog() {
        viewDialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                LoginCall();
                break;
            case R.id.label_donthaveaccount:
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
                break;
        }
    }
}
