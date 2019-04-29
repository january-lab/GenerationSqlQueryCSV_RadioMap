import model.Record
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

const val SAMPLE_RSSI_ID = 0
const val SAMPLE_ID = 1
const val SAMPLE_POS_ID = 2
const val NAME_BEACON = 3
const val MAC_BEACON = 4
const val X = 5
const val Y = 6
const val VALUE_RSSI = 7
// $ kotlin GenerationSQL <fileNameData.csv> <fileNameQuery.sql> <floorID>

fun main(args: Array<String>) {
    if (args.isNotEmpty()) {
        val records = readCSV2Records(args[0])
        val floorId = args[2].toInt()
        if (records.isNotEmpty()) {
            write2File(floorId, records, args[1])
        }
    } else
        error("Arguments are empty. Syntax information: ${"$ kotlin GenerationSQL <fileNameData.csv> <fileNameQuery.sql> <floorId>"}")
}

private fun write2File(floorId: Int, records: ArrayList<Record>, fileNameQuery: String) {
    File(fileNameQuery).printWriter().use { out ->
        out.println("-- Insert SamplePositions")
        for (record in records) {
            val queryInsertionSamplePosition =
                "INSERT INTO \"SamplePositions\"(\"SamplePositionId\",\"X\",\"Y\",\"MaKhuVuc\") " +
                        "values(${record.sample_pos_id}," +
                        "${record.x}," +
                        "${record.y}," +
                        "$floorId)"
            out.println(queryInsertionSamplePosition)
        }
        out.println("-- Insert Sample")
        for (record in records) {
            val queryInsertionSample =
                "INSERT INTO \"Samples\"(\"SampleId\",\"SamplePositionId\") " +
                        "values(${record.sample_id},${record.sample_pos_id})"
            out.println(queryInsertionSample)
        }
        out.println("-- Insert SampleRssi")
        for (record in records) {
            val queryInsertionSampleRssi =
                "INSERT INTO \"SampleRssis\"(\"SampleRssiId\",\"Name\",\"Mac\",\"RssiValue\",\"SampleId\") " +
                        "values(${record.sample_rssi_id}," +
                        "\"${record.name_beacon}\"," +
                        "\"${record.mac_beacon}\"," +
                        "${record.value_rssi}," +
                        "${record.sample_id})"
            out.println(queryInsertionSampleRssi)
        }
    }
}

private fun readCSV2Records(fileName: String): ArrayList<Record> {
    var fileReader: BufferedReader? = null
    val records = ArrayList<Record>()
    try {
        var line: String?
        fileReader = BufferedReader(FileReader(fileName))
        // skip header
        fileReader.readLine()
        line = fileReader.readLine()
        while (null != line) {
            val tokens = line.split(",")
            if (tokens.isNotEmpty()) {
                val record = Record(
                    tokens[SAMPLE_RSSI_ID].toInt(),
                    tokens[SAMPLE_ID].toInt(),
                    tokens[SAMPLE_POS_ID].toInt(),
                    tokens[NAME_BEACON],
                    tokens[MAC_BEACON],
                    tokens[X].toInt(),
                    tokens[Y].toInt(),
                    tokens[VALUE_RSSI].toInt()
                )
                records.add(record)
            }
            line = fileReader.readLine()
        }
    } catch (error: Exception) {
        println("Error while reading CSV: ${error.message}")
    } finally {
        fileReader?.close()
    }
    return records
}