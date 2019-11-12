package com.example.gameconsole;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import yuku.ambilwarna.AmbilWarnaDialog;

public class Coloring extends AppCompatActivity {
    private App mApp;
    private boolean isPaint=true;

    private TextView colorPicker;
    private int lastColor=Color.BLACK;

    private boolean populatedCells;
    protected Map<View, Rect> cells = new HashMap<>();

    private MainActivity.ThreadConnected thread;

    ImageButton brush,eraser,paintBucket,clearBucket;

    @Override
    protected void onResume() {
        super.onResume();
        mApp.setCurrentActivity(this);
    }

    @Override
    protected void onPause() {
        clearReferences();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearReferences();
    }

    private void clearReferences(){
        Activity currActivity = mApp.getCurrentActivity();
        if (this.equals(currActivity))
            mApp.setCurrentActivity(null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coloring);

        mApp=(App)this.getApplicationContext();
        TableLayout table = this.findViewById(R.id.square);

        colorPicker = this.findViewById(R.id.colorPicker);
        brush = this.findViewById(R.id.brush);
        eraser = this.findViewById(R.id.eraser);
        paintBucket = this.findViewById(R.id.paintBucket);
        clearBucket = this.findViewById(R.id.clearBucket);

        thread = (MainActivity.ThreadConnected)((App) getApplication()).getThread();
        GradientDrawable gradientDrawable = (GradientDrawable)colorPicker.getBackground();
        gradientDrawable.setColor(Color.BLACK);
        setResult(RESULT_OK);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        final int width = size.x-100;
        int curCellId = 0;
        for(int i = 0;i < 16;i++){
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            for (int j = 0;j < 16;j++){
                TextView t = new TextView(this);
                t.setWidth(width/16);
                t.setHeight(width/16);
                t.setId(curCellId);
                t.setBackground(getResources().getDrawable(R.drawable.cell_shape,null));
                t.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                tableRow.addView(t);
                curCellId++;
            }
            table.addView(tableRow, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        }
        table.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if(!populatedCells){
                    handlePopulate();
                    populatedCells = true;
                }
            }

            private Rect getRawCoordinatesRect(final View view) {
                int[] coords = new int[2];
                view.getLocationOnScreen(coords);
                Rect rect = new Rect();
                rect.left = coords[0];
                rect.top = coords[1];
                rect.right = rect.left + view.getWidth();
                rect.bottom = rect.top + view.getHeight();
                return rect;
            }

            void handlePopulate(){
                int curCellId = 0;
                for (int i = 0; i < 16; i++) {
                    for (int j = 0; j < 16; j++) {
                        final TextView tv = findViewById(curCellId);
                        Rect rect = getRawCoordinatesRect(tv);
                        cells.put(tv, rect);
                        curCellId++;
                    }
                }
            }
        });
        table.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    final int x = (int) motionEvent.getRawX();
                    final int y = (int) motionEvent.getRawY();
                    for (final Map.Entry<View, Rect> entry : cells.entrySet()) {
                        final View viewTemp = entry.getKey();
                        final Rect rect = entry.getValue();
                        if (rect.contains(x, y)) {
                            changeColor(viewTemp);
                        }
                    }
                    return true;
                }
                return false;
            }
        });

        brush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isPaint = true;
                Toast.makeText(getApplicationContext(),"Paint mode",Toast.LENGTH_SHORT).show();
            }
        });
        eraser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isPaint = false;
                Toast.makeText(getApplicationContext(),"Cleaning mode",Toast.LENGTH_SHORT).show();
            }
        });
        paintBucket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeColorAll(true);
                Toast.makeText(getApplicationContext(),"Paint all",Toast.LENGTH_SHORT).show();
            }
        });
        clearBucket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeColorAll(false);
                Toast.makeText(getApplicationContext(),"Clear all",Toast.LENGTH_SHORT).show();
            }
        });

        colorPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openColorPicker();
            }
        });
    }



    void changeColor(View view){
        GradientDrawable gradientDrawable = (GradientDrawable)view.getBackground();
        String idCell="";
        int cell=view.getId();
        int y=15-cell/16,x=cell%16;
        int id;
        if(y%2==0) id=y*16+15-x;
        else id=y*16+x;
        idCell+=255-id;
        int color;
        if(isPaint) {
            gradientDrawable.setColor(lastColor);
            color=lastColor;
        }
        else {
            gradientDrawable.setColor(getResources().getColor(R.color.colorText, null));
            color=Color.BLACK;
        }
        String Hex=Integer.toHexString(color);
        String total="$#"+Hex.substring(2)+" "+idCell+";";
        if (thread!=null)
        {
            thread.write("c".getBytes());
            thread.write(total.getBytes());
        }
    }

    void changeColorAll(boolean bucket){
        for (final Map.Entry<View, Rect> entry : cells.entrySet()) {
            final View viewTemp = entry.getKey();
            GradientDrawable gradientDrawable = (GradientDrawable)viewTemp.getBackground();

            if(bucket) {
                gradientDrawable.setColor(lastColor);
            }
            else {
                gradientDrawable.setColor(getResources().getColor(R.color.colorText,null));
            }

        }
        int color=(bucket)?lastColor:Color.BLACK;
        String Hex=Integer.toHexString(color);
        String total="$#"+Hex.substring(2)+";";
        if (thread!=null)
        {
            thread.write("B".getBytes());
            thread.write(total.getBytes());
        }
    }

    void openColorPicker(){
        AmbilWarnaDialog ambilWarnaDialog = new AmbilWarnaDialog(this, lastColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                lastColor = color;
                GradientDrawable gradientDrawable = (GradientDrawable)colorPicker.getBackground();
                gradientDrawable.setColor(lastColor);
            }
        });
        ambilWarnaDialog.show();
    }
}
