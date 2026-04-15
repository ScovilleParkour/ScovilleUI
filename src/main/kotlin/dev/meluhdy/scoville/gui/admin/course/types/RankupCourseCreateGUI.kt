package dev.meluhdy.scoville.gui.admin.course.types

import dev.meluhdy.melodia.gui.MelodiaGUI
import dev.meluhdy.melodia.gui.MelodiaGUIItem
import dev.meluhdy.melodia.utils.ItemUtils
import dev.meluhdy.melodia.utils.TranslatedString
import dev.meluhdy.scoville.core.course.CourseManager
import dev.meluhdy.scoville.core.course.courses.RankupCourse
import dev.meluhdy.scoville.gui.admin.course.CourseCreateGUI
import dev.meluhdy.scoville.serialization.course.RankupCourseSerializer
import net.kyori.adventure.text.TextComponent
import org.bukkit.Material
import org.bukkit.entity.Player

class RankupCourseCreateGUI(p: Player, pg: MelodiaGUI?) : CourseCreateGUI<RankupCourseSerializer.RankupCourseBuilder>("", p, pg) {

    var availableRanks = RankupCourse.Rank.entries.filter { rank -> !CourseManager.exists { course -> course is RankupCourse && course.rank == rank } }.sortedBy { rank -> rank.ordinal }

    override val rows: Int = 5
    override var title: TextComponent = this.getTitle(this.p, TranslatedString("menu.admin.courses.create.title.rankup", arrayOf())) as TextComponent

    init {
        if (availableRanks.isEmpty()) {
            p.closeInventory()
            p.sendMessage(this.getTitle(this.p, TranslatedString("menu.admin.courses.create.rankup.exists", arrayOf())))
        }
        this.currBuilder.rank = this.availableRanks.first()
    }

    override fun getBuilder(): RankupCourseSerializer.RankupCourseBuilder {
        return RankupCourseSerializer.RankupCourseBuilder()
    }

    fun rankToItem(rank: RankupCourse.Rank): Material {
        return when (rank) {
            RankupCourse.Rank.BELL -> Material.GREEN_TERRACOTTA
            RankupCourse.Rank.PEPPERONCINI -> Material.GREEN_CONCRETE
            RankupCourse.Rank.ANAHEIM -> Material.GREEN_WOOL
            RankupCourse.Rank.POBLANO -> Material.LIME_TERRACOTTA
            RankupCourse.Rank.GUAJILLO -> Material.LIME_CONCRETE
            RankupCourse.Rank.JALAPENO -> Material.YELLOW_WOOL
            RankupCourse.Rank.SERRANO -> Material.YELLOW_CONCRETE
            RankupCourse.Rank.MANZANO -> Material.YELLOW_TERRACOTTA
            RankupCourse.Rank.CAYENNE -> Material.ORANGE_WOOL
            RankupCourse.Rank.THAI -> Material.ORANGE_CONCRETE
            RankupCourse.Rank.DATIL -> Material.ORANGE_TERRACOTTA
            else -> Material.STONE_BUTTON
        }
    }

    override val melodiaItems: ArrayList<MelodiaGUIItem>
        get() {
            val currRank = this.currBuilder.rank
            val currRankIndex = this.availableRanks.indexOf(currRank)

            val rankItems = ArrayList<MelodiaGUIItem>()
            if (currRankIndex > 0) {
                val prevRank = this.availableRanks[currRankIndex - 1]
                rankItems.add(MelodiaGUIItem(19, ItemUtils.createItem(
                    Material.ARROW, 1,
                    this.getTitle(this.p, TranslatedString("menu.admin.courses.create.rank.title", arrayOf(prevRank.color, prevRank.displayName)))
                )) {
                    this.currBuilder.rank = prevRank
                    this.currBuilder.name = prevRank.displayName
                    this.currBuilder.coloredName = "&${prevRank.color}&l" + prevRank.displayName
                    this.initializeItems()
                    p.updateInventory()
                })
            }
            rankItems.add(MelodiaGUIItem(22, ItemUtils.createItem(
                this.rankToItem(currRank), 1,
                this.getTitle(this.p, TranslatedString("menu.admin.courses.create.rank.title", arrayOf(currRank.color, currRank.displayName)))
            )) {})
            if (currRankIndex < this.availableRanks.size - 1) {
                val nextRank = this.availableRanks[currRankIndex + 1]
                rankItems.add(MelodiaGUIItem(25, ItemUtils.createItem(
                    Material.ARROW, 1,
                    this.getTitle(this.p, TranslatedString("menu.admin.courses.create.rank.title", arrayOf(nextRank.color, nextRank.displayName)))
                )) {
                    this.currBuilder.rank = nextRank
                    this.currBuilder.name = nextRank.displayName
                    this.currBuilder.coloredName = "&${nextRank.color}&l" + nextRank.displayName
                    this.initializeItems()
                    p.updateInventory()
                })
            }
            rankItems.add(makeConfirmButton(40))

            return rankItems
        }

}