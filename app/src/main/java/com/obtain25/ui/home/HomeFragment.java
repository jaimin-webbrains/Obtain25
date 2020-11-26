package com.obtain25.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.obtain25.R;
import com.obtain25.api.BuildConstants;
import com.obtain25.api.RetrofitHelper;
import com.obtain25.model.SuccessModel;
import com.obtain25.model.coupon.ResultDisplayActiveRestaurantCoupon;
import com.obtain25.model.coupon.ResultDisplayActiveRestaurantCoupon_;
import com.obtain25.model.login.LoginModel;
import com.obtain25.ui.HomeActivity;
import com.obtain25.utils.AppPreferences;
import com.obtain25.utils.PrefUtils;
import com.obtain25.utils.ViewDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {
    protected ViewDialog viewDialog;
    AppCompatTextView txt_name;
    FloatingActionButton fab;
    LoginModel loginModel;
    String streamid = "";
    LocationManager locationManager;
    String latitude, longitude;
    private RecyclerView recycler_view;
    private MyCustomAdapter myCustomAdapter;
    private ArrayList<ResultDisplayActiveRestaurantCoupon_> resultDisplayRestaurantCoupon_s = new ArrayList<>();
    private SwipeRefreshLayout contentHome_SwipeRefresh;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        loginModel = PrefUtils.getUser(getActivity());
        viewDialog = new ViewDialog(getContext());
        viewDialog.setCancelable(false);
        contentHome_SwipeRefresh = root.findViewById(R.id.contentHome_SwipeRefresh);
        txt_name = root.findViewById(R.id.txt_name);
        contentHome_SwipeRefresh.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED);

        recycler_view = root.findViewById(R.id.viewProducts_RecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recycler_view.setLayoutManager(layoutManager);
        recycler_view.setHasFixedSize(true);
        latitude = AppPreferences.getLati(getActivity());
        longitude = AppPreferences.getLongi(getActivity());
        GetCouponList();
        contentHome_SwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                contentHome_SwipeRefresh.setRefreshing(false);
                GetCouponList();


            }
        });

        return root;
    }

    private void GetCouponList() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("restaurant_id", loginModel.getSessionData().getId() + "");

        showProgressDialog();
        Log.e("GAYA", hashMap + "");
        Call<ResultDisplayActiveRestaurantCoupon> marqueCall = RetrofitHelper.createService(RetrofitHelper.Service.class).ActiveCoupanListModel(hashMap);
        marqueCall.enqueue(new Callback<ResultDisplayActiveRestaurantCoupon>() {
            @Override
            public void onResponse(@NonNull Call<ResultDisplayActiveRestaurantCoupon> call, @NonNull Response<ResultDisplayActiveRestaurantCoupon> response) {
                ResultDisplayActiveRestaurantCoupon object = response.body();
                hideProgressDialog();
                contentHome_SwipeRefresh.setRefreshing(false);
                Log.e("TAG", "ChatV_Response : " + new Gson().toJson(response.body()));
                if (object != null && object.getError() == false) {

                    txt_name.setVisibility(View.VISIBLE);
                    resultDisplayRestaurantCoupon_s = object.getResultDisplayActiveRestaurantCoupon();
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
            public void onFailure(@NonNull Call<ResultDisplayActiveRestaurantCoupon> call, @NonNull Throwable t) {
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

        private ArrayList<ResultDisplayActiveRestaurantCoupon_> moviesList;

        public MyCustomAdapter(ArrayList<ResultDisplayActiveRestaurantCoupon_> moviesList) {
            this.moviesList = moviesList;
        }

        @Override
        public MyCustomAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_view_active_coupon_list, parent, false);

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


            final ResultDisplayActiveRestaurantCoupon_ datum = moviesList.get(position);

            holder.lView.setVisibility(View.GONE);
            holder.view1.setVisibility(View.GONE);
            holder.txtName.setText(datum.getName() + "");
            holder.txtDiscount.setText("Discount Value : " + datum.getDiscountValue() + "");
            holder.txtType.setText("Discount Type : " + datum.getDiscountType() + "");
            holder.txtAmount.setText("Minimum Amount : " + datum.getMinimumAmount() + "");
            if (datum.getMaximum_amount() != null) {
                holder.vMax.setVisibility(View.VISIBLE);
                holder.txtMaxAmount.setVisibility(View.VISIBLE);
                holder.txtMaxAmount.setText("Maximum Amount : " + datum.getMaximum_amount() + "");
            } else {
                holder.vMax.setVisibility(View.GONE);
                holder.txtMaxAmount.setVisibility(View.GONE);
            }

            holder.imgpercent.setText(datum.getActive() + "");
            if (datum.getRestoPhoto() != null) {
                Glide.with(getActivity()).
                        load(BuildConstants.Main_Image + datum.getRestoPhoto()).
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


            TextView imgpercent, txtAmount, txtType, txtDiscount, txtName, txtMaxAmount;
            ImageView img;
            View vMax,view1;
            LinearLayout lView;


            public MyViewHolder(View view) {
                super(view);


                txtName = view.findViewById(R.id.txtName);
                txtDiscount = view.findViewById(R.id.txtDiscount);
                txtType = view.findViewById(R.id.txtType);
                txtMaxAmount = view.findViewById(R.id.txtMaxAmount);
                txtAmount = view.findViewById(R.id.txtAmount);
                imgpercent = view.findViewById(R.id.imgpercent);
                img = view.findViewById(R.id.img);
                vMax = view.findViewById(R.id.vMax);
                view1 = view.findViewById(R.id.view);
                lView = view.findViewById(R.id.lView);


            }

        }

    }


}
