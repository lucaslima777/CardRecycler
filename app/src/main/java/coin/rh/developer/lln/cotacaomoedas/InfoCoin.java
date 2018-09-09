package coin.rh.developer.lln.cotacaomoedas;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class InfoCoin extends AppCompatActivity {

    private TextView Nome;
    private TextView Data;
    private TextView Valor;
    private TextView Historia;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_coin);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        pref = getApplicationContext().getSharedPreferences("Pref", Context.MODE_PRIVATE);
        editor = pref.edit();

        int position = pref.getInt("key_position", 1);
        String nome = pref.getString("key_name", null);
        String data = pref.getString("key_date", null);
        String valor = pref.getString("key_price", null);

        getSupportActionBar().setTitle(nome);
        Nome = findViewById(R.id.nome);
        Data = findViewById(R.id.data);
        Valor = findViewById(R.id.valor);
        Historia = findViewById(R.id.historia);

        Nome.setText("Nome: " + nome);
        Data.setText("Data Cotação: " + data);
        Valor.setText("Valor: " + valor);

        switch (position){
            case 0:
                Historia.setText(R.string.dollar);
                break;
            case 1:
                Historia.setText(R.string.euro);
                break;
            case 2:
                Historia.setText(R.string.peso);
                break;
            case 3:
                Historia.setText(R.string.libra);
                break;
            case 4:
                Historia.setText(R.string.bitcoin);
                break;
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    protected void onDestroy() {
        editor.clear();
        editor.remove("key_position");
        editor.remove("key_name");
        editor.remove("key_date");
        editor.remove("key_price");
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        editor.clear();
        editor.remove("key_position");
        editor.remove("key_name");
        editor.remove("key_date");
        editor.remove("key_price");
        super.onBackPressed();
    }
}
