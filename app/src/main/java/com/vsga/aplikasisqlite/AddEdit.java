package com.vsga.aplikasisqlite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.vsga.aplikasisqlite.helper.DBHelper;

public class AddEdit extends AppCompatActivity {

    EditText etID, etName, etAddress;
    Button btnSubmit, btnCancel;
    DBHelper SQLite = new DBHelper(this);
    String id, name, address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etID = findViewById(R.id.txtID);
        etName = findViewById(R.id.txtName);
        etAddress = findViewById(R.id.txtAddress);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnCancel = findViewById(R.id.btnCancel);

        id = getIntent().getStringExtra(MainActivity.TAG_ID);
        name = getIntent().getStringExtra(MainActivity.TAG_NAME);
        address = getIntent().getStringExtra(MainActivity.TAG_ADDRESS);

        if (id == null || id.equals("")){
            setTitle("Add Data");
        } else {
            setTitle("Edit Data");
            etID.setText(id);
            etName.setText(name);
            etAddress.setText(address);
        }

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (etID.getText().toString().equals("")){
                        save();
                    } else {
                        edit();
                    }
                } catch (Exception e) {
                    Log.e("Submit", e.toString());
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blank();
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                blank();
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Mengosongkan semua Edit Text
    private void blank(){
        etName.requestFocus();
        etID.setText(null);
        etName.setText(null);
        etAddress.setText(null);
    }

    private void save(){
        if (String.valueOf(etName.getText()) == null || String.valueOf(etName.getText()).equals("") ||
                String.valueOf(etAddress.getText()) == null || String.valueOf(etAddress.getText()).equals("")){
            Toast.makeText(this, "Kolom tidak boleh kosong", Toast.LENGTH_SHORT).show();
        } else {
            SQLite.insert(etName.getText().toString().trim(), etAddress.getText().toString().trim());
            blank();
            finish();
        }
    }

    private void edit(){
        if (String.valueOf(etName.getText()) == null || String.valueOf(etName.getText()).equals("") ||
                String.valueOf(etAddress.getText()) == null || String.valueOf(etAddress.getText()).equals("")){
            Toast.makeText(this, "Kolom tidak boleh kosong", Toast.LENGTH_SHORT).show();
        } else {
            SQLite.update(Integer.parseInt(etID.getText().toString().trim()), etName.getText().toString().trim(),
                    etAddress.getText().toString().trim());
            blank();
            finish();
        }
    }
}