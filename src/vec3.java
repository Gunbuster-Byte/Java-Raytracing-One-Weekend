
public class vec3 
{
	private float e[] = new float[3];
	
	public vec3()
	{
		
	}
	public vec3(float e0, float e1, float e2)
	{
		e[0] = e0;
		e[1] = e1;
		e[2] = e2;
	}
	public float x()
	{
		return e[0];
	}
	public float y()
	{
		return e[1];
	}
	public float z()
	{
		return e[2];
	}
	public float r()
	{
		return e[0];
	}
	public float g()
	{
		return e[1];
	}
	public float b()
	{
		return e[2];
	}
	
	public static vec3 add(vec3 v1, vec3 v2)
	{
		return new vec3(v1.x() + v2.x(), v1.y() + v2.y(), v1.z() + v2.z());
	}
	public static vec3 sub(vec3 v1, vec3 v2)
	{
		return new vec3(v1.x() - v2.x(), v1.y() - v2.y(), v1.z() - v2.z());
	}
	public static vec3 mult(vec3 v1, vec3 v2)
	{
		return new vec3(v1.x() * v2.x(), v1.y() * v2.y(), v1.z() * v2.z());
	}
	public static vec3 div(vec3 v1, vec3 v2)
	{
		return new vec3(v1.x() / v2.x(), v1.y() / v2.y(), v1.z() / v2.z());
	}
	
	public float length()
	{
		return (float) Math.sqrt(e[0]*e[0] + e[1]*e[1] + e[2]*e[2]);
	}
	public float squared_length()
	{
		return e[0]*e[0] + e[1]*e[1] + e[2]*e[2];
	}
	
	public static float dot(vec3 v1, vec3 v2)
	{
		return v1.x() * v2.x() + v1.y() * v2.y() + v1.z() * v2.z();
	}
	public static vec3 cross(vec3 v1, vec3 v2)
	{
		return new vec3((v1.y()*v2.z() - v1.z()*v2.y()), 
				-(v1.x()*v2.z() - v1.z()*v2.x()), 
				(v1.x()*v2.y() - v1.y()*v2.x()));
	}
	
	public static vec3 unit_vector(vec3 v)
	{
		return new vec3(v.x()/v.length(), v.y()/v.length(), v.z()/v.length());
	}
	
	public vec3 scalar_mult(float t)
	{
		return new vec3(e[0]*t, e[1]*t, e[2]*t);
	}
	
	public static vec3 reflect(vec3 v, vec3 n)
	{
		return vec3.sub(v, n.scalar_mult(vec3.dot(v, n)*2.0f));
	}
	
	//scratchapixel algo used here
	public static vec3 refract(vec3 v, vec3 n, float ior)
	{
		vec3 uv = vec3.unit_vector(v);
		
		float cosi = vec3.dot(uv, n);
		if(cosi > 1)
		{
			cosi = 1;
		}
		else if(cosi < -1)
		{
			cosi = -1;
		}
		
		float etai = 1, etat = ior;
		vec3 N = n;
		
		if(cosi < 0)
		{
			cosi = -cosi;
		}
		else
		{
			float temp = etai;
			etai = etat;
			etat = temp;
			N = n.scalar_mult(-1.0f);
		}
		float eta = etai/etat;
		float k = 1.0f - eta * eta * (1 - cosi * cosi);
		
		vec3 first = uv.scalar_mult(eta);
		vec3 second = N.scalar_mult(eta * cosi - (float) Math.sqrt(k));
		
		return k < 0 ? null : vec3.add(first, second);
	}
}
