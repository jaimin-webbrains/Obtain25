package com.obtain25.ui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import com.google.gson.Gson;
import com.obtain25.R;
import com.obtain25.api.RetrofitHelper;
import com.obtain25.model.SuccessModel;
import com.obtain25.model.login.LoginModel;
import com.obtain25.utils.AppPreferences;
import com.obtain25.utils.PrefUtils;
import com.obtain25.utils.ViewDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    protected ViewDialog viewDialog;
    EditText editText_Email, editText_Password;
    AppCompatButton btn_login;
    AppCompatTextView label_donthaveaccount, label_f_password;
    LoginModel loginModel;
    ImageView show_confirm;
    Dialog dialog;
    String updateToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        updateToken = AppPreferences.getToken(LoginActivity.this);

        loginModel = PrefUtils.getUser(LoginActivity.this);
        viewDialog = new ViewDialog(LoginActivity.this);
        viewDialog.setCancelable(false);
        try {
            loginModel = PrefUtils.getUser(LoginActivity.this);
            if (loginModel.getSessionData() != null) {
                Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                i.putExtra("updateToken", updateToken);
                i.putExtra("type", "1");
                startActivity(i);
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        editText_Email = findViewById(R.id.editText_Email);
        editText_Password = findViewById(R.id.editText_Password);
        show_confirm = findViewById(R.id.show_confirm);
        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        label_f_password = findViewById(R.id.label_f_password);
        label_f_password.setOnClickListener(this);
        label_donthaveaccount = findViewById(R.id.label_donthaveaccount);
        label_donthaveaccount.setOnClickListener(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        show_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowHidePass(v);
            }

            private void ShowHidePass(View v) {
                if (v.getId() == R.id.show_confirm) {

                    if (editText_Password.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                        ((ImageView) (v)).setImageResource(R.drawable.ic_baseline_remove_red_eye_24);

                        //Show Password
                        editText_Password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    } else {
                        ((ImageView) (v)).setImageResource(R.drawable.ic_baseline_visibility_off_24);

                        //Hide Password
                        editText_Password.setTransformationMethod(PasswordTransformationMethod.getInstance());

                    }
                }
            }
        });
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
            hashMap.put("user_tocken", updateToken + "");

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
                        i.putExtra("type", "0");
                        i.putExtra("updateToken", object.getSessionData().getUser_tocken());
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
            case R.id.label_f_password:
                forgotPassword();
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)

    private void forgotPassword() {
        dialog = new Dialog(LoginActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_forgot_password);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        final EditText editEmail = dialog.findViewById(R.id.editEmail);
        final Button btn_update = dialog.findViewById(R.id.btn_update);


        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Email = editEmail.getText().toString().trim();


                if (Email.isEmpty()) {
                    editEmail.setError("Email Required");
                    editEmail.requestFocus();
                    return;
                } else {

                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("email", Email + "");

                    Log.e("GAYA", hashMap + "");
                    showProgressDialog();
                    Call<SuccessModel> marqueCall = RetrofitHelper.createService(RetrofitHelper.Service.class).forgetpassword(hashMap);
                    marqueCall.enqueue(new Callback<SuccessModel>() {
                        @Override
                        public void onResponse(@NonNull Call<SuccessModel> call, @NonNull Response<SuccessModel> response) {
                            SuccessModel object = response.body();
                            hideProgressDialog();
                            if (object != null && object.getError() == false) {
                                Toast.makeText(LoginActivity.this, object.getMsg() + "", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            } else if (object != null && object.getError() == true) {
                                Toast.makeText(LoginActivity.this, object.getMsg() + "", Toast.LENGTH_SHORT).show();

                                dialog.dismiss();
                            } else {
                                JSONObject jObjError = null;
                                try {
                                    jObjError = new JSONObject(response.errorBody().string());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    Log.e("TAG", "PO=> Error " + jObjError.getJSONObject("errors") + "");
                                    Toast.makeText(LoginActivity.this, jObjError.getJSONObject("errors") + "", Toast.LENGTH_LONG).show();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                try {

                                } catch (Exception e) {
                                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<SuccessModel> call, @NonNull Throwable t) {
                            t.printStackTrace();
                            hideProgressDialog();
                            Log.e("ChatV_Response", t.getMessage() + "");
                        }
                    });
                }

            }
        });

        ((ImageButton) dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

}
