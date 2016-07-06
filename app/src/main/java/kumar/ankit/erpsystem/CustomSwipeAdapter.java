package kumar.ankit.erpsystem;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by ranjeet prasad on 6/23/2016.
 */
public class CustomSwipeAdapter extends PagerAdapter {

    int i=0;


    int[] image_resources;
    Context context;
    LayoutInflater layoutInflater;
    ImageView image1,image2,image3;

    public CustomSwipeAdapter(Context context, int [] image_resources){

        this.context=context;
        this.image_resources=image_resources;

    }


    @Override
    public int getCount() {

        return image_resources.length/3;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view==(LinearLayout)object);
    }



    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.custom_swipe, container, false);
        image1=(ImageView)view.findViewById(R.id.image1);
        image2=(ImageView)view.findViewById(R.id.image2);
        image3=(ImageView)view.findViewById(R.id.image3);
Log.e(String.valueOf(i),String.valueOf(position));
        image1.setImageResource(image_resources[(position+i)%image_resources.length]);
        image2.setImageResource(image_resources[(++position + i) % image_resources.length]);
        image3.setImageResource(image_resources[(++position + i) % image_resources.length]);

        i+=3;

        container.addView(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
         //       Intent i=new Intent(context,SingleItemDetail.class);
           //     context.startActivity(i);
            }
        });
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

//        View view=(View)object;
        BitmapDrawable bmp=(BitmapDrawable)image1.getDrawable();
        if(bmp!=null && bmp.getBitmap()!=null)
            bmp.getBitmap().recycle();

        BitmapDrawable bmp2=(BitmapDrawable)image2.getDrawable();
        if(bmp2!=null && bmp2.getBitmap()!=null)
            bmp2.getBitmap().recycle();

        BitmapDrawable bmp3=(BitmapDrawable)image3.getDrawable();
        if(bmp3!=null && bmp3.getBitmap()!=null)
            bmp3.getBitmap().recycle();

      //  ((ViewPager)container).removeView(view);
//        view=null;

        // image1.rec

        //Drawable drawable =image1.getDrawable();


        container.removeView((LinearLayout)object);
    }

//
//    public static float megabytesFree() {
//        final Runtime rt = Runtime.getRuntime();
//        final float bytesUsed = rt.totalMemory();
//        final float mbUsed = bytesUsed/BYTES_IN_MB;
//        final float mbFree = megabytesAvailable() - mbUsed;
//        return mbFree;
//    }
//
//    public static float megabytesAvailable() {
//        final Runtime rt = Runtime.getRuntime();
//        final float bytesAvailable = rt.maxMemory();
//        return bytesAvailable/BYTES_IN_MB;
//    }
//
//
//    private void readBitmapInfo() {
//        final Resources res = Resources.getSystem();
//        final BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        BitmapFactory.decodeResource(res, R.drawable.quilt1, options);
//         imageHeight = options.outHeight;
//         imageWidth = options.outWidth;
//        //final String imageMimeType = options.outMimeType;
////        Log.d( TAG,"w,h, type:" + imageWidth + ", " + imageHeight + ", " + imageMimeType);
////        Log.d(TAG, "estimated memory required in MB: "+imageWidth * imageHeight * BYTES_PER_PX/BYTES_IN_MB);
//    }
//    private void subSampleImage(int powerOf2) {
//        if(powerOf2 < 1 || powerOf2 > 8) {
////            Log.e(TAG, "trying to apply upscale or excessive downscale: "+powerOf2);
//            return;
//        }
//        final Resources res = Resources.getSystem();
//
//        final BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = false;
//        options.inSampleSize = powerOf2;
//        bmp = BitmapFactory.decodeResource(res, R.drawable.quilt1, options);
//         bmpDrawable = new BitmapDrawable(res, bmp);
//
//       // image1.setImageDrawable(bmpDrawable);
//
//    }

}