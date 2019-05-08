package com.ignja.gl.vo;

import com.ignja.gl.util.Utils;

import java.nio.FloatBuffer;

/**
 * Light must be added to Scene to take effect.
 * 
 * Eg, "scene.lights().add(myLight);"  
 */
public class Light
{
	/**
	 * Position is relative to eye space, not world space.
	 */
	public Number3d 		position;
	
	/**
	 * Direction is a vector and should be normalized.
	 */
	public Number3d 		direction;
	
	public Color4 		ambient;
	public Color4 		diffuse;
	public Color4 		specular;
	public Color4 		emissive;
	
	private LightType			_type;

	public Light() {
		
		 ambient = new Color4(128,128,128, 255);
		 diffuse = new Color4(255,255,255, 255);
		 specular = new Color4(0,0,0,255);
		 emissive = new Color4(0,0,0,255);
		 
		 position = new Number3d(0f, 0f, 1f);
		 
		 direction = new Number3d(0f, 0f, -1f);
		 //_spotCutoffAngle = new FloatManaged(180, this);
		 //_spotExponent = new FloatManaged(0f, this);
		 
		 _attenuation = new Number3d(1f,0f,0f);
		 
		 _type = LightType.DIRECTIONAL;								
		 
		 _isVisible = true;
		 
		 _positionAndTypeBuffer = Utils.makeFloatBuffer4(0,0,0,0);

	}

	public boolean isVisible()
	{
		return _isVisible;
	}
	public void isVisible(boolean b)
	{
		_isVisible = b;
	}
	
	/**
	 * Default is DIRECTIONAL, matching OpenGL's default value.
	 */
	public LightType type()
	{
		return _type;
	}
	
	public void type(LightType $type)
	{
		_type = $type;
	}
	
	/**
	 * 0 = no attenuation towards edges of spotlight. Max is 128.
	 * Default is 0, matching OpenGL's default value.
	 */
//	public float spotExponent()
//	{
//		return _spotExponent.get();
//	}
//	public void spotExponent(float $f)
//	{
//		if ($f < 0) $f = 0;
//		if ($f > 128) $f = 128;
//		_spotExponent.set($f);
//	}
	
	/**
	 * Legal range is 0 to 90, plus 180, which is treated by OpenGL to mean no cutoff.
	 * Default is 180, matching OpenGL's default value.
	 */
//	public float spotCutoffAngle()
//	{
//		return _spotCutoffAngle.get();
//	}
//	public void spotCutoffAngle(Float $f)
//	{
//		if ($f < 0)
//			_spotCutoffAngle.set(0);
//		else if ($f <= 90)
//			_spotCutoffAngle.set($f);
//		else if ($f == 180)
//			_spotCutoffAngle.set($f);
//		else
//			_spotCutoffAngle.set(90);
//	}
	
	/**
	 * No cutoff angle (ie, no spotlight effect)
	 * (represented internally with a value of 180)
	 */
//	public void spotCutoffAngleNone()
//	{
//		_spotCutoffAngle.set(180);
//	}
//
//	public float attenuationConstant()
//	{
//		return _attenuation.getX();
//	}
//	public void attenuationConstant(float $normalizedValue)
//	{
//		_attenuation.setX($normalizedValue);
//		setDirtyFlag();
//	}
//
//	public float attenuationLinear()
//	{
//		return _attenuation.getY();
//	}
//	public void attenuationLinear(float $normalizedValue)
//	{
//		_attenuation.setY($normalizedValue);
//		setDirtyFlag();
//	}
//
//	public float attenuationQuadratic()
//	{
//		return _attenuation.getZ();
//	}
//	public void attenuationQuadratic(float $normalizedValue)
//	{
//		_attenuation.setZ($normalizedValue);
//		setDirtyFlag();
//	}
//
//	/**
//	 * Defaults are 1,0,0 (resulting in no attenuation over distance),
//	 * which match OpenGL default values.
//	 */
//	public void attenuationSetAll(float $constant, float $linear, float $quadratic)
//	{
//		_attenuation.setAll($constant, $linear, $quadratic);
//		setDirtyFlag();
//	}

	//
	
	/**
	 * Used by Renderer
	 * Normal clients of this class should use "isVisible" getter/setter.
	 */
	public boolean _isVisible;

	/**
	 * Used by Renderer
	 */
	public FloatBuffer _positionAndTypeBuffer;

	/**
	 * Used by Renderer
	 */
	public void commitPositionAndTypeBuffer()
	{
		// GL_POSITION takes 4 arguments, the first 3 being x/y/z position, 
		// and the 4th being what we're calling 'type' (positional or directional)
		
		_positionAndTypeBuffer.position(0);
		_positionAndTypeBuffer.put(position.x);
		_positionAndTypeBuffer.put(position.y);
		_positionAndTypeBuffer.put(position.z);
		_positionAndTypeBuffer.put(_type.glValue());
		_positionAndTypeBuffer.position(0);
	}
	
	/**
	 * Used by Renderer. 
	 * Normal clients of this class should use "useSpotProperties" getter/setter.
	 */
	//public FloatManaged _spotExponent;

	/**
	 * Used by Renderer. Normal clients of this class should use "useSpotProperties" getter/setter.
	 */
	//public FloatManaged _spotCutoffAngle;
	
	/**
	 * Used by Renderer. Normal clients of this class should use attenuation getter/setters.
	 */
	public Number3d _attenuation; // (the 3 properties of N3D used for the 3 attenuation properties)
}
