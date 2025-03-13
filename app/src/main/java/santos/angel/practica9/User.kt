package santos.angel.practica9

data class User(var name: String? = null, var lastName: String? = null, var age: String? = null){
    override fun toString()= name+"\t"+lastName+"\t"+age
}
