package santos.angel.practica9

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    private val userRef = FirebaseDatabase.getInstance().getReference("users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        var btnSave: Button = findViewById(R.id.save_button) as Button
        btnSave.setOnClickListener {
            saveMarkFromForm()
        }

        userRef.addChildEventListener(object: ChildEventListener{
            override fun onCancelled(error: DatabaseError) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}

            override fun onChildAdded(snapshot: DataSnapshot, p1: String?) {
                val usuario = snapshot.getValue(User::class.java)
                if (usuario != null) writeMark(usuario)
            }
        })

    }
    private fun saveMarkFromForm() {
        val name: EditText = findViewById(R.id.et_name) as EditText
        val lastName: EditText = findViewById(R.id.et_lastName) as EditText
        val age: EditText = findViewById(R.id.age) as EditText

        val user = User(
            name.text.toString(),
            lastName.text.toString(),
            age.text.toString()
        )
        userRef.push().setValue(user)
    }
    private fun writeMark(mark: User) {
        var list_textView: TextView = findViewById(R.id.list_textView) as TextView
        val text = list_textView.text.toString() + mark.toString() + "\n"
        list_textView.text = text
    }
}