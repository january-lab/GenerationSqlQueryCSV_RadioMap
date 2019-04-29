package model

data class Sample(val SampleId: Int, val SamplePositionId: Int) {
    override fun toString(): String {
        return "INSERT INTO \"Samples\"(\"SampleId\",\"SamplePositionId\") VALUES($SampleId,$SamplePositionId);"
    }
}