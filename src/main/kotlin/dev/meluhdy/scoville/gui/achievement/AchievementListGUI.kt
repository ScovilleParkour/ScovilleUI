package dev.meluhdy.scoville.gui.achievement

import dev.meluhdy.melodia.gui.MelodiaGUI
import dev.meluhdy.melodia.gui.MelodiaGUIItem
import dev.meluhdy.melodia.gui.MelodiaPaginationGUI
import dev.meluhdy.melodia.utils.ItemUtils
import dev.meluhdy.melodia.utils.TextUtils
import dev.meluhdy.melodia.utils.TranslatedString
import dev.meluhdy.melodia.utils.fromMiniMessage
import dev.meluhdy.scoville.Scoville
import dev.meluhdy.scoville.ScovilleUI
import dev.meluhdy.scoville.achievement.Achievement
import dev.meluhdy.scoville.achievement.AchievementManager
import dev.meluhdy.scoville.gui.IScovilleGUI
import dev.meluhdy.scovilleAchievements.ScovilleAchievements
import dev.meluhdy.scovilleAchievements.core.AchievementEarnerManager
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import org.apache.commons.lang3.StringUtils
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import java.text.DecimalFormat
import kotlin.math.roundToInt

class AchievementListGUI(val diff: Achievement.AchievementDifficulty, p: Player, pg: MelodiaGUI?) : MelodiaPaginationGUI<Achievement<*>>(ScovilleUI.plugin, p, pg), IScovilleGUI {

    override val itemRows: Int = 3
    override val prevItem: ItemStack = getBack(36, this).item
    override val nextItem: ItemStack = getNext(this.p)
    override val objects: ArrayList<Achievement<*>> = AchievementManager
        .getAll()
        .filter { ach -> ach.diff == this.diff }
        .toCollection(ArrayList())

    fun getCentralLore(obj: Achievement<*>): Array<Component> {
        val rateBars = '▮'
        val rateBarCount = 20

        // TODO: Store Course WLR and Rate
        val rate = (.5f * rateBarCount).roundToInt()

        return arrayOf(
            Component.empty(),
            getTitle(p, TranslatedString("menu.achievements.list.unlock_rate", arrayOf())),
            getTitle(
                "&8[&a${StringUtils.repeat(rateBars, rate)}&7${
                    StringUtils.repeat(
                        rateBars,
                        rateBarCount - rate
                    )
                }&8] &7${DecimalFormat("0.00").format(.5f * 100.0f)}%"
            ),
            Component.empty()
        )
    }

    override fun toItem(pos: Int, obj: Achievement<*>): MelodiaGUIItem {
        val earner = AchievementEarnerManager.get(this.p.uniqueId)
        val achievementsPlugin = Bukkit.getPluginManager().getPlugin("ScovilleAchievements") as ScovilleAchievements

        val item = if (earner == null || !earner.hasAchievement(obj.achievementId)) {
            ItemUtils.createItem(
                Material.GRAY_DYE,
                1,
                getTitle("&8&l"+ TextUtils.translate(achievementsPlugin, obj.nameId, p.locale())),
                getTitle("&7???"),
                *getCentralLore(obj),
                getTitle(p, TranslatedString("menu.general.locked", arrayOf()))
            )
        } else {
            ItemUtils.modifyItem(
                obj.baseStack,
                getTitle("&${obj.diff.color}&l"+ TextUtils.translate(achievementsPlugin, obj.nameId, p.locale())),
                getTitle("&7"+ TextUtils.translate(achievementsPlugin, obj.descId, p.locale())),
                *getCentralLore(obj),
                getTitle(p, TranslatedString("menu.general.unlocked", arrayOf()))
            )
        }

        return MelodiaGUIItem(pos, item) {}
    }

    override val rows: Int = 5
    override val title: TextComponent = getTitle(p, TranslatedString("menu.achievements.type.${diff.name.lowercase()}.title", arrayOf())) as TextComponent

    override fun extraItems() {
        createRow(this, 3)
    }

    override fun onInventoryClick(e: InventoryClickEvent) {}
}