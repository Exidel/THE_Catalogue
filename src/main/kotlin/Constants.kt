import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle

internal class Labels {

/** DDMenu */
    val firstDDLabel = "Library"
    val secondDDLabel = "Category"
    val sort = "Sort"
    val size = "Size"
    val add = "+"
    val searchDDMenu = listOf<String>("All", "Library", "Category", "Section")
    val sortDDMenu = listOf<String>("None", "Name", "Date")

/** EditMenu */
    val open = "Open"
    val edit = "Edit"
    val delete = "Delete"


    fun loadLanguage(): List<String> {return listOf<String>()}

}


object BasicColors {
    val primaryBGColor = Color(100, 100, 100, 255)
    val secondaryBGColor = Color(130, 130, 130, 255)
    val tertiaryBGColor = Color(200, 200, 200, 255)
    val textColor = Color.White
}


object Styles {
    val textStyle = TextStyle(
        color = BasicColors.textColor,
        shadow = Shadow(
            color = Color.Black,
            offset = Offset(1f, 1f),
            blurRadius = 0.5f)
    )
}