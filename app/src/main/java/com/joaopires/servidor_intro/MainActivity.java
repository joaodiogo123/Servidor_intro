package com.joaopires.servidor_intro;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ViewAsyncGenerator backgroundTask;
    protected Button b;
    protected TextView t;
    protected EditText IP;
    protected ListView lista;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        t = (TextView) findViewById(R.id.t);
        IP = (EditText) findViewById(R.id.ed_ip);
        b = (Button) findViewById(R.id.b);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backgroundTask = new ViewAsyncGenerator(MainActivity.this, b, "www.thomas-bayer.com", "/sqlrest/CUSTOMER", 80, t);
                backgroundTask.execute(1);
            }
        });
    }


    public class ViewAsyncGenerator extends AsyncTask<Integer, Double, String> {
        protected double percentage;
        protected Button b;
        protected TextView t;
        protected String savedLabel;
        protected int port;
        protected String host;
        protected String path;
        protected boolean ignorar;
        protected ListView lista;
        protected Context c;
        protected VerCostumer w;
        protected List<Integer> listaIds;

        public ViewAsyncGenerator(Context eu, Button botao, String host, String path, int port, TextView t) {
            b = botao;
            this.host = host;
            this.path = path;
            this.port = port;
            this.t = t;
            c = eu;
            lista = (ListView)findViewById(R.id.lista);
        }

        @Override // runs on the GUI thread
        protected void onPreExecute() {
            ignorar = false;
            savedLabel = b.getText().toString();
            //b.setText("Wait ... 0%");
            b.setEnabled(false);
        }

        @Override // runs on its own thread
        protected String doInBackground(Integer... args) { //Falar com a net e sacar o texto todo
            if (ignorar)
                return "";
            return Comunicar.contactar2(host, path, port);
        }

        @Override // runs on the GUI thread
        protected void onProgressUpdate(Double... percentComplete) {
            String theText;
            percentage = percentComplete[0];
            theText = "" + (int) (percentage * 100) + "%";
            b.setText(theText);
        }

        @Override // runs on the GUI thread
        protected void onPostExecute(String s) {
            listaIds = null;
            //b.setText(savedLabel);
            //b.setEnabled(true);
            ArrayList<String> aLista = new ArrayList<String>(10);
            try {
                SaxXmlParser<Integer, SaxXmlClientListHandler> oMeuParser =
                        new SaxXmlParser<Integer, SaxXmlClientListHandler>();
                oMeuParser.setHandler(new SaxXmlClientListHandler());
                listaIds = oMeuParser.parse(new StringReader(s));
            } catch (Exception e) {
                aLista.add(e.toString());  // devolve a excepção obtida
                t.setText("excepção : " + s);
            }
//            String osNomes = "";
//            Cliente umCliente = null;
//            for (int k = 0; k < listaClientes.size(); ++k) {
//                umCliente = listaClientes.get(k);
//                aLista.add(umCliente.getFirstName() + "-" + umCliente.getLastName() + "\n");
//                osNomes = osNomes + umCliente.getFirstName() + "-" + umCliente.getLastName() + "\n";
//            }
//            s = osNomes;
//            t.setText(s);
//            s = "Fist Name: " + listaClientes.get(0).getFirstName() + "\nLast Name: " + listaClientes.get(0).getLastName() + "\nCity: " + listaClientes.get(0).getCity() + "\nStreet: " + listaClientes.get(0).getStreet();
//            t.setText(s);
//            b.setEnabled(true);
//            for(int i = 0; i < listaIds.size(); i++){
//                String atual = t.getText().toString();
//                String a = listaIds.get(i).toString();
//                t.setText(atual + "\n" + a);
//            }
            ArrayAdapter<Integer> adaptador = new ArrayAdapter<Integer>(MainActivity.this, android.R.layout.simple_list_item_1, listaIds);
            lista.setAdapter(adaptador);
            lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(MainActivity.this, "www.thomas-bayer.com/sqlrest/CUSTOMER/" + position, Toast.LENGTH_SHORT).show();
                    w = new VerCostumer(c, "www.thomas-bayer.com", "/sqlrest/CUSTOMER/" + listaIds.get(position).toString(), 80);
                    w.execute();
                }
            });
            b.setEnabled(true);
        }
    }
}