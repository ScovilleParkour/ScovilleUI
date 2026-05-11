package dev.meluhdy.scoville.gui.cosmetic.cosmetics.tag

import dev.meluhdy.melodia.gui.MelodiaGUI
import dev.meluhdy.melodia.gui.MelodiaGUIItem
import dev.meluhdy.melodia.gui.MelodiaPaginationGUI
import dev.meluhdy.melodia.utils.ItemUtils
import dev.meluhdy.melodia.utils.TranslatedString
import dev.meluhdy.melodia.utils.fromLegacyMessage
import dev.meluhdy.melodia.utils.fromMiniMessage
import dev.meluhdy.scoville.ScovilleUI
import dev.meluhdy.scoville.gui.IScovilleGUI
import dev.meluhdy.scovilleCosmetics.core.player.PlayerCosmeticsManager
import dev.meluhdy.scovilleCosmetics.core.chat.tag.ChatTag
import dev.meluhdy.scovilleCosmetics.core.player.PlayerCosmetics
import net.kyori.adventure.text.TextComponent
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

abstract class TagListInventory<T : ChatTag>(p: Player, prevGUI: MelodiaGUI?) : MelodiaPaginationGUI<T>(ScovilleUI.plugin, p, prevGUI), IScovilleGUI {

    override val itemRows: Int = 3
    override val prevItem: ItemStack = getBack(36, this).item
    override val nextItem: ItemStack = getNext(p)

    abstract fun getDesc(obj: T): String

    override fun toItem(
        pos: Int,
        obj: T
    ): MelodiaGUIItem {
        val settings = PlayerCosmeticsManager.getOrCreate(p)
        return MelodiaGUIItem(pos, ItemUtils.createItem(
            if (p.hasPermission(obj.permission)) { Material.NAME_TAG } else { Material.GRAY_DYE },
            1,
            obj.tag.fromLegacyMessage(),
            "".fromMiniMessage(),
            this.getDesc(obj).fromLegacyMessage(),
            "".fromMiniMessage(),
            getTitle(p, TranslatedString(if (settings.tag?.tagId == obj.id) { "menu.general.selected.true" } else { "menu.general.selected.false" }, arrayOf()))
        )) {
            settings.tag = PlayerCosmetics.TagSelector(obj.id, obj.type)
            this.initializeItems()
            this.p.updateInventory()
        }
    }

    override val rows: Int = itemRows + 2
    override val title: TextComponent = getTitle(p, TranslatedString("menu.cosmetics.tag.list.title", arrayOf())) as TextComponent

    override fun extraItems() {
        createRow(this, itemRows)
    }

    override val melodiaItems: ArrayList<MelodiaGUIItem>
        get() {
            val out = super.melodiaItems
            out.add(MelodiaGUIItem(
                rows * 9 - 5,
                ItemUtils.createItem(Material.BARRIER, 1, getTitle(p, TranslatedString("menu.cosmetics.tag.list.reset.title", arrayOf())))
            ) {
                PlayerCosmeticsManager.getOrCreate(p).tag = null
            })
            return out
        }

    override fun onInventoryClick(e: InventoryClickEvent) {}

}