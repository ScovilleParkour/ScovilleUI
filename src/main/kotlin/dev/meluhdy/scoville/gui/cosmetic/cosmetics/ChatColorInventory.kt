package dev.meluhdy.scoville.gui.cosmetic.cosmetics

import dev.meluhdy.melodia.gui.MelodiaGUI
import dev.meluhdy.melodia.gui.MelodiaGUIItem
import dev.meluhdy.melodia.utils.ItemUtils
import dev.meluhdy.melodia.utils.TextUtils
import dev.meluhdy.melodia.utils.TranslatedString
import dev.meluhdy.melodia.utils.fromLegacyMessage
import dev.meluhdy.melodia.utils.fromMiniMessage
import dev.meluhdy.scoville.ScovilleUI
import dev.meluhdy.scoville.gui.IScovilleGUI
import dev.meluhdy.scovilleChat.core.modifiers.ChatColorModifier
import dev.meluhdy.scovilleChat.core.player.PlayerMessageSettingsManager
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent

class ChatColorInventory(p: Player, prevGUI: MelodiaGUI?) : MelodiaGUI(ScovilleUI.plugin, p, prevGUI), IScovilleGUI {

    fun makeItem(pos: Int, mat: Material, chatColor: ChatColorModifier.ChatColor): MelodiaGUIItem {
        val settings = PlayerMessageSettingsManager.getOrCreate(p)
        val selectMessage: Component = if (settings.chatColor.chatColor == chatColor) {
            getTitle(p, TranslatedString("menu.general.selected.true", arrayOf()))
        } else {
            getTitle(p, TranslatedString("menu.general.selected.false", arrayOf()))
        }

        return MelodiaGUIItem(
            pos,
            ItemUtils.createItem(
                mat, 1,
                getTitle(p, TranslatedString("menu.cosmetics.chat_color.${chatColor.color}.title", arrayOf())),
                "&8&m----------------".fromLegacyMessage(),
                getTitle(p, TranslatedString("menu.cosmetics.chat_color.${chatColor.color}.desc", arrayOf())),
                Component.text(""),
                selectMessage,
                "&8&m----------------".fromLegacyMessage()
            )
        ) {
            settings.chatColor = ChatColorModifier(chatColor)
            this.initializeItems()
            this.p.updateInventory()
        }
    }

    override val rows: Int = 4
    override val title: TextComponent = getTitle(p, TranslatedString("menu.cosmetics.chat_color.inv.title", arrayOf())) as TextComponent
    override val melodiaItems: ArrayList<MelodiaGUIItem>
        get() = arrayListOf(
            makeItem(9, Material.GREEN_DYE, ChatColorModifier.ChatColor.DARK_GREEN),
            makeItem(10, Material.LIME_DYE, ChatColorModifier.ChatColor.GREEN),
            makeItem(11, Material.YELLOW_DYE, ChatColorModifier.ChatColor.YELLOW),
            makeItem(12, Material.ORANGE_DYE, ChatColorModifier.ChatColor.GOLD),
            makeItem(13, Material.RED_DYE, ChatColorModifier.ChatColor.RED),
            makeItem(14, Material.NETHER_WART, ChatColorModifier.ChatColor.DARK_RED),
            makeItem(15, Material.GRAY_DYE, ChatColorModifier.ChatColor.DARK_GRAY),
            makeItem(16, Material.PINK_DYE, ChatColorModifier.ChatColor.LIGHT_PURPLE),
            makeItem(17, Material.PURPLE_DYE, ChatColorModifier.ChatColor.DARK_PURPLE),
            makeItem(22, Material.BONE_MEAL, ChatColorModifier.ChatColor.WHITE),
            getBack(27, this)
        )

    override fun extraItems() {
        createRow(this, 0)
        createRow(this, 3)
    }

    override fun onInventoryClick(e: InventoryClickEvent) {}
}