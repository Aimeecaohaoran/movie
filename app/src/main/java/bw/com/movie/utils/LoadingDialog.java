package bw.com.movie.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import bw.com.movie.R;


public class LoadingDialog {
    String message = null;
    private Dialog dialog;
    private Context context;

    public LoadingDialog(Context context) {
        this.context = context;
    }

    public LoadingDialog(Context context, String message) {
        this.context = context;
        this.message = message;

    }

    public void show() {
        if (context != null) {
            dialog = new Dialog(context);
            View view = LayoutInflater.from(context).inflate(R.layout.load_contacts_dialog, null, false);
            TextView text_loading = (TextView) view.findViewById(R.id.text_loading);
            dialog.setContentView(view);
            dialog.show();
//            if (!StringUtils.isBlank(message)) {
//                text_loading.setText(message);
//            }
            WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
            lp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setAttributes(lp);
        }
    }

    public void dismiss() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

}
