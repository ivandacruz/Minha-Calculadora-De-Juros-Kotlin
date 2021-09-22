package br.com.uware.juros

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Envia dados para a próxima activity
        btnEnviar.setOnClickListener {
            if(etValor.text.isNotEmpty() && etQnt.text.isNotEmpty() && etJuros.text.isNotEmpty()){
                val intent = Intent(this, RespActivity::class.java).apply {
                    putExtra(
                        "mode",
                        if (rdbOptSimples.isChecked) "simples"
                        else "composto"
                    )
                    putExtra("valor", etValor.text.toString())
                    putExtra("qnt", etQnt.text.toString())
                    putExtra("juros", etJuros.text.toString())
                }
                startActivity(intent)
            }
            else{
                Toast.makeText(this, R.string.err_vazio, Toast.LENGTH_LONG).show()
            }
        }

        // Limpa dados do EditText
        btnLimpar.setOnClickListener {
            etValor.setText("")
            etQnt.setText("")
            etJuros.setText("")
            rdbOptSimples.isChecked = true
        }
    }
}