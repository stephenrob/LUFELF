package uk.co.stephen_robinson.uni.lufelf.utilities;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import uk.co.stephen_robinson.uni.lufelf.R;

/**
 * @author James
 * displays a custom message dialog
 */
public class CustomMessages {

    /**
     * shows a message to the user
     * @param title the title of the dialog
     * @param message the content of the message
     * @param positiveButton the text for the positive button
     * @param negativeButton the text for the negative button
     * @param context the current context
     * @param dialogCallback the call back to use to return the result
     */
    public static void showMessage(String title,String message,String positiveButton, String negativeButton,Context context,final DialogCallback dialogCallback){
        final Dialog showMessage = new Dialog(context);
        showMessage.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        showMessage.requestWindowFeature(Window.FEATURE_NO_TITLE);
        showMessage.setContentView(R.layout.alert_layout);

        TextView titleText = (TextView) showMessage.findViewById(R.id.alert_title);
        TextView messageText = (TextView) showMessage.findViewById(R.id.alert_message);
        Button positive = (Button) showMessage.findViewById(R.id.alert_button_pos);
        Button negative = (Button) showMessage.findViewById(R.id.alert_button_neg);

        titleText.setText(title);
        messageText.setText(message);

        positive.setText(positiveButton);
        negative.setText(negativeButton);

        positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogCallback.messageComplete(1);
                showMessage.dismiss();
            }
        });
        negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogCallback.messageComplete(-1);
                showMessage.dismiss();
            }
        });
        showMessage.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                dialogCallback.messageComplete(0);
                showMessage.dismiss();
            }
        });
        showMessage.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                dialogCallback.messageComplete(0);
                showMessage.dismiss();
            }
        });
        showMessage.show();
    }

}
