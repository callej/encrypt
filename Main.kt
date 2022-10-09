import java.io.File
import kotlin.system.exitProcess

data class EncryptionData(val mode: String, val alg: String, val key: Int, val data: String)

fun String.crypt(key: Int, op: (Int, Int) -> Int) = this.map { op(it.code, key).mod(Char.MAX_VALUE.code).toChar() }.joinToString("")

fun getEncryptionData(args: Array<String>): EncryptionData {
    val mode = if (args.contains("-mode") && args.lastIndex > args.indexOf("-mode")) args[args.indexOf("-mode") + 1] else "enc"
    val alg = if (args.contains("-alg") && args.lastIndex > args.indexOf("-alg")) args[args.indexOf("-alg") + 1] else "shift"
    val key = if (args.contains("-key") && args.lastIndex > args.indexOf("-key")) args[args.indexOf("-key") + 1].toInt() else 0
    var data = if (args.contains("-data") && args.lastIndex > args.indexOf("-data")) args[args.indexOf("-data") + 1] else ""
    if (!args.contains("-data") && args.contains("-in")) {
        if (args.lastIndex > args.indexOf("-in")) {
            data = File(args[args.indexOf("-in") + 1]).readText()
        } else {
            println("Error: No filename provided after the -in argument")
            exitProcess(3)
        }
    }
    if (mode !in listOf("enc", "dec")) {
        println("Error: No such mode!  (mode = $mode)")
        exitProcess(1)
    }
    return EncryptionData(mode, alg, key, data)
}

fun getMethod(mode: String, alg: String): (Int, Int) -> (Int) {
    val method: (Int, Int) -> Int = when (alg) {
        "shift" -> { c, k ->
            when (c) {
                in 'a'.code..'z'.code -> (c - 'a'.code + if (mode == "enc") k else -k).mod('z'.code - 'a'.code + 1) + 'a'.code
                in 'A'.code..'Z'.code -> (c - 'A'.code + if (mode == "enc") k else -k).mod('Z'.code - 'A'.code + 1) + 'A'.code
                else -> c
            }
        }
        "unicode" -> if (mode == "enc") Int::plus else Int::minus
        else -> { println("Error: No such algorithm!  (alg = $alg)"); exitProcess(2) }
    }
    return method
}

fun outputResult(args: Array<String>, data: String, key: Int, method: (Int, Int) -> Int) {
    if (args.contains("-out") && args.lastIndex > args.indexOf("-out")) {
        File(args[args.indexOf("-out") + 1]).writeText(data.crypt(key, method))
    } else {
        println(data.crypt(key, method))
    }
}

fun main(args: Array<String>) {
    val (mode, alg, key, data) = getEncryptionData(args)
    val method = getMethod(mode, alg)
    outputResult(args, data, key, method)
}