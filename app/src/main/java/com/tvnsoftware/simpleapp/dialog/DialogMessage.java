package com.tvnsoftware.simpleapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tvnsoftware.simpleapp.R;

/**
 * Created by TamHH on 6/2/2017.
 */

public class DialogMessage {
    public interface DialogMessageListener {
        void onButtonClick();
    }

    private Dialog dialog;
    private Context mContext;
    private DialogMessageListener listener;

    public DialogMessage(Context context, DialogMessageListener listener) {
        mContext = context;
        this.listener = listener;
    }

    public void showDialog(String message) {

        dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_error);

        TextView tvMessage = (TextView) dialog.findViewById(R.id.tv_dialog_error_message);
        tvMessage.setText(message);

        Button butDone = (Button) dialog.findViewById(R.id.btn_ok);
        butDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                listener.onButtonClick();
            }
        });
        dialog.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
