
public class ray 
{
	vec3 A, B;
	public ray()
	{
		
	}
	public ray(vec3 a, vec3 b)
	{
		A = a;
		B = b;
	}
	public vec3 origin()
	{
		return A;
	}
	public vec3 direction()
	{
		return B;
	}
	public vec3 point_at_parameter(float t)
	{
		return vec3.add(A, new vec3(B.x()*t, B.y()*t, B.z()*t));
	}

}
