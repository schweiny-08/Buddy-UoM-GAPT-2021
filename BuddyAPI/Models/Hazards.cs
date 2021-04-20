using System;

public class Hazards
{
	public Hazards()
	{
		public int hazardId { get; set; }
		public string hazardType { get; set; }

		public virtual ICollection<Hazards> Hazards { get; set; }
	}
}
