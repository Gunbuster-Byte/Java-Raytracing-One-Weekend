
public class hitable_list implements hitable
{
	int list_size;
	hitable objects[];
	hit_record rec;
	float closest_so_far;
	
	public hitable_list(hitable objects[], int n)
	{
		list_size = n;
		this.objects = objects;
	}

	@Override
	public boolean hit(ray r, float t_min, float t_max, hit_record rec) 
	{
		// TODO Auto-generated method stub
		boolean hit_anything = false;
		closest_so_far = t_max;
		for(int i = 0; i < list_size; i++)
		{
			if(objects[i].hit(r, t_min, closest_so_far, rec))
			{
				hit_anything = true;
				rec = objects[i].getRec();
				closest_so_far = rec.t;
				this.rec = rec;
			}
		}
		return hit_anything;
	}

	@Override
	public hit_record getRec() {
		// TODO Auto-generated method stub
		return rec;
	}

}
