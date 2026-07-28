package com.bombcard.app.utils

import java.util.Calendar

object LunarCalendar {
    private val lunarInfo = longArrayOf(
        0x04bd8, 0x04ae0, 0x0a570, 0x054d5, 0x0d260, 0x0d950, 0x16554, 0x056a0, 0x09ad0, 0x055d2,
        0x04ae0, 0x0a5b6, 0x0a4d0, 0x0d250, 0x1d255, 0x0b540, 0x0d6a0, 0x0ada2, 0x095b0, 0x14977,
        0x04970, 0x0a4b0, 0x0b4b5, 0x06a50, 0x06d40, 0x1ab54, 0x02b60, 0x09570, 0x052f2, 0x04970,
        0x06566, 0x0d4a0, 0x0ea50, 0x06e95, 0x05ad0, 0x02b60, 0x186e3, 0x092e0, 0x1c8d7, 0x0c950,
        0x0d4a0, 0x1d8a6, 0x0b550, 0x056a0, 0x1a5b4, 0x025d0, 0x092d0, 0x0d2b2, 0x0a950, 0x0b557,
        0x06ca0, 0x0b550, 0x15355, 0x04da0, 0x0a5d0, 0x14573, 0x052d0, 0x0a9a8, 0x0e950, 0x06aa0,
        0x0aea6, 0x0ab50, 0x04b60, 0x0aae4, 0x0a570, 0x05260, 0x0f263, 0x0d950, 0x05b57, 0x056a0,
        0x096d0, 0x04dd5, 0x04ad0, 0x0a4d0, 0x0d4d4, 0x0d250, 0x0d558, 0x0b540, 0x0b5a0, 0x195a6,
        0x095b0, 0x049b0, 0x0a974, 0x0a4b0, 0x0b27a, 0x06a50, 0x06d40, 0x0af46, 0x0ab60, 0x09570,
        0x04af5, 0x04970, 0x064b0, 0x074a3, 0x0ea50, 0x06b58, 0x055c0, 0x0ab60, 0x096d5, 0x092e0,
        0x0c960, 0x0d954, 0x0d4a0, 0x0da50, 0x07552, 0x056a0, 0x0abb7, 0x025d0, 0x092d0, 0x0cab5,
        0x0a950, 0x0b4a0, 0x0baa4, 0x0ad50, 0x055d9, 0x04ba0, 0x0a5b0, 0x15176, 0x052b0, 0x0a930,
        0x07954, 0x06aa0, 0x0ad50, 0x05b52, 0x04b60, 0x0a6e6, 0x0a4e0, 0x0d260, 0x0ea65, 0x0d530,
        0x05aa0, 0x076a3, 0x096d0, 0x04bd7, 0x04ad0, 0x0a4d0, 0x1d0b6, 0x0d250, 0x0d520, 0x0dd45,
        0x0b5a0, 0x056d0, 0x055b2, 0x049b0, 0x0a577, 0x0a4b0, 0x0aa50, 0x1b255, 0x06d20, 0x0ada0
    )

    private val gan = arrayOf("甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸")
    private val zhi = arrayOf("子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥")
    private val animals = arrayOf("鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴", "鸡", "狗", "猪")
    private val nStr1 = arrayOf("日", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十")
    private val nStr2 = arrayOf("初", "十", "廿", "卅", " ")
    private val monthNong = arrayOf("正", "二", "三", "四", "五", "六", "七", "八", "九", "十", "冬", "腊")

    fun getLunarDate(): String {
        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH) + 1
        val day = cal.get(Calendar.DAY_OF_MONTH)
        
        val lunar = solarToLunar(year, month, day)
        return "${lunar.year}年${lunar.month}月${lunar.day}"
    }

    private fun solarToLunar(year: Int, month: Int, day: Int): LunarDate {
        var baseDate = Calendar.getInstance()
        baseDate.set(1900, 0, 31)
        
        var objDate = Calendar.getInstance()
        objDate.set(year, month - 1, day)
        
        var offset = ((objDate.timeInMillis - baseDate.timeInMillis) / 86400000L).toInt()
        
        var iYear = 1900
        var daysOfYear = 0
        
        while (iYear < 2100 && offset > 0) {
            daysOfYear = lYearDays(iYear)
            offset -= daysOfYear
            iYear++
        }
        
        if (offset < 0) {
            offset += daysOfYear
            iYear--
        }
        
        val lunarYear = iYear
        val leapMonth = leapMonth(iYear)
        var isLeap = false
        var iMonth = 1
        var daysOfMonth = 0
        
        while (iMonth < 13 && offset > 0) {
            if (leapMonth > 0 && iMonth == leapMonth + 1 && !isLeap) {
                iMonth--
                isLeap = true
                daysOfMonth = leapDays(lunarYear)
            } else {
                daysOfMonth = monthDays(lunarYear, iMonth)
            }
            
            if (isLeap && iMonth == leapMonth + 1) {
                isLeap = false
            }
            
            offset -= daysOfMonth
            iMonth++
        }
        
        if (offset == 0 && leapMonth > 0 && iMonth == leapMonth + 1) {
            if (isLeap) {
                isLeap = false
            } else {
                isLeap = true
                iMonth--
            }
        }
        
        if (offset < 0) {
            offset += daysOfMonth
            iMonth--
        }
        
        val lunarMonth = iMonth
        val lunarDay = offset + 1
        
        return LunarDate(
            year = "${gan[(lunarYear - 4) % 10]}${zhi[(lunarYear - 4) % 12]}",
            month = if (isLeap) "闰${monthNong[lunarMonth - 1]}" else monthNong[lunarMonth - 1],
            day = cDay(lunarDay),
            animal = animals[(lunarYear - 4) % 12]
        )
    }

    private fun lYearDays(y: Int): Int {
        var sum = 348
        for (i in 0x8000 downTo 0x8) {
            if ((lunarInfo[y - 1900] and i.toLong()) != 0L) sum++
        }
        return sum + leapDays(y)
    }

    private fun leapDays(y: Int): Int {
        if (leapMonth(y) != 0) {
            return if ((lunarInfo[y - 1900] and 0x10000L) != 0L) 30 else 29
        }
        return 0
    }

    private fun leapMonth(y: Int): Int {
        return (lunarInfo[y - 1900] and 0xf).toInt()
    }

    private fun monthDays(y: Int, m: Int): Int {
        return if ((lunarInfo[y - 1900] and (0x10000 shr m).toLong()) != 0L) 30 else 29
    }

    private fun cDay(d: Int): String {
        var s = ""
        when (d) {
            10 -> s = "初十"
            20 -> s = "二十"
            30 -> s = "三十"
            else -> {
                s = nStr2[d / 10]
                s += nStr1[d % 10]
            }
        }
        return s
    }
}

data class LunarDate(
    val year: String,
    val month: String,
    val day: String,
    val animal: String
)