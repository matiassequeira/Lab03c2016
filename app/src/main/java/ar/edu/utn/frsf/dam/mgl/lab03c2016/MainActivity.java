package ar.edu.utn.frsf.dam.mgl.lab03c2016;

import android.content.Context;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
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

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener {

    private List<Trabajo> trabajos;
    private ListView lvTrabajos;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvTrabajos = (ListView) findViewById(R.id.lvTrabajos);

        trabajos = Arrays.asList(Trabajo.TRABAJOS_MOCK);

        Adaptador adaptador = new Adaptador(this, trabajos);

        lvTrabajos.setAdapter(adaptador);
        lvTrabajos.setOnItemLongClickListener(this);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, "Selecciono el trabajo: " + trabajos.get(position).getDescripcion(), Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://ar.edu.utn.frsf.dam.mgl.lab03c2016/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://ar.edu.utn.frsf.dam.mgl.lab03c2016/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }


    public class Adaptador extends BaseAdapter {
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