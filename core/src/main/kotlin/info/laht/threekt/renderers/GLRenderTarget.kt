package info.laht.threekt.renderers

import info.laht.threekt.LinearFilter
import info.laht.threekt.core.EventDispatcher
import info.laht.threekt.core.EventDispatcherImpl
import info.laht.threekt.math.Vector4
import info.laht.threekt.textures.DepthTexture
import info.laht.threekt.textures.Texture

open class GLRenderTarget(
    width: Int,
    height: Int,
    options: Options? = null
) : EventDispatcher by EventDispatcherImpl() {

    var width = width
        private set

    var height = height
        private set

    val scissor = Vector4(0, 0, width, height)
    var scissorTest = false

    val viewport = Vector4(0, 0, width, height)

    var depthBuffer: Boolean
    var stencilBuffer: Boolean
    var depthTexture: DepthTexture?

    var texture: Texture

    init {

        @Suppress("NAME_SHADOWING")
        val options = options ?: Options()

        depthBuffer = options.depthBuffer ?: true
        stencilBuffer = options.stencilBuffer ?: true
        depthTexture = options.depthTexture

        texture = Texture(
            null,
            null,
            options.wrapT,
            options.wrapS,
            options.magFilter,
            options.minFilter,
            options.format,
            options.type,
            options.anisotropy,
            options.encoding
        )

        texture.minFilter = options.minFilter ?: LinearFilter
        texture.generateMipmaps = options.generateMipmaps ?: false

    }

    fun setSize(width: Int, height: Int) {

        if (this.width != width || this.height != height) {

            this.width = width
            this.height = height

            this.texture.image?.width = width;
            this.texture.image?.height = height;

            dispose()

        }

        this.viewport.set(0, 0, width, height);
        this.scissor.set(0, 0, width, height);

    }

    fun copy( source: GLRenderTarget ): GLRenderTarget {

        this.width = source.width
        this.height = source.height

        this.viewport.copy( source.viewport )

        this.texture = source.texture.clone()

        this.depthBuffer = source.depthBuffer
        this.stencilBuffer = source.stencilBuffer
        this.depthTexture = source.depthTexture

        return this

    }

    fun dispose() {
        dispatchEvent("dispose", this)
    }

    data class Options(
        val wrapT: Int? = null,
        val wrapS: Int? = null,
        val magFilter: Int? = null,
        val minFilter: Int? = null,
        val format: Int? = null,
        val type: Int? = null,
        val anisotropy: Int? = null,
        val encoding: Int? = null,
        val generateMipmaps: Boolean? = null,
        val depthBuffer: Boolean? = null,
        val stencilBuffer: Boolean? = null,
        val depthTexture: DepthTexture? = null
    )

}
