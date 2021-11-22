package de.comobi.chat

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import de.comobi.Constants

class ChatViewModel : ViewModel()
{
    init {
        getMessages()
    }

    private val _message = MutableLiveData("")
    val message: LiveData<String> = _message

    private var _messages = MutableLiveData(emptyList<Map<String, Any>>().toMutableList())
    val messages: LiveData<MutableList<Map<String, Any>>> = _messages

    /**
      Update the message value as user types
     **/

    fun updateMessage(message: String)
    {
        _message.value = message
    }

    /**
      Send Message to send to FireStore
     **/
    fun addMessage()
    {
        val message: String = _message.value ?: throw IllegalArgumentException("Message empty")
        if (message.isNotEmpty())
        {
            Firebase.firestore.collection(Constants.MESSAGES).document().set(
                hashMapOf(
                    Constants.MESSAGE to message,
                    Constants.SENT_BY to Firebase.auth.currentUser?.uid,
                    Constants.SENT_ON to System.currentTimeMillis()
                )
            )
                .addOnSuccessListener {
                _message.value = ""
            }
        }
    }

    /**
      Get the messages from FireStore
     **/

    private fun getMessages()
    {
        Firebase.firestore.collection(Constants.MESSAGES)
            .orderBy(Constants.SENT_ON)
            .addSnapshotListener { value, it ->
                if (it != null)
                {
                    Log.w(Constants.TAG, "Listen failed.", it)
                    return@addSnapshotListener
                }

                val list = emptyList<Map<String, Any>>().toMutableList()

                if (value != null)
                {
                    for (doc in value)
                    {
                        val data = doc.data
                        data[Constants.IS_CURRENT_USER] =
                            Firebase.auth.currentUser?.uid.toString() == data[Constants.SENT_BY].toString()

                        list.add(data)
                    }
                }

                updateMessages(list)
            }
    }

    /**
      Update the list after getting the details from firestore
     **/

    private fun updateMessages(list: MutableList<Map<String, Any>>)
    {
        _messages.value = list.asReversed()
    }


}