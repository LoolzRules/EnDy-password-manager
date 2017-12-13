package kz.programmer.loolzrules.endy.dataModels


class DBEntry(private val resource: String, private val username: String, private val password: String) {
    fun getResource() = resource
    fun getUsername() = username
    fun getPassword() = password
}