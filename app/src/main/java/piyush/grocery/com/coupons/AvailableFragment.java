package piyush.grocery.com.coupons;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@SuppressLint("ValidFragment")
public class AvailableFragment extends Fragment {

	String [] title,description,imageURL,redemptionEndDate;

	String TAG = "AvailableFragment_TAG";
	JSONObject data_jobject;
	CouponDataAdapter coupon_adapter;
	ArrayList<CouponData> coupon_data = null;
	ListView coupon_list;
    ProgressDialog dialog;
    String couponCount;
    TextView totalcount;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View v=inflater.inflate(R.layout.available_fragment, container, false);
		coupon_list=(ListView)v.findViewById(R.id.coupon_list);
		totalcount=(TextView)v.findViewById(R.id.totalcount);
		coupon_data = new ArrayList<CouponData>();
		getCoupons();
		return v;
	}

// Fetch all coupon data
	public void getCoupons() {
        dialog = new ProgressDialog(getActivity());
        dialog.setTitle("Wait");
        dialog.show();
		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
				"https://meijerkraig.azurewebsites.net/api/Products?code=34lgBae%2FxIEnqksQpkn3w9F0JTKCafuiCr0ejLNLvBzlOlOZBa1CMA%3D%3D", null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(final JSONObject response) {
						Log.e(TAG, response.toString());
						try {
							couponCount=response.getString("couponCount");
							totalcount.setText(couponCount);
							JSONArray get_data=response.getJSONArray("listOfCoupons");
							title = new String[get_data.length()];
							description = new String[get_data.length()];
							imageURL = new String[get_data.length()];
							redemptionEndDate = new String[get_data.length()];

							for (int i = 0; i < get_data.length(); i++) {
								data_jobject = get_data.getJSONObject(i);
								title[i] = data_jobject.getString("title");
								description[i] = data_jobject.getString("description");
								imageURL[i] = data_jobject.getString("imageURL");
								redemptionEndDate[i] = data_jobject.getString("redemptionEndDate");

								coupon_data.add(new CouponData(title[i],description[i],imageURL[i],redemptionEndDate[i],"true"));
							}
						// Assigned all object to adapter
							coupon_adapter=new CouponDataAdapter((FragmentActivity) getActivity(), R.layout.layout_available, coupon_data);

							coupon_list.setAdapter(coupon_adapter);

							dialog.dismiss();

						} catch (Exception e) {

						}


					}
				}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				VolleyLog.e(TAG, "Error: " + error.getMessage());
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run()
					{
						AlertDialog.Builder dlgAlert = new AlertDialog.Builder(getActivity());
						dlgAlert.setMessage("Error , please try again");
						dlgAlert.setPositiveButton("OK", null);
						dlgAlert.setCancelable(true);
						dlgAlert.create().show();
					}
				});
                dialog.dismiss();
			}
		}) {
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				HashMap<String, String> headers = new HashMap<String, String>();
				headers.put("Content-Type", "application/json; charset=utf-8");
				return headers;
			}
		};

		AppController.getInstance().addToRequestQueue(jsonObjReq, TAG);

	}
}
