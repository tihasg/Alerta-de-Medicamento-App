package com.app.alertamedicamento.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.StrictMode;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.alertamedicamento.R;

@SuppressLint("NewApi")
public abstract class FuncoesAndroid {

    /*public static void escondeTeclado(final Activity activity) {
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }*/

    public static void visualizaTeclado(final Activity activity) {
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    /*public static void setContext(final Activity activity) {
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        //Mostrar Teclado
        //((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(editText, 0);

        //Ocultar Teclado
        //((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }*/

    public static void setListViewHeightBasedOnChildren(final ListView listView) {
        /*ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();
        int listWidth = listView.getMeasuredWidth();
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(
                    View.MeasureSpec.makeMeasureSpec(listWidth, View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = (int) ((totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1))));
        listView.setLayoutParams(params);
        listView.requestLayout();*/
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            if (listItem instanceof ViewGroup) {
                listItem.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    public static void hideKeyboard(Activity activity) {
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void hideKeyboard(Context context, EditText input) {
        input.setInputType(0);
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
    }

    public static void validaCampo(Context context, EditText editText, String msg) throws Exception {
        if (editText.getText().toString().trim().equals("")) {
            editText.requestFocus();
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            throw new Exception();
        } else {
            return;
        }
    }

    public static ProgressDialog createProgressDialog(Context mContext, String msg) {
        ProgressDialog dialog = new ProgressDialog(mContext);
        try {
            dialog.show();
        } catch (WindowManager.BadTokenException e) {

        }

        dialog.setIcon(R.drawable.ic_launcher);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.progressdialog);

        if (msg != null) {
            dialog.setMessage(msg);
        }

        return dialog;
    }

    public static boolean TemConexao(Context context) {
        boolean lblnRet = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isAvailable() && cm.getActiveNetworkInfo().isConnected()) {
                lblnRet = true;
            } else {
                lblnRet = false;
            }
        } catch (Exception e) {

        }
        return lblnRet;
    }

    public static void fecharApp(final Activity activity, boolean showDialog) {
        if (showDialog) {
            Builder msg = new Builder(activity);
            msg.setMessage("Fechar aplicação?");
            msg.setPositiveButton("Sim",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            activity.startActivity(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME));
                            activity.finish();
                            System.exit(0);
                        }
                    });
            msg.setNegativeButton("Nao", null);
            msg.show();
        } else {
            activity.startActivity(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME));
            activity.finish();
            System.exit(0);
        }

    }

    public static void setPolicy() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public static void setReadOnly(EditText edit, boolean readonly) {
        edit.setFocusable(!readonly);
        edit.setFocusableInTouchMode(!readonly);
        edit.setClickable(!readonly);
        edit.setLongClickable(!readonly);
    }

    public static void setReadOnly(DatePicker edit, boolean readonly) {
        edit.setFocusable(!readonly);
        edit.setFocusableInTouchMode(!readonly);
        edit.setClickable(!readonly);
        edit.setLongClickable(!readonly);
    }

    public static void setReadOnly(ListView lv, boolean readonly) {
        lv.setFocusable(!readonly);
        lv.setFocusableInTouchMode(!readonly);
        lv.setClickable(!readonly);
        lv.setLongClickable(!readonly);
    }

    public static void setReadOnly(Spinner sp, boolean readonly) {
        sp.setFocusable(!readonly);
        sp.setFocusableInTouchMode(!readonly);
        sp.setClickable(!readonly);
        sp.setLongClickable(!readonly);
    }

    public static void colorAlertDialogTitle(AlertDialog dialog, int color) {
        int dividerId = dialog.getContext().getResources().getIdentifier("android:id/titleDivider", null, null);
        if (dividerId != 0) {
            View divider = dialog.findViewById(dividerId);
            divider.setBackgroundColor(color);
        }

        int textViewId = dialog.getContext().getResources().getIdentifier("android:id/alertTitle", null, null);
        if (textViewId != 0) {
            TextView tv = (TextView) dialog.findViewById(textViewId);
            tv.setTextColor(color);
        }

        int iconId = dialog.getContext().getResources().getIdentifier("android:id/icon", null, null);
        if (iconId != 0) {
            ImageView icon = (ImageView) dialog.findViewById(iconId);
            icon.setColorFilter(color);
        }
    }

}
