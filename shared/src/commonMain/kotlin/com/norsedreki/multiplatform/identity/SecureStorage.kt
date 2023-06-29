package com.norsedreki.multiplatform.identity

import com.russhwolf.settings.Settings

interface SecureStorage {

    suspend fun put(key: String, value: String)

    suspend fun get()
}

interface SerializedPrivateState {

    suspend fun get(): User?

    suspend fun put(user: User)

    suspend fun clear()
}

class FakeSerializedPrivateState : SerializedPrivateState {

    private var state: User? = null

    override suspend fun get(): User? {
        return state
    }

    override suspend fun put(user: User) {
        state = user
    }

    override suspend fun clear() {
        state = null
    }

}

class Impl() {

    init {

        val s = Settings()

        //s.put
    }
}