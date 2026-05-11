package dev.meluhdy.scoville.gui.cosmetic.cosmetics.tag.type

import dev.meluhdy.melodia.gui.MelodiaGUI
import dev.meluhdy.melodia.utils.TextUtils
import dev.meluhdy.scoville.gui.cosmetic.cosmetics.tag.TagListInventory
import dev.meluhdy.scovilleCosmetics.ScovilleCosmetics
import dev.meluhdy.scovilleCosmetics.core.chat.tag.type.special.SpecialTag
import dev.meluhdy.scovilleCosmetics.core.chat.tag.type.special.SpecialTagManager
import org.bukkit.entity.Player

class SpecialTagListInventory(p: Player, prevGUI: MelodiaGUI?) : TagListInventory<SpecialTag>(p, prevGUI) {

    override fun getDesc(obj: SpecialTag): String = TextUtils.translate(ScovilleCosmetics.plugin, obj.descTransId, p.locale())

    override val objects: ArrayList<SpecialTag> = SpecialTagManager.getAll()

}