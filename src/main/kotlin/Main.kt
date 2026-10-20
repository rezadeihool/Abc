package ir.andy

import java.io.File;


fun main() {
    val sss = ProcessBuilder("ls").start()

    sss.inputStream.bufferedReader().forEachLine {
        println("ls command output >>>>>")
        println(it)
    }


    while (true){

    }
    val nativeDir  = File("native").absoluteFile
    val os = System.getProperty("os.name")
    val server = if (os.contains("windows",true)){
        File(nativeDir, "server.exe")
    }else{
        File(nativeDir, "server")
    }


    println("Working directory: ${System.getProperty("user.dir")}")
    println("Native directory: ${nativeDir.absolutePath}")
    println("Native directory exists: ${nativeDir.isDirectory}")
    println("Server path: ${server.absolutePath}")
    println("Server exists: ${server.exists()}")
    println("Server executable: ${server.canExecute()}")
    println("Native files: ${nativeDir.list()?.joinToString()}")







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