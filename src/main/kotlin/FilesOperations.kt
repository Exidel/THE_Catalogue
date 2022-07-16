import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import org.jetbrains.skia.Image
import java.awt.Desktop
import java.io.File
import java.nio.file.Files
import java.nio.file.Files.createDirectory
import java.nio.file.Path
import kotlin.io.path.*


object DirManipulations {

/** DIRECTORIES HIERARCHY */

    private const val root: String = "Resources/"


    /** get directories list */
    fun getDirList(path: String = ""): List<Path> {

        val list = mutableListOf<Path>()
        if (Files.notExists(Path(root))) createDirectory(Path(root))
        val p: Path = if (path != "") Path(path) else Path(root)

        try {
            p.listDirectoryEntries().filter { it.isDirectory() }.forEach { list.add(it) }
        } catch (e: Exception) { e.printStackTrace() }

        return list
    }


/** SEARCH */

    /** get searching "everywhere" list */
    fun scanAll(): List<Path> {

        val list = mutableListOf<Path>()

        if (Path(root).exists()) {
            try {
                File(root).walkTopDown().filter { !it.isDirectory }.forEach { list.add(it.toPath()) }
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


}