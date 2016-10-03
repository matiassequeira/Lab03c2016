package ar.edu.utn.frsf.dam.mgl.lab03c2016;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NuevaOferta extends AppCompatActivity implements View.OnClickListener{

    private Spinner espiner;
    Button btnGuardar;
    EditText txtOferta;
    String oferta;
    RadioGroup rdGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_nueva_oferta);

        llenarspinner();

        txtOferta = (EditText) findViewById(R.id.TextOfertaIngresada);
        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        btnGuardar.setOnClickListener(this);
    }

    private void llenarspinner() {
        List<Categoria> categorias = Arrays.asList(Categoria.CATEGORIAS_MOCK);
        List<String> string = new ArrayList<>();
        for (Categoria cat: categorias ) {
            string.add(cat.toString());
        }
        ArrayAdapter<String> adaptadorEspiner = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, string);

        adaptadorEspiner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        espiner= (Spinner) findViewById(R.id.spinnerCategoria);
        espiner.setAdapter(adaptadorEspiner);
    }

    @Override
    public void onClick(View v) {
        oferta = txtOferta.toString();

        Categoria categoria= (Categoria) espiner.getSelectedItem();
        rdGroup = (RadioGroup) findViewById(R.id.rdGroup);

        if(oferta.equals("") || categoria==null ){
            Toast.makeText(v.getContext(), "Falta completar algún campo" , Toast.LENGTH_SHORT).show();
        }
        else {
            Trabajo trabajo = new Trabajo();
            trabajo.setCategoria(categoria);
            trabajo.setDescripcion(oferta);
            //1 US$ 2Euro 3 AR$- 4 Libra 5 R$
            switch (rdGroup.getCheckedRadioButtonId()) {
                case R.id.pesosAr:
                    trabajo.setMonedaPago(3);
                    break;
                case R.id.usd:
                    trabajo.setMonedaPago(1);
                    break;
                case R.id.euros:
                    trabajo.setMonedaPago(2);
                    break;
                case R.id.libras:
                    trabajo.setMonedaPago(4);
                    break;
                case R.id.reales:
                    trabajo.setMonedaPago(5);
                    break;
            }

            Intent i = getIntent();
            // seteamos el resultado a enviar a la actividad principal.
            i.putExtra("resultado", trabajo);
            // invocamos al método de activity setResult
            setResult(RESULT_OK, i);
            // Finalizamos la Activity para volver a la anterior
            finish();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("oferta",txtOferta.toString());
        outState.putInt("espiner",espiner.getSelectedItemPosition());
        outState.putInt("rdGroup", rdGroup.getCheckedRadioButtonId());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        txtOferta.setText(savedInstanceState.getString("oferta"));

        espiner.setSelection(savedInstanceState.getInt("espiner"));

        rdGroup.getChildAt(savedInstanceState.getInt("rdGroup")).setSelected(true);
    }
}
