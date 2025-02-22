package info.laht.threekt.objects

import info.laht.threekt.core.*
import info.laht.threekt.materials.LineBasicMaterial
import info.laht.threekt.math.Vector3

open class Line(

    override val geometry: BufferGeometry = BufferGeometry(),
    override val material: LineBasicMaterial = LineBasicMaterial()

): Object3DImpl(), GeometryObject, MaterialObject {

    open fun computeLineDistances(): Line {

        val start = Vector3()
        val end = Vector3()

        // we assume non-indexed geometry
        if (geometry.index == null) {

            val positionAttribute = geometry.attributes.position!!
            val lineDistances = mutableListOf(0f)

            for (i in 1 until positionAttribute.count) {
                start.fromBufferAttribute(positionAttribute, i - 1);
                end.fromBufferAttribute(positionAttribute, i);

                lineDistances[i] = lineDistances[i - 1];
                lineDistances[i] += start.distanceTo(end);
            }

            geometry.addAttribute("lineDistance", FloatBufferAttribute(lineDistances.toFloatArray(), 1));

        } else {
            println( "Line.computeLineDistances(): Computation only possible with non-indexed BufferGeometry." )
        }

        return this
    }

    override fun raycast(raycaster: Raycaster, intersects: List<Intersection>) {

    }

}
