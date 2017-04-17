package com.zoutu.gotopaotui.ActivityMain.FragmentPersonCenter;

import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.zoutu.gotolibrary.DBCache.XCCacheManager.xccache.XCCacheManager;
import com.zoutu.gotolibrary.Dialog.AlertView.AlertView;
import com.zoutu.gotolibrary.Dialog.AlertView.OnItemClickListener;
import com.zoutu.gotolibrary.ImageView.RoundImageView;
import com.zoutu.gotolibrary.ImmersionBar.SystemBarTintManager;
import com.zoutu.gotolibrary.ScrollView.SpringScrollView;
import com.zoutu.gotolibrary.Utils.XCCacheManagerSavedName;
import com.zoutu.gotopaotui.ActivityMain.BaseFragment;
import com.zoutu.gotopaotui.ActivityMain.FragmentPersonCenter.Login.MainPersonCenterLoginActivity;
import com.zoutu.gotopaotui.R;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * Created by admin on 2017/3/27.
 */

public class MainPersonCenterFragment extends BaseFragment {
    /**打开相册，并截图*/
    private  final int INTENT_ACTION_PICTURE = 0;
    /**打开相机照相*/
    private  final int INTENT_ACTION_CAREMA = 1;
    /**照相后，截图*/
    private  final int INTENT_ACTION_CROP = 2;
    /**图片名字*/
    private  final String PICTURE_NAME = "userIcon.jpg";
    private String picturePath;
    @BindView(R.id.riv_main_personcenter_top_head)
    RoundImageView rivMainPersonCenterTopHead;
    @BindView(R.id.lly_main_personcenter_topbar)
    LinearLayout llyMainPersonCenterTopbar;
    @BindView(R.id.ssv_main_personcenter)
    SpringScrollView ssvMainPersonCenter;
    float by = 0;;
    @OnTouch(R.id.ssv_main_personcenter)
    public boolean ssvMainPersonCenterOnclick(View view, MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                by = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float ey = event.getY();
                float dis = ey - by;
                dis = Math.abs(dis);
                if(ey < by){
                    float alphTemp = dis/270;
                    llyMainPersonCenterTopbar.setAlpha(alphTemp);
                }else{
                    float alphTemp = dis/270;
                /*    Log.i("main_index_alph",alph+"");*/
                    llyMainPersonCenterTopbar.setAlpha(alphTemp);
                    /*System.out.println("alph2:"+alph);*/
                }
                System.out.println("dis:"+dis);
                break;

        }
        return false;
    }
    @BindView(R.id.rly_main_personcenter_head)
    RelativeLayout rlyMainPersonCenterHead;
    @OnClick(R.id.rly_main_personcenter_head)
    public void rlyMainPersonCenterHeadOnclick(){
        loginOrSelectPhotoHead();
    }

    MainPersonCenterFragmentController mainPersonCenterFragmentController;
    private View view1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity_main_personcenter_fly, container, false);
        view1 = view;
        init(view);
        return view;
    }


    private void init(View view){
        ButterKnife.bind(this,view);
        initPhotoSavePath();
        mainPersonCenterFragmentController = new MainPersonCenterFragmentController(view);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        Log.i("onActivityResult","this is onAResult0");
       /* Toast.makeText(getActivity(),"this is onAResult",Toast.LENGTH_LONG).show();*/
        if(requestCode == INTENT_ACTION_PICTURE && resultCode == Activity.RESULT_OK && null != data){
            Cursor c = getActivity().getContentResolver().query(data.getData(), null, null, null, null);
            c.moveToNext();
            String path = c.getString(c.getColumnIndex(MediaStore.MediaColumns.DATA));
            c.close();
            Log.i("onActivityResult","this is onAResult1");
            System.out.println("\nonActivityResult == " + path);

            if(new File(path).exists()){
                System.out.println("onActivityResult == " + path +" == exist");
                /*userInfo.setUserIcon(path);*/
                rivMainPersonCenterTopHead.setImageBitmap(compressImageFromFile(path));
                //ivUserIcon.setImageURI(Uri.parse(path));
                //ivUserIcon.setImageDrawable(Drawable.createFromPath(path));
            }
        }else if(requestCode == INTENT_ACTION_CAREMA && resultCode == Activity.RESULT_OK){
            Log.i("onActivityResult","this is onAResult2");
          /*  userInfo.setUserIcon(picturePath);*/
            //ivUserIcon.setImageURI(Uri.parse(picturePath));
            rivMainPersonCenterTopHead.setImageDrawable(Drawable.createFromPath(picturePath));
        }else if(requestCode == INTENT_ACTION_CROP && resultCode == Activity.RESULT_OK && null != data){
            Log.i("onActivityResult","this is onAResult3");
            //ivUserIcon.setImageURI(Uri.parse(picturePath));
            rivMainPersonCenterTopHead.setImageDrawable(Drawable.createFromPath(picturePath));
        }
        /*Toast.makeText(view1.getContext(),"this is onAResult",Toast.LENGTH_LONG).show();*/
        Log.i("onActivityResult","this is onAResult4");
    }

    /*判断是否为登录状态 若不是 前往登录界面 or选择头像*/
    public void loginOrSelectPhotoHead(){
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(view1.getContext());
        XCCacheManagerSavedName xcCacheManagerSavedName = new XCCacheManagerSavedName();
        /*判断是否为登录状态 若不是 前往登录界面 or选择头像*/
        String loginStatus = xcCacheManager.readCache(xcCacheManagerSavedName.loginStatue);
        if(loginStatus == null){
            Intent intent = new Intent(view1.getContext(), MainPersonCenterLoginActivity.class);
            view1.getContext().startActivity(intent);
        }else {
            if (!loginStatus.equals("yes")) {
                Intent intent = new Intent(view1.getContext(), MainPersonCenterLoginActivity.class);
                view1.getContext().startActivity(intent);
            } else {
                //从相册选择头像代码
                new AlertView.Builder().setContext(view1.getContext())
                        .setStyle(AlertView.Style.ActionSheet)
                        .setTitle("选择头像")
                        .setMessage(null)
                        .setCancelText("取消")
                        .setDestructive("从相册获取图片")
                        .setDestructive1("打开相机照相")
                        .setOthers(null)
                        .setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(Object o, int position) {
                        /*Toast.makeText(getBaseContext(),"pos"+position,Toast.LENGTH_SHORT).show();*/
                                switch (position){
                                    case 0:
                                        getPicture();
                                        break;
                                    case 1:
                                        openCamera();
                                        break;
                                    case -1:
                                        break;
                                }
                            }
                        }).build()
                        .show();
            }

        }
    }




    /**从相册获取图片*/
    private void getPicture(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(intent, INTENT_ACTION_PICTURE);
        //TODO
    }

    /**打开相机照相*/
    private void openCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(picturePath)));
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);

        startActivityForResult(intent, INTENT_ACTION_CAREMA);
    }

    private void initPhotoSavePath(){
        //初始化照片保存地址
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            String thumPicture = Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+getActivity().getPackageName()+"/download";
            File pictureParent =new File(thumPicture);
            File pictureFile =new File(pictureParent, PICTURE_NAME);

            if(!pictureParent.exists()){
                pictureParent.mkdirs();
            }
            try{
                if (!pictureFile.exists()) {
                    pictureFile.createNewFile();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
            picturePath = pictureFile.getAbsolutePath();
            Log.e("picturePath ==>>", picturePath);
        }else{
            Log.e("change user icon ==>>", "there is not sdcard!");
        }
    }
    /**剪裁方法*/
    private void openCrop(Uri uri){
        //TODO 裁剪方法，自己做
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");//可裁剪
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 100);
        intent.putExtra("outputY", 100);
        intent.putExtra("scale", true);
        //   intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra("return-data", true);//若为false则表示不返回数据
        //   intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        /*activity.startActivityForResult(intent, INTENT_ACTION_CROP);*/
//		startActivityForResult(intent, INTENT_ACTION_CAREMA);
    }
    //图片压缩
    private Bitmap compressImageFromFile(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;//只读边,不读内容
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        float hh = 800f;//
        float ww = 480f;//
        int be = 1;
        if (w > h && w > ww) {
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置采样率

        newOpts.inPreferredConfig = Bitmap.Config.ARGB_8888;//该模式是默认的,可不设
        newOpts.inPurgeable = true;// 同时设置才会有效
        newOpts.inInputShareable = true;//。当系统内存不够时候图片自动被回收

        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
//		return compressBmpFromBmp(bitmap);//原来的方法调用了这个方法企图进行二次压缩
        //其实是无效的,大家尽管尝试
        return bitmap;
    }









    @Override
    public void onResume(){
        super.onResume();
/*        Toast.makeText(view1.getContext(),"this is onAResult",Toast.LENGTH_LONG).show();*/
        mainPersonCenterFragmentController.onResume();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
    }

/*    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(view1.getContext(),"this is receive",Toast.LENGTH_LONG).show();
    }*/

    /*http://blog.csdn.net/xiazdong/article/details/7768807/*/
/*    public class loginOutBroadReceiver extends BroadcastReceiver {


        @Override
        public void onReceive(Context context, Intent intent) {
           *//* String action = intent.getExtras().getString("action");*//*

*//*            if(action != null){
                onResume();
            }
            Log.i("Recevier1", "接收到:"+action);*//*
        }
    }*/
}