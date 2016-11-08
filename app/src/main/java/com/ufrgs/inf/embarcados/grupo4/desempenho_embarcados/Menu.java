package com.ufrgs.inf.embarcados.grupo4.desempenho_embarcados;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Menu extends AppCompatActivity {

    public Button btn_calc;
    public TextView txt_result, txt_err, txt_time;
    public EditText edt_step;
    public Spinner spn_func, spn_mtd;

    private List<String> mtdList, funcList, stepList;

    private int function=0;
    private double[] intResult =
            {0.5 ,  2.4785, 10, 30937.5};
    private double[] intervalMin = {0, .0001, 0, 0},
                     intervalMax = {100, Math.PI/2, 10, 5};
    private void integrate(){
        function = spn_func.getSelectedItemPosition();
        int mtd = spn_mtd.getSelectedItemPosition();
        int steps = Integer.parseInt(edt_step.getText().toString());
        double result;

        long startTime = System.nanoTime();
        switch (mtd){
            case 0:
                result = intgTrapezio(intervalMin[function], intervalMax[function], steps);
                break;
            case 1:
            default:
                result = intgSimpsons(intervalMin[function], intervalMax[function], steps);
                break;
        }
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.

        txt_result.setText(String.format("%3.3e", result));
        txt_err.setText(String.format("%3.3e",intResult[function]-result));
        txt_time.setText(String.format("%3.3e",duration/1000000.0f));

    }

    private double f(double x){
        switch (function) {
            case 0:
                return exp(x);
            case 1:
                return sine(x);
            case 2:
                return one(x);
            case 3:
            default:
                return largePoly(x);
        }
    }

    private double sine(double x) {
        return Math.sin(1/x)+1;
    }

    private double exp(double x) {
        return Math.exp(- x * x / 2) / Math.sqrt(2 * Math.PI);
    }

    private double one(double x) {
        return 1;
    }

    private double largePoly(double x){
        return 30*Math.pow(x, 5) - 166*Math.pow(x, 4) - 542*Math.pow(x, 3) + 2838*Math.pow(x, 2) + 1520*x + 800;
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
        txt_time = (TextView) findViewById(R.id.txt_time);

        edt_step = (EditText) findViewById(R.id.edt_steps);

        // populate spinners
        spn_mtd = (Spinner) findViewById(R.id.spinner_mtd);
        mtdList = new ArrayList<>();
        mtdList.add("Trapezios");
        mtdList.add("Simpsons");

        ArrayAdapter<String> mtdAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mtdList);
        spn_mtd.setAdapter(mtdAdapter);

        spn_func = (Spinner)findViewById(R.id.spinner_func);
        funcList = new ArrayList<>();
        funcList.add("normal [0;100]");
        funcList.add("seno(1/x)+1 [0;PI/2]");
        funcList.add("1 [0;10]");
        funcList.add("large Poly [0;5]");

        ArrayAdapter<String> funcAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, funcList);
        spn_func.setAdapter(funcAdapter);
    }
}
