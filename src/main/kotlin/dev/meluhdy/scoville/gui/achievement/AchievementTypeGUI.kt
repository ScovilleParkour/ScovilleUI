package dev.meluhdy.scoville.gui.achievement

import dev.meluhdy.melodia.gui.MelodiaGUI
import dev.meluhdy.melodia.gui.MelodiaGUIItem
import dev.meluhdy.melodia.utils.ItemUtils
import dev.meluhdy.melodia.utils.TranslatedString
import dev.meluhdy.melodia.utils.toMiniMessage
import dev.meluhdy.scoville.Scoville
import dev.meluhdy.scoville.ScovilleUI
import dev.meluhdy.scoville.achievement.Achievement
import dev.meluhdy.scoville.gui.IScovilleGUI
import net.kyori.adventure.text.TextComponent
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class AchievementTypeGUI(p: Player, pg: MelodiaGUI?) : MelodiaGUI(ScovilleUI.plugin, p, pg), IScovilleGUI {

    override val rows: Int = 5
    override val title: TextComponent = getTitle(p, TranslatedString("menu.achievements.type.title", arrayOf())) as TextComponent
    override val melodiaItems: ArrayList<MelodiaGUIItem>
        get() {
            val out = mapOf(
                Achievement.AchievementDifficulty.EASY to 19,
                Achievement.AchievementDifficulty.MEDIUM to 21,
                Achievement.AchievementDifficulty.HARD to 23,
                Achievement.AchievementDifficulty.SPECIAL to 25
            ).map { pair -> makeItem(pair.value, pair.key) }.toCollection(ArrayList())

            out.add(getBack(36, this))

            return out
        }

    fun makeItem(pos: Int, difficulty: Achievement.AchievementDifficulty): MelodiaGUIItem {
        val diffItems: Map<Achievement.AchievementDifficulty, Material> = mapOf(
            Achievement.AchievementDifficulty.EASY to Material.LIME_CONCRETE,
            Achievement.AchievementDifficulty.MEDIUM to Material.YELLOW_CONCRETE,
            Achievement.AchievementDifficulty.HARD to Material.RED_CONCRETE,
            Achievement.AchievementDifficulty.SPECIAL to Material.NETHER_STAR
        )

        return MelodiaGUIItem(pos, ItemUtils.createItem(
            diffItems[difficulty]!!,
            1,
            getTitle(
                "&${difficulty.color}" + getTitle(
                    p, TranslatedString("menu.achievements.type.${difficulty.name.lowercase()}.title", arrayOf())
                ).toMiniMessage())
        )) {
            AchievementListGUI(difficulty, p, this).open()
        }
    }

    override fun extraItems() {
        createRow(this, 0)
        createRow(this, 4)
    }

    override fun onInventoryClick(e: InventoryClickEvent) {}

}