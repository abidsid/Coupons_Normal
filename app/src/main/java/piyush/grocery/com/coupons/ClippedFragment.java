package piyush.grocery.com.coupons;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class ClippedFragment extends Fragment {
	String [] title,description,imageURL,redemptionEndDate;

	String TAG = "SignupActivity_TAG";
	ProgressDialog pDialog;
	JSONObject data_jobject;
	ClipDataAdapter coupon_adapter;
	ArrayList<CouponData> coupon_data = null;
	ListView coupon_list;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.clipped_fragment, container, false);
		coupon_list=(ListView)v.findViewById(R.id.coupon_list);
		coupon_data = new ArrayList<CouponData>();

		SharedPreferences mPrefs = getActivity().getSharedPreferences("MyObject",Context.MODE_PRIVATE);
		SharedPreferences.Editor prefsEditor = mPrefs.edit();
		Gson gson = new Gson();
		String json = mPrefs.getString("MyObject", "");
		Type type = new TypeToken<ArrayList<CouponData>>(){}.getType();
		ArrayList<CouponData> clipped_coupon = gson.fromJson(json, type);

		// If any coupon will be clipped fetched here

		if(clipped_coupon!=null) {

			coupon_adapter = new ClipDataAdapter((FragmentActivity) getActivity(), R.layout.layout_clipped_adapter, clipped_coupon);

			coupon_list.setAdapter(coupon_adapter);
			coupon_adapter.notifyDataSetChanged();

		}
		return v;
	}



}
