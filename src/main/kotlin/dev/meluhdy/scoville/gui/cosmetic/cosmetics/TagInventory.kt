package dev.meluhdy.scoville.gui.cosmetic.cosmetics

import dev.meluhdy.melodia.gui.MelodiaGUI
import dev.meluhdy.melodia.gui.MelodiaGUIItem
import dev.meluhdy.melodia.utils.ItemUtils
import dev.meluhdy.melodia.utils.TranslatedString
import dev.meluhdy.scoville.ScovilleUI
import dev.meluhdy.scoville.gui.IScovilleGUI
import dev.meluhdy.scoville.gui.cosmetic.cosmetics.tag.TagListInventory
import dev.meluhdy.scovilleCosmetics.core.tag.ChatTag
import net.kyori.adventure.text.TextComponent
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent

class TagInventory(p: Player, prevGUI: MelodiaGUI? = null) : MelodiaGUI(ScovilleUI.plugin, p, prevGUI), IScovilleGUI {

    override val rows: Int = 5
    override val title: TextComponent = getTitle(p, TranslatedString("menu.cosmetics.tag.title", arrayOf())) as TextComponent
    override val melodiaItems: ArrayList<MelodiaGUIItem> = arrayListOf(
        getBack(36, this),
        this.createSkull(10, "https://textures.minecraft.net/texture/49958766c85fdf9d41ae847b36bbd4c9c6978b6fac6f446d65d6dbe16622c90", "menu.cosmetics.tag.rank.title", ChatTag.TagType.RANK),
        this.createSkull(13, "https://textures.minecraft.net/texture/daa8fc8de6417b48d48c80b443cf5326e3d9da4dbe9b25fcd49549d96168fc0", "menu.cosmetics.tag.course.title", ChatTag.TagType.COURSE),
        this.createSkull(16, "https://textures.minecraft.net/texture/57c66f5a4b408005b336da6676e8f6a2a67eea315fb7e91360acc047802fa320", "menu.cosmetics.tag.shiny.title", ChatTag.TagType.SHINY),
        this.createSkull(28, "https://textures.minecraft.net/texture/89a995928090d842d4afdb2296ffe24f2e944272205ceba848ee4046e01f3168", "menu.cosmetics.tag.hidden.title", ChatTag.TagType.HIDDEN),
        this.createSkull(31, "https://textures.minecraft.net/texture/e34a592a79397a8df3997c43091694fc2fb76c883a76cce89f0227e5c9f1dfe", "menu.cosmetics.tag.record.title", ChatTag.TagType.RECORD),
        this.createSkull(34, "https://textures.minecraft.net/texture/df7467c5f738c641246c09f8ce791e339a86e81de62049b41f492888172fa726", "menu.cosmetics.tag.special.title", ChatTag.TagType.SPECIAL),
    )

    fun createSkull(
        pos: Int,
        url: String,
        title: String,
        type: ChatTag.TagType
    ) = MelodiaGUIItem(
        pos, ItemUtils.createSkull(
            url,
            1,
            getTitle(p, TranslatedString(title, arrayOf()))
        )
    ) {
        TagListInventory(type, p, this).open()
    }

    override fun extraItems() {
        createBorder(this)
    }

    override fun onInventoryClick(e: InventoryClickEvent) {}

}