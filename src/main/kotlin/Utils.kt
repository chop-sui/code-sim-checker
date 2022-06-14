import java.io.IOException
import java.io.RandomAccessFile

fun loadFileContent(path: String): String? {
    val bytes: ByteArray
    
    try {
        val f = RandomAccessFile(path, "r")
        bytes = ByteArray(f.length().toInt())
        f.read(bytes)
    } catch (e : IOException) {
        return null
    }
    
    return String(bytes)
}

