package com.example.demo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.demo.R;
import com.google.android.material.textfield.TextInputEditText;

public class EditTextActivity extends AppCompatActivity {

    TextInputEditText columnEdit, rowEdit;
    Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text);

        idBinding();

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(columnEdit.getText().toString())) {
                    Toast.makeText(EditTextActivity.this, "Enter Column Value", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(rowEdit.getText().toString())) {
                    Toast.makeText(EditTextActivity.this, "Enter Row Value", Toast.LENGTH_SHORT).show();
                    return;
                }

                int col_Count = Integer.parseInt(columnEdit.getText().toString());
                int row_Count = Integer.parseInt(rowEdit.getText().toString());

                Intent intent = new Intent(EditTextActivity.this, GridActivity.class);
                intent.putExtra("columnCount", col_Count);
                intent.putExtra("rowCount", row_Count);
                startActivity(intent);
            }
        });
    }

    private void idBinding() {
        columnEdit = findViewById(R.id.columnEdit);
        rowEdit = findViewById(R.id.rowEdit);
        submitBtn = findViewById(R.id.submitBtn);
    }
}