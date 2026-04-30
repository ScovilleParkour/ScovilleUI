package dev.meluhdy.scoville.gui.cosmetic

import dev.meluhdy.melodia.gui.MelodiaGUI
import dev.meluhdy.melodia.gui.MelodiaGUIItem
import dev.meluhdy.melodia.utils.ItemUtils
import dev.meluhdy.melodia.utils.TranslatedString
import dev.meluhdy.scoville.ScovilleUI
import dev.meluhdy.scoville.gui.IScovilleGUI
import dev.meluhdy.scoville.gui.cosmetic.cosmetics.ChatColorInventory
import dev.meluhdy.scoville.gui.cosmetic.cosmetics.JoinMessageInventory
import net.kyori.adventure.text.TextComponent
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent

class CosmeticInventory(p: Player, prevGUI: MelodiaGUI?) : MelodiaGUI(ScovilleUI.plugin, p, prevGUI), IScovilleGUI {

    override val rows: Int = 5
    override val title: TextComponent = getTitle(p, TranslatedString("menu.cosmetics.title", arrayOf())) as TextComponent
    override val melodiaItems: ArrayList<MelodiaGUIItem> = arrayListOf(
        getBack(36, this),
        MelodiaGUIItem(
            10,
            ItemUtils.createSkull(
                "https://textures.minecraft.net/texture/2ad8a3a3b36add5d9541a8ec970996fbdcdea9414cd754c50e48e5d34f1bf60a",
                title=getTitle(p, TranslatedString("menu.cosmetics.tags.title", arrayOf()))
            )
        ) {
            p.sendMessage("Tags")
        },
        MelodiaGUIItem(
            12,
            ItemUtils.createSkull(
                "https://textures.minecraft.net/texture/17dd34924d2b6a213a5ed46ae5783f95373a9ef5ce5c88f9d736705983b97",
                title=getTitle(p, TranslatedString("menu.cosmetics.chat_colors.title", arrayOf()))
            )
        ) {
            ChatColorInventory(p, this).open()
        },
        MelodiaGUIItem(
            14,
            ItemUtils.createSkull(
                "https://textures.minecraft.net/texture/d5c6dc2bbf51c36cfc7714585a6a5683ef2b14d47d8ff714654a893f5da622",
                title=getTitle(p, TranslatedString("menu.cosmetics.hotbars.title", arrayOf()))
            )
        ) {
            p.sendMessage("Hotbars")
        },
        MelodiaGUIItem(
            16,
            ItemUtils.createSkull(
                "https://textures.minecraft.net/texture/e4d8a8d527f65a4f434f894f7ee42eb843015bda7927c63c6ea8a754afe9bb1b",
                title=getTitle(p, TranslatedString("menu.cosmetics.particles.title", arrayOf()))
            )
        ) {
            p.sendMessage("Particles")
        },
        MelodiaGUIItem(
            28,
            ItemUtils.createItem(Material.CREEPER_HEAD, title = getTitle(p, TranslatedString("menu.cosmetics.disguises.title", arrayOf())))
        ) {
            p.sendMessage("Disguises")
        },
        MelodiaGUIItem(
            30,
            ItemUtils.createSkull(
                "https://textures.minecraft.net/texture/d55b1aa95fdb777179a4bb9c92f116d787eddc97b9b8c1666256eedf2d6b35",
                title=getTitle(p, TranslatedString("menu.cosmetics.hats.title", arrayOf()))
            )
        ) {
            p.sendMessage("Hats")
        },
        MelodiaGUIItem(
            32,
            ItemUtils.createSkull(
                "https://textures.minecraft.net/texture/5ff31431d64587ff6ef98c0675810681f8c13bf96f51d9cb07ed7852b2ffd1",
                title=getTitle(p, TranslatedString("menu.cosmetics.join_messages.title", arrayOf()))
            )
        ) {
            JoinMessageInventory(p, this).open()
        },
        MelodiaGUIItem(
            34,
            ItemUtils.createSkull(
                "https://textures.minecraft.net/texture/e3e45831c1ea817f7477bcaebfa3d2ee3a936ee8ea2b8bde29006b7e9bdf58",
                title=getTitle(p, TranslatedString("menu.cosmetics.win_messages.title", arrayOf()))
            )
        ) {
            p.sendMessage("Win Messages")
        }
    )

    override fun extraItems() {
        createBorder(this)
    }

    override fun onInventoryClick(e: InventoryClickEvent) {}

}