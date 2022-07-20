import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import org.jetbrains.skia.Image
import java.awt.Desktop
import java.io.File
import java.nio.file.Files
import java.nio.file.Files.createDirectory
import java.nio.file.Path
import kotlin.io.path.*


object DirManipulations {

/** DIRECTORIES HIERARCHY */

    /** root directory for materials */
    private const val root: String = "Resources/"

    /** get directories list */
    fun getDirList(path: String = ""): List<Path> {

        val rootDirectory = if (loadSettings().rootDirectory != "") loadSettings().rootDirectory else root
        val list = mutableListOf<Path>()
        if (Files.notExists(Path(rootDirectory))) createDirectory(Path(rootDirectory))
        val p: Path = if (path != "") Path(path) else Path(rootDirectory)

        try {
            p.listDirectoryEntries().filter { it.isDirectory() }.forEach { list.add(it) }
        } catch (e: Exception) { e.printStackTrace() }

        return list
    }


/** SEARCH */

    /** get searching "everywhere" list */
    fun scanAll(): List<Path> {

        val rootDirectory = if (loadSettings().rootDirectory != "") loadSettings().rootDirectory else root
        val list = mutableListOf<Path>()

        if (Path(rootDirectory).exists()) {
            try {
                File(rootDirectory).walkTopDown().filter { !it.isDirectory }.forEach { list.add(it.toPath()) }
            } catch (e: Exception) { e.printStackTrace() }
        }

        return list
    }

    /** get searching "library" list */
    fun scanSelected(path: String = ""): List<Path> {

        val list = mutableListOf<Path>()

        if (Path(path).exists() && path != "") {
            try {
                File(path).walkTopDown().filter { !it.isDirectory }.forEach { list.add(it.toPath()) }
            } catch (e: Exception) { e.printStackTrace() }
        }

        return list

    }


/** GET IMG */

    /** get ImageBitmap from path */
    fun getImg(path: String): ImageBitmap {
        val img: ByteArray = File(path).readBytes()
        return Image.makeFromEncoded(img).toComposeImageBitmap()
    }


/** GET GRID LIST */

    /** return list for Grid from selected folder */
    fun getMainList(libIndex: Int, categoryIndex: Int, sectionIndex: Int): List<String> {

        val libList = getDirList()
        val catList = if (libIndex <= libList.lastIndex) getDirList(libList[libIndex].pathString) else listOf()
        val secList = if (libIndex <= libList.lastIndex && categoryIndex <= catList.lastIndex) getDirList(catList[categoryIndex].pathString) else listOf()

        val list = if (
            libList.isNotEmpty() && libIndex <= libList.lastIndex
            && catList.isNotEmpty() && categoryIndex <= catList.lastIndex
            && secList.isNotEmpty() && sectionIndex <= secList.lastIndex
            && scanSelected(secList[sectionIndex].pathString).isNotEmpty()
        ) {
            scanSelected(secList[sectionIndex].pathString)
                .filter {
                    it.name.contains("preview", true) &&
                            it.name.contains(".jpg", true)
                }
                .map { it.pathString }
        } else listOf()

        return list
    }

    /** return list for Grid filtered by searching request */
    fun getSearchList(tfText: String, libIndex: Int, categoryIndex: Int, sectionIndex: Int, searchIndex: Int): List<String> {

        val libList = getDirList()
        val catList = if (libIndex <= libList.lastIndex) getDirList(libList[libIndex].pathString) else listOf()
        val secList = if (libIndex <= libList.lastIndex && categoryIndex <= catList.lastIndex) getDirList(catList[categoryIndex].pathString) else listOf()

        val list = if (tfText != "") {

            when(searchIndex) {

                0 -> if (scanAll().isNotEmpty()) {
                    scanAll().filter {
                        it.name.contains(tfText, true)
                                && it.name.contains(".jpg", true)
                                && it.name.contains("preview", true)
                    }
                        .map { it.pathString }
                } else listOf()

                1 -> if (
                    libList.isNotEmpty() && libIndex <= libList.lastIndex &&
                    scanSelected(libList[libIndex].pathString).isNotEmpty()
                ) {
                    scanSelected(libList[libIndex].pathString).filter {
                        it.name.contains(tfText, true)
                                && it.name.contains(".jpg", true)
                                && it.name.contains("preview", true)
                    }.map { it.pathString }
                } else listOf()

                2 -> if (
                    libList.isNotEmpty() && libIndex <= libList.lastIndex
                    && catList.isNotEmpty() && categoryIndex <= catList.lastIndex
                    && scanSelected(catList[categoryIndex].pathString).isNotEmpty()
                ) {
                    scanSelected(catList[categoryIndex].pathString)
                        .filter { it.name.contains(tfText, true)
                                && it.name.contains(".jpg", true)
                                && it.name.contains("preview", true)
                        }
                        .map { it.pathString }
                } else listOf()

                3 -> if (
                    libList.isNotEmpty() && libIndex <= libList.lastIndex
                    && catList.isNotEmpty() && categoryIndex <= catList.lastIndex
                    && secList.isNotEmpty() && sectionIndex <= secList.lastIndex
                    && scanSelected(secList[sectionIndex].pathString).isNotEmpty()
                ) {
                    scanSelected(secList[sectionIndex].pathString)
                        .filter { it.name.contains(tfText, true)
                                && it.name.contains(".jpg", true)
                                && it.name.contains("preview", true)
                        }
                        .map { it.pathString }
                } else listOf()

                else -> listOf()

            }

        } else listOf()

        return list

    }


/** INFO.txt actions */

    /** get info.txt as String */
    fun getInfo(path: String): String {
        return if (path != "" && File(path).exists()) File(path).readText() else ""
    }

    /** create info.txt */
    fun createInfo(txt: String, path: String) {
        if (path != "" && File(File(path).parent).exists()) File(path).writeText(txt, Charsets.UTF_8)
    }


/** OPEN DIRECTORY FROM PROVIDED PATH */

    /** open directory */
    fun openDir(path: String) {
        if (path != "" && File(path).exists()) {
            val uri = File(path).toURI()
            Desktop.getDesktop().browse(uri)
        }
    }

    /** remove directory or file */
    fun removeDir(path: String) {
        if (path != "" && File(path).exists()) {
            File(path).deleteRecursively()
        }
    }


/** SETTINGS */

    /** save settings.txt */
    fun saveSettings(state: WindowState? = null, lang: Int = 0, logo: Boolean = false, size: Float = 128f, rootDirectory: String = "") {

        val settings: String = if (state != null)
            "${state.size.width.value}" + "\n" + "${state.size.height.value}" + "\n" +
            "${state.position.x.value}" + "\n" + "${state.position.y.value}" + "\n" +
            "$lang" + "\n" + "$logo" + "\n" + "$size" + "\n" + rootDirectory
        else ""

        if (settings.isNotEmpty()) File("settings.txt").writeText(settings, Charsets.UTF_8)
    }

    /** load settings.txt */
    fun loadSettings(): Settings {
        val list = if (File("settings.txt").exists()) File("settings.txt").readLines() else listOf()
        var obj = Settings()

        try {
            obj = if (list.isNotEmpty()) Settings(
                winSize = if (list[0].isNotEmpty() && list[1].isNotEmpty() && list.lastIndex >= 1)
                    DpSize(list[0].toFloat().dp, list[1].toFloat().dp) else Settings().winSize,
                winPosition = if ( !list[2].contains("NaN", true) && !list[3].contains("NaN", true) &&
                    list[2].isNotEmpty() && list[3].isNotEmpty() && list.lastIndex >= 3)
                    WindowPosition(list[2].toFloat().dp, list[3].toFloat().dp) else Settings().winPosition,
                lang = if (list[4].isNotEmpty() && list.lastIndex >= 4) list[4].toInt() else Settings().lang,
                logo = if (list[5].isNotEmpty() && list.lastIndex >= 5) list[5].toBoolean() else Settings().logo,
                itemSize = if (list[6].isNotEmpty() && list.lastIndex >= 6) list[6].toFloat() else Settings().itemSize,
                rootDirectory = if (list[7].isNotEmpty() && list.lastIndex >= 7) list[7] else Settings().rootDirectory
            ) else Settings()
        } catch (e: Exception) { File("settingsErrorLog.txt").writeText(e.stackTraceToString()) }

        return obj

    }


/** LOCALIZATION */

    /** create "localization" folder and eng.txt file if not exist */
    fun createLangFolder() {

        if (Path("localization/").notExists()) {

            createDirectory(Path("localization/"))

            File("localization/eng.txt").writeText(
                Labels().firstDDLabel + "\n" +
                        Labels().secondDDLabel + "\n" +
                        Labels().sort + "\n" +
                        Labels().size + "\n" +
                        Labels().searchDDMenu + "\n" +
                        Labels().sortDDMenu + "\n" +
                        Labels().menuList + "\n" +
                        Labels().open + "\n" +
                        Labels().delete + "\n" +
                        Labels().yes + "\n" +
                        Labels().no + "\n" +
                        Labels().message + "\n"
            )

        } else {

            if (Path("localization/eng.txt").notExists())

                File("localization/eng.txt").writeText(
                    Labels().firstDDLabel + "\n" +
                            Labels().secondDDLabel + "\n" +
                            Labels().sort + "\n" +
                            Labels().size + "\n" +
                            Labels().searchDDMenu + "\n" +
                            Labels().sortDDMenu + "\n" +
                            Labels().menuList + "\n" +
                            Labels().open + "\n" +
                            Labels().delete + "\n" +
                            Labels().yes + "\n" +
                            Labels().no + "\n" +
                            Labels().message + "\n"
                )
        }

    }

    /** get list of lang.txt files */
    fun getLangList(): List<Path> {
        createLangFolder()
        return if (Path("localization/").exists()) scanSelected("localization/") else listOf()
    }

    /** load language to use for program labels */
    fun loadLanguage(index: Int): Labels {
        val lang = if (getLangList().isNotEmpty() && index <= getLangList().lastIndex)
                                        File(getLangList()[index].pathString).readLines() else listOf()
        var obj = Labels()

        try {
            obj = if (lang.isNotEmpty()) {
                Labels(
                    firstDDLabel = if (lang[0].isNotEmpty()) lang[0] else Labels().firstDDLabel,
                    secondDDLabel = if (lang[1].isNotEmpty() && lang.lastIndex >= 1) lang[1] else Labels().secondDDLabel,
                    sort = if (lang[2].isNotEmpty() && lang.lastIndex >= 2) lang[2] else Labels().sort,
                    size = if (lang[3].isNotEmpty() && lang.lastIndex >= 3) lang[3] else Labels().size,
                    searchDDMenu = if (lang[4].isNotEmpty() && lang.lastIndex >= 4) lang[4]
                        .removeSurrounding("[", "]").split(", ") else Labels().searchDDMenu,
                    sortDDMenu = if (lang[5].isNotEmpty() && lang.lastIndex >= 5) lang[5]
                        .removeSurrounding("[", "]").split(", ") else Labels().sortDDMenu,
                    menuList = if (lang[6].isNotEmpty() && lang.lastIndex >= 6) lang[6]
                        .removeSurrounding("[", "]").split(", ") else Labels().menuList,
                    open = if (lang[7].isNotEmpty() && lang.lastIndex >= 7) lang[7] else Labels().open,
                    delete = if (lang[8].isNotEmpty() && lang.lastIndex >= 8) lang[8] else Labels().delete,
                    yes = if (lang[9].isNotEmpty() && lang.lastIndex >= 9) lang[9] else Labels().yes,
                    no = if (lang[10].isNotEmpty() && lang.lastIndex >= 10) lang[10] else Labels().no,
                    message = if (lang[11].isNotEmpty() && lang.lastIndex >= 11) lang[11] else Labels().message
                )
            } else Labels()
        } catch (e: Exception) { e.printStackTrace() }

        return obj

    }




}



data class Settings(
    val winSize: DpSize = DpSize(1200.dp, 800.dp),
    val winPosition: WindowPosition = WindowPosition(Alignment.Center),
    val lang: Int = 0,
    val logo: Boolean = false,
    val itemSize: Float = 128f,
    val rootDirectory: String = ""
)