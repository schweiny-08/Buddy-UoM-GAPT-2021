using System;

public class Locations
{
	public Locations()
	{
		public int locationId { get; set; }
		public int longitude { get; set; }
		public int latitude { get; set; }


		public virtual ICollection<Locations> Locations { get; set; }
	}
}
