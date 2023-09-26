package com.example.escuchadorconimplements;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;

public class MainActivity extends Activity implements View.OnClickListener, View.OnKeyListener {

    private EditText etcN1;
    private EditText etcN2;
    private EditText etcN3;
    private TextView resultado;
    private Button objcalcular;
    private Handler handler = new Handler(Looper.getMainLooper());

    // Variable para controlar el enfoque en el campo de resultado
    private boolean allowResultFieldFocus = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Enlazar los elementos de la interfaz de usuario con variables en Java
        resultado = findViewById(R.id.resul);
        etcN1 = findViewById(R.id.etn1);
        etcN2 = findViewById(R.id.etn2);
        etcN3 = findViewById(R.id.etntres);
        objcalcular = findViewById(R.id.btncalcular);

        // Establecer listeners para los eventos de teclado y clic del botón
        etcN1.setOnKeyListener(this);
        etcN2.setOnKeyListener(this);
        etcN3.setOnKeyListener(this);
        objcalcular.setOnClickListener(this);

        // Establecer el foco inicial en el primer campo de entrada
        etcN1.requestFocus();
    }

    @Override
    public void onClick(@NonNull View v) {
        // Manejar el evento de clic del botón "Calcular"
        if (v.getId() == R.id.btncalcular) {
            ejecutarMiFuncion();
        }
    }

    public boolean onKey(View v, int keyCode, @NonNull KeyEvent event) {
        // Manejar eventos de teclado (cuando se presiona "Enter")
        if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
            if (v == etcN1) {
                // Agregar un retraso de 2 segundos antes de mover el enfoque al segundo campo
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        etcN2.requestFocus(); // Mover el enfoque al segundo campo
                    }
                }, 2000); // 2000 ms = 2 segundos
                return true; // Indicar que el evento de teclado fue manejado
            } else if (v == etcN2) {
                // Agregar un retraso de 2 segundos antes de mover el enfoque al tercer campo
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        etcN3.requestFocus(); // Mover el enfoque al tercer campo
                    }
                }, 2000); // 2000 ms = 2 segundos
                return true; // Indicar que el evento de teclado fue manejado
            } else if (v == etcN3 && allowResultFieldFocus) {
                // Mover directamente el enfoque al botón "Calcular"
                objcalcular.requestFocus(); // Mover el enfoque al botón "Calcular"
                return true; // Indicar que el evento de teclado fue manejado
            }
        }
        return false; // No se maneja el evento de teclado
    }

    private void ejecutarMiFuncion() {
        try {
            // Obtener los valores de los campos de entrada y convertirlos a números enteros
            int valorN1 = Integer.parseInt(etcN1.getText().toString());
            int valorN2 = Integer.parseInt(etcN2.getText().toString());
            int valorN3 = Integer.parseInt(etcN3.getText().toString());

            // Encontrar el número mayor entre los tres valores ingresados
            int numeroMayor = valorN1;

            if (valorN2 > numeroMayor) {
                numeroMayor = valorN2;
            }

            if (valorN3 > numeroMayor) {
                numeroMayor = valorN3;
            }

            // Mostrar el resultado en el campo de resultado
            resultado.setText("El mayor es: " + numeroMayor);
        } catch (NumberFormatException e) {
            // Manejar una excepción si los campos están vacíos o no contienen números válidos
            resultado.setText("Hay campos vacíos o no válidos");
        }

        // Después de calcular, evitar que el enfoque se mueva al campo de resultado
        allowResultFieldFocus = false;
    }
}
