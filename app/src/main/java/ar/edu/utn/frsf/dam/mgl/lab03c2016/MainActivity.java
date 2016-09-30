package ar.edu.utn.frsf.dam.mgl.lab03c2016;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnCreateContextMenuListener {

    private List<Trabajo> trabajos;
    private ListView lvTrabajos;
    Adaptador adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvTrabajos = (ListView) findViewById(R.id.lvTrabajos);

        trabajos = Arrays.asList(Trabajo.TRABAJOS_MOCK);

        adaptador = new Adaptador(this, trabajos);

        lvTrabajos.setAdapter(adaptador);

    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuprincipal, menu);

        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.crearOferta) {
            Intent i = new Intent(this, NuevaOferta.class);
            startActivityForResult(i, 0);
            return true;
        }
        return false;
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Trabajo res = (Trabajo)data.getSerializableExtra("resultado");
        trabajos.add(res);
        adaptador.notifyDataSetChanged();

    }

    public class Adaptador extends BaseAdapter implements View.OnLongClickListener{
        LayoutInflater inflador;
        Context context;
        List<Trabajo> ofertas;


        Adaptador(Context context, List<Trabajo> items) {
            context = context;
            ofertas = items;
            inflador = LayoutInflater.from(context);
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
            return ofertas.get(position).getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View row = convertView;
            if (row == null) {
                row = inflador.inflate(R.layout.layout_row, parent, false);
            }

            ViewHolder holder = (ViewHolder) row.getTag();
            if (holder == null) {
                holder = new ViewHolder(row);
                row.setTag(holder);
            }

            row.setOnLongClickListener(this);

            holder.puesto.setText(ofertas.get(position).getCategoria().getDescripcion());
            holder.trabajo.setText(ofertas.get(position).getDescripcion());

            holder.horas.setText("Horas: " + ofertas.get(position).getHorasPresupuestadas());


            holder.precio.setText(" Max $/Hora: " +  Math.rint(ofertas.get(position).getPrecioMaximoHora())+" ");

            if (ofertas.get(position).getMonedaPago() == 1) {
                holder.bandera.setImageResource(R.drawable.us);
            } else if (ofertas.get(position).getMonedaPago() == 2) {
                holder.bandera.setImageResource(R.drawable.eu);
            } else if (ofertas.get(position).getMonedaPago() == 3) {
                holder.bandera.setImageResource(R.drawable.ico_ar);
            } else if (ofertas.get(position).getMonedaPago() == 4) {
                holder.bandera.setImageResource(R.drawable.uk);
            } else if (ofertas.get(position).getMonedaPago() == 5) {
                holder.bandera.setImageResource(R.drawable.br);
            }
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            String fecha = df.format(ofertas.get(position).getFechaEntrega());

            holder.fechaFin.setText("Fecha Fin: " + fecha);

            holder.ingles.setChecked(ofertas.get(position).getRequiereIngles());

            return row;
        }

        @Override
        public boolean onLongClick(View v) {

            ViewHolder vH= (ViewHolder) v.getTag();

            Toast.makeText(v.getContext(), "Selecciono el trabajo: " + vH.trabajo.getText() , Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    class ViewHolder {
        TextView puesto = null;
        TextView trabajo = null;
        TextView horas = null;
        TextView precio = null;
        ImageView bandera = null;
        TextView fechaFin = null;
        CheckBox ingles = null;

        ViewHolder(View row) {
            puesto = (TextView) row.findViewById(R.id.puesto);
            trabajo = (TextView) row.findViewById(R.id.trabajo);
            horas = (TextView) row.findViewById(R.id.horas);
            precio = (TextView) row.findViewById(R.id.precio);
            bandera = (ImageView) row.findViewById(R.id.banderaPais);
            fechaFin = (TextView) row.findViewById(R.id.fechaFin);
            ingles = (CheckBox) row.findViewById(R.id.checkBox);
        }
    }


}