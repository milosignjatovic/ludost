package com.ignja.gl.vo;

/**
 * Encapsulates camera-related properties, including view frustrum.
 */
public class CameraVo {
	public Number3d position = new Number3d(0,-8, 5f); // ... note, not 'managed'
	public Number3d target = new Number3d(0,0,0);
	public Number3d upAxis = new Number3d(0,0,-1);

	// TODO
	public Frustum frustum = new Frustum();

	
	public CameraVo()
	{
	}
}
