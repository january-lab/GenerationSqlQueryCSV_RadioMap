package model

data class Record(
    val sample_rssi_id: Int,
    val sample_id: Int,
    val sample_pos_id: Int,
    val name_beacon: String,
    val mac_beacon: String,
    val x: Int,
    val y: Int,
    val value_rssi: Int
)