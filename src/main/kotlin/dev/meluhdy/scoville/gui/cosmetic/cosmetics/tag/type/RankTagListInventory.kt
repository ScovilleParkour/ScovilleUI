package dev.meluhdy.scoville.gui.cosmetic.cosmetics.tag.type

import dev.meluhdy.melodia.gui.MelodiaGUI
import dev.meluhdy.melodia.utils.TextUtils
import dev.meluhdy.scoville.gui.cosmetic.cosmetics.tag.TagListInventory
import dev.meluhdy.scovilleCosmetics.ScovilleCosmetics
import dev.meluhdy.scovilleCosmetics.core.chat.tag.type.rank.RankTag
import dev.meluhdy.scovilleCosmetics.core.chat.tag.type.rank.RankTagManager
import org.bukkit.entity.Player

class RankTagListInventory(p: Player, prevGUI: MelodiaGUI?) : TagListInventory<RankTag>(p, prevGUI) {

    override fun getDesc(obj: RankTag): String = TextUtils.translate(ScovilleCosmetics.plugin, obj.descTransId, p.locale())

    override val objects: ArrayList<RankTag> = RankTagManager.getAll()

}