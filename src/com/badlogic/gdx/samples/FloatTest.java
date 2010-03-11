package com.badlogic.gdx.samples;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Application.TextureFilter;
import com.badlogic.gdx.Application.TextureWrap;
import com.badlogic.gdx.backends.jogl.JoglApplication;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.RenderListener;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;

public class FloatTest implements RenderListener
{
	public static void main( String[] argv )
	{
		JoglApplication app = new JoglApplication( "Float Test", 480, 320 );
		app.addRenderListener( new FloatTest() );
	}

	Texture tex;
	FloatBuffer vertices;
	final int BYTES_PER_VERTEX = (4+3+2)*4;	
	
	@Override
	public void dispose(Application application) 
	{	
		
	}

	@Override
	public void render(Application app) 
	{			
		GL10 gl = app.getGL();
		gl.glClearColor( 0.7f, 0.7f, 0.7f, 1 );		
		gl.glClear( GL10.GL_COLOR_BUFFER_BIT );
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		vertices.position(0);
		gl.glColorPointer(4, GL10.GL_FLOAT, BYTES_PER_VERTEX, vertices );
		vertices.position(4);
		gl.glTexCoordPointer( 2, GL10.GL_FLOAT, BYTES_PER_VERTEX, vertices );
		vertices.position(6);
		gl.glVertexPointer( 3, GL10.GL_FLOAT, BYTES_PER_VERTEX, vertices );		
		gl.glDrawArrays( GL10.GL_TRIANGLES, 0, 3);			
	}

	@Override
	public void setup(Application application) 
	{
		ByteBuffer buffer = ByteBuffer.allocateDirect( BYTES_PER_VERTEX * 3 );
		buffer.order(ByteOrder.nativeOrder());
		vertices = buffer.asFloatBuffer();					
				
		float[] verts = { 							
						  1, 0, 0, 1,
						  0, 1, 
						  -0.5f, -0.5f, 0,
						  0, 1, 0, 1,
						  1, 1, 
						   0.5f, -0.5f, 0,
						   0, 0, 1, 1,
						   0.5f, 0, 
						   0, 0.5f, 0,
						 };
		vertices.put(verts);
		vertices.flip();	
		
		Pixmap pixmap = application.newPixmap(256, 256, Format.RGBA8888 );
		pixmap.setColor(1, 1, 1, 1 );
		pixmap.fill();
		pixmap.setColor(0, 0, 0, 1 );
		pixmap.drawLine(0, 0, 256, 256);
		pixmap.drawLine(256, 0, 0, 256);
		
		tex = application.newTexture( pixmap, TextureFilter.MipMap, TextureFilter.Linear, TextureWrap.ClampToEdge, TextureWrap.ClampToEdge );		
	}
}
