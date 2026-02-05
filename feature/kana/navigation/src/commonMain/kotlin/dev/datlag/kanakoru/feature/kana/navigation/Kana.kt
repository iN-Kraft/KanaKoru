package dev.datlag.kanakoru.feature.kana.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Kana : NavKey {

    val chars: Set<Char>

    @Serializable
    data object Hiragana : Kana {

        override val chars: Set<Char> = setOf(
            Char(direct = 'あ', latin = "a"),
            Char(direct = 'い', latin = "i"),
            Char(direct = 'う', latin = "u"),
            Char(direct = 'え', latin = "e"),
            Char(direct = 'お', latin = "o"),

            Char(direct = 'か', latin = "ka"),
            Char(direct = 'き', latin = "ki"),
            Char(direct = 'く', latin = "ku"),
            Char(direct = 'け', latin = "ke"),
            Char(direct = 'こ', latin = "ko"),

            Char(direct = 'さ', latin = "sa"),
            Char(direct = 'し', latin = "shi"),
            Char(direct = 'す', latin = "su"),
            Char(direct = 'せ', latin = "se"),
            Char(direct = 'そ', latin = "so"),

            Char(direct = 'た', latin = "ta"),
            Char(direct = 'ち', latin = "chi"),
            Char(direct = 'つ', latin = "tsu"),
            Char(direct = 'て', latin = "te"),
            Char(direct = 'と', latin = "to"),

            Char(direct = 'な', latin = "na"),
            Char(direct = 'に', latin = "ni"),
            Char(direct = 'ぬ', latin = "nu"),
            Char(direct = 'ね', latin = "ne"),
            Char(direct = 'の', latin = "no"),

            Char(direct = 'は', latin = "ha"),
            Char(direct = 'ひ', latin = "hi"),
            Char(direct = 'ふ', latin = "fu"),
            Char(direct = 'へ', latin = "he"),
            Char(direct = 'ほ', latin = "ho"),

            Char(direct = 'ま', latin = "ma"),
            Char(direct = 'み', latin = "mi"),
            Char(direct = 'む', latin = "mu"),
            Char(direct = 'め', latin = "me"),
            Char(direct = 'も', latin = "mo"),

            Char(direct = 'ら', latin = "ra"),
            Char(direct = 'り', latin = "ri"),
            Char(direct = 'る', latin = "ru"),
            Char(direct = 'れ', latin = "re"),
            Char(direct = 'ろ', latin = "ro"),

            Char(direct = 'や', latin = "ya"),
            Char(direct = 'ゆ', latin = "yu"),
            Char(direct = 'よ', latin = "yo"),

            Char(direct = 'わ', latin = "wa"),
            Char(direct = 'を', latin = "o"),
            Char(direct = 'ん', latin = "n"),
        )

    }

    @Serializable
    data object Katakana : Kana {

        override val chars: Set<Char> = setOf(
            Char(direct = 'ア', latin = "a"),
            Char(direct = 'イ', latin = "i"),
            Char(direct = 'ウ', latin = "u"),
            Char(direct = 'エ', latin = "e"),
            Char(direct = 'オ', latin = "o"),

            Char(direct = 'カ', latin = "ka"),
            Char(direct = 'キ', latin = "ki"),
            Char(direct = 'ク', latin = "ku"),
            Char(direct = 'ケ', latin = "ke"),
            Char(direct = 'コ', latin = "ko"),

            Char(direct = 'サ', latin = "sa"),
            Char(direct = 'シ', latin = "shi"),
            Char(direct = 'ス', latin = "su"),
            Char(direct = 'セ', latin = "se"),
            Char(direct = 'ソ', latin = "so"),

            Char(direct = 'タ', latin = "ta"),
            Char(direct = 'チ', latin = "chi"),
            Char(direct = 'ツ', latin = "tsu"),
            Char(direct = 'テ', latin = "te"),
            Char(direct = 'ト', latin = "to"),

            Char(direct = 'ナ', latin = "na"),
            Char(direct = 'ニ', latin = "ni"),
            Char(direct = 'ヌ', latin = "nu"),
            Char(direct = 'ネ', latin = "ne"),
            Char(direct = 'ノ', latin = "no"),

            Char(direct = 'ハ', latin = "ha"),
            Char(direct = 'ヒ', latin = "hi"),
            Char(direct = 'フ', latin = "fu"),
            Char(direct = 'ヘ', latin = "he"),
            Char(direct = 'ホ', latin = "ho"),

            Char(direct = 'マ', latin = "ma"),
            Char(direct = 'ミ', latin = "mi"),
            Char(direct = 'ム', latin = "mu"),
            Char(direct = 'メ', latin = "me"),
            Char(direct = 'モ', latin = "mo"),

            Char(direct = 'ラ', latin = "ra"),
            Char(direct = 'リ', latin = "ri"),
            Char(direct = 'ル', latin = "ru"),
            Char(direct = 'レ', latin = "re"),
            Char(direct = 'ロ', latin = "ro"),

            Char(direct = 'ヤ', latin = "ya"),
            Char(direct = 'ユ', latin = "yu"),
            Char(direct = 'ヨ', latin = "yo"),

            Char(direct = 'ワ', latin = "wa"),
            Char(direct = 'ヲ', latin = "o"),
            Char(direct = 'ン', latin = "n"),
        )

    }

    @Serializable
    data class Char(
        val direct: kotlin.Char,
        val latin: String,
    )

}
