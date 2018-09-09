package coin.rh.developer.lln.cotacaomoedas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<InfoEntity> dataList = new ArrayList<>();
    private RecyclerView.Adapter adapter;

    private String TAG = MainActivity.class.getSimpleName();
    private static String url = "https://api.promasters.net.br/cotacao/v1/valores";
    private ProgressDialog pDialog;

    ArrayList<String> coin_name = new ArrayList<String>(10);
    private String name[] = new String[6];

    ArrayList<String> coin_valor = new ArrayList<String>(10);
    private String valor[] = new String[6];

    ArrayList<String> coin_fonte = new ArrayList<String>(10);
    private String fonte[] = new String[6];


    Date dataHoraAtual = new Date();
    String data = new SimpleDateFormat("dd/MM/yyyy").format(dataHoraAtual);

    int logo[] = {R.drawable.dollar,
            R.drawable.euro,
            R.drawable.peso,
            R.drawable.libra,
            R.drawable.bitcoin};

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pref = getApplicationContext().getSharedPreferences("Pref", MODE_PRIVATE);
        editor = pref.edit();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.main_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = getAdapter();
        recyclerView.setAdapter(adapter);

        new getCotacao().execute();

        adapter.notifyDataSetChanged();
    }


    private void prepareDataList() {

        for (int i = 0; i <= 4; i++){
            dataList.add(new InfoEntity(logo[i], name[i], data, fonte[i].substring(0,12), i));
        }
    }

    private RecyclerView.Adapter getAdapter() {
        final LayoutInflater inflater = LayoutInflater.from(this);
        RecyclerView.Adapter adapter = new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = inflater.inflate(R.layout.card_list, parent, false);
                return new MyViewHolder(view);
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                MyViewHolder myHolder = (MyViewHolder) holder;
                myHolder.bindData(dataList.get(position));
            }

            @Override
            public int getItemCount() {
                return dataList.size();
            }
        };
        return adapter;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView moeda;
        TextView cotacao;
        ImageView logo;
        TextView fonte_txt;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.moeda = (TextView) itemView.findViewById(R.id.name_coin);
            this.cotacao = (TextView) itemView.findViewById(R.id.date_cot);
            this.logo = (ImageView) itemView.findViewById(R.id.logo_coin);
            this.fonte_txt = (TextView) itemView.findViewById(R.id.fonte);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();

                    if(pos != RecyclerView.NO_POSITION){

                        switch (pos){
                            case 0:
                                editor.putString("key_name", name[0]);
                                editor.putString("key_date", data);
                                editor.putString("key_price", valor[0]);
                                editor.putInt("key_position", pos);
                                editor.commit();
                                newActivity();
                                break;
                            case 1:
                                editor.putString("key_name", name[1]);
                                editor.putString("key_date", data);
                                editor.putString("key_price", valor[1]);
                                editor.putInt("key_position", pos);
                                editor.commit();
                                newActivity();
                                break;
                            case 2:
                                editor.putString("key_name", name[2]);
                                editor.putString("key_date", data);
                                editor.putString("key_price", valor[2]);
                                editor.putInt("key_position", pos);
                                editor.commit();
                                newActivity();
                                break;
                            case 3:
                                editor.putString("key_name", name[3]);
                                editor.putString("key_date", data);
                                editor.putString("key_price", valor[3]);
                                editor.putInt("key_position", pos);
                                editor.commit();
                                newActivity();
                                break;
                            case 4:
                                editor.putString("key_name", name[4]);
                                editor.putString("key_date", data);
                                editor.putString("key_price", valor[4]);
                                editor.putInt("key_position", pos);
                                editor.commit();
                                newActivity();
                                break;
                        }
                    }
                }
            });
        }

        public void bindData(InfoEntity infoEntity) {
            moeda.setText(infoEntity.getMoeda());
            cotacao.setText(infoEntity.getData());
            logo.setImageResource(infoEntity.getLogo());
            fonte_txt.setText(infoEntity.getPosition() > 3 ? fonte[4].substring(0,15) : fonte[0].substring(0,12));
        }

    }



    public void newActivity(){
        Intent intent = new Intent(MainActivity.this, InfoCoin.class);
        startActivity(intent);
    }




    public class getCotacao extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Aguarde...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    JSONObject jObjquery = jsonObj.getJSONObject("valores");

                    JSONObject USD = jObjquery.getJSONObject("USD");
                    JSONObject EUR = jObjquery.getJSONObject("EUR");
                    JSONObject ARS = jObjquery.getJSONObject("ARS");
                    JSONObject GBP = jObjquery.getJSONObject("GBP");
                    JSONObject BTC = jObjquery.getJSONObject("BTC");


                    String nameUSD = USD.getString("nome");
                    String valorUSD = USD.getString("valor");
                    String fonteUSD = USD.getString("fonte");

                    String nameEUR = EUR.getString("nome");
                    String valorEUR = EUR.getString("valor");
                    String fonteEUR = EUR.getString("fonte");

                    String nameARS = ARS.getString("nome");
                    String valorARS = ARS.getString("valor");
                    String fonteARS = ARS.getString("fonte");

                    String nameGBP = GBP.getString("nome");
                    String valorGBP = GBP.getString("valor");
                    String fonteGBP = GBP.getString("fonte");

                    String nameBTC = BTC.getString("nome");
                    String valorBTC = BTC.getString("valor");
                    String fonteBTC = BTC.getString("fonte");

                    String name_coin[] = {nameUSD, nameEUR, nameARS, nameGBP, nameBTC};
                    String valor_coin[] = {valorUSD, valorEUR, valorARS, valorGBP, valorBTC};
                    String fonte_coin[] = {fonteUSD, fonteEUR, fonteARS, fonteGBP, fonteBTC};

                    int l;
                    for (l = 0; l < 5; l++) {

                        coin_name.add(l, name_coin[l]);
                        name[l] = coin_name.get(l);

                        coin_valor.add(l, valor_coin[l]);
                        valor[l] = coin_valor.get(l);

                        coin_fonte.add(l, fonte_coin[l]);
                        fonte[l] = coin_fonte.get(l);

                    }


                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            prepareDataList();
            adapter.notifyDataSetChanged();

            pDialog.dismiss();
        }

    }
}
