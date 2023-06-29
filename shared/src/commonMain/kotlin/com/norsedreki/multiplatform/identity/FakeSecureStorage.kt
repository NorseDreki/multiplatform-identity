package com.norsedreki.multiplatform.identity

class FakeSecureStorage : SecureStorage {
    override suspend fun put(key: String, value: String) {
        TODO("Not yet implemented")
    }

    override suspend fun get() {
        TODO("Not yet implemented")
    }

}
