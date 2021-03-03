package hu.laszloba.fortnightly

import hu.laszloba.fortnightly.data.database.DateConverter
import org.junit.Assert
import org.junit.Test
import java.util.Calendar

class DateConverterTest {

    private val calendar = Calendar.getInstance()
        .apply {
            set(Calendar.YEAR, 2016)
            set(Calendar.MONTH, Calendar.JUNE)
            set(Calendar.DAY_OF_MONTH, 25)
        }

    @Test
    fun dateToTimestamp_dateToTimestampConversion_success() {
        Assert.assertEquals(calendar.timeInMillis, DateConverter().dateToTimestamp(calendar.time))
    }

    @Test
    fun fromTimestamp_timestampToDateConversion_success() {
        Assert.assertEquals(DateConverter().fromTimestamp(calendar.timeInMillis), calendar.time)
    }
}
