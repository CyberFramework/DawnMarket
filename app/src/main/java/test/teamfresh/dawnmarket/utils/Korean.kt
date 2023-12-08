package test.teamfresh.dawnmarket.utils

fun String.addPostposition(existCoda: String, notExistCoda: String): String  {
    val lastChar: Char = this[this.length - 1]

    // 한글의 시작(가)이나 끝(힣) 범위 초과시 오류
    if(lastChar.code < 0xAC00 || lastChar.code > 0xD7A3) {
        return this
    }
    val postposition: String =
        if ((lastChar.code - 0xAC00) % 28 > 0) existCoda
        else notExistCoda

    return this + postposition
}