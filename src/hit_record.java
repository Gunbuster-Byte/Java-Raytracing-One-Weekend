
public class hit_record 
{
	float t;
	vec3 p;
	vec3 normal;
	material mat;
	
	public hit_record(float t, vec3 p, vec3 normal, material mat)
	{
		this.t = t;
		this.p = p;
		this.normal = normal;
		this.mat = mat;
	}
}
