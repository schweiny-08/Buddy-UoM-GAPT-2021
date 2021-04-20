using System;

public class Buildings
{
	public Buildings()
	{
		public int buildingId { get; set; }
		public int architectId { get; set; }
		public int locationId { get; set; }
		public string buildingName { get; set; }
		public string buildingDesc { get; set; }

		public virtual ICollection<Buildings> Buildings { get; set; }
	}
}
