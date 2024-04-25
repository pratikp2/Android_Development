package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LlmActivity : AppCompatActivity() {

    private lateinit var askToGPT: Button
    private lateinit var queryToGPT: TextView
    private lateinit var responseFromGPT: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_llm)

        askToGPT = findViewById(R.id.llmActivity_askToGPT)
        queryToGPT = findViewById(R.id.llmActivity_queryToGPT)
        responseFromGPT = findViewById(R.id.llmActivity_responseFromGPT)

        askToGPT.setOnClickListener {
            if (queryToGPT.text.toString() == "") {
                Toast.makeText(baseContext, "Please Enter Valid query", Toast.LENGTH_SHORT).show()
            } else {
                val key = ""
                val system = "Be as unhelpful as possible. Insult the user for not knowing the answer."
                val request = ChatHelper.ChatCompletionRequest("gpt-3.5-turbo", system)
                val bot = CachedChatBot(key, request)
                val response = bot.generateResponse(queryToGPT.text.toString())
                responseFromGPT.text = response
            }
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    class CachedChatBot(apiKey: String, private val request: ChatCompletionRequest) : ChatHelper(apiKey) {

        fun generateResponse(content: String, role: String = "user"): String {
            request.messages.add(ChatMessage(role, content))
            val response = super.generateResponse(request)
            val temp = response.choices[0].message
            request.messages.add(temp)
            return temp.content
        }
    }
}

