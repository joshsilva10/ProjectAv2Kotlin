package com.example.projectav2

data class DataListener (
        val desc: String,
        val code: String)

class testBuilder {
    var desc: String = ""
    var code: String = ""


    fun build(): DataListener = DataListener(desc, code)
}

fun DataListener(block: testBuilder.() -> Unit): DataListener = testBuilder().apply(block).build()

fun testSend(): MutableList<DataListener> = mutableListOf(
        DataListener {
            code = "UZ102645BR"
            desc = "SSD"
        },
        DataListener {
            code = "KT524865BR"
            desc = "PENDRIVE"
        },
        DataListener {
            code = "UV584379BR"
            desc = "TECLADO WIRELESS"
        },
        DataListener {
            code = "GK854395BR"
            desc = "XIAOMI MI10"

        },
        DataListener {
            code = "TS458247BR"
            desc = "PS5"

        },
        DataListener {
            code = "UV584209UK"
            desc = "ADAPTADOR WIRELESS"

        },
        DataListener {
            code = "UF865719BR"
            desc = "NOTEBBOK GAMER"

        },
        DataListener {
            code = "FP859324SG"
            desc = "SMARTWATCH"

        }
)