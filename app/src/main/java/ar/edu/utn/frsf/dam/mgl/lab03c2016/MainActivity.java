package ar.edu.utn.frsf.dam.mgl.lab03c2016;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public class Adaptador extends BaseAdapter{
        LayoutInflater inflador;
        Context context;
        List<Trabajo> ofertas;


        Adaptador(Context context, List<Trabajo> items){
            context=context;
            ofertas=items;
            inflador= LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return ofertas.size();
        }

        @Override
        public Object getItem(int position) {
            return ofertas.get(position);
        }

        @Override
        public long getItemId(int position) {

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            return null;
        }
    }
}
