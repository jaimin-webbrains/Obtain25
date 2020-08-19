package com.obtain25.ui.home.uploadimage;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.obtain25.R;
import com.obtain25.api.BuildConstants;
import com.obtain25.api.RetrofitHelper;
import com.obtain25.api.video_model.ApiConfig;
import com.obtain25.api.video_model.AppConfig;
import com.obtain25.api.video_model.ServerResponse;
import com.obtain25.model.login.LoginModel;
import com.obtain25.model.uploadphoto.ResultDisplayRestoPhoto;
import com.obtain25.model.uploadphoto.ResultDisplayRestoPhoto_;
import com.obtain25.utils.PrefUtils;
import com.obtain25.utils.ViewDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class UploadRestoImageFragment extends Fragment {

    final static String[] PERMISSIONS = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private static final int PERMISSION_ALL = 1;
    public static Bitmap bitmap;
    protected ViewDialog viewDialog;
    LinearLayout l1View;
    AppCompatTextView txttext;
    LoginModel loginModel;
    FloatingActionButton fab;
    ImageView imgResro;
    private RecyclerView recycler_view;
    private MyCustomAdapter myCustomAdapter;
    private ArrayList<ResultDisplayRestoPhoto_> resultDisplayRestaurantCoupon_s = new ArrayList<>();
    private SwipeRefreshLayout contentHome_SwipeRefresh;
    private String mediaPathImage;

    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_upload_resto_image, container, false);
        loginModel = PrefUtils.getUser(getActivity());
        viewDialog = new ViewDialog(getContext());
        viewDialog.setCancelable(false);
        l1View = root.findViewById(R.id.l1View);
        fab = root.findViewById(R.id.fab);
        txttext = root.findViewById(R.id.txttext);
        imgResro = root.findViewById(R.id.imgResro);
        contentHome_SwipeRefresh = root.findViewById(R.id.contentHome_SwipeRefresh);
        contentHome_SwipeRefresh.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED);
        recycler_view = root.findViewById(R.id.viewProducts_RecyclerView);
        // LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recycler_view.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recycler_view.setHasFixedSize(true);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasPermissions(getActivity(), PERMISSIONS)) {
                    SelectImage();
                } else {
                    ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, PERMISSION_ALL);
                }
            }
        });
        UploadRestrorentList();
        contentHome_SwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                contentHome_SwipeRefresh.setRefreshing(false);
                UploadRestrorentList();


            }
        });
        return root;
    }

    private void SelectImage() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, 0);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {

            if (requestCode == 0 && resultCode == RESULT_OK && null != data) {

                // Get the Image from data
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mediaPathImage = cursor.getString(columnIndex);
                // Set the Image in ImageView for Previewing the Media

                    Map<String, RequestBody> map = new HashMap<>();
                    File file = null;


                    try {
                        file = new File(mediaPathImage);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    RequestBody requestBody = null;
                    try {
                        assert file != null;
                        requestBody = RequestBody.create(MediaType.parse("*/*"), file);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    RequestBody id = RequestBody.create(MediaType.parse("text/plain"), "" + loginModel.getSessionData().getId());

                    viewDialog.show();

                    if (file != null) {
                        map.put("resto_photo\"; filename=\"" + file.getName() + "\"", requestBody);
                        map.put("id", id);
                        Log.e("Params", map + "");
                        ApiConfig getResponse = AppConfig.getRetrofit().create(ApiConfig.class);
                        Call<ServerResponse> call = getResponse.RestomultiPhotoUpload("application/json", map);
                        call.enqueue(new Callback<ServerResponse>() {
                            @Override
                            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                                ServerResponse serverResponse = response.body();
                                if (serverResponse != null && serverResponse.getError() == false) {
                                    Log.e("TAG", "Product : " + new Gson().toJson(response.body()));
                                    UploadRestrorentList();
                                    Toast.makeText(getActivity(), serverResponse.getMsg(), Toast.LENGTH_SHORT).show();


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
                                        Toast.makeText(getActivity(), jObjError.getJSONObject("errors") + "", Toast.LENGTH_LONG).show();

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }


                                }
                                viewDialog.dismiss();
                            }

                            @Override
                            public void onFailure(Call<ServerResponse> call, Throwable t) {
                                viewDialog.dismiss();
                                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });

                    } else {

                    }


                cursor.close();

            } else {
                Toast.makeText(getActivity(), "You haven't picked Image", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void UploadRestrorentList() {
        txttext.setVisibility(View.GONE);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("resto_id", loginModel.getSessionData().getId() + "");

        showProgressDialog();
        Log.e("GAYA", hashMap + "");
        Call<ResultDisplayRestoPhoto> marqueCall = RetrofitHelper.createService(RetrofitHelper.Service.class).DisplayRestoPhoto(hashMap);
        marqueCall.enqueue(new Callback<ResultDisplayRestoPhoto>() {
            @Override
            public void onResponse(@NonNull Call<ResultDisplayRestoPhoto> call, @NonNull Response<ResultDisplayRestoPhoto> response) {
                ResultDisplayRestoPhoto object = response.body();
                hideProgressDialog();
                contentHome_SwipeRefresh.setRefreshing(false);
                Log.e("TAG", "ChatV_Response : " + new Gson().toJson(response.body()));
                if (object != null && object.getError() == false) {

                    resultDisplayRestaurantCoupon_s = object.getResultDisplayRestoPhoto();
                    myCustomAdapter = new MyCustomAdapter(resultDisplayRestaurantCoupon_s);
                    recycler_view.setAdapter(myCustomAdapter);

                } else if (object != null && object.getError() == true) {
                    // Toast.makeText(getActivity(), object.getMessage(), Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getActivity(), jObjError.getJSONObject("errors") + "", Toast.LENGTH_LONG).show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }

            @Override
            public void onFailure(@NonNull Call<ResultDisplayRestoPhoto> call, @NonNull Throwable t) {
                t.printStackTrace();
                hideProgressDialog();
                contentHome_SwipeRefresh.setRefreshing(false);
                Log.e("ChatV_Response", t.getMessage() + "");
            }
        });
    }

    protected void hideProgressDialog() {
        viewDialog.dismiss();
    }

    protected void showProgressDialog() {
        viewDialog.show();
    }

    public class MyCustomAdapter extends RecyclerView.Adapter<MyCustomAdapter.MyViewHolder> {

        private ArrayList<ResultDisplayRestoPhoto_> moviesList;

        public MyCustomAdapter(ArrayList<ResultDisplayRestoPhoto_> moviesList) {
            this.moviesList = moviesList;
        }

        @Override
        public MyCustomAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_view_image_list, parent, false);

            return new MyCustomAdapter.MyViewHolder(itemView);
        }

        public void clear() {
            int size = this.moviesList.size();
            if (size > 0) {
                for (int i = 0; i < size; i++) {
                    this.moviesList.remove(0);
                }

                this.notifyItemRangeRemoved(0, size);
            }
        }

        @Override
        public void onBindViewHolder(MyCustomAdapter.MyViewHolder holder, final int position) {


            final ResultDisplayRestoPhoto_ datum = moviesList.get(position);


            if (datum.getImageName() != null) {
                Glide.with(getActivity()).
                        load(BuildConstants.Main_Image + datum.getImageName()).
                        asBitmap().
                        into(holder.img);
            } else {
                Glide.with(getActivity()).
                        load(R.drawable.mcdonalds).
                        asBitmap().
                        into(holder.img);
            }

        }


        @Override
        public int getItemCount() {
            return moviesList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {


            ImageView img;


            public MyViewHolder(View view) {
                super(view);


                img = view.findViewById(R.id.img);


            }

        }

    }

}