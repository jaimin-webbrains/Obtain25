package com.obtain25.ui;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import com.google.gson.Gson;
import com.obtain25.R;
import com.obtain25.api.RetrofitHelper;
import com.obtain25.model.SuccessModel;
import com.obtain25.utils.AppPreferences;
import com.obtain25.utils.ViewDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {


    protected ViewDialog viewDialog;
    AppCompatEditText editText_Address, editText_CPassword, editText_Password, editText_Food, editText_Adhar, editText_GST, editText_Mobile, editText_Email, editText_Person, editText_Name;
    AppCompatButton btn_signUp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        viewDialog = new ViewDialog(RegisterActivity.this);
        viewDialog.setCancelable(false);
        editText_Address = findViewById(R.id.editText_Address);
        editText_CPassword = findViewById(R.id.editText_CPassword);
        editText_Password = findViewById(R.id.editText_Password);
        editText_Food = findViewById(R.id.editText_Food);
        editText_Adhar = findViewById(R.id.editText_Adhar);
        editText_GST = findViewById(R.id.editText_GST);
        editText_Mobile = findViewById(R.id.editText_Mobile);
        editText_Email = findViewById(R.id.editText_Email);
        editText_Person = findViewById(R.id.editText_Person);
        editText_Name = findViewById(R.id.editText_Name);
        btn_signUp = findViewById(R.id.btn_signUp);
        btn_signUp.setOnClickListener(this);

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

            case R.id.btn_signUp:
                SignUpCall();
                break;
        }
    }


    public void SignUpCall() {
        final String editTextName = editText_Name.getText().toString().trim();
        final String person_name = editText_Person.getText().toString().trim();
        final String email = editText_Email.getText().toString().trim();
        final String mobile = editText_Mobile.getText().toString().trim();
        final String gst = editText_GST.getText().toString().trim();
        final String adhar = editText_Adhar.getText().toString().trim();
        final String food = editText_Food.getText().toString().trim();
        final String pass_word = editText_Password.getText().toString().trim();
        final String c_password = editText_CPassword.getText().toString().trim();
        final String address = editText_Address.getText().toString().trim();


        if (editTextName.isEmpty()) {
            editText_Name.setError("Restaurant Name Required");
            editText_Name.requestFocus();
            return;
        } else if (person_name.isEmpty()) {
            editText_Person.setError("Person Name Required");
            editText_Person.requestFocus();
            return;
        } else if (!email.matches(("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+"))) {
            editText_Email.setError("Invalid Email");
            editText_Email.requestFocus();
            return;
        } else if (mobile.isEmpty()) {
            editText_Mobile.setError("Mobile Required");
            editText_Mobile.requestFocus();
            return;
        } else if (gst.isEmpty()) {
            editText_GST.setError("GST Number Required");
            editText_GST.requestFocus();
            return;
        } else if (adhar.isEmpty()) {
            editText_Adhar.setError("Adhar Card Number Required");
            editText_Adhar.requestFocus();
            return;
        } else if (food.isEmpty()) {
            editText_Food.setError("Food License Number Required");
            editText_Food.requestFocus();
            return;
        } else if (pass_word.isEmpty()) {
            editText_Password.setError("Password Required");
            editText_Password.requestFocus();
            return;
        } else if (c_password.isEmpty()) {
            editText_CPassword.setError("Confirm Password Required");
            editText_CPassword.requestFocus();
            return;
        } else if (address.isEmpty()) {
            editText_Address.setError("Address Required");
            editText_Address.requestFocus();
            return;
        } else {
            HashMap<String, String> hashMap = new HashMap<>();

            hashMap.put("name", editTextName + "");
            hashMap.put("contact_person", person_name + "");
            hashMap.put("gst", gst + "");
            hashMap.put("adharcard", adhar + "");
            hashMap.put("mobile", mobile + "");
            hashMap.put("email", email + "");
            hashMap.put("password", pass_word + "");
            hashMap.put("cpassword", c_password + "");
            hashMap.put("address", address + "");
            hashMap.put("food_license", food + "");
            hashMap.put("latitude", AppPreferences.getLati(this) + "");
            hashMap.put("longitude", AppPreferences.getLongi(this) + "");


            Log.e("HasMap =>", hashMap + "");
            showProgressDialog();
            Call<SuccessModel> viewBillingInfoModelCall = RetrofitHelper.createService(RetrofitHelper.Service.class).RegisterModel(hashMap);
            viewBillingInfoModelCall.enqueue(new Callback<SuccessModel>() {

                @Override
                public void onResponse(@NonNull Call<SuccessModel> call, @NonNull Response<SuccessModel> response) {
                    SuccessModel object = response.body();

                    hideProgressDialog();
                    if (object != null && object.getError() == false) {
                        Log.e("TAG", "PO_Response : " + new Gson().toJson(response.body()));

                        Toast.makeText(RegisterActivity.this, object.getMsg() + "", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(i);
                        finish();
                    } else if (object != null && object.getError() == true) {
                        Toast.makeText(RegisterActivity.this, object.getMsg(), Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(RegisterActivity.this, jObjError.getJSONObject("errors") + "", Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }

                @Override
                public void onFailure(@NonNull Call<SuccessModel> call, @NonNull Throwable t) {
                    hideProgressDialog();
                    t.printStackTrace();
                    Log.e("PO_Response", t.getMessage() + "");
                }
            });
        }

    }
}












