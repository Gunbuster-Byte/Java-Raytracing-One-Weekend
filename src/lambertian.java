
public class lambertian implements material 
{

	vec3 albedo;
	vec3 attenuation;
	ray scattered;
	hit_record rec;
	
	public lambertian(vec3 a)
	{
		albedo = a;
	}
	@Override
	public boolean scatter(ray r_in, hit_record rec, vec3 attenuation, ray scattered) 
	{
		// TODO Auto-generated method stub
		vec3 target = vec3.add(vec3.add(rec.p, rec.normal), Main.random_in_unit_sphere());
		this.scattered = new ray(rec.p, vec3.sub(target, rec.p));
		this.attenuation = albedo;
		return true;
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
