
public class metal implements material
{
	vec3 albedo;
	vec3 attenuation;
	ray scattered;
	hit_record rec;
	float fuzz;
	
	public metal(vec3 a, float f)
	{
		albedo = a;
		
		if(f < 1)
		{
			fuzz = f;
		}
		else
		{
			fuzz = 1;
		}
	}
	@Override
	public boolean scatter(ray r_in, hit_record rec, vec3 attenuation, ray scattered) 
	{
		// TODO Auto-generated method stub
		vec3 reflected = vec3.reflect(vec3.unit_vector(r_in.direction()), rec.normal);
		scattered = new ray(rec.p, vec3.add(reflected, Main.random_in_unit_sphere().scalar_mult(fuzz)));
		this.scattered = scattered;
		this.attenuation = albedo;
		return ((vec3.dot(scattered.direction(), rec.normal)) > 0);
	}
	@Override
	public hit_record getRec() {
		// TODO Auto-generated method stub
		return rec;
	}
	@Override
	public ray getScattered() {
		// TODO Auto-generated method stub
		return scattered;
	}
	@Override
	public vec3 getAtten() {
		// TODO Auto-generated method stub
		return attenuation;
	}
}
