package ro.pub.cs.systems.pdsd.practicaltest02v3

import android.util.Log
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket
import java.net.URL

class CommunicationThread(private val socket: Socket) : Thread() {

    override fun run() {
        try {
            val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
            val writer = PrintWriter(socket.getOutputStream(), true)
            val word = reader.readLine()
            val urlString = "https://api.dictionaryapi.dev/api/v2/entries/en/$word"
            Log.d("PracticalTest", "[Thread] Requesting: $urlString")

            val responseData = URL(urlString).readText()

            Log.d("PracticalTest", "[Thread] Response: $responseData")
            val jsonArray = JSONArray(responseData)
            val firstEntry = jsonArray.getJSONObject(0)
            val meanings = firstEntry.getJSONArray("meanings")
            val firstMeaning = meanings.getJSONObject(0)
            val definitions = firstMeaning.getJSONArray("definitions")
            val firstDefinitionObj = definitions.getJSONObject(0)

            val definitionResult = firstDefinitionObj.getString("definition")
            Log.d("PracticalTest", "[Thread] Definition found: $definitionResult")
            writer.println(definitionResult)

        } catch (e: Exception) {
            Log.e("CalculatorApp", "[COMM] Error: ${e.message}")
        } finally {
            socket.close()
        }
    }
}