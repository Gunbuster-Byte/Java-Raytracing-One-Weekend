
public interface material 
{
	public boolean scatter(ray r_in, hit_record rec, vec3 attenuation, ray scattered);
	public hit_record getRec();
	public ray getScattered();
	public vec3 getAtten();
}
