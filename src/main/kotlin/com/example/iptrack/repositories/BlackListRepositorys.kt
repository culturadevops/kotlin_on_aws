package com.example.iptrack.repositories

import com.example.iptrack.contants.FileSystemConstants.BLACK_LIST_FOLDER
import com.example.iptrack.entities.Blacklist
import com.opencsv.CSVParserBuilder
import com.opencsv.CSVReaderBuilder
import com.opencsv.CSVWriter
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.io.Reader
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths


@Repository
class BlackListRepositorys {

    @Value(value = "\${environment}")
    private lateinit var environment: String

    @Throws(java.lang.Exception::class)
    fun getBlackList(): List<String?> {
        val fileName = "src/$environment/resources/$BLACK_LIST_FOLDER"
        val myPath: Path = Paths.get(fileName)

        val path =
            Paths.get(
                ClassLoader.getSystemResource(BLACK_LIST_FOLDER).toURI()
            ).toAbsolutePath()


        val parser = CSVParserBuilder().withSeparator(',').build()

        Files.newBufferedReader(path, StandardCharsets.UTF_8).use { br ->
            CSVReaderBuilder(br).withCSVParser(parser)
                .build().use { reader ->
                    return getValueFromReader(reader.readAll())
                }
        }
    }

    fun getValueFromReader(reader: List<Array<String>>): List<String> {
        return if (!reader.isNullOrEmpty()) {
            reader[0].toList()
        } else {
            listOf()
        }
    }

    @Throws(Exception::class)
    fun csvWriterOneByOne(stringArray: List<String?>): List<String?> {
        val entries = stringArray.toTypedArray()
        val fileName = "src/$environment/resources/$BLACK_LIST_FOLDER"

        val reader = Paths.get(
            ClassLoader.getSystemResource(BLACK_LIST_FOLDER).toURI()
        ).toAbsolutePath()


        FileOutputStream(reader.toString()).use { fos ->
            OutputStreamWriter(fos, StandardCharsets.UTF_8).use { osw ->
                CSVWriter(osw).use { writer ->
                    writer.writeNext(
                        entries
                    )
                }
            }
        }

        return stringArray
    }
}