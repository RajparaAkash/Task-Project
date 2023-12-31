package com.example.demo.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.demo.R;

public class GridActivity extends AppCompatActivity {

    private int rowCount, colCount;
    private TextView[][] boxes;
    GridLayout gridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);

        gridLayout = findViewById(R.id.gridLayout);
        colCount = getIntent().getIntExtra("columnCount", 0);
        rowCount = getIntent().getIntExtra("rowCount", 0);

        makeGrid();

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                final int row = i;
                final int col = j;

                boxes[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onBoxClick(row, col);
                    }
                });
            }
        }
    }

    private void makeGrid() {

        gridLayout.removeAllViews();
        gridLayout.setColumnCount(colCount);
        gridLayout.setRowCount(rowCount);

        boxes = new TextView[rowCount][colCount];

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                boxes[i][j] = new TextView(this);
                boxes[i][j].setBackgroundColor(Color.BLUE);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = 150;
                params.height = 150;
                params.setMargins(10, 10, 10, 10);
                params.rowSpec = GridLayout.spec(i);
                params.columnSpec = GridLayout.spec(j);
                boxes[i][j].setLayoutParams(params);
                gridLayout.addView(boxes[i][j]);
            }
        }
    }

    private void onBoxClick(int row, int col) {

        boxes[row][col].setBackgroundColor(Color.GREEN);

        if (row > 0) {
            boxes[row - 1][col].setBackgroundColor(Color.GREEN);
        }
        if (row < rowCount - 1) {
            boxes[row + 1][col].setBackgroundColor(Color.GREEN);
        }
        if (col > 0) {
            boxes[row][col - 1].setBackgroundColor(Color.GREEN);
        }
        if (col < colCount - 1) {
            boxes[row][col + 1].setBackgroundColor(Color.GREEN);
        }

        boxes[row][col].setOnClickListener(null);
        if (row > 0) boxes[row - 1][col].setOnClickListener(null);
        if (row < rowCount - 1) boxes[row + 1][col].setOnClickListener(null);
        if (col > 0) boxes[row][col - 1].setOnClickListener(null);
        if (col < colCount - 1) boxes[row][col + 1].setOnClickListener(null);
    }
}