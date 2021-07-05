package com.netpaisa.aepsriseinlib;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import static android.text.Html.fromHtml;

public class MyDialog {


    public static Dialog getFullWaitDialog(Context ctx) {
        Dialog dialog = new Dialog(ctx);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if(dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }

        dialog.setContentView(R.layout.dialogprogress);
        return dialog ;
    }

    public static void errorDialog(Context context, String message) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alertdialogcustom);
        if(dialog.getWindow() != null ) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        TextView text = dialog.findViewById(R.id.msg_txv);
        text.setText(fromHtml(message+""));
        (dialog.findViewById(R.id.btn_ok)).setOnClickListener(view -> dialog.dismiss());
        dialog.show();
    }


    public static void show(Dialog dialog) {
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public static boolean exit(Dialog dialog) {
        if(dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            return true;
        }
        return false;
    }

}
