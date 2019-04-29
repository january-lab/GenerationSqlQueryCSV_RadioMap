import model.Record
import model.Sample
import model.SamplePosition
import model.SampleRssi
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

        val samplePositions = ArrayList<SamplePosition>()
        val samples = ArrayList<Sample>()
        val sampleRssis = ArrayList<SampleRssi>()
        for (record in records) {
            samplePositions.add(SamplePosition(record.sample_pos_id, record.x, record.y, floorId))
            samples.add(Sample(record.sample_id, record.sample_pos_id))
            sampleRssis.add(
                SampleRssi(
                    record.sample_rssi_id,
                    record.name_beacon,
                    record.mac_beacon,
                    record.value_rssi,
                    record.sample_id
                )
            )
        }
        out.println("-- Insert SamplePositions")
        samplePositions.distinct().forEach {
            out.println(it.toString())
        }
        out.println("-- Insert Samples")
        samples.distinct().forEach {
            out.println(it.toString())
        }
        out.println("-- Insert SampleRssis")
        sampleRssis.distinct().forEach {
            out.println(it.toString())
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