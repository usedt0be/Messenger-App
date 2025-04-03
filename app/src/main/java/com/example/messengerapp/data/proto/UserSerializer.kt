package com.example.messengerapp.data.proto

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.example.messengerapp.UserProto
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject


class UserSerializer @Inject constructor(): Serializer<UserProto> {
    override val defaultValue: UserProto
        get() = UserProto.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): UserProto {
         try {
             return UserProto.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto", exception)
        }
    }

    override suspend fun writeTo(t: UserProto, output: OutputStream) {
        t.writeTo(output)
    }
}