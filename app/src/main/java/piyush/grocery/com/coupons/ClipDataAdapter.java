package piyush.grocery.com.coupons;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ClipDataAdapter extends ArrayAdapter<CouponData> {

    Context context;
    int layoutResourceId;
    ArrayList<CouponData> dataget = null;
    ArrayList<CouponData> clippeddata = null;
    ViewHolder holder;
    int pos = 0;
    CouponData getdata;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    ProgressDialog pDialog;

    public ClipDataAdapter(FragmentActivity activity, int marketlayoutproductfull, ArrayList<CouponData> coupon_data) {
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

        String[] parts = getdata.getRedemptionEndDate().split("T");
        String part1 = parts[0]; // 004

        String replacedString = part1.replace("-", "/");

        holder.validdate.setText("Valid thru "+replacedString);
        holder.coupon_product_image.setImageUrl(getdata.getImageURL(), imageLoader);
        holder.clip.setText("UNCLIP");

        holder.clip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getdata = dataget.get(position);
                // removing unclip data
                dataget.remove(getdata);
                ClipDataAdapter.this.notifyDataSetChanged();
                // Saving updated data in shared preference
                SharedPreferences mPrefs = context.getSharedPreferences("MyObject",Context.MODE_PRIVATE);
                SharedPreferences.Editor prefsEditor = mPrefs.edit();
                Gson gson = new Gson();
                String json = gson.toJson(dataget);
                prefsEditor.putString("MyObject", json);
                prefsEditor.commit();


            }
        });


        return convertView;
    }


    class ViewHolder {
        TextView coupon_title, coupont_description, validdate, clip;
        NetworkImageView coupon_product_image;
    }
}
