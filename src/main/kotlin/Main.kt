package ir.andy

import java.io.File;


fun main() {
    val nativeDir  = File("native").absoluteFile
    val os = System.getProperty("os.name")
    val server = if (os.contains("windows",true)){
        File(nativeDir, "server.exe")
    }else{
        File(nativeDir, "server")
    }





    val startCommand : Array<String> = if (os.contains("windows",true)){
        println("{Windows Detected >> $os}")
        arrayOf(server.absolutePath,File(nativeDir,"config.yaml").absolutePath)


    }
    else{
        println("{Linux Detected >> $os}")
        arrayOf(server.absolutePath,File(nativeDir,"config.yaml").absolutePath)
    }

    val process = ProcessBuilder(*startCommand)
        .directory(nativeDir)
        .redirectErrorStream(true)
        .inheritIO()
        .start()


    process.errorStream.bufferedReader().use {
        println("Error -> ${it.readText()}")
    }

    process.inputStream.bufferedReader().forEachLine {
        println("Reading from the process itself >>")
        println(it)
    }
    val code = process.waitFor()
    println("process ended with $code")
}