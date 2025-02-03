package com.example.messengerapp.data.proto

import androidx.datastore.core.Serializer
import com.example.messengerapp.UserProto
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject


class UserSerializer @Inject constructor(): Serializer<UserProto> {
    override val defaultValue: UserProto
        get() = TODO("Not yet implemented")

    override suspend fun readFrom(input: InputStream): UserProto {
        return UserProto.parseFrom(input)
    }

    override suspend fun writeTo(t: UserProto, output: OutputStream) {
        t.writeTo(output)
    }
}