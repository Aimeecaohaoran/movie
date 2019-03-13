package bw.com.movie.app;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Environment;
import android.util.Log;
import android.view.WindowManager;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class Myapp extends Application {
    //绘制页面时参照的设计图宽度
    public final static float DESIGN_WIDTH = 750;
    private static Context context;

    @SuppressLint("NewApi")
    @Override
    public void onCreate() {
        super.onCreate();
        //设置磁盘缓存
        DiskCacheConfig diskCacheConfig=DiskCacheConfig.newBuilder(this)
                .setBaseDirectoryName("images")
                .setBaseDirectoryPath(Environment.getExternalStorageDirectory())
                .build();
        //设置磁盘缓存的配置生成文件
        ImagePipelineConfig config=ImagePipelineConfig.newBuilder(this)
                .setMainDiskCacheConfig(diskCacheConfig)
                .build();
        Fresco.initialize(this,config);
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        resetDensity();
    }
    public void resetDensity(){
        Point size = new Point();
        ((WindowManager)getSystemService(WINDOW_SERVICE)).getDefaultDisplay().getSize(size);
        getResources().getDisplayMetrics().xdpi = size.x/DESIGN_WIDTH*72f;
        getApplication();
    }


    public static void saveBitmap(Bitmap bitmap, String path, int quality) throws IOException {
        String dir = path.substring(0, path.lastIndexOf("/"));
        File dirFile = new File(dir);
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            try {
                if (!dirFile.mkdirs()) {
                    return;
                }
            } catch (Exception e) {
                Log.e("TAG", e.getMessage());
            }

        }
        FileOutputStream out;
        try {
            out = new FileOutputStream(path);
            if (bitmap.compress(Bitmap.CompressFormat.PNG, quality, out)) {
                out.flush();
                out.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e("TAG", e.getMessage());
        }
    }

    public static Context getApplication() {
        return context;
    }
}
