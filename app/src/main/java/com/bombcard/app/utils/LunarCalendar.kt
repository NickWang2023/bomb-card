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

    private const val MIN_YEAR = 1900
    private const val MAX_YEAR = 2099

    fun getCurrentDate(): String {
        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH) + 1
        val day = cal.get(Calendar.DAY_OF_MONTH)
        val weekDays = arrayOf("周日", "周一", "周二", "周三", "周四", "周五", "周六")
        val weekDay = weekDays[cal.get(Calendar.DAY_OF_WEEK) - 1]
        return year.toString() + "年" + month.toString() + "月" + day.toString() + "日 " + weekDay
    }

    fun getLunarDate(): String {
        return try {
            val cal = Calendar.getInstance()
            val year = cal.get(Calendar.YEAR)
            val month = cal.get(Calendar.MONTH) + 1
            val day = cal.get(Calendar.DAY_OF_MONTH)
            
            // 边界检查
            if (year < MIN_YEAR || year > MAX_YEAR) {
                return getSimpleLunarFallback(year, month, day)
            }
            
            val lunar = solarToLunar(year, month, day)
            lunar.year + "年" + lunar.month + "月" + lunar.day
        } catch (e: Exception) {
            // 任何异常都返回安全回退值
            getSimpleLunarFallback(Calendar.getInstance().get(Calendar.YEAR), 
                Calendar.getInstance().get(Calendar.MONTH) + 1,
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
        }
    }

    fun getLunarDateFull(): String {
        return try {
            val cal = Calendar.getInstance()
            val year = cal.get(Calendar.YEAR)
            val month = cal.get(Calendar.MONTH) + 1
            val day = cal.get(Calendar.DAY_OF_MONTH)
            
            if (year < MIN_YEAR || year > MAX_YEAR) {
                return getSimpleLunarFallback(year, month, day)
            }
            
            val lunar = solarToLunar(year, month, day)
            lunar.year + "年" + lunar.month + "月" + lunar.day + " " + lunar.animal + "年"
        } catch (e: Exception) {
            getSimpleLunarFallback(Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH) + 1,
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
        }
    }

    private fun getSimpleLunarFallback(year: Int, month: Int, day: Int): String {
        // 简单的回退显示，不计算真实农历
        val ganZhi = gan[(year - 4) % 10] + zhi[(year - 4) % 12]
        val animal = animals[(year - 4) % 12]
        return ganZhi + "年 " + animal + "年"
    }

    private fun solarToLunar(year: Int, month: Int, day: Int): LunarDate {
        val baseDate = Calendar.getInstance()
        baseDate.set(MIN_YEAR, 0, 31, 0, 0, 0)
        baseDate.set(Calendar.MILLISECOND, 0)
        
        val objDate = Calendar.getInstance()
        objDate.set(year, month - 1, day, 0, 0, 0)
        objDate.set(Calendar.MILLISECOND, 0)
        
        var offset = ((objDate.timeInMillis - baseDate.timeInMillis) / 86400000L).toInt()
        
        // 安全检查
        if (offset < 0) offset = 0
        
        var iYear = MIN_YEAR
        var daysOfYear = 0
        var loopCount = 0
        val maxLoop = 300  // 防止死循环
        
        while (iYear <= MAX_YEAR && offset > 0 && loopCount < maxLoop) {
            daysOfYear = lYearDays(iYear)
            offset -= daysOfYear
            iYear++
            loopCount++
        }
        
        if (loopCount >= maxLoop) {
            // 死循环保护，返回回退值
            return LunarDate(
                year = gan[(year - 4) % 10] + zhi[(year - 4) % 12],
                month = monthNong[0],
                day = "初一",
                animal = animals[(year - 4) % 12]
            )
        }
        
        if (offset < 0) {
            offset += daysOfYear
            iYear--
        }
        
        if (iYear > MAX_YEAR) {
            iYear = MAX_YEAR
            offset = 0
        }
        
        val lunarYear = iYear
        val leapMonth = leapMonth(iYear)
        var isLeap = false
        var iMonth = 1
        var daysOfMonth = 0
        loopCount = 0
        
        while (iMonth <= 13 && offset > 0 && loopCount < maxLoop) {
            if (leapMonth > 0 && iMonth == leapMonth + 1 && !isLeap) {
                // 这是闰月
                isLeap = true
                daysOfMonth = leapDays(lunarYear)
            } else {
                daysOfMonth = monthDays(lunarYear, iMonth)
            }
            
            offset -= daysOfMonth
            
            if (isLeap && iMonth == leapMonth + 1) {
                // 闰月处理完，继续正常月份
            }
            
            if (offset > 0) {
                iMonth++
                if (isLeap && iMonth > leapMonth + 1) {
                    isLeap = false
                }
            }
            loopCount++
        }
        
        if (loopCount >= maxLoop) {
            return LunarDate(
                year = gan[(lunarYear - 4) % 10] + zhi[(lunarYear - 4) % 12],
                month = monthNong[0],
                day = "初一",
                animal = animals[(lunarYear - 4) % 12]
            )
        }
        
        if (offset < 0) {
            offset += daysOfMonth
        } else if (offset == 0) {
            // 正好是月末
            offset = daysOfMonth
            if (iMonth > 1) {
                iMonth--
            }
        }
        
        val lunarMonth = if (iMonth > 12) 12 else iMonth
        val lunarDay = offset + 1
        
        val yearGanZhi = gan[(lunarYear - 4) % 10] + zhi[(lunarYear - 4) % 12]
        val monthStr = if (isLeap && lunarMonth == leapMonth) "闰" + monthNong[lunarMonth - 1] else monthNong[lunarMonth - 1]
        
        return LunarDate(
            year = yearGanZhi,
            month = monthStr,
            day = cDay(lunarDay),
            animal = animals[(lunarYear - 4) % 12]
        )
    }

    private fun lYearDays(y: Int): Int {
        if (y < MIN_YEAR || y > MAX_YEAR) return 354
        var sum = 348
        for (i in 0x8000 downTo 0x8) {
            if ((lunarInfo[y - MIN_YEAR] and i.toLong()) != 0L) sum++
        }
        return sum + leapDays(y)
    }

    private fun leapDays(y: Int): Int {
        if (y < MIN_YEAR || y > MAX_YEAR) return 0
        if (leapMonth(y) != 0) {
            return if ((lunarInfo[y - MIN_YEAR] and 0x10000L) != 0L) 30 else 29
        }
        return 0
    }

    private fun leapMonth(y: Int): Int {
        if (y < MIN_YEAR || y > MAX_YEAR) return 0
        return (lunarInfo[y - MIN_YEAR] and 0xf).toInt()
    }

    private fun monthDays(y: Int, m: Int): Int {
        if (y < MIN_YEAR || y > MAX_YEAR) return 29
        if (m < 1 || m > 12) return 29
        return if ((lunarInfo[y - MIN_YEAR] and (0x10000 shr m).toLong()) != 0L) 30 else 29
    }

    private fun cDay(d: Int): String {
        return when (d) {
            10 -> "初十"
            20 -> "二十"
            30 -> "三十"
            else -> nStr2[d / 10] + nStr1[d % 10]
        }
    }
}

data class LunarDate(
    val year: String,
    val month: String,
    val day: String,
    val animal: String
)
