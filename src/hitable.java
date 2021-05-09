
interface hitable 
{
	public boolean hit(ray r, float t_min, float t_max, hit_record rec);
	public hit_record getRec();
}
