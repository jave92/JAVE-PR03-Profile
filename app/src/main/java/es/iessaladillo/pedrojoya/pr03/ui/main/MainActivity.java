package es.iessaladillo.pedrojoya.pr03.ui.main;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import es.iessaladillo.pedrojoya.pr03.R;
import es.iessaladillo.pedrojoya.pr03.data.local.Database;
import es.iessaladillo.pedrojoya.pr03.data.local.model.Avatar;

import static es.iessaladillo.pedrojoya.pr03.utils.ValidationUtils.isValidEmail;
import static es.iessaladillo.pedrojoya.pr03.utils.ValidationUtils.isValidPhone;
import static es.iessaladillo.pedrojoya.pr03.utils.ValidationUtils.isValidUrl;

public class MainActivity extends AppCompatActivity {

    private TextView lblAvatar,lblName,lblEmail,lblPhonenumber,lblAddress,lblWeb;
    private ImageView imgAvatar,imgEmail,imgPhonenumber,imgAddress,imgWeb;
    private EditText txtName,txtEmail,txtPhonenumber,txtAddress,txtWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        txtName.requestFocus();
        lblName.setTypeface(Typeface.DEFAULT_BOLD);
        imgAvatar.setImageResource(Database.getInstance().getDefaultAvatar().getImageResId());
        imgAvatar.setTag(Database.getInstance().getDefaultAvatar().getImageResId());
        lblAvatar.setText(Database.getInstance().getDefaultAvatar().getName());
        imgAvatar.setOnClickListener(v1 -> setRndAvatar());
        lblAvatar.setOnClickListener((View v1) -> setRndAvatar());
        txtName.setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus){
                lblName.setTypeface(Typeface.DEFAULT_BOLD);
            }else{
                lblName.setTypeface(Typeface.DEFAULT);
            }
        });
        txtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                validateName();
            }
        });
        txtEmail.setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus){
                lblEmail.setTypeface(Typeface.DEFAULT_BOLD);
            }else{
                lblEmail.setTypeface(Typeface.DEFAULT);
            }
        });
        txtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                validateEmail();
            }
        });
        txtPhonenumber.setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus){
                lblPhonenumber.setTypeface(Typeface.DEFAULT_BOLD);
            }else{
                lblPhonenumber.setTypeface(Typeface.DEFAULT);
            }
        });
        txtPhonenumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                validatePhonenumber();
            }
        });
        txtAddress.setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus){
                lblAddress.setTypeface(Typeface.DEFAULT_BOLD);
            }else{
                lblAddress.setTypeface(Typeface.DEFAULT);
            }
        });
        txtAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                validateAddress();
            }
        });
        txtWeb.setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus){
                lblWeb.setTypeface(Typeface.DEFAULT_BOLD);
            }else{
                lblWeb.setTypeface(Typeface.DEFAULT);
            }
        });
        txtWeb.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                validateWeb();
            }
        });
        txtWeb.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    save();
                    InputMethodManager imm =
                            (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    // Se ha gestionado el evento.
                    return true;
                }
                return false;
            }
        });
    }

    private void initViews(){
        imgAvatar = findViewById(R.id.imgAvatar);
        lblAvatar = findViewById(R.id.lblAvatar);
        lblName = findViewById(R.id.lblName);
        txtName = findViewById(R.id.txtName);
        lblEmail = findViewById(R.id.lblEmail);
        txtEmail = findViewById(R.id.txtEmail);
        imgEmail = findViewById(R.id.imgEmail);
        lblPhonenumber = findViewById(R.id.lblPhonenumber);
        txtPhonenumber = findViewById(R.id.txtPhonenumber);
        imgPhonenumber = findViewById(R.id.imgPhonenumber);
        lblAddress = findViewById(R.id.lblAddress);
        txtAddress = findViewById(R.id.txtAddress);
        imgAddress = findViewById(R.id.imgAddress);
        lblWeb = findViewById(R.id.lblWeb);
        txtWeb = findViewById(R.id.txtWeb);
        imgWeb = findViewById(R.id.imgWeb);
    }
    private boolean validateName(){
        if(txtName.getText().toString().isEmpty()){
            txtName.setError(getString(R.string.main_invalid_data));
            lblName.setEnabled(false);
            return false;
        }
        lblName.setEnabled(true);
        return true;
    }
    private boolean validateEmail(){
        if(!isValidEmail(txtEmail.getText().toString())){
            txtEmail.setError(getString(R.string.main_invalid_data));
            imgEmail.setEnabled(false);
            lblEmail.setEnabled(false);
            return false;
        }else{
            imgEmail.setEnabled(true);
            lblEmail.setEnabled(true);
            return true;
        }
    }
    private boolean validatePhonenumber(){
        if(!isValidPhone(txtPhonenumber.getText().toString())){
            txtPhonenumber.setError(getString(R.string.main_invalid_data));
            imgPhonenumber.setEnabled(false);
            lblPhonenumber.setEnabled(false);
            return false;
        }else{
            imgPhonenumber.setEnabled(true);
            lblPhonenumber.setEnabled(true);
            return true;
        }
    }
    private boolean validateAddress(){
        if(txtAddress.getText().toString().isEmpty()){
            txtAddress.setError(getString(R.string.main_invalid_data));
            imgAddress.setEnabled(false);
            lblAddress.setEnabled(false);
            return false;
        }else{
            imgAddress.setEnabled(true);
            lblAddress.setEnabled(true);
            return true;
        }
    }
    private boolean validateWeb(){
        if(!isValidUrl(txtWeb.getText().toString())){
            txtWeb.setError(getString(R.string.main_invalid_data));
            imgWeb.setEnabled(false);
            lblWeb.setEnabled(false);
            return false;
        }else{
            imgWeb.setEnabled(true);
            lblWeb.setEnabled(true);
            return true;
        }
    }
    private void setRndAvatar() {
        Avatar avt = Database.getInstance().getRandomAvatar();
        imgAvatar.setImageResource(avt.getImageResId());
        imgAvatar.setTag(avt.getImageResId());
        lblAvatar.setText(avt.getName());
    }
    // DO NOT TOUCH
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // DO NOT TOUCH
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnuSave) {
            save();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Checks if form is valid or not and shows a Snackbar accordingly
     **/
    private void save() {
        String message;
        boolean validName = validateName();
        boolean validEmail = validateEmail();
        boolean validPhone = validatePhonenumber();
        boolean validAddress = validateAddress();
        boolean validWeb = validateWeb();
        boolean valid = validName && validEmail && validPhone && validAddress && validWeb;
        if(valid){
            message = getString(R.string.main_saved_succesfully);
        }else{
            message = getString(R.string.main_error_saving);
        }

        Snackbar.make(txtName,message, Snackbar.LENGTH_LONG).show();
    }

}
