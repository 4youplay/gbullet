package com.bulletphysics.util;

import javax.vecmath.Matrix3f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

import com.bulletphysics.extras.gimpact.BoxCollision.AABB;
import com.bulletphysics.extras.gimpact.BoxCollision.BoxBoxTransformCache;
import com.bulletphysics.extras.gimpact.PrimitiveTriangle;
import com.bulletphysics.extras.gimpact.TriangleContact;
import com.bulletphysics.linearmath.Transform;

public class Stack {

	private static final int TYPE_VECTOR3F = 0;
	private static final int TYPE_VECTOR4F = 1;
	private static final int TYPE_AABB = 2;
	private static final int TYPE_TRANSFORM = 3;
	private static final int TYPE_MATRIX3F = 4;
	private static final int TYPE_QUAT4F = 5;
	private static final int TYPE_BOX_BOX_TRANSFORM_CACHE = 6;
	private static final int TYPE_PRIMITIVE_TRIANGLE = 7;
	private static final int TYPE_TRIANGLE_CONTACT = 8;

	static ObjectArrayList<Vector3f> vector3fStack = new ObjectArrayList<Vector3f>();
	static ObjectArrayList<Vector4f> vector4fStack = new ObjectArrayList<Vector4f>();
	static ObjectArrayList<AABB> aabbStack = new ObjectArrayList<AABB>();
	static ObjectArrayList<Transform> transformStack = new ObjectArrayList<Transform>();
	static ObjectArrayList<Matrix3f> matrix3fStack = new ObjectArrayList<Matrix3f>();
	static ObjectArrayList<Quat4f> quat4fStack = new ObjectArrayList<Quat4f>();
	static ObjectArrayList<BoxBoxTransformCache> boxBoxTransformCacheStack = new ObjectArrayList<BoxBoxTransformCache>();
	static ObjectArrayList<PrimitiveTriangle> primitiveTriangleStack = new ObjectArrayList<PrimitiveTriangle>();
	static ObjectArrayList<TriangleContact> triangleContactStack = new ObjectArrayList<TriangleContact>();

	static int[] stackPositions = new int[9];
	static int[] positions = new int[32768];
	static int[] types = new int[65536];
	public static int typePos;
	static int posPos;
	
	Stack stack = new Stack();

	public static void libraryCleanCurrentThread() {
		posPos = 0;
		typePos = 0;
		for (int i = 0; i < stackPositions.length; i++) {
			stackPositions[i] = 0;
		}
	}

	public static Vector3f alloc(Vector3f original) {
		Vector3f v = allocVector3f();
		v.set(original);
		return v;
	}

	public static Transform alloc(Transform original) {
		Transform t = allocTransform();
		t.set(original);
		return t;
	}

	public static Matrix3f alloc(Matrix3f original) {
		Matrix3f m = allocMatrix3f();
		m.set(original);
		return m;
	}

	public static AABB alloc(AABB box) {
		AABB aabb = allocAABB();
		aabb.set(box);
		return aabb;
	}

	public static Quat4f alloc(Quat4f rotation) {
		Quat4f q = allocQuat4f();
		q.set(rotation);
		return q;
	}

	public static Vector3f allocVector3f() {
		types[typePos++] = TYPE_VECTOR3F;
		int pos = stackPositions[TYPE_VECTOR3F]++;
		if (vector3fStack.size() <= pos) {
			vector3fStack.add(new Vector3f());
		}
		return vector3fStack.get(pos);
	}

	public static Matrix3f allocMatrix3f() {
		types[typePos++] = TYPE_MATRIX3F;
		int pos = stackPositions[TYPE_MATRIX3F]++;
		if (matrix3fStack.size() <= pos) {
			matrix3fStack.add(new Matrix3f());
		}
		return matrix3fStack.get(pos);
	}

	public static Quat4f allocQuat4f() {
		types[typePos++] = TYPE_QUAT4F;
		int pos = stackPositions[TYPE_QUAT4F]++;
		if (quat4fStack.size() <= pos) {
			quat4fStack.add(new Quat4f());
		}
		return quat4fStack.get(pos);
	}

	public static Transform allocTransform() {
		types[typePos++] = TYPE_TRANSFORM;
		int pos = stackPositions[TYPE_TRANSFORM]++;
		if (transformStack.size() <= pos) {
			transformStack.add(new Transform());
		}
		return transformStack.get(pos);
	}

	public static Vector4f allocVector4f() {
		types[typePos++] = TYPE_VECTOR4F;
		int pos = stackPositions[TYPE_VECTOR4F]++;
		if (vector4fStack.size() <= pos) {
			vector4fStack.add(new Vector4f());
		}
		return vector4fStack.get(pos);
	}

	public static AABB allocAABB() {
		types[typePos++] = TYPE_AABB;
		int pos = stackPositions[TYPE_AABB]++;
		if (aabbStack.size() <= pos) {
			aabbStack.add(new AABB());
		}
		return aabbStack.get(pos);
	}

	public static BoxBoxTransformCache allocBoxBoxTransformCache() {
		types[typePos++] = TYPE_BOX_BOX_TRANSFORM_CACHE;
		int pos = stackPositions[TYPE_BOX_BOX_TRANSFORM_CACHE]++;
		if (boxBoxTransformCacheStack.size() <= pos) {
			boxBoxTransformCacheStack.add(new BoxBoxTransformCache());
		}
		return boxBoxTransformCacheStack.get(pos);
	}

	public static PrimitiveTriangle allocPrimitiveTriangle() {
		types[typePos++] = TYPE_PRIMITIVE_TRIANGLE;
		int pos = stackPositions[TYPE_PRIMITIVE_TRIANGLE]++;
		if (primitiveTriangleStack.size() <= pos) {
			primitiveTriangleStack.add(new PrimitiveTriangle());
		}
		return primitiveTriangleStack.get(pos);
	}

	public static TriangleContact allocTriangleContact() {
		types[typePos++] = TYPE_TRIANGLE_CONTACT;
		int pos = stackPositions[TYPE_TRIANGLE_CONTACT]++;
		if (triangleContactStack.size() <= pos) {
			triangleContactStack.add(new TriangleContact());
		}
		return triangleContactStack.get(pos);
	}

	public static int enter() {
		types[typePos++] = -1;
		return typePos;
	}
	
	public static int getSp() {
		return typePos;
	}
	
	public static void leave() {
		while(true) {
			int type = types[--typePos];
			if (type == -1) {
				break;
			}
			stackPositions[type]--;
		}
		typePos--;
	}

	public static void leave(int sp) {
		for (int i = sp; i < typePos; i++) {
			int type = types[i];
			if (type != -1) {
				stackPositions[type]--;
			}
		}
		assert types[typePos] == -1;
		typePos = sp - 1;
	}

}
