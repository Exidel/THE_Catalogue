import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle



class Labels(
    /** DDMenu */
    val firstDDLabel: String = "Library",
    val secondDDLabel: String = "Category",
    val sort: String = "Sort",
    val size: String = "Size",
    val searchDDMenu: List<String> = listOf("All", "Library", "Category", "Section"),
    val sortDDMenu: List<String> = listOf("None", "Name: A - Z", "Name: Z - A"),
    val menuList: List<String> = listOf("LOGO shadow", "Reset window size", "Language", "Exit"),

    /** EditMenu */
    val open: String = "Open",
    val delete: String = "Delete",

    /** DeleteDialog */
    val yes: String = "Yes",
    val no: String = "No",
    val message: String = "Delete selected?"
)


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