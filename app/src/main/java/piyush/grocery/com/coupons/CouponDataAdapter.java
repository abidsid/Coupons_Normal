package piyush.grocery.com.coupons;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CouponDataAdapter  extends ArrayAdapter<CouponData> {

    Context context;
    int layoutResourceId;
    ArrayList<CouponData> dataget = null;
    ArrayList<CouponData> clippeddata = null;
    ViewHolder holder;
    int pos = 0;
    CouponData getdata;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    ProgressDialog pDialog;
    int check=0;

    public CouponDataAdapter(FragmentActivity activity, int marketlayoutproductfull, ArrayList<CouponData> coupon_data) {
        super(activity, marketlayoutproductfull, coupon_data);
        clippeddata=new ArrayList<CouponData>();
        context = activity;
        layoutResourceId = marketlayoutproductfull;
        dataget = coupon_data;
        pDialog = new ProgressDialog(context);
    }


    @Override
    public int getCount() {
        return dataget.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        holder = null;
        LayoutInflater inflater = LayoutInflater.from(context);

        if (convertView == null) {

            convertView = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.coupon_title = convertView
                    .findViewById(R.id.coupon_title);
            holder.coupont_description = convertView
                    .findViewById(R.id.coupont_description);
            holder.validdate = convertView
                    .findViewById(R.id.validdate);
            holder.clip = convertView
                    .findViewById(R.id.clip);
            holder.coupon_product_image = convertView
                    .findViewById(R.id.coupon_product_image);
            holder.coupon_title.setTag(position);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        getdata = dataget.get(position);
        pos = position;

        holder.coupon_title.setText(getdata.getTitle());
        holder.coupont_description.setText(getdata.getDescription());
        holder.validdate.setText(getdata.getRedemptionEndDate());
        holder.coupon_product_image.setImageUrl(getdata.getImageURL(), imageLoader);

        holder.clip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getdata = dataget.get(position);

                SharedPreferences mPrefs = context.getSharedPreferences("MyObject",Context.MODE_PRIVATE);
                SharedPreferences.Editor prefsEditor = mPrefs.edit();
                Gson gson = new Gson();
                String json = mPrefs.getString("MyObject", "");
                Type type = new TypeToken<ArrayList<CouponData>>(){}.getType();
                ArrayList<CouponData> clipped_coupon = gson.fromJson(json, type);

                if(clipped_coupon!=null) {
                    if(check==0) {
                        clippeddata.addAll(clipped_coupon);
                        check=check+1;
                    }
                }
                clippeddata.add(getdata);
                json = gson.toJson(clippeddata);
                prefsEditor.putString("MyObject", json);
                prefsEditor.commit();
                check=check+1;
            }
        });


        return convertView;
    }


    class ViewHolder {
        TextView coupon_title, coupont_description, validdate, clip;
        NetworkImageView coupon_product_image;
    }
}
