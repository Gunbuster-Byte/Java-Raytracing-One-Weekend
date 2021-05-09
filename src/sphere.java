
public class sphere implements hitable
{
	vec3 center;
	float radius;
	hit_record rec;
	material mat;
	
	public sphere(vec3 center, float radius, material mat)
	{
		this.center = center;
		this.radius = radius;
		this.mat = mat;
	}
	@Override
	public boolean hit(ray r, float t_min, float t_max, hit_record rec) 
	{
		// TODO Auto-generated method stub
		vec3 oc = vec3.sub(r.origin(), center);
		float a = vec3.dot(r.direction(), r.direction());
		float b = vec3.dot(oc, r.direction());
		float c = vec3.dot(oc, oc) - radius * radius;
		float discriminant = b * b - a * c;

		if(discriminant > 0)
		{
			float temp = (-b - (float)Math.sqrt(b * b - a * c))/a;
			
			if(temp < t_max && temp > t_min)
			{
				rec.t = temp;
				rec.p = r.point_at_parameter(rec.t);
				rec.normal = vec3.sub(rec.p, center).scalar_mult(1/radius);
				rec.mat = mat;
				this.rec = rec;

				return true;
			}
			temp = (-b + (float)Math.sqrt(b * b - a * c))/a;
			if(temp < t_max && temp > t_min)
			{
				rec.t = temp;
				rec.p = r.point_at_parameter(rec.t);
				rec.normal = vec3.sub(rec.p, center).scalar_mult(1/radius);
				rec.mat = mat;
				this.rec = rec;

				return true;
			}
		}
		return false;
	}
	@Override
	public hit_record getRec() {
		// TODO Auto-generated method stub
		return rec;
	}
	
}
