import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

public class Main extends JPanel
{
	private static BufferedImage canvas;
	
	public static vec3 random_in_unit_sphere()
	{
		vec3 p;
		do
		{
			p = new vec3((float) Math.random(), (float) Math.random(), (float) Math.random()).scalar_mult(2.0f);
			p = vec3.sub(p, new vec3(1,1,1));
		} while(vec3.dot(p, p) >= 1.0f);
		return p;
	}
	public static vec3 color(ray r, hitable world, int depth)
	{
		hit_record rec = new hit_record(0.0f, null, null, null);
		if(world.hit(r, 0.001f, Float.MAX_VALUE, rec))
		{
			rec = world.getRec();
			ray scattered = rec.mat.getScattered();
			vec3 attenuation = rec.mat.getAtten();
			if(depth < 50 && rec.mat.scatter(r, rec, attenuation, scattered))
			{
				scattered = rec.mat.getScattered();
				attenuation = rec.mat.getAtten();
				return vec3.mult(attenuation, color(scattered, world, depth + 1));
			}
			else
			{
				return new vec3(0, 0, 0);
			}
		}
		else
		{
			vec3 unit_direction = vec3.unit_vector(r.direction());
			float t = (float) (0.5 * (unit_direction.y() + 1.0));
			return vec3.add(new vec3(1.0f, 1.0f, 1.0f).scalar_mult(1.0f - t),
					new vec3(0.5f, 0.7f, 1.0f).scalar_mult(t));
		}
	}
	public static void main(String args[])
	{
		int nx = 1920;
		int ny = 1080;
			
		//Frame stuff
		JFrame n = new JFrame("Render Screen");
		n.setSize(nx, ny);
	    
		n.addWindowListener(new WindowAdapter() 
	    {
	          public void windowClosing(WindowEvent windowEvent)
	          {
	             System.exit(0);
	          } 
	    });
	    n.setVisible(true);    
	    Main drawing = new Main();
	    n.add(drawing);
		canvas = new BufferedImage(nx, ny, BufferedImage.TYPE_INT_RGB);

		System.out.println("P3\n" + nx + " " + ny + "\n255");

		hitable list[] = new hitable[4];
		list[0] = new sphere(new vec3(0, 0, -1), 0.5f, new lambertian(new vec3(0.1f, 0.2f, 0.5f)));
		list[1] = new sphere(new vec3(0, -100.5f, -1), 100, new lambertian(new vec3(0.8f, 0.8f, 0)));
		list[2] = new sphere(new vec3(1, 0, -1), 0.5f, new metal(new vec3(0.8f, 0.6f, 0.2f), 0.3f));
		list[3] = new sphere(new vec3(-1, 0, -1), 0.5f, new dielectric(1.5f));
		hitable world = random_scene();
		int ns = 100;
		
		vec3 lookfrom = new vec3(5, 1, 6);
		vec3 lookat = new vec3(0, 0, -1);
		float dist_to_focus = vec3.sub(lookfrom, lookat).length();
		float aperture = 0.3f;
		camera cam = new camera(lookfrom, lookat, new vec3(0, 1, 0), 40, (float) nx / (float) ny, aperture, dist_to_focus);
		
		for(int j = ny-1; j >= 0; j--)
		{
			for(int i = 0; i < nx; i++)
			{
				vec3 col = new vec3(0,0,0);
				for(int s = 0; s < ns; s++)
				{
					float u = (float) (i + Math.random()) / (float) nx;
					float v = (float) (j + Math.random()) / (float) ny;
					ray r = cam.get_ray(u, v);
					vec3 p = r.point_at_parameter(2.0f);
					col = vec3.add(col, color(r, world, 0));
				}
				col = col.scalar_mult(1.0f/(float)ns);
				col = new vec3((float)Math.sqrt(col.x()), (float)Math.sqrt(col.y()), (float)Math.sqrt(col.z()));
				int ir = (int) ((int) 255.99 * col.x());
				int ig = (int) ((int) 255.99 * col.y());
				int ib = (int) ((int) 255.99 * col.z());

				canvas.setRGB(i, ny - (j + 1), new Color(ir, ig, ib).getRGB());
				n.repaint();
				System.out.println(ir + " " + ig + " " + ib);
			}
		}
	}
	public void paint(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(canvas, null, null);
	}
	
	static hitable random_scene()
	{
		int n = 500;
		hitable list[] = new hitable[n + 1];
		list[0] = new sphere(new vec3(0, -1000, 0), 1000, new lambertian(new vec3(0.5f, 0.5f, 0.5f)));
		int i = 1;
		for(int a = -11; a < 11; a++)
		{
			for(int b = -11; b < 11; b++)
			{
				float choose_mat = (float) Math.random();
				vec3 center = new vec3((float) (a + 0.9*Math.random()), 0.2f, (float)(b + 0.9*Math.random()));
				if(vec3.sub(center, new vec3(4, 0.2f, 0)).length() > 0.9)
				{
					if(choose_mat < 0.8f)
					{
						list[i++] = new sphere(center, 0.2f, new lambertian(new vec3((float) (Math.random()*Math.random()), (float) (Math.random()*Math.random()), (float) (Math.random()*Math.random()))));
					}
					else if(choose_mat < 0.95f)
					{
						list[i++] = new sphere(center, 0.2f, new metal(new vec3((float) (0.5 * (1 + Math.random())), (float) (0.5 * (1 + Math.random())), (float) (0.5 * (1 + Math.random()))), (float) (0.5 * Math.random())));
					}
					else
					{
						list[i++] = new sphere(center, 0.2f, new dielectric(1.5f));
					}
				}
			}
		}
		list[i++] = new sphere(new vec3(0, 1, 0), 1, new dielectric(1.5f));
		list[i++] = new sphere(new vec3(-4, 1, 0), 1, new lambertian(new vec3(0.4f, 0.2f, 0.1f)));
		list[i++] = new sphere(new vec3(4, 1, 0), 1, new metal(new vec3(0.7f, 0.6f, 0.5f), 0));

		return new hitable_list(list, i);
	}

}
