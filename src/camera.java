
public class camera 
{
	vec3 origin;
	vec3 lower_left_corner;
	vec3 horizontal;
	vec3 vertical;
	vec3 u, v, w;
	float lens_radius;
	public camera(vec3 lookfrom, vec3 lookat, vec3 vup, float vfov, float aspect, float aperture, float focus_dist)
	{
		lens_radius = aperture / 2;
		float theta = vfov * (float) Math.PI/180;
		float half_height = (float) Math.tan(theta / 2);
		float half_width = aspect * half_height;
		origin = lookfrom;
		w = vec3.unit_vector(vec3.sub(lookfrom, lookat));
		u = vec3.unit_vector(vec3.cross(vup, w));
		v = vec3.cross(w, u);
		vec3 secondTerm = vec3.sub(u.scalar_mult(half_width * focus_dist * -1), v.scalar_mult(half_height * focus_dist));
		lower_left_corner = vec3.sub(vec3.add(origin, secondTerm), w.scalar_mult(focus_dist));
		vertical = v.scalar_mult(2 * half_height * focus_dist);
		horizontal = u.scalar_mult(2 * half_width * focus_dist);
	}
	public ray get_ray(float s, float t)
	{
		vec3 rd = random_in_unit_disk().scalar_mult(lens_radius);
		vec3 offset = vec3.add(u.scalar_mult(rd.x()), v.scalar_mult(rd.y()));
		vec3 first = vec3.sub(vec3.add(
				vec3.add(lower_left_corner, horizontal.scalar_mult(s)), vertical.scalar_mult(t))
				, origin);
		return new ray(vec3.add(origin, offset), vec3.sub(first, offset));
	}
	public vec3 random_in_unit_disk()
	{
		vec3 p;
		do
		{
			p = vec3.sub(new vec3((float) Math.random(), (float) Math.random(), 0), new vec3(1, 1, 0)).scalar_mult(2);
		}while(vec3.dot(p, p) >= 1.0f);
		
		return p;
	}

}
