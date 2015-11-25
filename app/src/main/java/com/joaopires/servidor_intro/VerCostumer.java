package com.joaopires.servidor_intro;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class VerCostumer extends AsyncTask<Integer, Double, String> {
    protected double percentage;
    protected int carregado;
    protected String savedLabel;
    protected int port;
    protected String host;
    protected String path;
    protected boolean ignorar;
    protected ListView lista;
    protected Intent costumerActivity;
    protected Context quemChamou;

    public VerCostumer(Context a, String host, String path, int port) {
        this.host = host;
        this.path = path;
        this.port = port;
        quemChamou = a;
        this.carregado = carregado;
    }

    @Override // runs on the GUI thread
    protected void onPreExecute() {
        ignorar = false;
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
    }

    @Override // runs on the GUI thread
    protected void onPostExecute(String s) {
        List<Cliente> osClientes = null;
        //b.setText(savedLabel);
        //b.setEnabled(true);
        ArrayList<String> aLista = new ArrayList<String>(10);
        try {
            SaxXmlParser<Cliente, SaxXmlClienteHandler> oMeuParser =
                    new SaxXmlParser<Cliente, SaxXmlClienteHandler>();
            oMeuParser.setHandler(new SaxXmlClienteHandler());
            osClientes = oMeuParser.parse(new StringReader(s));
        } catch (Exception e) {
            aLista.add(e.toString());  // devolve a excepção obtida
            Log.w("excepcao:", s);
        }

        costumerActivity = new Intent(quemChamou, CostumerActivity.class);
        Cliente aux = new Cliente();
        costumerActivity.putExtra("costumer", osClientes.get(0));
        quemChamou.startActivity(costumerActivity);
    }
}
