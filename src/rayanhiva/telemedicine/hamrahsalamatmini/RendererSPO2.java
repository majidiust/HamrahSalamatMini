package rayanhiva.telemedicine.hamrahsalamatmini;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;

public class RendererSPO2 implements Renderer {
	private FloatBuffer mVertexBuffer = null;
	private ShortBuffer mIndexBuffer = null;
	
	public Integer lock;
	
	private final int GAP_COUNT = 10;
	private final float DATA_LENGTH = 0.06f;
	private float mTotalLength = 6.0f;// 12.6f;
	private float mStartX = -3.0f;// -6.3f;
	private int mDataCount;
	private int mCurrentPos = 0;

	public RendererSPO2(Context context, float width) {
		lock = 0;
		mTotalLength = width;
		mStartX = (mTotalLength / 2.0f) * -1.0f;
	}

	public void onDrawFrame(GL10 gl) {
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();

		gl.glTranslatef(0.0f, 0.0f, -3.0f);

		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);

		// Set line color to Cyan
		gl.glColor4f(0.0f, 1.0f, 1.0f, 1.0f);

		// Draw all lines
		int limit = mCurrentPos * 2;
		if (limit > mDataCount * 2 - 2)
			limit = mDataCount * 2 - 2;
		
		gl.glDrawElements(GL10.GL_LINE_STRIP, limit, GL10.GL_UNSIGNED_SHORT,
				mIndexBuffer);

		int newPos = (mCurrentPos + GAP_COUNT) * 2;
		if (newPos < mDataCount * 2 - 1) {
			mIndexBuffer.position(newPos);
			gl.glDrawElements(GL10.GL_LINE_STRIP, mIndexBuffer.remaining(), GL10.GL_UNSIGNED_SHORT,
					mIndexBuffer);
			mIndexBuffer.position(0);
		}
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glEnable(GL10.GL_LINE_SMOOTH);
		gl.glHint(GL10.GL_LINE_SMOOTH_HINT, GL10.GL_NICEST);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

		// Get all the buffers ready
		setAllBuffers();
	}

	public void onSurfaceChanged(GL10 gl, int width, int height) {
		gl.glViewport(0, 0, width, height);
		float aspect = (float) width / height;
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glFrustumf(-aspect, aspect, -1.0f, 1.0f, 1.0f, 10.0f);
	}

	private void setAllBuffers() {
		// Setup the vertex buffer
		mDataCount = (int)(mTotalLength / DATA_LENGTH);
		float vertexlist[] = new float[mDataCount * 3];
		float currentX = mStartX;

		for (int i = 0; i < mDataCount; ++i){
			vertexlist[i * 3] = currentX;
			vertexlist[i * 3 + 1] = 0.0f;
			currentX += DATA_LENGTH;
			vertexlist[i * 3 + 2] = 0.0f;
		};

		ByteBuffer vbb = ByteBuffer.allocateDirect(vertexlist.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		mVertexBuffer = vbb.asFloatBuffer();
		mVertexBuffer.put(vertexlist);
		mVertexBuffer.position(0);

		// Setup the index buffer
		short indexList[] = new short[mDataCount * 2 - 2];
		indexList[0] = 0;
		for (short i = 1; i < mDataCount - 1; ++i) {
			indexList[i * 2 - 1] = i;
			indexList[i * 2] = i;
		}
		indexList[mDataCount * 2 - 3] = (short) (mDataCount - 1);

		ByteBuffer ibb = ByteBuffer.allocateDirect(indexList.length * 2);
		ibb.order(ByteOrder.nativeOrder());
		mIndexBuffer = ibb.asShortBuffer();
		mIndexBuffer.put(indexList);
		mIndexBuffer.position(0);
	}

	public void addNewSample(float sample) {
		synchronized (lock) {
			mVertexBuffer.put(mCurrentPos * 3 + 1, sample);
			mCurrentPos++;
			if (mCurrentPos >= mDataCount)
				mCurrentPos -= mDataCount;
		}
	}
}
