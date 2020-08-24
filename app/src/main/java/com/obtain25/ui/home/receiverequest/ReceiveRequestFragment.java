package com.obtain25.ui.home.receiverequest;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.obtain25.R;
import com.obtain25.api.RetrofitHelper;
import com.obtain25.model.SuccessModel;
import com.obtain25.model.login.LoginModel;
import com.obtain25.model.sendrequest.ResponseRestoDisplayUserRequest;
import com.obtain25.model.sendrequest.ResponseRestoDisplayUserRequest_;
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


public class ReceiveRequestFragment extends Fragment {

    protected ViewDialog viewDialog;
    LoginModel loginModel;
    String latitude, longitude;
    private RecyclerView myFriendsRecyclerView;
    private MyCustomAdapter myCustomAdapter;
    private ArrayList<ResponseRestoDisplayUserRequest_> resultDisplayRestaurantCoupon_s = new ArrayList<>();
    private SwipeRefreshLayout contentHome_SwipeRefresh;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_receive_request, container, false);
        loginModel = PrefUtils.getUser(getActivity());
        viewDialog = new ViewDialog(getContext());
        viewDialog.setCancelable(false);
        contentHome_SwipeRefresh = rootView.findViewById(R.id.contentHome_SwipeRefresh);
        contentHome_SwipeRefresh.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED);

        myFriendsRecyclerView = rootView.findViewById(R.id.myFriendsRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        myFriendsRecyclerView.setLayoutManager(layoutManager);
        myFriendsRecyclerView.setHasFixedSize(true);
        latitude = AppPreferences.getLati(getActivity());
        longitude = AppPreferences.getLongi(getActivity());
        GetReceiveRequest();
        contentHome_SwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                contentHome_SwipeRefresh.setRefreshing(false);
                GetReceiveRequest();


            }
        });
        return rootView;
    }

    private void GetReceiveRequest() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("resto_id", loginModel.getSessionData().getId() + "");

        showProgressDialog();
        Log.e("GAYA", hashMap + "");
        Call<ResponseRestoDisplayUserRequest> marqueCall = RetrofitHelper.createService(RetrofitHelper.Service.class).RestoDisplayUserRequest(hashMap);
        marqueCall.enqueue(new Callback<ResponseRestoDisplayUserRequest>() {
            @Override
            public void onResponse(@NonNull Call<ResponseRestoDisplayUserRequest> call, @NonNull Response<ResponseRestoDisplayUserRequest> response) {
                ResponseRestoDisplayUserRequest object = response.body();
                hideProgressDialog();
                contentHome_SwipeRefresh.setRefreshing(false);
                Log.e("TAG", "ChatV_Response : " + new Gson().toJson(response.body()));
                if (object != null && object.getError() == false) {

                    resultDisplayRestaurantCoupon_s = object.getResponseRestoDisplayUserRequest();
                    myCustomAdapter = new MyCustomAdapter(resultDisplayRestaurantCoupon_s);
                    myFriendsRecyclerView.setAdapter(myCustomAdapter);

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
            public void onFailure(@NonNull Call<ResponseRestoDisplayUserRequest> call, @NonNull Throwable t) {
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

        String id, status;
        private ArrayList<ResponseRestoDisplayUserRequest_> moviesList;

        public MyCustomAdapter(ArrayList<ResponseRestoDisplayUserRequest_> moviesList) {
            this.moviesList = moviesList;
        }

        @Override
        public MyCustomAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_receive_request_list, parent, false);

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


            final ResponseRestoDisplayUserRequest_ datum = moviesList.get(position);

            id = datum.getId();
            status = datum.getStatus() + "";
            holder.txtCName.setText(datum.getName() + "");
            holder.txtPerson.setText(datum.getNumMember() + "");
            holder.txtDate.setText(datum.getDateAndTime() + "");
            holder.l1Accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AcceptRequest();
                }

                private void AcceptRequest() {
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("reasone", "");
                    hashMap.put("status", "1" + "");
                    hashMap.put("id", id);

                    Log.e("GAYA", hashMap + "");
                    showProgressDialog();
                    Call<SuccessModel> marqueCall = RetrofitHelper.createService(RetrofitHelper.Service.class).UpdateRequest(hashMap);
                    marqueCall.enqueue(new Callback<SuccessModel>() {
                        @Override
                        public void onResponse(@NonNull Call<SuccessModel> call, @NonNull Response<SuccessModel> response) {
                            SuccessModel object = response.body();
                            hideProgressDialog();
                            Log.e("TAG", "Add_Shop : " + new Gson().toJson(response.body()));
                            if (object != null && object.getError() == false) {
                                Toast.makeText(getContext(), object.getMsg() + "", Toast.LENGTH_SHORT).show();
                                holder.card.setVisibility(View.GONE);
                                GetReceiveRequest();
                            } else if (object != null && object.getError() == true) {
                                Toast.makeText(getContext(), object.getMsg() + "", Toast.LENGTH_SHORT).show();

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

            });
            holder.l1Reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RejectRequest();
                }

                private void RejectRequest() {
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("reasone", "");
                    hashMap.put("status", "0" + "");
                    hashMap.put("id", id);

                    Log.e("GAYA", hashMap + "");
                    showProgressDialog();
                    Call<SuccessModel> marqueCall = RetrofitHelper.createService(RetrofitHelper.Service.class).UpdateRequest(hashMap);
                    marqueCall.enqueue(new Callback<SuccessModel>() {
                        @Override
                        public void onResponse(@NonNull Call<SuccessModel> call, @NonNull Response<SuccessModel> response) {
                            SuccessModel object = response.body();
                            hideProgressDialog();
                            Log.e("TAG", "Add_Shop : " + new Gson().toJson(response.body()));
                            if (object != null && object.getError() == false) {
                                Toast.makeText(getContext(), object.getMsg() + "", Toast.LENGTH_SHORT).show();
                                holder.card.setVisibility(View.GONE);
                                GetReceiveRequest();
                            } else if (object != null && object.getError() == true) {
                                Toast.makeText(getContext(), object.getMsg() + "", Toast.LENGTH_SHORT).show();

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
            });


        }


        @Override
        public int getItemCount() {
            return moviesList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            final CardView card;
            AppCompatButton l1Accept, l1Reject;
            TextView txtCName, txtPerson, txtDate;

            public MyViewHolder(View view) {
                super(view);


                txtCName = view.findViewById(R.id.txtCName);
                txtPerson = view.findViewById(R.id.txtPerson);
                txtDate = view.findViewById(R.id.txtDate);
                l1Reject = view.findViewById(R.id.l1Reject);
                l1Accept = view.findViewById(R.id.l1Accept);
                card = view.findViewById(R.id.card);


            }

        }

    }

}