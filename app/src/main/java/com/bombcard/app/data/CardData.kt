package com.bombcard.app.data

import android.content.Context
import android.os.Environment
import java.io.File

data class SoulCard(
    val id: Int,
    val content: String,
    val tags: List<String>,
    val source: String = ""
)

object CardRepository {
    
    // 内置默认卡片（保底内容）
    private val defaultCards = listOf(
        SoulCard(1, 
            "《周易》有云：\"和气致祥，乖气致戾。\"和气，是一种人生智慧，它彰显着一个人的宽厚与仁慈。若行事和气有礼，常常能赢得信赖与支持。和气待人，能让对方如沐春风、如浴暖阳，能使寒冰融水、乌云消散。",
            listOf("处世智慧", "情绪管理"), "《周易》"),
        SoulCard(2,
            "生活是艰苦的，甘于平庸的人是平凡的，依赖别人的人是悲惨的。人生犹如逆水行舟，想要冲破风浪就地奋力划桨，随波逐流只会让你离目标越来越远。与其寄希望于别人的善心，不如增强自己划桨的实力。",
            listOf("自我成长", "励志"), ""),
        SoulCard(3,
            "周星驰开车送莫文蔚回家，车开到中环时，他却突然下车，径直走向人行道上一个席地而卧的乞丐身边，然后掏空口袋，把随身带着的钱全部塞进了乞丐怀里。回到车里的周星驰却突然落泪：如果不是我咬着牙挺到今天的话，也许躺在地上的那个人就是我。",
            listOf("名人故事", "励志"), "周星驰"),
        SoulCard(4,
            "情绪稳定，波澜不惊，保有内心的淡定与从容，也终将邂逅人生最曼妙的风景。哲人尼采说：\"如果情绪总是处于失控状态，就会被感情牵着鼻子走，丧失自由。\"与其怨天尤人，不如看淡看开，挣脱情绪的枷锁。",
            listOf("情绪管理", "哲学"), "尼采"),
        SoulCard(5,
            "所谓门槛，能力够了就是门，能力不够就是槛。当你的能力越来越强时，一些难题就会迎刃而解。不要害怕当下处处碰壁，凡事反观诸己，埋头过坎，日拱一卒。",
            listOf("自我成长", "能力提升"), ""),
        SoulCard(6,
            "周国平曾言：人生要有不较劲的智慧。真正有大智慧的人，绝对不会和过往纠缠，因为他们明白，不论过去如何，既然已经发生了，就无法改变。好的人生，就是在做减法，内心越简单，越轻松，越安宁。",
            listOf("处世智慧", "人生哲学"), "周国平"),
        SoulCard(7,
            "每个人的身体里，都有两个\"我\"，一个是我们所期待的完美的\"我\"；另一个是现实中面对重重困难想要竭尽全力又无能为力的\"我\"。当你觉得累了，就喊停吧，不要死撑着非要跑完这一圈，适当喊停，痛苦减半。",
            listOf("心理健康", "自我接纳"), ""),
        SoulCard(8,
            "《菜根谭》：性躁心粗者，一事无成；心平气和者，百福自集。性情急躁、粗浮轻率的人，做什么事都难以有成；性情温和、心绪平静的人，不用做太多，福气就会集于一身。",
            listOf("处世智慧", "传统文化"), "《菜根谭》"),
        SoulCard(9,
            "俗话说：\"金无足赤，人无完人。\"世间从没有绝对完美的人，也没有一帆风顺的人生，犯错本就是成长的必经之路。犯错带来的不是耻辱，而是修正方向的契机、积累经验的养分。",
            listOf("自我成长", "心理健康"), ""),
        SoulCard(10,
            "古人说：\"君子务本，本立而道生。\"当从根本上提升自己时，一切自会水到渠成。先修己，再交友。成为最好的自己，才会遇到更好的别人。",
            listOf("自我成长", "传统文化"), "")
    )
    
    private var customCards: List<SoulCard> = emptyList()
    private var isInitialized = false
    
    fun init(context: Context) {
        if (isInitialized) return
        
        // 尝试从本地文件加载
        customCards = loadCardsFromFile(context)
        isInitialized = true
    }
    
    private fun loadCardsFromFile(context: Context): List<SoulCard> {
        val cards = mutableListOf<SoulCard>()
        
        // 尝试多个路径
        val possiblePaths = listOf(
            File(Environment.getExternalStorageDirectory(), "Download/心灵点滴.txt"),
            File(Environment.getExternalStorageDirectory(), "心灵点滴.txt"),
            File(context.getExternalFilesDir(null), "心灵点滴.txt"),
            File(context.filesDir, "心灵点滴.txt")
        )
        
        val file = possiblePaths.find { it.exists() && it.canRead() }
        
        file?.let {
            try {
                val content = it.readText(Charsets.UTF_8)
                cards.addAll(parseCardsFromText(content))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        
        return cards
    }
    
    fun parseCardsFromText(text: String): List<SoulCard> {
        val cards = mutableListOf<SoulCard>()
        val regex = "心灵点滴[：:]\\s*\\n?([^#]+?)(?=\\n\\n|心灵点滴|$)".toRegex(RegexOption.DOT_MATCHES_ALL)
        
        regex.findAll(text).forEachIndexed { index, matchResult ->
            val content = matchResult.groupValues[1].trim()
            if (content.length > 10) {
                // 提取标签（#标签名）
                val tags = mutableListOf("心灵点滴")
                val tagRegex = "#([^\\s\\n]+)".toRegex()
                tagRegex.findAll(content).forEach { tagMatch ->
                    tags.add(tagMatch.groupValues[1])
                }
                
                // 清理内容中的标签
                val cleanContent = content.replace("#\\S+".toRegex(), "").trim()
                
                cards.add(SoulCard(
                    id = 1000 + index,
                    content = cleanContent,
                    tags = tags.distinct().take(3),
                    source = ""
                ))
            }
        }
        
        return cards
    }
    
    fun getAllCards(): List<SoulCard> {
        return if (customCards.isNotEmpty()) customCards else defaultCards
    }
    
    fun getRandomCard(): SoulCard {
        val cards = getAllCards()
        return cards.random()
    }
    
    fun getCardById(id: Int): SoulCard? {
        return getAllCards().find { it.id == id }
    }
    
    fun getCardsCount(): Int {
        return getAllCards().size
    }
}
