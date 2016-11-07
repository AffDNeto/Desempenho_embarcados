package com.ufrgs.inf.embarcados.grupo4.desempenho_embarcados;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Menu extends AppCompatActivity {

    public Button btn_calc;
    public TextView txt_result, txt_err, txt_step;
    public Spinner spn_func, spn_mtd;

    private List<String> mtdList, funcList, stepList;

    private void integrate(){
        txt_result.setText("working");
        txt_err.setText(spn_mtd.getItemAtPosition(spn_mtd.getSelectedItemPosition()).toString()+","+txt_step.getText());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btn_calc = (Button)findViewById(R.id.button);
        btn_calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                integrate();
            }
        });

        txt_err = (TextView) findViewById(R.id.txt_err);
        txt_result = (TextView) findViewById(R.id.txt_result);
        txt_step = (TextView) findViewById(R.id.txt_step);

        // populate spinners
        spn_mtd = (Spinner) findViewById(R.id.spinner_mtd);
        mtdList = new ArrayList<String>();
        mtdList.add("Trapezios");
        mtdList.add("Simpsons");

        ArrayAdapter<String> mtdAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mtdList);
        spn_mtd.setAdapter(mtdAdapter);

        spn_func = (Spinner)findViewById(R.id.spinner_func);
        funcList = new ArrayList<String>();
        funcList.add("quadrado");
        funcList.add("seno");

        ArrayAdapter<String> funcAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, funcList);
        spn_func.setAdapter(funcAdapter);
    }
}
