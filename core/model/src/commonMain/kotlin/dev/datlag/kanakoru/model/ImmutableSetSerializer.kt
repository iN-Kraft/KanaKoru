package dev.datlag.kanakoru.model

import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.toImmutableSet
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.SetSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

class ImmutableSetSerializer<T>(
    private val elementSerializer: KSerializer<T>
) : KSerializer<ImmutableSet<T>> {

    override val descriptor: SerialDescriptor = SetSerializer(elementSerializer).descriptor

    override fun serialize(encoder: Encoder, value: ImmutableSet<T>) {
        encoder.encodeSerializableValue(SetSerializer(elementSerializer), value.toSet())
    }

    override fun deserialize(decoder: Decoder): ImmutableSet<T> {
        return decoder.decodeSerializableValue(SetSerializer(elementSerializer)).toImmutableSet()
    }
}