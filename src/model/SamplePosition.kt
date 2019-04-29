package model

data class SamplePosition(val SamplePositionId: Int, val X: Int, val Y: Int, val MaKhuVuc: Int) {
    override fun toString(): String {
        return "INSERT INTO \"SamplePositions\"(\"SamplePositionId\",\"X\",\"Y\",\"MaKhuVuc\") VALUES($SamplePositionId,$X,$Y,$MaKhuVuc);"
    }
}