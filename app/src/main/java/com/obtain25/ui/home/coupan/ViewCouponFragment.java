package com.obtain25.ui.home.coupan;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
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
import com.obtain25.model.coupon.ResultDisplayRestaurantCoupon;
import com.obtain25.model.coupon.ResultDisplayRestaurantCoupon_;
import com.obtain25.model.login.LoginModel;
import com.obtain25.utils.AppPreferences;
import com.obtain25.utils.PrefUtils;
import com.obtain25.utils.ViewDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ViewCouponFragment extends Fragment implements View.OnClickListener {
    protected ViewDialog viewDialog;
    Dialog dialog;
    LoginModel loginModel;
    FloatingActionButton fab;
    String latitude, longitude;
    private RecyclerView recycler_view;
    private MyCustomAdapter myCustomAdapter;
    private ArrayList<ResultDisplayRestaurantCoupon_> resultDisplayRestaurantCoupon_s = new ArrayList<>();
    private SwipeRefreshLayout contentHome_SwipeRefresh;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_view_coupon, container, false);
        loginModel = PrefUtils.getUser(getActivity());
        viewDialog = new ViewDialog(getContext());
        viewDialog.setCancelable(false);
        contentHome_SwipeRefresh = root.findViewById(R.id.contentHome_SwipeRefresh);
        fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(this);
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
        Call<ResultDisplayRestaurantCoupon> marqueCall = RetrofitHelper.createService(RetrofitHelper.Service.class).CoupanListModel(hashMap);
        marqueCall.enqueue(new Callback<ResultDisplayRestaurantCoupon>() {
            @Override
            public void onResponse(@NonNull Call<ResultDisplayRestaurantCoupon> call, @NonNull Response<ResultDisplayRestaurantCoupon> response) {
                ResultDisplayRestaurantCoupon object = response.body();
                hideProgressDialog();
                contentHome_SwipeRefresh.setRefreshing(false);
                Log.e("TAG", "ChatV_Response : " + new Gson().toJson(response.body()));
                if (object != null && object.getError() == false) {

                    resultDisplayRestaurantCoupon_s = object.getResultDisplayRestaurantCoupon();
                    myCustomAdapter = new MyCustomAdapter(resultDisplayRestaurantCoupon_s);
                    recycler_view.setAdapter(myCustomAdapter);

                } else if (object != null && object.getError() == true) {
                    //  Toast.makeText(getActivity(), object.getMessage(), Toast.LENGTH_SHORT).show();
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
            public void onFailure(@NonNull Call<ResultDisplayRestaurantCoupon> call, @NonNull Throwable t) {
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                Navigation.findNavController(v).navigate(R.id.nav_add_coupan);
                break;
        }
    }

    public class MyCustomAdapter extends RecyclerView.Adapter<MyCustomAdapter.MyViewHolder> {

        private ArrayList<ResultDisplayRestaurantCoupon_> moviesList;

        public MyCustomAdapter(ArrayList<ResultDisplayRestaurantCoupon_> moviesList) {
            this.moviesList = moviesList;
        }

        @Override
        public MyCustomAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_view_coupon_list, parent, false);

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
        public void onBindViewHolder(final MyCustomAdapter.MyViewHolder holder, final int position) {


            final ResultDisplayRestaurantCoupon_ datum = moviesList.get(position);


            holder.txtName.setText(datum.getName() + "");
            holder.txtDiscount.setText("Discount Value : " + datum.getDiscountValue() + "");
            holder.txtType.setText("Discount Type : " + datum.getDiscountType() + "");
            holder.txtAmount.setText("Minimum Amount : " + datum.getMinimumAmount() + "");
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
            holder.label_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog diaBox = AskOption();
                    diaBox.show();


                }

                private AlertDialog AskOption() {
                    AlertDialog myQuittingDialogBox = new AlertDialog.Builder(getActivity())
                            .setTitle("Delete Coupon")
                            .setMessage("Do you want to Delete Coupon?")


                            .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {
                                    //your deleting code
                                    showProgressDialog();
                                    final HashMap<String, String> hashMap = new HashMap<>();
                                    hashMap.put("id", datum.getId() + "");
                                    Call<SuccessModel> marqueCall = RetrofitHelper.createService(RetrofitHelper.Service.class).DeleteCoupon(hashMap);
                                    marqueCall.enqueue(new Callback<SuccessModel>() {
                                        @Override
                                        public void onResponse(@NonNull Call<SuccessModel> call, @NonNull Response<SuccessModel> response) {
                                            SuccessModel object = response.body();
                                            hideProgressDialog();

                                            Log.e("TAG", "Delete_Response : " + new Gson().toJson(response.body()));
                                            if (object != null && object.getError() == false) {
                                                holder.l1.setVisibility(View.GONE);
                                                Toast.makeText(getContext(), object.getMsg() + "", Toast.LENGTH_SHORT).show();
                                                GetCouponList();
                                            } else if (object != null && object.getError() == true) {
                                                Toast.makeText(getActivity(), object.getMsg(), Toast.LENGTH_SHORT).show();
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
                                                    Toast.makeText(getContext(), jObjError.getJSONObject("errors") + "", Toast.LENGTH_LONG).show();

                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }

                                                try {

                                                } catch (Exception e) {
                                                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
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
                                    dialog.dismiss();
                                }

                            })
                            .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.dismiss();

                                }
                            })
                            .create();
                    return myQuittingDialogBox;
                }
            });
            holder.label_po_edit.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onClick(View v) {
                    dialog = new Dialog(requireActivity());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
                    dialog.setContentView(R.layout.dialog_add_coupon);
                    dialog.setCancelable(true);

                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                    lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
                    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

                    final EditText editText_Discount = dialog.findViewById(R.id.editText_Discount);
                    final EditText editText_Rupee = dialog.findViewById(R.id.editText_Rupee);
                    final Button btn_addLocation = dialog.findViewById(R.id.btn_addLocation);

                    editText_Discount.setText(datum.getDiscountValue() + "");
                    editText_Rupee.setText(datum.getMinimumAmount() + "");


                    btn_addLocation.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final String editDiccount = editText_Discount.getText().toString().trim();
                            final String editAmount = editText_Rupee.getText().toString().trim();


                            if (editDiccount.isEmpty()) {
                                editText_Discount.setError("Discount Value Required");
                                editText_Discount.requestFocus();
                                return;
                            }
                            if (editAmount.isEmpty()) {
                                editText_Rupee.setError("Maximum Amount Required");
                                editText_Rupee.requestFocus();
                                return;
                            } else {

                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("discount_value", editDiccount + "");
                                hashMap.put("minimum_amount", editAmount + "");
                                hashMap.put("discount_type", datum.getDiscountType());
                                hashMap.put("restaurant_id", PrefUtils.getUser(getActivity()).getSessionData().getId());
                                hashMap.put("id", datum.getId() + "");

                                Log.e("GAYA", hashMap + "");
                                showProgressDialog();
                                Call<SuccessModel> marqueCall = RetrofitHelper.createService(RetrofitHelper.Service.class).EditCouponDetail(hashMap);
                                marqueCall.enqueue(new Callback<SuccessModel>() {
                                    @Override
                                    public void onResponse(@NonNull Call<SuccessModel> call, @NonNull Response<SuccessModel> response) {
                                        SuccessModel object = response.body();
                                        hideProgressDialog();
                                        Log.e("TAG", "Add_Shop : " + new Gson().toJson(response.body()));
                                        if (object != null && object.getError() == false) {
                                            Toast.makeText(getContext(), object.getMsg() + "", Toast.LENGTH_SHORT).show();

                                            dialog.dismiss();
                                            GetCouponList();
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

                                            try {

                                            } catch (Exception e) {
                                                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
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
            });

        }


        @Override
        public int getItemCount() {
            return moviesList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {


            TextView imgpercent, txtAmount, txtType, txtDiscount, txtName;
            ImageView img;
            ImageButton label_po_edit, label_delete;
            LinearLayout l1;

            public MyViewHolder(View view) {
                super(view);


                txtName = view.findViewById(R.id.txtName);
                txtDiscount = view.findViewById(R.id.txtDiscount);
                txtType = view.findViewById(R.id.txtType);
                txtAmount = view.findViewById(R.id.txtAmount);
                imgpercent = view.findViewById(R.id.imgpercent);
                img = view.findViewById(R.id.img);
                label_delete = view.findViewById(R.id.label_delete);
                label_po_edit = view.findViewById(R.id.label_po_edit);
                l1 = view.findViewById(R.id.l1);


            }

        }

    }

}