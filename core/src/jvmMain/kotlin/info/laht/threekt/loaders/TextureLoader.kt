package info.laht.threekt.loaders

import info.laht.threekt.TextureFormat
import info.laht.threekt.TextureType
import info.laht.threekt.textures.Texture
import java.io.File

object TextureLoader {

    @JvmOverloads
    fun load(path: String, flipY: Boolean = true): Texture {

        val file = File(path)
        if (!file.exists()) {
            throw NoSuchFileException(file)
        }
        val isJpg = file.name.endsWith(".jpg", true) || file.name.endsWith(".jpeg", true)

        val texture = Texture(
            image = ImageLoader.load(file, flipY),
            format = if (isJpg) TextureFormat.RGB else TextureFormat.RGBA,
            type = TextureType.UnsignedByte
        )
        texture.needsUpdate = true

        return texture

    }

}
