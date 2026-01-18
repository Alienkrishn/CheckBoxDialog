package alienkrishn.checkboxdialog;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Immediately show the dialog when activity starts
        CheckboxDialog.showDialog(this);
    }
}
