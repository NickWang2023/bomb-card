package com.bombcard.app.utils

import android.content.Context
import android.os.Environment
import org.json.JSONObject
import java.io.File

/**
 * 配置管理器 - 从本地文件读取配置
 * 配置文件路径：/sdcard/Download/bombcard_config.json
 */
object ConfigManager {
    
    data class BottomLinkConfig(
        val title: String = "💣 炸弹卡片",
        val subtitle: String = "点击了解更多 · 每日心灵鸡汤",
        val url: String = "https://github.com/NickWang2023/bomb-card",
        val buttonText: String = "去看看",
        val enabled: Boolean = true
    )
    
    private var cachedConfig: BottomLinkConfig? = null
    private var lastLoadTime: Long = 0
    private const val CACHE_VALID_MS = 30000 // 30秒缓存
    
    fun getBottomLinkConfig(context: Context): BottomLinkConfig {
        val now = System.currentTimeMillis()
        if (cachedConfig != null && (now - lastLoadTime) < CACHE_VALID_MS) {
            return cachedConfig!!
        }
        
        cachedConfig = loadConfigFromFile(context)
        lastLoadTime = now
        return cachedConfig!!
    }
    
    private fun loadConfigFromFile(context: Context): BottomLinkConfig {
        val possiblePaths = listOf(
            File(Environment.getExternalStorageDirectory(), "Download/bombcard_config.json"),
            File(Environment.getExternalStorageDirectory(), "bombcard_config.json"),
            File(context.getExternalFilesDir(null), "bombcard_config.json"),
            File(context.filesDir, "bombcard_config.json")
        )
        
        val configFile = possiblePaths.find { it.exists() && it.canRead() }
        
        return try {
            if (configFile != null) {
                val json = JSONObject(configFile.readText(Charsets.UTF_8))
                val linkJson = json.optJSONObject("bottom_link") ?: return BottomLinkConfig()
                
                BottomLinkConfig(
                    title = linkJson.optString("title", "💣 炸弹卡片"),
                    subtitle = linkJson.optString("subtitle", "点击了解更多 · 每日心灵鸡汤"),
                    url = linkJson.optString("url", "https://github.com/NickWang2023/bomb-card"),
                    buttonText = linkJson.optString("button_text", "去看看"),
                    enabled = linkJson.optBoolean("enabled", true)
                )
            } else {
                BottomLinkConfig()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            BottomLinkConfig()
        }
    }
    
    /**
     * 获取默认配置文件内容（用于用户参考）
     */
    fun getDefaultConfig(): String {
        return """{
  "bottom_link": {
    "title": "💣 炸弹卡片",
    "subtitle": "点击了解更多 · 每日心灵鸡汤",
    "url": "https://github.com/NickWang2023/bomb-card",
    "button_text": "去看看",
    "enabled": true
  }
}""".trimIndent()
    }
}
