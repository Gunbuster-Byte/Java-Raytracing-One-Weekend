
public class dielectric implements material
{
	float ref_idx;
	hit_record rec;
	vec3 albedo;
	vec3 attenuation;
	ray scattered;

	public dielectric(float ri)
	{
		ref_idx = ri;
	}
	public float schlick(float cosine)
	{
		float r0 = (1 - ref_idx) / (1 + ref_idx);
		r0 = r0 * r0;
		return r0 + (1 - r0) * (float) Math.pow(1 - cosine, 5);
	}
	@Override
	public boolean scatter(ray r_in, hit_record rec, vec3 attenuation, ray scattered) 
	{
		// TODO Auto-generated method stub
		this.rec = rec;
		vec3 reflected = vec3.reflect(r_in.direction(), rec.normal);
		attenuation = new vec3(1.0f, 1.0f, 1.0f);
		this.attenuation = attenuation;
		vec3 refracted = new vec3(0, 0, 0);
		float cosine;
		float reflect_prob;
		
		if(vec3.dot(r_in.direction(), rec.normal) > 0)
		{
			cosine = ref_idx * vec3.dot(r_in.direction(), rec.normal) / r_in.direction().length();
		}
		else
		{
			cosine = -vec3.dot(r_in.direction(), rec.normal) / r_in.direction().length();
		}
		refracted = vec3.refract(r_in.direction(), rec.normal, ref_idx);
		if(refracted != null)
		{
			reflect_prob = schlick(cosine);
		}
		else
		{
			scattered = new ray(rec.p, reflected);
			this.scattered = scattered;
			reflect_prob = 1.0f;
		}
		if(Math.random() < reflect_prob)
		{
			scattered = new ray(rec.p, reflected);
			this.scattered = scattered;
		}
		else
		{
			scattered = new ray(rec.p, refracted);
			this.scattered = scattered;
		}
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
