package ro.pub.cs.systems.pdsd.practicaltest02v3

import android.widget.TextView
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

class ClientThread (
    private val word: String,
    private val address: String,
    private val port: Int,
    private val resultTextView: TextView

): Thread() {
    override fun run() {
        val socket = Socket(address, port)
        val writer = PrintWriter(socket.getOutputStream(), true)
        val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
        try {
            writer.println(word)

        } catch (e: Exception) {
            resultTextView.post {
                resultTextView.text = "Eroare: ${e.message}"
            }
        }
        val result = reader.readLine()

        // 4. Actualizare UI
        resultTextView.post {
            resultTextView.text = "Definition: $result"
        }
        socket.close()
    }
}