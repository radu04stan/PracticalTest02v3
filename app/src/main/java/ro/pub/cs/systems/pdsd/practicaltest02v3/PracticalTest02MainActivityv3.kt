package ro.pub.cs.systems.pdsd.practicaltest02v3

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ro.pub.cs.systems.pdsd.practicaltest02v3.ui.theme.PracticalTest02v3Theme

class PracticalTest02MainActivityv3 : AppCompatActivity() {
    private lateinit var resultTextView: TextView
    private lateinit var wordEditText: EditText
    private var serverThread: ServerThread? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practical_test02v3_main)

        wordEditText = findViewById(R.id.word_edit_text)
        val resultTv = findViewById<TextView>(R.id.result_text_view)
        val requestButton: Button = findViewById(R.id.request_button)
        val serverPortEt = findViewById<EditText>(R.id.server_port_edit_text)
        val startServerBtn = findViewById<Button>(R.id.server_start_button)

        val clientAddrEt = findViewById<EditText>(R.id.client_address_edit_text)
        val clientPortEt = findViewById<EditText>(R.id.client_port_edit_text)
        startServerBtn.setOnClickListener {
            val port = serverPortEt.text.toString().toIntOrNull()
            if (port != null) {
                serverThread = ServerThread(port)
                serverThread?.start()
                Toast.makeText(this, "Server Started", Toast.LENGTH_SHORT).show()
            }
        }
        fun startClient(operator: String) {
            val addr = clientAddrEt.text.toString()
            val portStr = clientPortEt.text.toString()
            val word = wordEditText.text.toString()

            if (addr.isNotEmpty() && portStr.isNotEmpty() && word.isNotEmpty()) {
                val client = ClientThread(word, addr, portStr.toInt(),  resultTv)
                client.start()
            } else {
                Toast.makeText(this, "Completează toate câmpurile!", Toast.LENGTH_SHORT).show()
            }
        }
        requestButton.setOnClickListener {
            val word = wordEditText.text.toString()
            startClient(word)
        }
    }
}
