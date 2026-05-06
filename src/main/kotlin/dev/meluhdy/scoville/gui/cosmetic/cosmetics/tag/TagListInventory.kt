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
import dev.meluhdy.scovilleChat.core.player.PlayerMessageSettingsManager
import dev.meluhdy.scovilleChat.core.tag.ChatTag
import dev.meluhdy.scovilleChat.core.tag.TagManager
import net.kyori.adventure.text.TextComponent
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class TagListInventory(type: ChatTag.TagType, p: Player, prevGUI: MelodiaGUI?) : MelodiaPaginationGUI<ChatTag>(ScovilleUI.plugin, p, prevGUI), IScovilleGUI {

    override val itemRows: Int = 3
    override val prevItem: ItemStack = getBack(36, this).item
    override val nextItem: ItemStack = getNext(p)
    override val objects: ArrayList<ChatTag> = TagManager.getAll().filter { it.type == type } as ArrayList<ChatTag>

    override fun toItem(
        pos: Int,
        obj: ChatTag
    ): MelodiaGUIItem {
        val settings = PlayerMessageSettingsManager.getOrCreate(p)
        return MelodiaGUIItem(pos, ItemUtils.createItem(
            if (p.hasPermission(obj.permission)) { Material.NAME_TAG } else { Material.GRAY_DYE },
            1,
            obj.tag.fromLegacyMessage(),
            "".fromMiniMessage(),
            "&8&lTODO: Figure This Out".fromLegacyMessage(),
            "".fromMiniMessage(),
            getTitle(p, TranslatedString(if (settings.tag == obj.uuid) { "menu.general.selected.true" } else { "menu.general.selected.false" }, arrayOf()))
        )) {
            settings.tag = obj.uuid
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
                PlayerMessageSettingsManager.getOrCreate(p).tag = null
            })
            return out
        }

    override fun onInventoryClick(e: InventoryClickEvent) {}

}