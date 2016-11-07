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

    private int function=0;
    private double[] intResult = {1.5708,  500.137681127712};
    private void integrate(){
        function = spn_func.getSelectedItemPosition();
        int mtd = spn_mtd.getSelectedItemPosition();
        int steps = Integer.parseInt(txt_step.getText().toString());
        double result;

        switch (mtd){
            case 0:
                result = intgTrapezio(0, 100, steps);
                break;
            case 1:
            default:
                result = intgSimpsons(0, 100, steps);
                break;
        }


        txt_result.setText(String.format("%6.6e", result));
        txt_err.setText(String.format("%6.6e",intResult[function]-result));
    }

    private double f(double x){
        switch (function) {
            case 0:
                return exp(x);
            case 1:
            default:
                return other(x);
        }
    }

    private double other(double x) {
        return Math.sin(x)+5;
    }

    private double exp(double x) {
        return Math.exp(- (x * x) / 2) / Math.sqrt(2 * Math.PI);
    }

    private double intgTrapezio(double a, double b, int N) {
        double h = (b - a) / N;              // step size
        double sum = 0.5 * (f(a) + f(b));    // area
        for (int i = 1; i < N; i++) {
            double x = a + h * i;
            sum = sum + f(x);
        }

        return sum * h;
    }

    private double intgSimpsons(double a, double b,int N) {
        double h = (b - a) / (N - 1);     // step size

        // 1/3 terms
        double sum = 1.0 / 3.0 * (f(a) + f(b));

        // 4/3 terms
        for (int i = 1; i < N - 1; i += 2) {
            double x = a + h * i;
            sum += 4.0 / 3.0 * f(x);
        }

        // 2/3 terms
        for (int i = 2; i < N - 1; i += 2) {
            double x = a + h * i;
            sum += 2.0 / 3.0 * f(x);
        }

        return sum * h;
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
        mtdList = new ArrayList<>();
        mtdList.add("Trapezios");
        mtdList.add("Simpsons");

        ArrayAdapter<String> mtdAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mtdList);
        spn_mtd.setAdapter(mtdAdapter);

        spn_func = (Spinner)findViewById(R.id.spinner_func);
        funcList = new ArrayList<>();
        funcList.add("normal");
        funcList.add("seno+5");

        ArrayAdapter<String> funcAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, funcList);
        spn_func.setAdapter(funcAdapter);
    }
}
