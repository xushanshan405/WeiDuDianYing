package com.bw.movie.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.bw.movie.Base.BaseFragment;
import com.bw.movie.R;
import com.bw.movie.app.App;
import com.bw.movie.bean.SCTXBean;
import com.bw.movie.bean.ShengRiBean;
import com.bw.movie.bean.UserBean;
import com.bw.movie.bean.XiuGaiShouJIBean;
import com.bw.movie.contract.HomeConteract;
import com.bw.movie.presenter.UserPresenter;
import com.bw.movie.view.FanKuiActivity;
import com.bw.movie.view.GouPiaoJiLuActivity;
import com.bw.movie.view.GuanZhuActivity;
import com.bw.movie.view.KanGuoActivity;
import com.bw.movie.view.MainActivity;
import com.bw.movie.view.SheZhiActivity;
import com.bw.movie.view.WDPLActivity;
import com.bw.movie.view.WdActivity;
import com.bw.movie.view.WdYpActivity;
import com.bw.movie.view.XinXiActivity;
import com.bw.movie.view.YuYueActivity;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zxy.tiny.Tiny;
import com.zxy.tiny.callback.FileWithBitmapCallback;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;


public class WoDeFragment extends BaseFragment<UserPresenter> implements HomeConteract.UserContreact.IView, View.OnClickListener {

    @BindView(R.id.wd_touxiang)
    SimpleDraweeView wdTouxiang;
    @BindView(R.id.img_sz)
    ImageView imgSz;
    @BindView(R.id.wd_nicheng)
    TextView wdNicheng;
    @BindView(R.id.wd_wdyp)
    LinearLayout wdWdyp;
    @BindView(R.id.wd_guanzhu)
    ImageView wdGuanzhu;
    @BindView(R.id.wd_yuyue)
    ImageView wdYuyue;
    @BindView(R.id.goupiao_jilu)
    ImageView goupiaoJilu;
    @BindView(R.id.wd_kanguo)
    ImageView wdKanguo;
    @BindView(R.id.wd_pinglun)
    ImageView wdPinglun;
    @BindView(R.id.wd_fankui)
    ImageView wdFankui;
    @BindView(R.id.wd_xinxi)
    ImageView wdXinxi;
    @BindView(R.id.wd_wdxx)
    SimpleDraweeView wdWdxx;
    private TextView takePhotoTV;
    private TextView choosePhotoTV;
    private TextView cancelTV;
    private AlertDialog.Builder builder;
    private LayoutInflater inflater;
    private View layout;
    private AlertDialog dialog;
    private static final int MY_ADD_CASE_CALL_PHONE = 6;
    //打开相册的请求码
    private static final int MY_ADD_CASE_CALL_PHONE2 = 7;

    @Override
    protected UserPresenter providePresenter() {
        return new UserPresenter();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        String sessionId = App.sharedPreferences.getString("sessionId", null);
        String userId = App.sharedPreferences.getString("userId", null);
       if (userId!=null && sessionId!=null){
           mPresenter.getUserPresenter(userId,sessionId);
       }else {
           Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
       }
    }


    protected int provideLayoutId() {
        return R.layout.fragment_my;
    }

    @OnClick({R.id.wd_wdyp,R.id.wd_nicheng, R.id.wd_guanzhu, R.id.wd_yuyue, R.id.goupiao_jilu, R.id.wd_kanguo, R.id.wd_pinglun, R.id.wd_fankui, R.id.wd_xinxi, R.id.wd_wdxx,R.id.img_sz,R.id.wd_touxiang})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.wd_wdyp:
                startActivity(new Intent(getActivity(), WdYpActivity.class));
                break;
            case R.id.wd_nicheng:
                viewInit();
                break;
            case R.id.wd_guanzhu:
                startActivity(new Intent(getActivity(), GuanZhuActivity.class));
                break;
            case R.id.wd_yuyue:
                startActivity(new Intent(getActivity(), YuYueActivity.class));
                break;
            case R.id.goupiao_jilu:
                startActivity(new Intent(getActivity(), GouPiaoJiLuActivity.class));
                break;
            case R.id.wd_kanguo:
                startActivity(new Intent(getActivity(), KanGuoActivity.class));
                break;
            case R.id.wd_pinglun:
                startActivity(new Intent(getActivity(), WDPLActivity.class));
                break;
            case R.id.wd_fankui:
                startActivity(new Intent(getActivity(), FanKuiActivity.class));

                break;
            case R.id.wd_xinxi:
                startActivity(new Intent(getActivity(), XinXiActivity.class));

                break;
            case R.id.wd_wdxx:
                startActivity(new Intent(getActivity(), WdActivity.class));
                break;
                case R.id.img_sz:
                    startActivity(new Intent(getActivity(), SheZhiActivity.class));
                    break;
                case R.id.wd_touxiang:
                startActivity(new Intent(getActivity(), MainActivity.class));
                break;
        }
    }

    private void viewInit() {
        //创建对话框
        builder = new AlertDialog.Builder(getActivity());
        inflater = getLayoutInflater();
        //获取自定义布局
        layout = inflater.inflate(R.layout.dialog_select_photo, null);
        builder.setView(layout);//设置对话框的布局
        //生成最终的对话框
        dialog = builder.create();
        dialog.show();//显示对话框

        takePhotoTV = layout.findViewById(R.id.photograph);
        choosePhotoTV = layout.findViewById(R.id.photo);
        cancelTV = layout.findViewById(R.id.cancel);
        //设置监听
        takePhotoTV.setOnClickListener(this);
        choosePhotoTV.setOnClickListener(this);
        cancelTV.setOnClickListener(this);
    }


    @Override
    public void onUserSuccess(UserBean data) {
        UserBean.ResultBean result = data.getResult();
        String headPic = result.getHeadPic();
        Uri parse = Uri.parse(headPic);
        wdTouxiang.setImageURI(parse);
        String nickName = result.getNickName();
        wdNicheng.setText(nickName);
    }

    @Override
    public void onUserFailure(Throwable e) {

    }

    @Override
    public void onSCTXSuccess(SCTXBean data) {

    }

    @Override
    public void onSCTXFailure(Throwable e) {

    }

    @Override
    public void onXGSJHSuccess(XiuGaiShouJIBean data) {

    }

    @Override
    public void onXGSJHFailure(Throwable e) {

    }

    @Override
    public void onXGSRSuccess(ShengRiBean data) {

    }

    @Override
    public void onXGSRFailure(Throwable e) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.photograph:
                //"点击了照相";
                //  6.0之后动态申请权限 摄像头调取权限,SD卡写入权限
                //判断是否拥有权限，true则动态申请
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MY_ADD_CASE_CALL_PHONE);
                } else {
                    //有权限,去打开摄像头
                    try {
                        takePhoto();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                dialog.dismiss();
                break;
            case R.id.photo:
                //"点击了相册";
                //  6.0之后动态申请权限 SD卡写入权限
                if (ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MY_ADD_CASE_CALL_PHONE2);

                } else {
                    //打开相册
                    choosePhoto();
                }
                dialog.dismiss();
                break;
            case R.id.cancel:
                dialog.dismiss();//关闭对话框
                break;
            default:break;
        }
    }

    private void takePhoto() throws IOException {
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        // 获取文件
        File file = createFileIfNeed("UserIcon.png");
        //拍照后原图回存入此路径下
        Uri uri;
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
            uri = Uri.fromFile(file);
        } else {
            /**
             * 7.0 调用系统相机拍照不再允许使用Uri方式，应该替换为FileProvider
             * 并且这样可以解决MIUI系统上拍照返回size为0
             * 的情况
             */
            uri = FileProvider.getUriForFile(getActivity(), "com.example.bobo.getphotodemo.fileprovider", file);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        startActivityForResult(intent, 1);
    }

    private File createFileIfNeed(String fileName) throws IOException {
        String fileA = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/nbinpic";
        File fileJA = new File(fileA);
        if (!fileJA.exists()) {
            fileJA.mkdirs();
        }
        File file = new File(fileA, fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    private void choosePhoto() {
        //这是打开系统默认的相册(就是你系统怎么分类,就怎么显示,首先展示分类列表)
        Intent picture = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(picture, 2);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode != Activity.RESULT_CANCELED) {

            String state = Environment.getExternalStorageState();
            if (!state.equals(Environment.MEDIA_MOUNTED)) return;
            // 把原图显示到界面上
            Tiny.FileCompressOptions options = new Tiny.FileCompressOptions();
            Tiny.getInstance().source(readpic()).asFile().withOptions(options).compress(new FileWithBitmapCallback() {
                @Override
                public void callback(boolean isSuccess, Bitmap bitmap, String outfile, Throwable t) {
                    saveImageToServer(bitmap, outfile);//显示图片到imgView上
                }
            });
        } else if (requestCode == 2 && resultCode == Activity.RESULT_OK
                && null != data) {
            try {
                Uri selectedImage = data.getData();//获取路径
                Tiny.FileCompressOptions options = new Tiny.FileCompressOptions();
                Tiny.getInstance().source(selectedImage).asFile().withOptions(options).compress(new FileWithBitmapCallback() {
                    @Override
                    public void callback(boolean isSuccess, Bitmap bitmap, String outfile, Throwable t) {
                        saveImageToServer(bitmap, outfile);
                    }
                });
            } catch (Exception e) {
                //"上传失败");
            }
        }
    }

    private void saveImageToServer(Bitmap bitmap, String outfile) {
        File file = new File(outfile);
        // TODO: 2018/12/4  这里就可以将图片文件 file 上传到服务器,上传成功后可以将bitmap设置给你对应的图片展示
        wdTouxiang.setImageBitmap(bitmap);
    }

    private String readpic() {
        String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/nbinpic/" + "UserIcon.png";
        return filePath;
    }
}
