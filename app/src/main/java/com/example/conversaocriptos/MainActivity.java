package com.example.conversaocriptos;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private WebClientTask mTask;
    private TextView mTvBtc;
    private TextView mTvEth;
    private TextView mTvLtc;
    private TextView mTvXrp;
    private ArrayList<Coin> cotacaoes;

    private  Coin  mBTC;
    private Coin  mETH;
    private Coin  mLTC ;
    private Coin  mXRP;


    /*meu*/
    private TextView valLTC;
    private double totalLTC = 0;
    private TextView valBTC;
    private double totalBTC = 0;
    private TextView valETH;
    private double totalETH = 0;
    private TextView valXRP;
    private double totalXRP = 0;
    private TextView valorConv;
    private Button converte;

    /**/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WebClient requisicao= new WebClient();
        startDownload();
        mTvBtc= findViewById(R.id.tv_value_btc);
        mTvLtc= findViewById(R.id.tv_value_ltc);
        mTvXrp= findViewById(R.id.tv_value_xrp);
        mTvEth= findViewById(R.id.tv_value_eth);

        /*valores*/
        valBTC = findViewById(R.id.BTC);
        valLTC = findViewById(R.id.LTC);
        valETH = findViewById(R.id.ETH);
        valXRP = findViewById(R.id.XRP);
        valorConv = findViewById(R.id.valor);


        converte= findViewById(R.id.btnConverte);


        converte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (valorConv!=null){
                    /* Bitcoin */
                    String valor_btc = valorConv.getText().toString();
                    double qtd_bitcoin = Double.parseDouble(valor_btc);
                    totalBTC = qtd_bitcoin * mBTC.getBuy();
                    valBTC.setText("BTC :" + totalBTC);

                    /*Litecoin*/
                    String valor_ltc = valorConv.getText().toString();
                    double qtd_ltc = Double.parseDouble(valor_ltc);
                    totalLTC = qtd_bitcoin * mLTC.getBuy();
                    valLTC.setText("LTC :" + totalLTC);

                    /*Etherun - acho que é assim que se escreve*/
                    String valor_eth = valorConv.getText().toString();
                    double qtd_eth = Double.parseDouble(valor_eth);
                    totalETH = qtd_bitcoin * mETH.getBuy();
                    valETH.setText("BTC :" + totalETH);

                    /*XRP - não sei o nome dessa*/
                    String valor_xrp = valorConv.getText().toString();
                    double qtd_xrp = Double.parseDouble(valor_xrp);
                    totalXRP = qtd_bitcoin * mXRP.getBuy();
                    valXRP.setText("XRP :" + totalXRP);

                } else if (valorConv==null){
                    String erro = valorConv.getText().toString();
                    valorConv.setText("@string/erro");
                }
                }
        });















    }




    /*Não é necessario mexer aqui */


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {

            Toast.makeText(this,"Loading Preços",Toast.LENGTH_LONG).show();
            startDownload();


        }
        return true;
    }


    public void startDownload() {
        if (mTask == null || mTask.getStatus() != AsyncTask.Status.RUNNING) {
            mTask = new WebClientTask();
            mTask.execute();
        }
    }

    class  WebClientTask extends AsyncTask<Void,Void, ArrayList<Coin>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(getApplicationContext(), "Pronto...", Toast.LENGTH_LONG).show();
        }

        @Override
        protected ArrayList<Coin> doInBackground(Void... strings) {
            ArrayList<Coin> coinsList=new ArrayList<>();
            mBTC = WebClient.getCoin("BTC");
            mETH = WebClient.getCoin("ETH");
            mLTC = WebClient.getCoin("LTC");
            mXRP = WebClient.getCoin("XRP");
            coinsList.add(mBTC);
            coinsList.add(mETH);
            coinsList.add(mLTC);
            coinsList.add(mXRP);
            cotacaoes=coinsList;
            Log.i("BTC",cotacaoes.get(0).getStringBuy());
            Log.i("ETH",cotacaoes.get(1).getStringBuy());
            Log.i("XRP",cotacaoes.get(2).getStringBuy());
            Log.i("LTC",cotacaoes.get(3).getStringBuy());
            return coinsList;
        }

        @Override
        protected void onPostExecute(ArrayList<Coin> coins) {
            super.onPostExecute(coins);
            //     showProgress(false);
            if (coins != null) {

                mTvBtc.setText(coins.get(0).getStringBuy());
                mTvEth.setText(coins.get(1).getStringBuy());
                mTvLtc.setText(coins.get(2).getStringBuy());
                mTvXrp.setText(coins.get(3).getStringBuy());
                Toast.makeText(getApplicationContext(), "Valores Atualizados", Toast.LENGTH_LONG).show();
            } else {

                Toast.makeText(getApplicationContext(), "Buscando...", Toast.LENGTH_LONG).show();
            }
        }
    }
}

