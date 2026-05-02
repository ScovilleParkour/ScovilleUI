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
import dev.meluhdy.scovilleChat.core.modifiers.ConnectionMessages
import dev.meluhdy.scovilleChat.core.player.PlayerMessageSettingsManager
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent

class JoinMessageInventory(p: Player, prevGUI: MelodiaGUI?) : MelodiaGUI(ScovilleUI.plugin, p, prevGUI), IScovilleGUI {

    fun makeItem(pos: Int, skullURL: String, message: ConnectionMessages): MelodiaGUIItem {
        val settings = PlayerMessageSettingsManager.getOrCreate(p)
        val selectMessage: Component = if (settings.connectionMessage == message) {
            getTitle(p, TranslatedString("menu.general.selected.true", arrayOf()))
        } else {
            getTitle(p, TranslatedString("menu.general.selected.false", arrayOf()))
        }

        return MelodiaGUIItem(
            pos,
            ItemUtils.createSkull(
                skullURL, 1,
                getTitle(p, TranslatedString("menu.cosmetics.join_message.${message.translationID}.title", arrayOf())),
                "&8&m----------------".fromLegacyMessage(),
                getTitle(p, TranslatedString("menu.cosmetics.join_message.${message.translationID}.desc", arrayOf())),
                Component.text(""),
                selectMessage,
                "&8&m----------------".fromLegacyMessage()
            )
        ) {
            settings.connectionMessage = message
            this.initializeItems()
            this.p.updateInventory()
        }
    }

    override val rows: Int = 3
    override val title: TextComponent = getTitle(p, TranslatedString("menu.cosmetics.join_message.inv.title", arrayOf())) as TextComponent
    override val melodiaItems: ArrayList<MelodiaGUIItem>
        get() = arrayListOf(
            makeItem(9, "http://textures.minecraft.net/texture/868f4cef949f32e33ec5ae845f9c56983cbe13375a4dec46e5bbfb7dcb6", ConnectionMessages.MR_COOL),
            makeItem(10, "http://textures.minecraft.net/texture/fb59b146cbe49394dcdff3c150a3b6cd3e7bc493ba8db39c5273d6c7c04a15f1", ConnectionMessages.SHAKESPEARE),
            makeItem(11, "http://textures.minecraft.net/texture/45435c37edafe165c92ae3736e15c464ad621d61220435d1a39adb4705fb886", ConnectionMessages.SUPERHERO),
            makeItem(12, "http://textures.minecraft.net/texture/c779b643831f838fb5efd2bafa4c6ea97bb256839761588525498d6294fc47", ConnectionMessages.AVIATION),
            makeItem(13, "http://textures.minecraft.net/texture/9ae81d366634833dbbffc28e41eaa0ae77b0ae1ca53cfea9fbe47f74538ac38", ConnectionMessages.MONARCH),
            makeItem(14, "https://textures.minecraft.net/texture/fad0d1a763a7e946f866d101d4e29661cb48d0c5d4b6117a1f2e4106475dc17d", ConnectionMessages.REVERSED),
            makeItem(15, "https://textures.minecraft.net/texture/c882eb49d89f1eb8c68814e71c6263bbec7ba633f27bff6efdb92a6799ba", ConnectionMessages.STINKY),
            MelodiaGUIItem(
                17,
                ItemUtils.createItem(Material.BARRIER, 1, getTitle(p, TranslatedString("menu.cosmetics.join_message.reset.title", arrayOf())))) {
                PlayerMessageSettingsManager.getOrCreate(p).connectionMessage = ConnectionMessages.DEFAULT
                this.initializeItems()
                this.p.updateInventory()
            },
            getBack(18, this)
        )

    override fun extraItems() {
        createRow(this, 0)
        createRow(this, 2)
    }

    override fun onInventoryClick(e: InventoryClickEvent) {}

}