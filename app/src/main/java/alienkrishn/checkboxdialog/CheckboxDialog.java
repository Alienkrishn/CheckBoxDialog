package alienkrishn.checkboxdialog;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class CheckboxDialog {

    private static final String PREFS_NAME = "DialogPrefs";
    private static final String PREF_DONT_SHOW_AGAIN = "dontShowAgain";

    public static void showDialog(final Context context) {
        // Check preferences first
        if (shouldNotShowDialog(context)) {
            return;
        }

        // Create main container
        LinearLayout mainLayout = new LinearLayout(context);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setPadding(dpToPx(context, 16), dpToPx(context, 16), 
                              dpToPx(context, 16), dpToPx(context, 16));

        // Create title
        TextView titleView = createTitleView(context);
        mainLayout.addView(titleView);

        // Create message
        TextView messageView = createMessageView(context);
        mainLayout.addView(messageView);

        // Create checkbox
        final CheckBox dontShowAgain = createCheckbox(context);
        mainLayout.addView(dontShowAgain);

        // Create dialog
        final AlertDialog dialog = createDialog(context, mainLayout);

        // Create and add buttons
        addButtons(context, dialog, dontShowAgain, mainLayout);

        dialog.show();
    }

    private static boolean shouldNotShowDialog(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getBoolean(PREF_DONT_SHOW_AGAIN, false);
    }

    private static TextView createTitleView(Context context) {
        TextView titleView = new TextView(context);
        titleView.setText("Important Message");
        titleView.setTextColor(Color.parseColor("#FF5722")); // Orange
        titleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        titleView.setTypeface(null, android.graphics.Typeface.BOLD);
        titleView.setPadding(0, 0, 0, dpToPx(context, 10));
        return titleView;
    }

    private static TextView createMessageView(Context context) {
        TextView messageView = new TextView(context);
        messageView.setText("This is a test dialogue made by Alienkrishn with AIDE app");
        messageView.setTextColor(Color.BLACK);
        messageView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        messageView.setPadding(0, 0, 0, dpToPx(context, 20));
        return messageView;
    }

    private static CheckBox createCheckbox(Context context) {
        CheckBox checkBox = new CheckBox(context);
        checkBox.setText("Don't show this again");
        checkBox.setTextColor(Color.BLACK);
        return checkBox;
    }

    private static AlertDialog createDialog(final Context context, View view) {
        // Wrap in ScrollView for small screens
        ScrollView scrollView = new ScrollView(context);
        scrollView.addView(view);

        final AlertDialog dialog = new AlertDialog.Builder(context)
            .setView(scrollView)
            .setCancelable(false)
            .create();

        // Set rounded corners
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface di) {
                    View decorView = dialog.getWindow().getDecorView();
                    GradientDrawable shape = new GradientDrawable();
                    shape.setCornerRadius(dpToPx(context, 30));
                    shape.setColor(Color.WHITE);
                    decorView.setBackground(shape);
                }
            });

        return dialog;
    }

    private static void addButtons(final Context context, final AlertDialog dialog, 
                                   final CheckBox dontShowAgain, LinearLayout mainLayout) {
        LinearLayout buttonLayout = new LinearLayout(context);
        buttonLayout.setOrientation(LinearLayout.HORIZONTAL);
        buttonLayout.setWeightSum(3);
        buttonLayout.setPadding(0, dpToPx(context, 10), 0, 0);

        // Create Exit Button (Red)
        Button exitButton = createDialogButton(context, "Exit", "#F44336");
        exitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    savePreference(context, dontShowAgain.isChecked());
                    if (context instanceof Activity) {
                        ((Activity)context).finish();
                    }
                }
            });

        // Create Join Button (Blue)
        Button joinButton = createDialogButton(context, "Join", "#2196F3");
        joinButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    savePreference(context, dontShowAgain.isChecked());
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/nullnmods"));
                    context.startActivity(intent);
                }
            });

        // Create OK Button (Green)
        Button okButton = createDialogButton(context, "OK", "#4CAF50");
        okButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    savePreference(context, dontShowAgain.isChecked());
                    dialog.dismiss();
                }
            });

        buttonLayout.addView(exitButton);
        buttonLayout.addView(joinButton);
        buttonLayout.addView(okButton);
        mainLayout.addView(buttonLayout);
    }

    private static Button createDialogButton(Context context, String text, String color) {
        Button button = new Button(context);
        button.setText(text);
        button.setTextColor(Color.WHITE);

        GradientDrawable shape = new GradientDrawable();
        shape.setCornerRadius(dpToPx(context, 40));
        shape.setColor(Color.parseColor(color));
        button.setBackground(shape);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            0, 
            ViewGroup.LayoutParams.WRAP_CONTENT, 
            1.0f
        );
        params.setMargins(dpToPx(context, 5), 0, dpToPx(context, 5), 0);
        button.setLayoutParams(params);
        button.setPadding(0, dpToPx(context, 12), 0, dpToPx(context, 12));

        return button;
    }

    private static void savePreference(Context context, boolean dontShow) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putBoolean(PREF_DONT_SHOW_AGAIN, dontShow);
        editor.apply();
    }

    private static int dpToPx(Context context, int dp) {
        return (int) TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, 
            dp, 
            context.getResources().getDisplayMetrics()
        );
    }
}
