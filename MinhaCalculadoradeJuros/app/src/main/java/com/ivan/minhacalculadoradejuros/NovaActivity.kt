package com.ivan.minhacalculadoradejuros

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlin.android.synthetic.main.activity_nova.*
import java.lang.Exception
import java.text.DecimalFormat
import kotlin.math.pow

class NovaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nova)

        try {
            val valor = intent.getStringExtra("valor").toDouble()
            val qnt = intent.getStringExtra("qnt").toInt()
            val juros = intent.getStringExtra("juros").toDouble()
            val mode = intent.getStringExtra("mode")
            var result: Resultados = Resultados()
            when(mode){
                "simples" -> result = calcSimp(valor,qnt,juros)
                "composto" -> result = calcComp(valor,qnt,juros)
            }
            // Exibindo resposta no TextView
            tvResp.text = getString(R.string.et_valor)+" "+valor+"\n"+
                    getString(R.string.resp_total)+" "+result.totalValor+"\n"+
                    getString(R.string.et_parcelas)+" "+qnt+"\n"+
                    getString(R.string.resp_parcela)+" "+result.parcela+"\n"+
                    getString(R.string.et_juros)+" "+juros+"%\n"+
                    getString(R.string.resp_juros)+" "+result.totalJuros+"%"+"\n"+
                    getString(R.string.resp_total_juro)+" "+result.totalJuroValor
        } catch (e: Exception){
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
        }
    }

    // Calcula juros e retorna Valor parc, valor total, total de juros e valor dos juros
    private fun calcSimp(valor: Double, qnt: Int, juros: Double): Resultados{
        val result: Resultados = Resultados()
        result.totalJuros = (qnt*juros).format()
        result.totalValor = (valor+(valor*(result.totalJuros.toDouble()/100))).format()
        result.parcela = (result.totalValor.toDouble()/qnt).format()
        result.totalJuroValor = (result.totalValor.toDouble()-valor).format()
        return result
    }
    private fun calcComp(valor: Double, qnt: Int, juros: Double): Resultados{
        val result: Resultados = Resultados()
        val expo = (1+(juros/100)).pow(qnt)
        result.totalValor = (valor*expo).format()
        result.parcela = (result.totalValor.toDouble()/qnt).format()
        val perc = valor/100
        result.totalJuros = ((result.totalValor.toDouble()-valor)/perc).format()
        result.totalJuroValor = (result.totalValor.toDouble()-valor).format()
        return result
    }

    // Formata número double para duas casas
    private fun Double.format(): String {
        val df = DecimalFormat("#.00")
        return df.format(this)
    }

    // Classe para receber os dados
    data class Resultados(
        var parcela: String = "",
        var totalValor: String = "",
        var totalJuroValor: String = "",
        var totalJuros: String = ""
    )
}