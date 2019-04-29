package model

data class SampleRssi(val SampleRssiId: Int, val Name: String, val Mac: String, val RssiValue: Int, val SampleId: Int) {
    override fun toString(): String {
        return "INSERT INTO \"SampleRssis\"(\"SampleRssiId\",\"Name\",\"Mac\",\"RssiValue\",\"SampleId\") VALUES($SampleRssiId,'$Name','$Mac',$RssiValue,$SampleId);"
    }
}