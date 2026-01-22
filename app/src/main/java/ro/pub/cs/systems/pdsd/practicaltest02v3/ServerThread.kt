package ro.pub.cs.systems.pdsd.practicaltest02v3

import android.util.Log
import java.net.ServerSocket
import java.net.Socket

class ServerThread(private val port: Int) : Thread() {

    private var serverSocket: ServerSocket? = null

    override fun run() {
        try {
            serverSocket = ServerSocket(port)
            Log.d("DictionarApp", "[SERVER] Started on port $port")

            while (!currentThread().isInterrupted) {
                Log.d("DictionarApp", "[SERVER] Waiting for client...")
                val socket = serverSocket!!.accept()

                CommunicationThread(socket).start()
            }
        } catch (e: Exception) {
            Log.e("DictionarApp", "[SERVER] Error: ${e.message}")
        }
    }

    fun stopThread() {
        interrupt()
        serverSocket?.close()
    }
}