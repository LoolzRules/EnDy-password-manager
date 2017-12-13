package kz.programmer.loolzrules.endy.dataModels


class DBEntryEncrypted(private val resource: ByteArray, private val username: ByteArray, private val password: ByteArray) {
    fun getResource() = resource
    fun getUsername() = username
    fun getPassword() = password
}