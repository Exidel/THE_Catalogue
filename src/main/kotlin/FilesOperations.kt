import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import org.jetbrains.skia.Image
import java.io.File
import java.nio.file.Files
import java.nio.file.Files.createDirectory
import java.nio.file.Path
import kotlin.io.path.*


object DirManipulations {

/** DIRECTORIES HIERARCHY */

    internal const val root: String = "Resources/"


    /** get Libraries list */
    fun getLibsList(): List<String> {

        val list = mutableListOf<String>()

        if (Files.notExists(Path(root))) createDirectory(Path(root))

        try {

            Path(root).listDirectoryEntries().filter { it.isDirectory() }.forEach { list.add(it.name) }

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return list
    }


    /** get Categories list */
    fun getCategoriesList(index: Int): List<String> {

        val list = mutableListOf<String>()

        if (getLibsList().isNotEmpty()) {

            try {

                Path(root + getLibsList()[index]).listDirectoryEntries().filter { it.isDirectory() }
                    .forEach { list.add(it.name) }

            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        return list

    }


    /** get Sections list */
    fun getSectionsList(lib: Int, cat: Int): List<String> {

        val list = mutableListOf<String>()

        if (getLibsList().isNotEmpty() && getCategoriesList(lib).isNotEmpty() && cat <= getCategoriesList(lib).lastIndex) {

            if (Files.exists(Path(root + getLibsList()[lib] + "/" + getCategoriesList(lib)[cat]))) {

                try {

                    Path(root + getLibsList()[lib] + "/" + getCategoriesList(lib)[cat]).listDirectoryEntries()
                        .filter { it.isDirectory() }.forEach { list.add(it.name) }

                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

        }

        return list

    }


    /** BETA!!! Universal get dir list */
    fun getDirList(path: Path = Path("")): List<Path> {

        val list = mutableListOf<Path>()
        if (Files.notExists(Path(root))) createDirectory(Path(root))
        val p: Path = if (path.pathString != "") path else Path(root)

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

        val libList = getLibsList()
        val catList = getCategoriesList(libIndex)
        val secList = getSectionsList(libIndex, categoryIndex)

        val list = if (
            libList.isNotEmpty() && libIndex <= libList.lastIndex
            && catList.isNotEmpty() && categoryIndex <= catList.lastIndex
            && secList.isNotEmpty() && sectionIndex <= secList.lastIndex
            && scanSelected(root + libList[libIndex] + "/"
                    + catList[categoryIndex] + "/"
                    + secList[sectionIndex]).isNotEmpty()
        ) {
            scanSelected(root + libList[libIndex] + "/"
                    + catList[categoryIndex] + "/"
                    + secList[sectionIndex])
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

        val libList = getLibsList()
        val catList = getCategoriesList(libIndex)
        val secList = getSectionsList(libIndex, categoryIndex)

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
                    libList.isNotEmpty() && libIndex <= libList.lastIndex
                    && scanSelected(root + libList[libIndex]).isNotEmpty()
                ) {
                    scanSelected(root + libList[libIndex]).filter {
                        it.name.contains(tfText, true)
                                && it.name.contains(".jpg", true)
                                && it.name.contains("preview", true)
                    }.map { it.pathString }
                } else listOf()

                2 -> if (
                    libList.isNotEmpty() && libIndex <= libList.lastIndex
                    && catList.isNotEmpty() && categoryIndex <= catList.lastIndex &&
                    scanSelected(root + libList[libIndex] + "/" + catList[categoryIndex]).isNotEmpty()
                ) {
                    scanSelected(root + libList[libIndex] + "/" + catList[categoryIndex])
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
                    && scanSelected(root + libList[libIndex] + "/"
                            + catList[categoryIndex] + "/"
                            + secList[sectionIndex]).isNotEmpty()
                ) {
                    scanSelected(root + libList[libIndex] + "/"
                            + catList[categoryIndex] + "/"
                            + secList[sectionIndex])
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



}